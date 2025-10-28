package MeshX.HypeLink.head_office.order.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.common.Page.PageRes;
import MeshX.HypeLink.head_office.order.model.dto.request.HeadPurchaseOrderCreateReq;
import MeshX.HypeLink.head_office.order.model.dto.request.PurchaseOrderCreateReq;
import MeshX.HypeLink.head_office.order.model.dto.request.PurchaseOrderUpdateReq;
import MeshX.HypeLink.head_office.order.model.dto.response.*;
import MeshX.HypeLink.head_office.order.service.PurchaseOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public ResponseEntity<BaseResponse<PurchaseOrderInfoDetailRes>> getOrder(@PathVariable Integer id) {
        PurchaseOrderInfoDetailRes headPurchaseOrderInfoRes = headPurchaseOrderService.getDetails(id);
        return ResponseEntity.status(200).body(BaseResponse.of(headPurchaseOrderInfoRes));
    }

    @GetMapping("/read/all")
    public ResponseEntity<BaseResponse<PurchaseOrderInfoDetailListRes>> getOrders() {
        PurchaseOrderInfoDetailListRes headOrderInfoListRes = headPurchaseOrderService.getList();
        return ResponseEntity.status(200).body(BaseResponse.of(headOrderInfoListRes));
    }

    @GetMapping("/page/search")
    public ResponseEntity<BaseResponse<PageRes<PurchaseOrderInfoDetailRes>>> searchPurchaseOrders(Pageable pageReq,
                                                                                               @RequestParam String keyWord,
                                                                                               @RequestParam String status,
                                                                                               @AuthenticationPrincipal UserDetails userDetails) {
        PageRes<PurchaseOrderInfoDetailRes> pageRes = headPurchaseOrderService.searchList(pageReq, keyWord, status, userDetails);
        return ResponseEntity.status(200).body(BaseResponse.of(pageRes));
    }

    @GetMapping("/read/page/all")
    public ResponseEntity<BaseResponse<PageRes<PurchaseOrderInfoRes>>> getOrders(Pageable pageReq,
                                                                                 @RequestParam String keyWord,
                                                                                 @RequestParam String category) {
        PageRes<PurchaseOrderInfoRes> pageRes = headPurchaseOrderService.getPurchaseOrderList(pageReq, keyWord, category);
        return ResponseEntity.status(200).body(BaseResponse.of(pageRes));
    }

    @GetMapping("/read/order/state")
    public ResponseEntity<BaseResponse<PurchaseOrderStateInfoListRes>> getPurchaseOrderState() {
        PurchaseOrderStateInfoListRes purchaseStateList = headPurchaseOrderService.getPurchaseStateList();
        return ResponseEntity.status(200).body(BaseResponse.of(purchaseStateList));
    }

    @GetMapping("/read/order/detail")
    public ResponseEntity<BaseResponse<PurchaseDetailsStatusInfoListRes>> getPurchaseDetailStatus() {
        PurchaseDetailsStatusInfoListRes statuses = headPurchaseOrderService.getPurchaseDetailStatuses();
        return ResponseEntity.status(200).body(BaseResponse.of(statuses));
    }

    @PatchMapping("/update")
    public ResponseEntity<BaseResponse<PurchaseOrderInfoDetailRes>> updateOrder(@RequestBody PurchaseOrderUpdateReq dto) {
        PurchaseOrderInfoDetailRes headPurchaseOrderInfoRes = headPurchaseOrderService.update(dto);
        return ResponseEntity.status(200).body(BaseResponse.of(headPurchaseOrderInfoRes));
    }
}
