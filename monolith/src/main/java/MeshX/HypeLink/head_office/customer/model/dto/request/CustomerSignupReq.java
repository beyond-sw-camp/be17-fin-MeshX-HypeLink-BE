package MeshX.HypeLink.head_office.customer.model.dto.request;

import MeshX.HypeLink.head_office.customer.model.entity.Customer;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CustomerSignupReq {
    private String name;
    private String phone;
    private LocalDate birthDate; //yyyy-MM-dd 일때만 받아올 수 있음.

    public Customer toEntity(){
        return Customer.builder()
                .name(name)
                .phone(phone)
                .birthDate(birthDate)
                .build();
    }
}
