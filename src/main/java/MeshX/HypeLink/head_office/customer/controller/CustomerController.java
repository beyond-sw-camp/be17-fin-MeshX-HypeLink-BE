package MeshX.HypeLink.head_office.customer.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.head_office.customer.model.dto.request.CustomerUpdateReq;
import MeshX.HypeLink.head_office.customer.model.dto.response.CustomerInfoListRes;
import MeshX.HypeLink.head_office.customer.model.dto.response.CustomerInfoRes;
import MeshX.HypeLink.head_office.customer.model.dto.request.CustomerSignupReq;
import MeshX.HypeLink.head_office.customer.model.dto.response.ReceiptListRes;
import MeshX.HypeLink.head_office.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

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

    @GetMapping("/list")
    public ResponseEntity<BaseResponse<CustomerInfoListRes>> getCustomerInfos() {
        CustomerInfoListRes result = customerService.readAll();
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

    // 매장별 주문 내역 조회
    @GetMapping("/receipts")
    public ResponseEntity<BaseResponse<ReceiptListRes>> getReceipts(@RequestParam Integer storeId) {
        ReceiptListRes result = customerService.getReceiptsByStoreId(storeId);
        return ResponseEntity.ok(BaseResponse.of(result));
    }
}
