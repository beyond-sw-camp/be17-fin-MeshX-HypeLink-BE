package MeshX.HypeLink.head_office.customer.controller;

import MeshX.HypeLink.auth.model.dto.res.StoreWithPosResDto;
import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.auth.service.MemberService;
import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.head_office.customer.model.dto.request.CustomerSignupReq;
import MeshX.HypeLink.head_office.customer.model.dto.request.CustomerUpdateReq;
import MeshX.HypeLink.head_office.customer.model.dto.response.CustomerInfoListRes;
import MeshX.HypeLink.head_office.customer.model.dto.response.CustomerInfoRes;
import MeshX.HypeLink.head_office.customer.model.dto.response.ReceiptListPagingRes;
import MeshX.HypeLink.head_office.customer.model.dto.response.ReceiptListRes;
import MeshX.HypeLink.head_office.customer.service.CustomerService;
import com.example.apiclients.annotation.GetMemberEmail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "고객 관리", description = "고객 가입, 조회, 수정, 삭제 및 쿠폰 발급, 영수증 조회 API")
@RestController
@RequestMapping("/api/mono/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final MemberService memberService;

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<CustomerInfoRes>> getCustomerInfo(@PathVariable Integer id) {
        CustomerInfoRes result = customerService.findById(id);
        return ResponseEntity.status(200).body(BaseResponse.of(result));
    }

    @GetMapping("/phone/{phone}")
    public ResponseEntity<BaseResponse<CustomerInfoRes>> getCustomerByPhone(@PathVariable String phone) {
        CustomerInfoRes result = customerService.findByPhone(phone);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @GetMapping("/phone/{phone}/available-coupons")
    public ResponseEntity<BaseResponse<CustomerInfoRes>> getCustomerWithAvailableCoupons(@PathVariable String phone) {
        CustomerInfoRes result = customerService.findByPhoneWithAvailableCoupons(phone);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @GetMapping("/list")
    public ResponseEntity<BaseResponse<CustomerInfoListRes>> getCustomerInfos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        CustomerInfoListRes result = customerService.readAll(pageable);
        return ResponseEntity.status(200).body(BaseResponse.of(result));
    }

    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<String>> signupCustomer(@RequestBody CustomerSignupReq dto) {
        customerService.signup(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("가입이 완료되었습니다."));
    }

    @PatchMapping("/update")
    public ResponseEntity<BaseResponse<CustomerInfoRes>> updatePhoneNumber(@RequestBody CustomerUpdateReq dto) {
        CustomerInfoRes result = customerService.update(dto);
        return ResponseEntity.status(200).body(BaseResponse.of(result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<String>> deleteCustomer(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(BaseResponse.of(""));
    }

    @PostMapping("/{customerId}/coupons")
    public ResponseEntity<BaseResponse<String>> issueCoupon(
            @PathVariable Integer customerId,
            @RequestParam Integer couponId) {
        customerService.issueCoupon(customerId, couponId);
        return ResponseEntity.status(200).body(BaseResponse.of("쿠폰이 발급되었습니다."));
    }

    @GetMapping("/receipts")
    public ResponseEntity<BaseResponse<ReceiptListRes>> getReceipts(@GetMemberEmail String email) {
        Member member = memberService.findMember(email);
        StoreWithPosResDto dto = memberService.readMyStore(member);
        ReceiptListRes result = customerService.getReceiptsByStoreId(dto.getId());
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @GetMapping("/receipts/paging")
    public ResponseEntity<BaseResponse<ReceiptListPagingRes>> getReceiptsPaging(
           @GetMemberEmail String email,
            Pageable pageable) {
        Member member = memberService.findMember(email);
        StoreWithPosResDto dto = memberService.readMyStore(member);
        ReceiptListPagingRes result = customerService.getReceiptsByStoreId(dto.getId(), pageable);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

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
    @PatchMapping("/coupons/{customerCouponId}/use")
    public ResponseEntity<BaseResponse<String>> useCoupon( @PathVariable Integer customerCouponId){
        customerService.useCoupon(customerCouponId);
        return ResponseEntity.ok(BaseResponse.of("쿠폰 사용 완료"));
    }
}
