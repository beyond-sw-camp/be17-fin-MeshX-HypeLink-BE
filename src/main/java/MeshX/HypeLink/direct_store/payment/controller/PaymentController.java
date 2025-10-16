package MeshX.HypeLink.direct_store.payment.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.direct_store.payment.constansts.SwaggerConstants;
import MeshX.HypeLink.direct_store.payment.model.dto.request.PaymentValidationReq;
import MeshX.HypeLink.direct_store.payment.service.PaymentService;
import MeshX.HypeLink.direct_store.posOrder.model.dto.response.PosOrderDetailRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "POS 매장 결제", description = "POS 시스템에서의 결제 검증 및 주문 생성을 처리하는 API")
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "PortOne 결제 검증 및 주문 생성", description = "클라이언트(POS)에서 PortOne을 통해 결제가 완료된 후, 서버에서 해당 결제의 유효성을 검증하고 최종적으로 시스템에 주문을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "결제 검증 및 주문 생성 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = SwaggerConstants.PAYMENT_VALIDATION_RESPONSE))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 (예: 결제 정보 불일치, 금액 불일치)", content = @Content),
            @ApiResponse(responseCode = "404", description = "결제 정보를 찾을 수 없음", content = @Content)
    })
    @PostMapping("/validate")
    public ResponseEntity<BaseResponse<PosOrderDetailRes>> validatePayment(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "PortOne 결제 ID와 POS에서 생성된 주문 데이터",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaymentValidationReq.class),
                            examples = @ExampleObject(value = SwaggerConstants.PAYMENT_VALIDATION_REQUEST)
                    )
            )
            @RequestBody PaymentValidationReq req) {
        PosOrderDetailRes response = paymentService.validatePayment(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.of(response, "결제 검증 및 주문 생성 완료"));
    }
}
