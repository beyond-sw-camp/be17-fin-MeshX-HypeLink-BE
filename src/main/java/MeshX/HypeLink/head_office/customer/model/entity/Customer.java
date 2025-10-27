package MeshX.HypeLink.head_office.customer.model.entity;

import MeshX.HypeLink.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList; // Import ArrayList
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @Column(unique = true, nullable = false)
    private String phone;

    @Column(nullable = false)
    private LocalDate birthDate; // 생년월일, 연령대는 DB에서 SQL로 계산해서 들고 올 예정

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CustomerReceipt> customerReceipts = new ArrayList<>(); // 결제 내역들...

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CustomerCoupon> customerCoupons = new ArrayList<>();

    @Builder
    public Customer(String name, String phone, LocalDate birthDate, List<CustomerReceipt> customerReceipts, List<CustomerCoupon> customerCoupons) {
        this.name = name;
        this.phone = phone;
        this.birthDate = birthDate;
        this.customerReceipts = customerReceipts;
        this.customerCoupons = customerCoupons;
    }

    public void updatePhone(String phone) {
        this.phone = phone;
    }

    public void addCustomerCoupon(CustomerCoupon customerCoupon) {
        this.customerCoupons.add(customerCoupon);
        customerCoupon.setCustomer(this);
    }

}

