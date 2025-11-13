package org.example.apidirect.payment.usecase.port.in;

import org.example.apidirect.payment.adapter.in.web.dto.response.ReceiptListPagingResponse;
import org.springframework.data.domain.Pageable;

public interface ReceiptQueryPort {
    ReceiptListPagingResponse getReceipts(Pageable pageable);
}
