package MeshX.HypeLink.head_office.customer.model.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CustomerSignupReq {
    private String email;
    private String password;
    private String name;
    private String phone;
    private LocalDate birthDate; //yyyy-MM-dd 일때만 받아올 수 있음.
}
