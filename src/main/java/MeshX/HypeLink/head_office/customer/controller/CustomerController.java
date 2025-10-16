package MeshX.HypeLink.head_office.customer.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.head_office.customer.constansts.SwaggerConstants;
import MeshX.HypeLink.head_office.customer.model.dto.CustomerInfoListRes;
import MeshX.HypeLink.head_office.customer.model.dto.CustomerInfoRes;
import MeshX.HypeLink.head_office.customer.model.dto.CustomerSignupReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "B2B 거래처 관리", description = "본사와 계약된 B2B 거래처(고객사)의 정보를 관리하는 API")
@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    @Operation(summary = "거래처 상세 정보 조회", description = "거래처의 고유 ID를 사용하여 특정 거래처의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "거래처 상세 정보 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = SwaggerConstants.CUSTOMER_INFO_RESPONSE))),
            @ApiResponse(responseCode = "404", description = "해당 ID의 거래처를 찾을 수 없습니다.", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<CustomerInfoRes>> getCustomerInfo(@Parameter(description = "조회할 거래처의 ID") @PathVariable Integer id) {
        return ResponseEntity.status(200).body(BaseResponse.of(CustomerInfoRes.builder().build()));
    }

    @Operation(summary = "전체 거래처 목록 조회", description = "시스템에 등록된 모든 거래처의 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "거래처 목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = SwaggerConstants.CUSTOMER_INFO_LIST_RESPONSE)))
    })
    @GetMapping("/list")
    public ResponseEntity<BaseResponse<CustomerInfoListRes>> getCustomerInfos() {
        return ResponseEntity.status(200).body(BaseResponse.of(CustomerInfoListRes.builder().build()));
    }

    @Operation(summary = "신규 거래처 등록", description = "새로운 B2B 거래처를 시스템에 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "거래처 등록 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = SwaggerConstants.CUSTOMER_INFO_RESPONSE))),
            @ApiResponse(responseCode = "400", description = "요청 데이터가 유효하지 않거나, 이미 존재하는 이메일입니다.", content = @Content)
    })
    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<CustomerInfoRes>> signupCustomer(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "등록할 거래처의 가입 정보",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerSignupReq.class),
                            examples = @ExampleObject(value = SwaggerConstants.CUSTOMER_SIGNUP_REQUEST)
                    )
            )
            @RequestBody CustomerSignupReq dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.of(CustomerInfoRes.builder().build()));
    }

    @Operation(summary = "거래처 연락처 수정", description = "특정 거래처의 연락처 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "연락처 수정 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = SwaggerConstants.CUSTOMER_INFO_RESPONSE))),
            @ApiResponse(responseCode = "404", description = "해당 거래처를 찾을 수 없습니다.", content = @Content)
    })
    @PatchMapping("/phone")
    public ResponseEntity<BaseResponse<CustomerInfoRes>> updatePhoneNumber(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "새로운 연락처. 형식: 010-1234-5678",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = SwaggerConstants.UPDATE_PHONE_REQUEST)
                    )
            )
            @RequestBody String phoneNumber) {
        return ResponseEntity.status(200).body(BaseResponse.of(CustomerInfoRes.builder().build()));
    }

    @Operation(summary = "거래처 비밀번호 수정", description = "특정 거래처의 로그인 비밀번호를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "비밀번호 수정 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = SwaggerConstants.CUSTOMER_INFO_RESPONSE))),
            @ApiResponse(responseCode = "400", description = "비밀번호 정책에 맞지 않습니다.", content = @Content),
            @ApiResponse(responseCode = "404", description = "해당 거래처를 찾을 수 없습니다.", content = @Content)
    })
    @PatchMapping("/password")
    public ResponseEntity<BaseResponse<CustomerInfoRes>> updatePassword(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "새로운 비밀번호",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = SwaggerConstants.UPDATE_PASSWORD_REQUEST)
                    )
            )
            @RequestBody String password) {
        return ResponseEntity.status(200).body(BaseResponse.of(CustomerInfoRes.builder().build()));
    }

    @Operation(summary = "거래처 삭제", description = "거래처 ID를 사용하여 시스템에서 거래처 정보를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "거래처 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "해당 ID의 거래처를 찾을 수 없습니다.", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<String>> deleteCustomer(@Parameter(description = "삭제할 거래처의 ID") @PathVariable Integer id) {
        return ResponseEntity.status(200).body(BaseResponse.of(""));
    }
}
