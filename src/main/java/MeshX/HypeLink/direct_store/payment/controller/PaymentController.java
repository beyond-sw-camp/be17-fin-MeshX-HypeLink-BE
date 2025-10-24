package MeshX.HypeLink.direct_store.payment.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.direct_store.payment.model.dto.request.PaymentValidationReq;
import MeshX.HypeLink.direct_store.payment.service.PaymentService;
import MeshX.HypeLink.head_office.customer.model.entity.CustomerReceipt;
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

    @PostMapping("/validate")
    public ResponseEntity<BaseResponse<CustomerReceipt>> validatePayment(@RequestBody PaymentValidationReq req) {
        CustomerReceipt receipt = paymentService.validatePayment(req);
        return ResponseEntity.ok(BaseResponse.of(receipt, "결제 검증 및 영수증 생성 완료"));
    }
}
