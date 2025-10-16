package MeshX.HypeLink.head_office.shipment.controller;

import MeshX.HypeLink.head_office.shipment.model.dto.DriverDeliveryCompleteDto;
import MeshX.HypeLink.head_office.shipment.model.dto.DriverGpsDto;
import MeshX.HypeLink.head_office.shipment.model.dto.DriverLocationDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@Tag(name = "실시간 배송 현황 (WebSocket)", description = "WebSocket을 사용하여 배송 기사의 실시간 위치 정보 및 배송 상태를 중계하는 API")
@Slf4j
@RestController
@RequiredArgsConstructor
public class ShipmentGPSController {
    private final SimpMessagingTemplate messagingTemplate;
    // 목업용
    private final Random random = new Random();

    @Operation(summary = "배송 기사 GPS 정보 수신 및 중계", description = "STOMP /pub/gps. 배송 기사 클라이언트로부터 위도, 경도 정보를 수신하여 /topic/dashboard를 구독하는 모든 클라이언트에게 중계합니다.")
    @MessageMapping("/gps")
    public void getGPSData (@Payload DriverGpsDto dto) {
        DriverLocationDto driver1 = createMockDriver(dto.getDriverId(), "김테스트 기사", dto.getLat(), dto.getLng());
        // 배송기사 데이터베이스 찾아서 이름 찾아서 정리하기 + HashMap 같은 곳에 저장하기?
        messagingTemplate.convertAndSend("/topic/dashboard", driver1);
    }

    @Operation(summary = "배송 완료 상태 수신", description = "STOMP /pub/delivery-complete. 배송 기사 클라이언트로부터 배송 완료 신호를 수신하여 관련 상태를 업데이트합니다.")
    @MessageMapping("/delivery-complete")
    public void completeDelivery (@Payload DriverDeliveryCompleteDto dto) {
        // StoreId를 이용해서 데이터를 확인한 후, 배송 완료 처리 및 DashBoard 처리
    }

    // Mock 데이터 전송 (5초마다)
    @Scheduled(fixedRate = 5000)
    public void sendMockGPSData() {
        // 기사 3명 가정
        DriverLocationDto driver1 = createMockDriver("D001", "홍길동", 37.5665, 126.9780);
        DriverLocationDto driver2 = createMockDriver("D002", "김철수", 37.5700, 126.9820);
        DriverLocationDto driver3 = createMockDriver("D003", "이영희", 37.5600, 126.9750);

        messagingTemplate.convertAndSend("/topic/dashboard", driver1);
        messagingTemplate.convertAndSend("/topic/dashboard", driver2);
        messagingTemplate.convertAndSend("/topic/dashboard", driver3);
    }

    private DriverLocationDto createMockDriver(String id, String name, double baseLat, double baseLng) {
        double lat = baseLat + (random.nextDouble() - 0.5) * 0.01; // ±0.005 ≈ 약 500m 반경
        double lng = baseLng + (random.nextDouble() - 0.5) * 0.01;
        String status = random.nextBoolean() ? "배송중" : "지연";

        // 예시 출발지/도착지/품목 Mock 목록
        String[] fromList = { "서울 본사", "부산 물류센터", "대전 물류센터" };
        String[] toList = { "HypeLink 강남점", "HypeLink 홍대점", "HypeLink 부산점" };
        String[] itemList = { "Hype-Tee", "Link-Pants", "Hyper-Jacket", "Mesh-Cap" };

        String from = fromList[random.nextInt(fromList.length)];
        String to = toList[random.nextInt(toList.length)];
        String item = itemList[random.nextInt(itemList.length)];
        int qty = 10 + random.nextInt(91); // 10~100 사이 랜덤 수량

        return DriverLocationDto.builder()
                .driverId(id)
                .name(name)
                .from(from)
                .to(to)
                .item(item)
                .qty(qty)
                .latitude(lat)
                .longitude(lng)
                .status(status)
                .build();
    }

}
