package MeshX.HypeLink.head_office.shipment.controller;

import MeshX.HypeLink.auth.model.entity.Driver;
import MeshX.HypeLink.auth.model.entity.Store;
import MeshX.HypeLink.auth.repository.DriverJpaRepositoryVerify;
import MeshX.HypeLink.auth.repository.StoreJpaRepositoryVerify;
import MeshX.HypeLink.head_office.item.model.entity.ItemDetail;
import MeshX.HypeLink.head_office.order.model.entity.PurchaseOrder;
import MeshX.HypeLink.head_office.shipment.model.dto.DriverDeliveryCompleteDto;
import MeshX.HypeLink.head_office.shipment.model.dto.DriverGpsDto;
import MeshX.HypeLink.head_office.shipment.model.dto.DriverLocationDto;
import MeshX.HypeLink.head_office.shipment.model.entity.Parcel;
import MeshX.HypeLink.head_office.shipment.model.entity.ParcelItem;
import MeshX.HypeLink.head_office.shipment.model.entity.Shipment;
import MeshX.HypeLink.head_office.shipment.model.entity.ShipmentStatus;
import MeshX.HypeLink.head_office.shipment.repository.ParcelJpaRepositoryVerify;
import MeshX.HypeLink.head_office.shipment.repository.ShipmentJpaRepositoryVerify;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Tag(name = "배송 GPS 추적", description = "배송 기사 실시간 위치 추적 WebSocket API")
@Slf4j
@RestController
@RequiredArgsConstructor
public class ShipmentGPSController {
    private final SimpMessagingTemplate messagingTemplate;
    private final StoreJpaRepositoryVerify storeRepository;
    private final DriverJpaRepositoryVerify driverRepository;
    private final ShipmentJpaRepositoryVerify shipmentRepository;

    private final Random random = new Random();

    /** 현재 기사 위치 */
    private final Map<Integer, double[]> driverPositions = new HashMap<>();

    /** 매장 좌표 리스트 (DB 기반) */
    private List<double[]> storeCoords = new ArrayList<>();

    /** 기사 → 매장 인덱스 매핑 */
    private final Map<Integer, Integer> driverToStoreIndex = new HashMap<>();
    private Map<Integer, Driver> driverList = new HashMap<>();
    private Map<Integer, String> storeName = new HashMap<>();

    /** 최초 1회 초기화 */
    private boolean initialized = false;

    /** 초기 출발지 (판교) */
    private static final double BASE_LAT = 37.3943;
    private static final double BASE_LNG = 127.1111;


    // ---------------------------------------------------
    // 1) DB 기반 기사/매장 로딩 + 위치 초기화
    // ---------------------------------------------------
    private void initializeDriversAndStores() {
        if (initialized) return;   // 최초 1회만

        // ----- 매장 좌표 로딩 -----
        List<Store> stores = storeRepository.findAll();
        storeName = IntStream.range(0, stores.size())
                .boxed()
                .collect(Collectors.toMap(
                        i -> i,
                        i -> stores.get(i).getMember().getName()
                ));
        storeCoords = stores.stream()
                .map(s -> new double[]{s.getLat(), s.getLon()})
                .toList();   // Java 17+

        // ----- 기사 로딩 -----
        List<Driver> drivers = driverRepository.findAll();
        driverList = IntStream.range(0, drivers.size())
                .boxed()
                .collect(Collectors.toMap(
                        i -> drivers.get(i).getId(),
                        drivers::get
                ));

        int storeCnt = storeCoords.size();
        int driverCnt = drivers.size();

        if (storeCnt == 0 || driverCnt == 0) {
            log.warn("⚠️ 매장 또는 기사 데이터 없음. GPS Mocking 중단됨.");
            return;
        }

        int idx = 0;
        for (Driver driver : drivers) {
            Integer driverId = driver.getId();  // 기사 고유 ID 사용

            // 기사 초기 좌표 (판교)
            driverPositions.put(driverId, new double[]{BASE_LAT, BASE_LNG});

            // 기사별 타겟 매장 라운드로빈 매핑
            driverToStoreIndex.put(driverId, idx % storeCnt);
            idx++;
        }

        initialized = true;
        log.info("GPS Mocking 초기화 완료 — 기사 {}명, 매장 {}개", driverCnt, storeCnt);
    }

    @Scheduled(fixedRate = 1000000)
//    @Scheduled(fixedRate = 50000)
    public void reset() {
        driverPositions.clear();
        driverToStoreIndex.clear();
        driverList.clear();
        storeName.clear();
        storeCoords = new ArrayList<>();

        initialized = false;
        initializeDriversAndStores();

        log.info("🔄 GPS Mocking 리셋 완료 (16분 40초마다)");
    }

    // ---------------------------------------------------
    // 2) 5초마다 위치 이동 및 WebSocket 전송
    // ---------------------------------------------------
    @Scheduled(fixedRate = 5000)
    public void sendMockGPSData() {

        initializeDriversAndStores();

        driverPositions.forEach((driverId, pos) -> {

            int storeIndex = driverToStoreIndex.get(driverId); // 기사-매장 매핑
            double[] target = storeCoords.get(storeIndex);

            double lat = pos[0];
            double lng = pos[1];

            // 거리 계산
            double distance = Math.sqrt(
                    Math.pow(target[0] - lat, 2) +
                            Math.pow(target[1] - lng, 2)
            );

            String status;
            double nextLat, nextLng;

            if (distance < 0.01) {
                // 도착
                status = "완료";
                nextLat = lat;
                nextLng = lng;
            } else {
                // 이동 스텝
                double step = 0.02;
                double ratio = Math.min(step / distance, 1);

                nextLat = lat + (target[0] - lat) * ratio + (random.nextDouble() - 0.5) * 0.002;
                nextLng = lng + (target[1] - lng) * ratio + (random.nextDouble() - 0.5) * 0.002;

                status = random.nextDouble() > 0.9 ? "지연" : "배송중";
            }

            // 위치 갱신
            driverPositions.put(driverId, new double[]{nextLat, nextLng});

            List<Shipment> shipments = shipmentRepository.findByDriverAndShipmentStatus(driverList.get(driverId), ShipmentStatus.IN_PROGRESS);
            String itemCode = getItemDetailCodeOrDefault(shipments);
            // -------- DTO 생성 --------
            DriverLocationDto dto = DriverLocationDto.builder()
                    .driverId(driverList.get(driverId).getMember().getName())
                    .name("기사-" + driverId)
                    .from("판교 물류센터")
                    .to(storeName.get(storeIndex))
                    .item(itemCode)
                    .qty(shipments.size())
                    .latitude(nextLat)
                    .longitude(nextLng)
                    .status(status)
                    .build();

            // WebSocket 전송
            messagingTemplate.convertAndSend("/topic/dashboard", dto);
        });
    }

    private String getItemDetailCodeOrDefault(List<Shipment> shipments) {
        if (shipments == null || shipments.isEmpty()) return "TRK-000001";

        Shipment s = shipments.get(0);
        if (s.getParcel() == null) return "TRK-000001";

        return s.getParcel().getTrackingNumber();
    }

    @MessageMapping("/gps")
    public void getGPSData (@Payload DriverGpsDto dto) {
//        DriverLocationDto driver1 = createMockDriver(dto.getDriverId(), "김테스트 기사", dto.getLat(), dto.getLng());
        // 배송기사 데이터베이스 찾아서 이름 찾아서 정리하기 + HashMap 같은 곳에 저장하기?
//        messagingTemplate.convertAndSend("/topic/dashboard", driver1);
    }

    @MessageMapping("/delivery-complete")
    public void completeDelivery (@Payload DriverDeliveryCompleteDto dto) {
        // StoreId를 이용해서 데이터를 확인한 후, 배송 완료 처리 및 DashBoard 처리
    }

//    // Mock 데이터 전송 (5초마다)
//    @Scheduled(fixedRate = 5000)
//    public void sendMockGPSData() {
//        // 기사 3명 가정
//        DriverLocationDto driver1 = createMockDriver("D001", "홍길동", 37.5665, 126.9780);
//        DriverLocationDto driver2 = createMockDriver("D002", "김철수", 37.5700, 126.9820);
//        DriverLocationDto driver3 = createMockDriver("D003", "이영희", 37.5600, 126.9750);
//
//        messagingTemplate.convertAndSend("/topic/dashboard", driver1);
//        messagingTemplate.convertAndSend("/topic/dashboard", driver2);
//        messagingTemplate.convertAndSend("/topic/dashboard", driver3);
//    }
}
