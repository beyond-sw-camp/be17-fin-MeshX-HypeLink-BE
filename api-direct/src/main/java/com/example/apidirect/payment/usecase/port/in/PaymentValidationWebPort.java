package com.example.apidirect.payment.usecase.port.in;

import com.example.apidirect.payment.usecase.port.in.request.PaymentValidationCommand;
import com.example.apidirect.payment.usecase.port.in.request.ReceiptCreateCommand;

public interface PaymentValidationWebPort {
    void validatePayment(PaymentValidationCommand command, Integer MemberId);
    void testPaymentKafka(ReceiptCreateCommand command, Integer memberId);
}