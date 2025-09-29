package MeshX.HypeLink.head_office.store.model.entity;

import MeshX.HypeLink.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pos extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String posCode; // Store Code + 01, 02, 03, 04 ...
    private String password;

    @ManyToOne
    @JoinColumn(name = "store_member_id")
    private StoreMember storeMember;
    private Boolean healthCheck;
}
