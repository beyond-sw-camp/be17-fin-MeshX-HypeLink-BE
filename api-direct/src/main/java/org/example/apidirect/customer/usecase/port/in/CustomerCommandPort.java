package org.example.apidirect.customer.usecase.port.in;

import org.example.apidirect.customer.usecase.port.in.request.CustomerSignupCommand;
import org.example.apidirect.customer.usecase.port.in.request.CustomerUpdateCommand;
import org.example.apidirect.customer.usecase.port.out.response.CustomerResponse;

public interface CustomerCommandPort {
    CustomerResponse signup(CustomerSignupCommand command);
    CustomerResponse update(CustomerUpdateCommand command);
}
