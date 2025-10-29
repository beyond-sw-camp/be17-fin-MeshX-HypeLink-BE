package MeshX.HypeLink.head_office.customer.controller;

import MeshX.HypeLink.auth.model.dto.res.StoreWithPosResDto;
import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.auth.service.MemberService;
import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.head_office.customer.constants.CustomerSwaggerConstants;
import MeshX.HypeLink.head_office.customer.model.dto.request.CustomerSignupReq;
import MeshX.HypeLink.head_office.customer.model.dto.request.CustomerUpdateReq;
import MeshX.HypeLink.head_office.customer.model.dto.response.CustomerInfoListRes;
import MeshX.HypeLink.head_office.customer.model.dto.response.CustomerInfoRes;
import MeshX.HypeLink.head_office.customer.model.dto.response.ReceiptListPagingRes;
import MeshX.HypeLink.head_office.customer.model.dto.response.ReceiptListRes;
import MeshX.HypeLink.head_office.customer.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Tag(name = "본부 고객 관리", description = "본부에서 고객 정보를 관리하고 영수증을 조회하는 API")
@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final MemberService memberService;

    @Operation(summary = "고객 정보 조회 (ID)", description = "고객 ID로 고객 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = CustomerSwaggerConstants.CUSTOMER_INFO_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content),
            @ApiResponse(responseCode = "404", description = "해당 고객을 찾을 수 없습니다.", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<CustomerInfoRes>> getCustomerInfo(
            @Parameter(description = "조회할 고객의 ID") @PathVariable Integer id) {
        CustomerInfoRes result = customerService.findById(id);
        return ResponseEntity.status(200).body(BaseResponse.of(result));
    }

    @Operation(summary = "고객 정보 조회 (전화번호)", description = "전화번호로 고객 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = CustomerSwaggerConstants.CUSTOMER_INFO_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content),
            @ApiResponse(responseCode = "404", description = "해당 고객을 찾을 수 없습니다.", content = @Content)
    })
    @GetMapping("/phone/{phone}")
    public ResponseEntity<BaseResponse<CustomerInfoRes>> getCustomerByPhone(
            @Parameter(description = "조회할 고객의 전화번호") @PathVariable String phone) {
        CustomerInfoRes result = customerService.findByPhone(phone);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @Operation(summary = "고객 정보 및 사용 가능한 쿠폰 조회", description = "전화번호로 고객 정보와 사용 가능한 쿠폰을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = CustomerSwaggerConstants.CUSTOMER_INFO_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content),
            @ApiResponse(responseCode = "404", description = "해당 고객을 찾을 수 없습니다.", content = @Content)
    })
    @GetMapping("/phone/{phone}/available-coupons")
    public ResponseEntity<BaseResponse<CustomerInfoRes>> getCustomerWithAvailableCoupons(
            @Parameter(description = "조회할 고객의 전화번호") @PathVariable String phone) {
        CustomerInfoRes result = customerService.findByPhoneWithAvailableCoupons(phone);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @Operation(summary = "고객 목록 조회 (페이징)", description = "모든 고객의 목록을 페이징하여 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = CustomerSwaggerConstants.CUSTOMER_INFO_LIST_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content)
    })
    @GetMapping("/list")
    public ResponseEntity<BaseResponse<CustomerInfoListRes>> getCustomerInfos(
            @Parameter(description = "페이지 번호 (0부터 시작)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기") @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        CustomerInfoListRes result = customerService.readAll(pageable);
        return ResponseEntity.status(200).body(BaseResponse.of(result));
    }

    @Operation(summary = "고객 회원가입", description = "새로운 고객을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "가입 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = CustomerSwaggerConstants.CUSTOMER_SIGNUP_SUCCESS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "요청 데이터가 유효하지 않습니다.", content = @Content),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content)
    })
    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<String>> signupCustomer(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "고객 가입 정보",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerSignupReq.class),
                            examples = @ExampleObject(value = CustomerSwaggerConstants.CUSTOMER_SIGNUP_REQ_EXAMPLE)
                    )
            )
            @RequestBody CustomerSignupReq dto) {
        customerService.signup(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("가입이 완료되었습니다."));
    }

    @Operation(summary = "고객 정보 수정", description = "고객의 전화번호를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = CustomerSwaggerConstants.CUSTOMER_UPDATE_SUCCESS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "요청 데이터가 유효하지 않습니다.", content = @Content),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content),
            @ApiResponse(responseCode = "404", description = "해당 고객을 찾을 수 없습니다.", content = @Content)
    })
    @PatchMapping("/update")
    public ResponseEntity<BaseResponse<CustomerInfoRes>> updatePhoneNumber(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "고객 수정 정보",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerUpdateReq.class),
                            examples = @ExampleObject(value = CustomerSwaggerConstants.CUSTOMER_UPDATE_REQ_EXAMPLE)
                    )
            )
            @RequestBody CustomerUpdateReq dto) {
        CustomerInfoRes result = customerService.update(dto);
        return ResponseEntity.status(200).body(BaseResponse.of(result));
    }

    @Operation(summary = "고객 삭제", description = "고객 정보를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content),
            @ApiResponse(responseCode = "404", description = "해당 고객을 찾을 수 없습니다.", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<String>> deleteCustomer(
            @Parameter(description = "삭제할 고객의 ID") @PathVariable Integer id) {
        return ResponseEntity.status(200).body(BaseResponse.of(""));
    }

    @Operation(summary = "고객에게 쿠폰 발급", description = "특정 고객에게 쿠폰을 발급합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "쿠폰 발급 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = CustomerSwaggerConstants.COUPON_ISSUE_SUCCESS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "요청 데이터가 유효하지 않습니다.", content = @Content),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content),
            @ApiResponse(responseCode = "404", description = "해당 고객 또는 쿠폰을 찾을 수 없습니다.", content = @Content)
    })
    @PostMapping("/{customerId}/coupons")
    public ResponseEntity<BaseResponse<String>> issueCoupon(
            @Parameter(description = "쿠폰을 발급할 고객의 ID") @PathVariable Integer customerId,
            @Parameter(description = "발급할 쿠폰의 ID") @RequestParam Integer couponId) {
        customerService.issueCoupon(customerId, couponId);
        return ResponseEntity.status(200).body(BaseResponse.of("쿠폰이 발급되었습니다."));
    }

    @Operation(summary = "매장별 주문 내역 조회", description = "로그인한 매장의 모든 주문 내역을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = CustomerSwaggerConstants.RECEIPT_LIST_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content)
    })
    @GetMapping("/receipts")
    public ResponseEntity<BaseResponse<ReceiptListRes>> getReceipts(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails) {
        Member member = memberService.findMember(userDetails.getUsername());
        StoreWithPosResDto dto = memberService.readMyStore(member);
        ReceiptListRes result = customerService.getReceiptsByStoreId(dto.getId());
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @Operation(summary = "매장별 주문 내역 조회 (페이징)", description = "로그인한 매장의 주문 내역을 페이징하여 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = CustomerSwaggerConstants.RECEIPT_LIST_PAGING_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content)
    })
    @GetMapping("/receipts/paging")
    public ResponseEntity<BaseResponse<ReceiptListPagingRes>> getReceiptsPaging(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails,
            Pageable pageable) {
        Member member = memberService.findMember(userDetails.getUsername());
        StoreWithPosResDto dto = memberService.readMyStore(member);
        ReceiptListPagingRes result = customerService.getReceiptsByStoreId(dto.getId(), pageable);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    // 고객 검색 (키워드 + 연령대)
    @GetMapping("/search")
    public ResponseEntity<BaseResponse<CustomerInfoListRes>> searchCustomers(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "all") String ageGroup,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        CustomerInfoListRes result = customerService.searchCustomers(keyword, ageGroup, pageable);
        return ResponseEntity.ok(BaseResponse.of(result));
    }
}
