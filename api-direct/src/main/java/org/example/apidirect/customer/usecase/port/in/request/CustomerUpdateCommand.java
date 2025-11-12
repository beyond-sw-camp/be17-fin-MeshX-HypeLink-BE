package org.example.apidirect.customer.usecase.port.in.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerUpdateCommand {
    private Integer id;
    private String name;
    private String phone;
}
