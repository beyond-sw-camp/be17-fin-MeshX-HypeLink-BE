package MeshX.HypeLink.auth.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
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

    public void setStore(Store store) {
        this.store = store;
    }

}
