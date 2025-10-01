package MeshX.HypeLink.head_office.shipment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ShipmentGPSController {
    @MessageMapping("/gps")
    public void getGPSData (@Payload String message) {
        log.info("GPS : {}", message);
    }
}
