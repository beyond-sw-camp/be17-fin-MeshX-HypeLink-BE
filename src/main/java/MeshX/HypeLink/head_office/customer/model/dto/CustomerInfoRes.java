package MeshX.HypeLink.head_office.customer.model.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CustomerInfoRes {
    private String email;
    private String name;
    private String phone;
    private LocalDate birthday;

    @Builder
    private CustomerInfoRes(String email, String name, String phone, LocalDate birthday) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.birthday = birthday;
    }
}
