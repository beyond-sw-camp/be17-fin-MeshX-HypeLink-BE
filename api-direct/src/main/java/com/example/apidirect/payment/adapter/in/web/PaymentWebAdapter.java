package com.example.apidirect.payment.adapter.in.web;

import MeshX.common.BaseResponse;
import MeshX.common.WebAdapter;
import com.example.apiclients.annotation.GetMemberId;
import com.example.apidirect.constants.DirectSwaggerConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import com.example.apidirect.payment.usecase.port.in.request.PaymentValidationCommand;
import com.example.apidirect.payment.usecase.port.in.PaymentValidationWebPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "결제 관리", description = "매장 결제 검증 및 처리 API")
@WebAdapter
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentWebAdapter {

    private final PaymentValidationWebPort paymentValidationWebPort;

    @Operation(summary = "결제 검증", description = "결제 정보를 검증하고 처리합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "결제 검증 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = DirectSwaggerConstants.PAYMENT_VALIDATE_RES_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "잘못된 결제 정보", content = @Content),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content)
    })
    @PostMapping("/validate")
    public ResponseEntity<BaseResponse<String>> validatePayment(
            @RequestBody PaymentValidationCommand req,
            @GetMemberId Integer memberId) {
        paymentValidationWebPort.validatePayment(req, memberId);
        return ResponseEntity.ok(BaseResponse.of("검증 성공"));
    }
}
