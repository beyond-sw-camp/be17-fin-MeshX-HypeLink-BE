package MeshX.HypeLink.head_office.order.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.common.Page.PageReq;
import MeshX.HypeLink.common.Page.PageRes;
import MeshX.HypeLink.head_office.order.model.dto.request.HeadPurchaseOrderCreateReq;
import MeshX.HypeLink.head_office.order.model.dto.request.PurchaseOrderCreateReq;
import MeshX.HypeLink.head_office.order.model.dto.request.PurchaseOrderUpdateReq;
import MeshX.HypeLink.head_office.order.model.dto.response.PurchaseOrderInfoDetailListRes;
import MeshX.HypeLink.head_office.order.model.dto.response.PurchaseOrderInfoDetailRes;
import MeshX.HypeLink.head_office.order.model.dto.response.PurchaseOrderInfoRes;
import MeshX.HypeLink.head_office.order.model.entity.PurchaseOrder;
import MeshX.HypeLink.head_office.order.service.PurchaseOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class PurchaseOrderController {
    private final PurchaseOrderService headPurchaseOrderService;

    @PostMapping("/head/create")
    public ResponseEntity<BaseResponse<String>> createHeadOrder(@RequestBody HeadPurchaseOrderCreateReq dto) {
        headPurchaseOrderService.createHeadOrder(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("본사 발주가 생성되었습니다."));
    }

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<String>> createOrder(@RequestBody PurchaseOrderCreateReq dto) {
        headPurchaseOrderService.createOrder(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("본사 발주가 생성되었습니다."));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<BaseResponse<PurchaseOrderInfoDetailRes>> readOrder(@PathVariable Integer id) {
        PurchaseOrderInfoDetailRes headPurchaseOrderInfoRes = headPurchaseOrderService.readDetails(id);
        return ResponseEntity.status(200).body(BaseResponse.of(headPurchaseOrderInfoRes));
    }

    @GetMapping("/read/all")
    public ResponseEntity<BaseResponse<PurchaseOrderInfoDetailListRes>> readOrders() {
        PurchaseOrderInfoDetailListRes headOrderInfoListRes = headPurchaseOrderService.readList();
        return ResponseEntity.status(200).body(BaseResponse.of(headOrderInfoListRes));
    }

    @GetMapping("/page/all")
    public ResponseEntity<BaseResponse<PageRes<PurchaseOrderInfoDetailRes>>> readPurchaseOrders(Pageable pageReq) {
        PageRes<PurchaseOrderInfoDetailRes> pageRes = headPurchaseOrderService.readList(pageReq);
        return ResponseEntity.status(200).body(BaseResponse.of(pageRes));
    }

    @GetMapping("/read/page/all")
    public ResponseEntity<BaseResponse<PageRes<PurchaseOrderInfoRes>>> readOrders(Pageable pageReq) {
        PageRes<PurchaseOrderInfoRes> pageRes = headPurchaseOrderService.readPurchaseOrderList(pageReq);
        return ResponseEntity.status(200).body(BaseResponse.of(pageRes));
    }

    @PatchMapping("/update")
    public ResponseEntity<BaseResponse<PurchaseOrderInfoDetailRes>> updateOrder(@RequestBody PurchaseOrderUpdateReq dto) {
        PurchaseOrderInfoDetailRes headPurchaseOrderInfoRes = headPurchaseOrderService.update(dto);
        return ResponseEntity.status(200).body(BaseResponse.of(headPurchaseOrderInfoRes));
    }
}
