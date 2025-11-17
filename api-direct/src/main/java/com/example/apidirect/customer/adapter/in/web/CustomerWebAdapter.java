package com.example.apidirect.customer.adapter.in.web;

import MeshX.common.BaseResponse;
import com.example.apiclients.annotation.GetMemberId;
import com.example.apidirect.payment.usecase.service.ReceiptQueryUseCase;
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

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerWebAdapter {

    private final CustomerCommandPort customerCommandPort;
    private final CustomerQueryPort customerQueryPort;
    private final ReceiptQueryPort receiptQueryPort;
    private final POSQueryPort posQueryPort;

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
    public ResponseEntity<BaseResponse<CustomerListResponse>> getCustomerList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CustomerResponse> pageResult = customerQueryPort.findAll(pageable);
        CustomerListResponse result = CustomerResponseMapper.toListResponse(pageResult);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @GetMapping("/search")
    public ResponseEntity<BaseResponse<CustomerListResponse>> searchCustomers(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "all") String ageGroup,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CustomerResponse> pageResult = customerQueryPort.searchCustomers(keyword, ageGroup, pageable);
        CustomerListResponse result = CustomerResponseMapper.toListResponse(pageResult);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @PatchMapping("/update")
    public ResponseEntity<BaseResponse<CustomerResponse>> updateCustomer(@RequestBody CustomerUpdateCommand command) {
        CustomerResponse result = customerCommandPort.update(command);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @GetMapping("/receipts/paging")
    public ResponseEntity<BaseResponse<ReceiptListPagingResponse>> getReceiptsPaging(
            @GetMemberId Integer memberId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        // 로그인한 POS의 storeId 조회
        POS pos = posQueryPort.findByMemberId(memberId);
        Integer storeId = pos.getStoreId();

        Pageable pageable = PageRequest.of(page, size);
        ReceiptListPagingResponse result = ((ReceiptQueryUseCase) receiptQueryPort).getReceiptsByStoreId(storeId, pageable);
        return ResponseEntity.ok(BaseResponse.of(result));
    }
}
