package MeshX.HypeLink.direct_store.payment.controller;

import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.direct_store.payment.constansts.PaymentSwaggerConstants;
import MeshX.HypeLink.direct_store.payment.model.dto.request.PaymentValidationReq;
import MeshX.HypeLink.direct_store.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "직영점 결제 관리", description = "직영점의 결제 관련 API")
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "결제 검증", description = "결제 요청에 대한 유효성을 검증합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "결제 검증 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = PaymentSwaggerConstants.PAYMENT_VALIDATION_SUCCESS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "결제 정보가 유효하지 않습니다.", content = @Content),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content)
    })
    @PostMapping("/validate")
    public ResponseEntity<BaseResponse<String>> validatePayment(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "결제 검증 요청 정보",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaymentValidationReq.class),
                            examples = @ExampleObject(value = PaymentSwaggerConstants.PAYMENT_VALIDATION_REQ_EXAMPLE)
                    )
            )
            @RequestBody PaymentValidationReq req,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails) {
        Member member = paymentService.getMember(userDetails.getUsername());
        paymentService.validatePayment(req,member);
        return ResponseEntity.status(200).body(BaseResponse.of("검증 성공 "));
    }
}

