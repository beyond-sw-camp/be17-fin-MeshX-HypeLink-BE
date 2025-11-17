package com.example.apidirect.customer.usecase.port.in;

import com.example.apidirect.customer.usecase.port.in.request.CustomerSignupCommand;
import com.example.apidirect.customer.usecase.port.in.request.CustomerUpdateCommand;
import com.example.apidirect.customer.usecase.port.out.response.CustomerResponse;

public interface CustomerCommandPort {
    CustomerResponse signup(CustomerSignupCommand command);
    CustomerResponse update(CustomerUpdateCommand command);
}
