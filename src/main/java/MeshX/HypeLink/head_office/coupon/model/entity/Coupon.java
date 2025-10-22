package MeshX.HypeLink.head_office.coupon.model.entity;

import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.head_office.customer.model.entity.Customer;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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

    @ManyToOne
    @JoinColumn(name = "customer")
    private Customer customer;

    @Builder
    public Coupon(String name, CouponType couponType, Integer value, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.couponType = couponType;
        this.value = value;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
