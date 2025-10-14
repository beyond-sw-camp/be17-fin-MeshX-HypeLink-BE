package MeshX.HypeLink.head_office.shipment.controller;

import MeshX.HypeLink.head_office.shipment.model.dto.DriverLocationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ShipmentGPSController {
    private final SimpMessagingTemplate messagingTemplate;
    // 목업용
    private final Random random = new Random();

    @MessageMapping("/gps")
    public void getGPSData (@Payload String message) {
        log.info("GPS : {}", message);
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

        log.info("Sent Mock GPS data: {}, {}, {}", driver1, driver2, driver3);
    }

    private DriverLocationDto createMockDriver(String id, String name, double baseLat, double baseLng) {
        double lat = baseLat + (random.nextDouble() - 0.5) * 0.01; // ±0.005 ~ 약 500m 반경
        double lng = baseLng + (random.nextDouble() - 0.5) * 0.01;
        String status = random.nextBoolean() ? "배송중" : "지연";

        return DriverLocationDto.builder()
                .driverId(id)
                .name(name)
                .latitude(lat)
                .longitude(lng)
                .status(status)
                .build();
    }
}
