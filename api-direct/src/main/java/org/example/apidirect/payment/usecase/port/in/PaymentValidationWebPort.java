package org.example.apidirect.payment.usecase.port.in;

import org.example.apidirect.payment.usecase.port.in.request.PaymentValidationCommand;
import org.example.apidirect.payment.usecase.port.in.request.ReceiptCreateCommand;

public interface PaymentValidationWebPort {
    void validatePayment(PaymentValidationCommand command, Integer MemberId);
    void testPaymentKafka(ReceiptCreateCommand command, Integer memberId);
}