package MeshX.HypeLink.direct_strore.order.controller;


import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.direct_strore.order.model.dto.request.DirectOrderCreateReq;
import MeshX.HypeLink.direct_strore.order.model.dto.request.DirectOrderUpdateReq;
import MeshX.HypeLink.direct_strore.order.model.dto.response.DirectOrderInfoListRes;
import MeshX.HypeLink.direct_strore.order.model.dto.response.DirectOrderInfoRes;
import MeshX.HypeLink.direct_strore.order.service.DirectOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/direct-store/order")
@RequiredArgsConstructor
public class DirectOrderController {
    private final DirectOrderService directOrderService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<String>> createOrder(@RequestBody DirectOrderCreateReq dto) {
        directOrderService.createOrder(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("가맹점 발주가 생성되었습니다."));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<String>> deleteOrder(@PathVariable Integer id) {
        directOrderService.delete(id);
        return ResponseEntity.status(200).body(BaseResponse.of("가맹점 발주가 성공적으로 삭제 되었습니다."));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<BaseResponse<DirectOrderInfoRes>> readOrder(@PathVariable Integer id) {
        DirectOrderInfoRes directOrderInfoRes = directOrderService.readDetails(id);
        return ResponseEntity.status(200).body(BaseResponse.of(directOrderInfoRes));
    }

    @GetMapping("/read/all")
    public ResponseEntity<BaseResponse<DirectOrderInfoListRes>> readAllOrders() {
        DirectOrderInfoListRes directOrderInfoListRes = directOrderService.readList();
        return ResponseEntity.status(200).body(BaseResponse.of(directOrderInfoListRes));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<BaseResponse<DirectOrderInfoRes>> updateOrder(@PathVariable Integer id,
                                                                        @RequestBody DirectOrderUpdateReq dto) {
        DirectOrderInfoRes directOrderInfoRes = directOrderService.update(id, dto.getItemName(), dto.getUnitPrice(), dto.getQuantity(), dto.getTotalPrice(), dto.getDeliveryAddress(), dto.getDeliveryRequest(), dto.getOrderRequest());
        return ResponseEntity.status(200).body(BaseResponse.of(directOrderInfoRes));
    }


}
