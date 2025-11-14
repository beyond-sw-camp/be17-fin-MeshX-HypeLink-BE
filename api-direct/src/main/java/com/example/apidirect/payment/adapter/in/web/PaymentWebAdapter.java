package com.example.apidirect.payment.adapter.in.web;

import MeshX.common.BaseResponse;
import MeshX.common.WebAdapter;
import com.example.apiclients.annotation.GetMemberId;
import lombok.RequiredArgsConstructor;
import com.example.apidirect.payment.usecase.port.in.request.PaymentValidationCommand;
import com.example.apidirect.payment.usecase.port.in.PaymentValidationWebPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@WebAdapter
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentWebAdapter {

    private final PaymentValidationWebPort paymentValidationWebPort;

    @PostMapping("/validate")
    public ResponseEntity<BaseResponse<String>> validatePayment(
            @RequestBody PaymentValidationCommand req,
            @GetMemberId Integer memberId) {
        paymentValidationWebPort.validatePayment(req, memberId);
        return ResponseEntity.ok(BaseResponse.of("검증 성공"));
    }
}
