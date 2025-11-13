package org.example.apidirect.customer.adapter.in.web;

import MeshX.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.example.apidirect.customer.usecase.port.in.CustomerCommandPort;
import org.example.apidirect.customer.usecase.port.in.CustomerQueryPort;
import org.example.apidirect.customer.usecase.port.in.request.CustomerSignupCommand;
import org.example.apidirect.customer.usecase.port.in.request.CustomerUpdateCommand;
import org.example.apidirect.customer.usecase.port.out.response.CustomerResponse;
import org.example.apidirect.payment.adapter.in.web.dto.response.ReceiptListPagingResponse;
import org.example.apidirect.payment.usecase.port.in.ReceiptQueryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerWebAdapter {

    private final CustomerCommandPort customerCommandPort;
    private final CustomerQueryPort customerQueryPort;
    private final ReceiptQueryPort receiptQueryPort;

    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<CustomerResponse>> signup(@RequestBody CustomerSignupCommand command) {
        CustomerResponse result = customerCommandPort.signup(command);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<CustomerResponse>> getCustomerById(@PathVariable Integer id) {
        CustomerResponse result = customerQueryPort.findById(id);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @GetMapping("/phone/{phone}")
    public ResponseEntity<BaseResponse<CustomerResponse>> getCustomerByPhone(@PathVariable String phone) {
        CustomerResponse result = customerQueryPort.findByPhone(phone);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @GetMapping("/phone/{phone}/available-coupons")
    public ResponseEntity<BaseResponse<CustomerResponse>> getCustomerWithAvailableCoupons(@PathVariable String phone) {
        CustomerResponse result = customerQueryPort.findByPhoneWithAvailableCoupons(phone);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @GetMapping("/list")
    public ResponseEntity<BaseResponse<Page<CustomerResponse>>> getCustomerList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CustomerResponse> result = customerQueryPort.findAll(pageable);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @GetMapping("/search")
    public ResponseEntity<BaseResponse<Page<CustomerResponse>>> searchCustomers(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "all") String ageGroup,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CustomerResponse> result = customerQueryPort.searchCustomers(keyword, ageGroup, pageable);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @PatchMapping("/update")
    public ResponseEntity<BaseResponse<CustomerResponse>> updateCustomer(@RequestBody CustomerUpdateCommand command) {
        CustomerResponse result = customerCommandPort.update(command);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @GetMapping("/receipts/paging")
    public ResponseEntity<BaseResponse<ReceiptListPagingResponse>> getReceiptsPaging(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        ReceiptListPagingResponse result = receiptQueryPort.getReceipts(pageable);
        return ResponseEntity.ok(BaseResponse.of(result));
    }
}
