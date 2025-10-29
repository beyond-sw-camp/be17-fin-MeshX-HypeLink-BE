package MeshX.HypeLink.auth.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String macAddress;

    private String carNumber;
    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

//    private List 배달 리스트

    @Builder
    private Driver(String macAddress, String carNumber, Member member) {
        this.macAddress = macAddress;
        this.carNumber = carNumber;
        this.member = member;
    }

    public void updateMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
    public void updateCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }
}
