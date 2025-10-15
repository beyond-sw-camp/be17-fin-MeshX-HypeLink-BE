package MeshX.HypeLink.direct_store.posOrder.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.direct_store.posOrder.model.dto.response.PosOrderDetailRes;
import MeshX.HypeLink.direct_store.posOrder.model.dto.response.PosOrderInfoRes;
import MeshX.HypeLink.direct_store.posOrder.service.PosOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class PosOrderController {
    private final PosOrderService orderService;


    @GetMapping("/list")
    public ResponseEntity<BaseResponse<List<PosOrderInfoRes>>> readOrders() {
        List<PosOrderInfoRes> response = orderService.readAllOrders();
        return ResponseEntity.ok(BaseResponse.of(response, "전체 주문 조회 완료"));
    }

    @GetMapping("/today")
    public ResponseEntity<BaseResponse<List<PosOrderInfoRes>>> readTodayOrders() {
        List<PosOrderInfoRes> response = orderService.readTodayOrders();
        return ResponseEntity.ok(BaseResponse.of(response, "오늘 주문 조회 완료"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<PosOrderDetailRes>> readOrderDetail(@PathVariable Integer id) {
        PosOrderDetailRes response = orderService.readOrderDetail(id);
        return ResponseEntity.ok(BaseResponse.of(response, "주문 상세 조회 완료"));
    }

    @GetMapping("/number/{orderNumber}")
    public ResponseEntity<BaseResponse<PosOrderDetailRes>> readOrderByOrderNumber(@PathVariable String orderNumber) {
        PosOrderDetailRes response = orderService.readOrderByOrderNumber(orderNumber);
        return ResponseEntity.ok(BaseResponse.of(response, "주문 번호로 조회 완료"));
    }
}
