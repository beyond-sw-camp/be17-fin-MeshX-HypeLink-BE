package org.example.apidirect.customer.usecase.port.in.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerSignupCommand {
    private String name;
    private String phone;
    private LocalDate birthDate;
}
