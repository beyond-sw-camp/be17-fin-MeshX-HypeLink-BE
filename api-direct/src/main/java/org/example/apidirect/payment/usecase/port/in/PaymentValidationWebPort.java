package org.example.apidirect.payment.usecase.port.in;

import org.example.apidirect.payment.usecase.port.in.request.PaymentValidationCommand;

public interface PaymentValidationWebPort {
    void validatePayment(PaymentValidationCommand command, Integer MemberId);
}