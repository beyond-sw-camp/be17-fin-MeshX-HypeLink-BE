package com.example.apiauth.adapter.out.external.shipment;

import MeshX.common.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "monolith", url = "http://localhost:8079", path = "/api/shipment")
public interface ShipmentClient {

    @GetMapping("/driver/{driverId}/has-active")
    BaseResponse<Boolean> hasActiveShipments(@PathVariable("driverId") Integer driverId);
}
