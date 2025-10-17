package MeshX.HypeLink.head_office.shipment.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.head_office.shipment.model.dto.ConnectingReqDto;
import MeshX.HypeLink.head_office.shipment.model.dto.CreateParcelReqDto;
import MeshX.HypeLink.head_office.shipment.model.entity.Parcel;
import MeshX.HypeLink.head_office.shipment.model.entity.Shipment;
import MeshX.HypeLink.head_office.shipment.service.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/shipment")
@RequiredArgsConstructor
public class ShipmentController {

    private final ShipmentService shipmentService;

    // 프론트 부재
    @PostMapping("/create")
    public ResponseEntity<BaseResponse<Parcel>> create(CreateParcelReqDto dto) {
        Parcel result = shipmentService.create(dto);
        return  ResponseEntity.ok(BaseResponse.of(result));
    }


    // 기사 -> Parcel(여러개)
    @PostMapping("/connecting")
    public ResponseEntity<BaseResponse<Shipment>> connecting(ConnectingReqDto dto) {
        Shipment result = shipmentService.connetcting(dto);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

}
