package MeshX.HypeLink.direct_store.payment.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.direct_store.payment.model.dto.request.PaymentValidationReq;
import MeshX.HypeLink.direct_store.payment.service.PaymentService;
import MeshX.HypeLink.direct_store.posOrder.model.dto.response.PosOrderDetailRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    // todo 주문생성은 따로 빼야됨. 리턴 값 수정하기
    @PostMapping("/validate")
    public ResponseEntity<BaseResponse<PosOrderDetailRes>> validatePayment(@RequestBody PaymentValidationReq req) {
        PosOrderDetailRes response = paymentService.validatePayment(req);
        return ResponseEntity.ok(BaseResponse.of(response, "결제 검증 및 주문 생성 완료"));
    }
}
