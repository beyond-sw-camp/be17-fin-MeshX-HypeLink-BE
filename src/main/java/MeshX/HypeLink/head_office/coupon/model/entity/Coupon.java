package MeshX.HypeLink.head_office.coupon.model.entity;

import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.head_office.customer.model.entity.CustomerCoupon; // Import CustomerCoupon
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList; // Import ArrayList
import java.util.List; // Import List

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CouponType couponType;

    @Column(nullable = false)
    private Integer value;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @OneToMany(mappedBy = "coupon", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CustomerCoupon> customerCoupons = new ArrayList<>(); // Add OneToMany to CustomerCoupon

    @Builder
    public Coupon(String name, CouponType couponType, Integer value, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.couponType = couponType;
        this.value = value;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateCouponType(CouponType couponType) {
        this.couponType = couponType;
    }

    public void updateValue(Integer value) {
        this.value = value;
    }

    public void updateStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void updateEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
