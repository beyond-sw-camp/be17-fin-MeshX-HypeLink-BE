package MeshX.HypeLink.direct_store.payment.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.direct_store.payment.model.dto.request.PaymentValidationReq;
import MeshX.HypeLink.direct_store.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public ResponseEntity<BaseResponse<String>> validatePayment(@RequestBody PaymentValidationReq req
    , @AuthenticationPrincipal UserDetails userDetails) {
        paymentService.validatePayment(req,userDetails);
        return ResponseEntity.status(200).body(BaseResponse.of("검증 성공 "));
    }
}
