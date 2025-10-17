package MeshX.HypeLink.head_office.order.controller;


import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.common.Page.PageReq;
import MeshX.HypeLink.common.Page.PageRes;
import MeshX.HypeLink.head_office.order.model.dto.request.HeadOrderCreateReq;
import MeshX.HypeLink.head_office.order.model.dto.request.HeadOrderUpdateReq;
import MeshX.HypeLink.head_office.order.model.dto.response.HeadOrderInfoListRes;
import MeshX.HypeLink.head_office.order.model.dto.response.HeadOrderInfoRes;
import MeshX.HypeLink.head_office.order.service.HeadOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/head-office/order")
@RequiredArgsConstructor
public class HeadOrderController {
    private final HeadOrderService headOrderService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<String>> createOrder(@RequestBody HeadOrderCreateReq dto) {
        headOrderService.createOrder(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("본사 발주가 생성되었습니다."));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<String>> deleteOrder(@PathVariable Integer id) {
        headOrderService.delete(id);
        return ResponseEntity.status(200).body(BaseResponse.of("본사 발주가 성공적으로 삭제 되었습니다."));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<BaseResponse<HeadOrderInfoRes>> readOrder(@PathVariable Integer id) {
        HeadOrderInfoRes headOrderInfoRes = headOrderService.readDetails(id);
        return ResponseEntity.status(200).body(BaseResponse.of(headOrderInfoRes));
    }

    @GetMapping("/read/all")
    public ResponseEntity<BaseResponse<HeadOrderInfoListRes>> readOrders() {
        HeadOrderInfoListRes headOrderInfoListRes = headOrderService.readList();
        return ResponseEntity.status(200).body(BaseResponse.of(headOrderInfoListRes));
    }

    @GetMapping("/read/page/all")
    public ResponseEntity<BaseResponse<PageRes<HeadOrderInfoRes>>> readOrders(PageReq pageReq) {
        PageRes<HeadOrderInfoRes> pageRes = headOrderService.readList(pageReq);
        return ResponseEntity.status(200).body(BaseResponse.of(pageRes));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<BaseResponse<HeadOrderInfoRes>> updateOrder(@PathVariable Integer id,
                                                                      @RequestBody HeadOrderUpdateReq dto) {
        HeadOrderInfoRes headOrderInfoRes = headOrderService.update(id, dto.getItemName(), dto.getUnitPrice(), dto.getQuantity(), dto.getTotalPrice(), dto.getDeliveryAddress(), dto.getDeliveryRequest(), dto.getOrderRequest());
        return ResponseEntity.status(200).body(BaseResponse.of(headOrderInfoRes));
    }


}
