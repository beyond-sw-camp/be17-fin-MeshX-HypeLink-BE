package com.example.apidirect.customer.adapter.in.web;

import MeshX.common.BaseResponse;
import com.example.apiclients.annotation.GetMemberId;
import com.example.apidirect.constants.DirectSwaggerConstants;
import com.example.apidirect.payment.usecase.service.ReceiptQueryUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import com.example.apidirect.auth.domain.POS;
import com.example.apidirect.auth.usecase.port.in.POSQueryPort;
import com.example.apidirect.customer.usecase.port.in.CustomerCommandPort;
import com.example.apidirect.customer.usecase.port.in.CustomerQueryPort;
import com.example.apidirect.customer.usecase.port.in.request.CustomerSignupCommand;
import com.example.apidirect.customer.usecase.port.in.request.CustomerUpdateCommand;
import com.example.apidirect.customer.usecase.port.out.response.CustomerResponse;
import com.example.apidirect.customer.usecase.port.out.response.CustomerListResponse;
import com.example.apidirect.customer.usecase.port.out.mapper.CustomerResponseMapper;
import com.example.apidirect.payment.adapter.in.web.dto.response.ReceiptListPagingResponse;
import com.example.apidirect.payment.usecase.port.in.ReceiptQueryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "고객 관리", description = "매장 고객 가입, 조회, 수정 등 고객 관리 API")
@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerWebAdapter {

    private final CustomerCommandPort customerCommandPort;
    private final CustomerQueryPort customerQueryPort;
    private final ReceiptQueryPort receiptQueryPort;
    private final POSQueryPort posQueryPort;

    @Operation(summary = "고객 가입", description = "매장에서 새로운 고객을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "고객 가입 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = DirectSwaggerConstants.CUSTOMER_SIGNUP_RES_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터", content = @Content)
    })
    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<CustomerResponse>> signup(@RequestBody CustomerSignupCommand command) {
        CustomerResponse result = customerCommandPort.signup(command);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @Operation(summary = "고객 ID로 조회", description = "고객 ID를 사용하여 고객 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "고객 정보 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = DirectSwaggerConstants.CUSTOMER_INFO_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "고객을 찾을 수 없음", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<CustomerResponse>> getCustomerById(@Parameter(description = "고객 ID") @PathVariable Integer id) {
        CustomerResponse result = customerQueryPort.findById(id);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @Operation(summary = "전화번호로 고객 조회", description = "전화번호를 사용하여 고객 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "고객 정보 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = DirectSwaggerConstants.CUSTOMER_INFO_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "고객을 찾을 수 없음", content = @Content)
    })
    @GetMapping("/phone/{phone}")
    public ResponseEntity<BaseResponse<CustomerResponse>> getCustomerByPhone(@Parameter(description = "고객 전화번호") @PathVariable String phone) {
        CustomerResponse result = customerQueryPort.findByPhone(phone);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @Operation(summary = "고객 쿠폰 정보 조회", description = "전화번호로 고객 정보와 사용 가능한 쿠폰을 함께 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "고객 쿠폰 정보 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = DirectSwaggerConstants.CUSTOMER_WITH_COUPONS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "고객을 찾을 수 없음", content = @Content)
    })
    @GetMapping("/phone/{phone}/available-coupons")
    public ResponseEntity<BaseResponse<CustomerResponse>> getCustomerWithAvailableCoupons(@Parameter(description = "고객 전화번호") @PathVariable String phone) {
        CustomerResponse result = customerQueryPort.findByPhoneWithAvailableCoupons(phone);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @Operation(summary = "고객 목록 조회 (페이징)", description = "모든 고객 목록을 페이징하여 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "고객 목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = DirectSwaggerConstants.CUSTOMER_LIST_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content)
    })
    @GetMapping("/list")
    public ResponseEntity<BaseResponse<CustomerListResponse>> getCustomerList(
            @Parameter(description = "페이지 번호 (0부터 시작)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기") @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CustomerResponse> pageResult = customerQueryPort.findAll(pageable);
        CustomerListResponse result = CustomerResponseMapper.toListResponse(pageResult);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @Operation(summary = "고객 검색", description = "키워드와 연령대로 고객을 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "고객 검색 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = DirectSwaggerConstants.CUSTOMER_LIST_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content)
    })
    @GetMapping("/search")
    public ResponseEntity<BaseResponse<CustomerListResponse>> searchCustomers(
            @Parameter(description = "검색 키워드 (이름, 전화번호)") @RequestParam(required = false) String keyword,
            @Parameter(description = "연령대 (10s, 20s, 30s, 40s, 50s, all)") @RequestParam(required = false, defaultValue = "all") String ageGroup,
            @Parameter(description = "페이지 번호 (0부터 시작)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기") @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CustomerResponse> pageResult = customerQueryPort.searchCustomers(keyword, ageGroup, pageable);
        CustomerListResponse result = CustomerResponseMapper.toListResponse(pageResult);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @Operation(summary = "고객 정보 수정", description = "고객의 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "고객 정보 수정 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = DirectSwaggerConstants.CUSTOMER_UPDATE_RES_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "고객을 찾을 수 없음", content = @Content)
    })
    @PatchMapping("/update")
    public ResponseEntity<BaseResponse<CustomerResponse>> updateCustomer(@RequestBody CustomerUpdateCommand command) {
        CustomerResponse result = customerCommandPort.update(command);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @Operation(summary = "고객 영수증 목록 조회", description = "매장의 고객 영수증 목록을 페이징하여 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "영수증 목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = DirectSwaggerConstants.RECEIPT_LIST_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content)
    })
    @GetMapping("/receipts/paging")
    public ResponseEntity<BaseResponse<ReceiptListPagingResponse>> getReceiptsPaging(
            @GetMemberId Integer memberId,
            @Parameter(description = "페이지 번호 (0부터 시작)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기") @RequestParam(defaultValue = "10") int size) {
        // 로그인한 POS의 storeId 조회
        POS pos = posQueryPort.findByMemberId(memberId);
        Integer storeId = pos.getStoreId();

        Pageable pageable = PageRequest.of(page, size);
        ReceiptListPagingResponse result = ((ReceiptQueryUseCase) receiptQueryPort).getReceiptsByStoreId(storeId, pageable);
        return ResponseEntity.ok(BaseResponse.of(result));
    }
}
