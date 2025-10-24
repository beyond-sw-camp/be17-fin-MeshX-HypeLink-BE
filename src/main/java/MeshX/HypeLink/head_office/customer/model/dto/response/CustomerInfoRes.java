package MeshX.HypeLink.head_office.customer.model.dto.response;

import MeshX.HypeLink.head_office.customer.model.entity.Customer;
import MeshX.HypeLink.head_office.customer.model.entity.CustomerCoupon;
import MeshX.HypeLink.head_office.customer.model.entity.CustomerReceipt;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors; // Import Collectors

@Getter
public class CustomerInfoRes {
    private Integer customerId;
    private String name;
    private String phone;
    private LocalDate birthday;
    private List<CustomerCouponRes> customerCoupons; // Change to CustomerCouponRes
    private List<CustomerReceipt> customerReceiptList;

    public static CustomerInfoRes toDto (Customer entity) {
        return CustomerInfoRes.builder()
                .customerId(entity.getId())
                .name(entity.getName())
                .phone(entity.getPhone())
                .birthday(entity.getBirthDate())
                .customerCoupons(entity.getCustomerCoupons().stream()
                                        .map(CustomerCouponRes::toDto)
                                        .collect(Collectors.toList())) // Map to CustomerCouponRes
                .customerReceiptList(entity.getCustomerReceipts())
                .build();
    }

    public static List<CustomerInfoRes> toDtoList(List<Customer> entities) {
        return entities.stream()
                .map(CustomerInfoRes::toDto)
                .toList();
    }

    @Builder
    private CustomerInfoRes(Integer customerId, String email, String name, String phone, LocalDate birthday,
                            List<CustomerCouponRes> customerCoupons, List<CustomerReceipt> customerReceiptList) { // Change to CustomerCouponRes
        this.customerId = customerId;
        this.name = name;
        this.phone = phone;
        this.birthday = birthday;
        this.customerCoupons = customerCoupons;
        this.customerReceiptList = customerReceiptList;
    }
}