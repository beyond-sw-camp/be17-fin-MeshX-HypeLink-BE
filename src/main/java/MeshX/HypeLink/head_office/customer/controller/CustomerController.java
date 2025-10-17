package MeshX.HypeLink.head_office.customer.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.head_office.customer.model.dto.CustomerInfoListRes;
import MeshX.HypeLink.head_office.customer.model.dto.CustomerInfoRes;
import MeshX.HypeLink.head_office.customer.model.dto.CustomerSignupReq;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<CustomerInfoRes>> getCustomerInfo(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(BaseResponse.of(CustomerInfoRes.builder().build()));
    }

    @GetMapping("/list")
    public ResponseEntity<BaseResponse<CustomerInfoListRes>> getCustomerInfos() {
        return ResponseEntity.status(200).body(BaseResponse.of(CustomerInfoListRes.builder().build()));
    }

    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<CustomerInfoRes>> signupCustomer(@RequestBody CustomerSignupReq dto) {
        return ResponseEntity.status(200).body(BaseResponse.of(CustomerInfoRes.builder().build()));
    }

    @PatchMapping("/phone")
    public ResponseEntity<BaseResponse<CustomerInfoRes>> updatePhoneNumber(@RequestBody String phoneNumber) {
        return ResponseEntity.status(200).body(BaseResponse.of(CustomerInfoRes.builder().build()));
    }

    @PatchMapping("/password")
    public ResponseEntity<BaseResponse<CustomerInfoRes>> updatePassword(@RequestBody String password) {
        return ResponseEntity.status(200).body(BaseResponse.of(CustomerInfoRes.builder().build()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<String>> deleteCustomer(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(BaseResponse.of(""));
    }
}
