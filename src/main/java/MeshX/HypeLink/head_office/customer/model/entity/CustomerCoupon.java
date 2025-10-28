package MeshX.HypeLink.head_office.customer.model.entity;

import MeshX.HypeLink.common.BaseEntity;
import MeshX.HypeLink.head_office.coupon.model.entity.Coupon;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomerCoupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;

    @Column(nullable = false)
    private LocalDate issuedDate;

    @Column(nullable = false)
    private LocalDate expirationDate;

    @JsonProperty("isUsed")
    @Column(nullable = false)
    private Boolean isUsed;

    private LocalDate usedDate;

    @Builder
    public CustomerCoupon(Customer customer, Coupon coupon, LocalDate issuedDate, LocalDate expirationDate, Boolean isUsed, LocalDate usedDate) {
        this.customer = customer;
        this.coupon = coupon;
        this.issuedDate = issuedDate;
        this.expirationDate = expirationDate;
        this.isUsed = isUsed;
        this.usedDate = usedDate;
    }

    public void changeCustomer(Customer customer) {
        this.customer = customer;
    }

    public void useCoupon(LocalDate usedDate) {
        this.isUsed = true;
        this.usedDate = usedDate;
    }
}
