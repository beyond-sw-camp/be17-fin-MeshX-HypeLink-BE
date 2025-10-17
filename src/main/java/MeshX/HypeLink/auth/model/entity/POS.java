package MeshX.HypeLink.auth.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class POS {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String posCode; // Store Code + 01, 02, 03, 04 ...

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    private Boolean healthCheck;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    private POS(String posCode, Store store, Boolean healthCheck, Member member) {
        this.posCode = posCode;
        this.store = store;
        this.healthCheck = healthCheck;
        this.member = member;
    }
}
