package MeshX.HypeLink.head_office.customer.model.dto.response;

import MeshX.HypeLink.head_office.customer.model.entity.Customer;
import MeshX.HypeLink.head_office.customer.model.entity.CustomerCoupon;
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
    private List<CustomerCouponRes> customerCoupons;
    private List<ReceiptRes> customerReceiptList;

    public static CustomerInfoRes toDto (Customer entity) {
        return CustomerInfoRes.builder()
                .customerId(entity.getId())
                .name(entity.getName())
                .phone(entity.getPhone())
                .birthday(entity.getBirthDate())
                .customerCoupons(entity.getCustomerCoupons().stream()
                                        .map(CustomerCouponRes::toDto)
                                        .collect(Collectors.toList()))
                .customerReceiptList(entity.getCustomerReceipts().stream()
                                        .map(ReceiptRes::toDto)
                                        .collect(Collectors.toList()))
                .build();
    }

    public static CustomerInfoRes toDtoWithAvailableCoupons(Customer entity) {
        return CustomerInfoRes.builder()
                .customerId(entity.getId())
                .name(entity.getName())
                .phone(entity.getPhone())
                .birthday(entity.getBirthDate())
                .customerCoupons(entity.getCustomerCoupons().stream()
                                        .filter(cc -> !cc.getIsUsed() && !cc.getExpirationDate().isBefore(LocalDate.now()))
                                        .map(CustomerCouponRes::toDto)
                                        .collect(Collectors.toList()))
                .customerReceiptList(entity.getCustomerReceipts().stream()
                                        .map(ReceiptRes::toDto)
                                        .collect(Collectors.toList()))
                .build();
    }

    public static List<CustomerInfoRes> toDtoList(List<Customer> entities) {
        return entities.stream()
                .map(CustomerInfoRes::toDtoSimple)
                .toList();
    }

    public static CustomerInfoRes toDtoSimple(Customer entity) {
        return CustomerInfoRes.builder()
                .customerId(entity.getId())
                .name(entity.getName())
                .phone(entity.getPhone())
                .birthday(entity.getBirthDate())
                .customerCoupons(List.of())
                .customerReceiptList(List.of())
                .build();
    }

    @Builder
    private CustomerInfoRes(Integer customerId, String email, String name, String phone, LocalDate birthday,
                            List<CustomerCouponRes> customerCoupons, List<ReceiptRes> customerReceiptList) {
        this.customerId = customerId;
        this.name = name;
        this.phone = phone;
        this.birthday = birthday;
        this.customerCoupons = customerCoupons;
        this.customerReceiptList = customerReceiptList;
    }
}