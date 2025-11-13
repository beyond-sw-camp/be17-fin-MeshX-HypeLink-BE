package org.example.apidirect.customer.adapter.out.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "customer")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String phone;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Builder
    private CustomerEntity(Integer id, String name, String phone, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.birthDate = birthDate;
    }

    public void updatePhone(String phone) {
        this.phone = phone;
    }

    public void updateName(String name) {
        this.name = name;
    }
}
