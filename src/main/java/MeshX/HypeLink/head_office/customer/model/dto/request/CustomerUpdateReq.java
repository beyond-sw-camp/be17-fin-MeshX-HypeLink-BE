package MeshX.HypeLink.head_office.customer.model.dto.request;

import lombok.Getter;

@Getter
public class CustomerUpdateReq {
    private Integer id;
    private String password;
    private String phone;
}
