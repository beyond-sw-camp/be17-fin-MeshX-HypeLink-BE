package MeshX.HypeLink.head_office.shipment.controller;

import MeshX.HypeLink.head_office.shipment.model.dto.DriverDeliveryCompleteDto;
import MeshX.HypeLink.head_office.shipment.model.dto.DriverGpsDto;
import MeshX.HypeLink.head_office.shipment.model.dto.DriverLocationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ShipmentGPSController {
    private final SimpMessagingTemplate messagingTemplate;
    // 목업용
    private final Random random = new Random();
    /** 기사들의 현재 위치 (좌표만 유지) */
    private final Map<String, double[]> driverPositions = new HashMap<>();

    /** 매장 좌표 (도착지) */
    private static final double[][] STORE_COORDS = {
            {37.5665, 126.9780}, // 가맹점주1 - 서울
            {35.1796, 129.0756}, // 가맹점주2 - 부산
            {36.3504, 127.3845}, // 가맹점주3 - 대전
            {35.1595, 126.8526}, // 가맹점주4 - 광주
            {33.4996, 126.5312}  // 가맹점주5 - 제주
    };

    /** 기사 초기화 */
    private void initializeDrivers() {
        if (driverPositions.isEmpty()) {
            double baseLat = 37.3943; // 판교
            double baseLng = 127.1111;

            for (int i = 0; i < STORE_COORDS.length; i++) {
                String id = String.format("D%03d", i + 1);
                driverPositions.put(id, new double[]{baseLat, baseLng});
            }
        }
    }

    /** 5초마다 새로운 위치 DTO를 만들어 송신 */
    @Scheduled(fixedRate = 5000)
    public void sendMockGPSData() {
        initializeDrivers();

        driverPositions.forEach((driverId, pos) -> {
            int idx = Integer.parseInt(driverId.substring(1)) - 1;
            double[] target = STORE_COORDS[idx];
            double lat = pos[0];
            double lng = pos[1];

            // 도착 여부
            double distance = Math.sqrt(Math.pow(target[0] - lat, 2) + Math.pow(target[1] - lng, 2));

            String status;
            double nextLat, nextLng;

            if (distance < 0.01) { // 도착
                status = "완료";
                nextLat = lat;
                nextLng = lng;
            } else {
                // 매장 방향으로 이동
                double step = 0.02;
                double ratio = step / distance;
                if (ratio > 1) ratio = 1;

                nextLat = lat + (target[0] - lat) * ratio + (random.nextDouble() - 0.5) * 0.002;
                nextLng = lng + (target[1] - lng) * ratio + (random.nextDouble() - 0.5) * 0.002;
                status = random.nextDouble() > 0.9 ? "지연" : "배송중";
            }

            // 새 좌표 갱신
            driverPositions.put(driverId, new double[]{nextLat, nextLng});

            // 새 DTO 생성 (불변 객체)
            DriverLocationDto dto = DriverLocationDto.builder()
                    .driverId(driverId)
                    .name("기사" + driverId.substring(1))
                    .from("판교 물류센터")
                    .to("가맹점주" + (idx + 1))
                    .item(randomItem())
                    .qty(10 + random.nextInt(91))
                    .latitude(nextLat)
                    .longitude(nextLng)
                    .status(status)
                    .build();

            messagingTemplate.convertAndSend("/topic/dashboard", dto);
        });
    }

    @MessageMapping("/gps")
    public void getGPSData (@Payload DriverGpsDto dto) {
        DriverLocationDto driver1 = createMockDriver(dto.getDriverId(), "김테스트 기사", dto.getLat(), dto.getLng());
        // 배송기사 데이터베이스 찾아서 이름 찾아서 정리하기 + HashMap 같은 곳에 저장하기?
        messagingTemplate.convertAndSend("/topic/dashboard", driver1);
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

    // 단일 수동 테스트용
    private DriverLocationDto createMockDriver(String id, String name, double lat, double lng) {
        return DriverLocationDto.builder()
                .driverId(id)
                .name(name)
                .from("판교 물류센터")
                .to("가맹점 테스트")
                .item(randomItem())
                .qty(50)
                .latitude(lat)
                .longitude(lng)
                .status("배송중")
                .build();
    }

    private String randomItem() {
        String[] items = { "Hype-Tee", "Link-Pants", "Hyper-Jacket", "Mesh-Cap" };
        return items[random.nextInt(items.length)];
    }
}
