package MeshX.HypeLink.head_office.customer.model.entity;

import MeshX.HypeLink.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String membershipNumber;
    private String name;
    private String email;
    private String password;
    private String phone;

    @Column(nullable = false)
    private LocalDate birthDate; // 생년월일, 연령대는 DB에서 SQL로 계산해서 들고 올 예정

    @OneToMany(mappedBy = "customer")
    private List<CustomerReceipt> customerReceipts; // 결제 내역들...

}
