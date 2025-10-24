package MeshX.HypeLink.head_office.customer.model.dto.response;

import MeshX.HypeLink.head_office.customer.model.entity.CustomerReceipt;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class ReceiptListRes {
    private List<ReceiptRes> receipts;
    private Integer totalCount;

    public static ReceiptListRes toDto(List<CustomerReceipt> receiptList) {
        List<ReceiptRes> receipts = receiptList.stream()
                .map(ReceiptRes::toDto)
                .collect(Collectors.toList());

        return ReceiptListRes.builder()
                .receipts(receipts)
                .totalCount(receipts.size())
                .build();
    }
}
