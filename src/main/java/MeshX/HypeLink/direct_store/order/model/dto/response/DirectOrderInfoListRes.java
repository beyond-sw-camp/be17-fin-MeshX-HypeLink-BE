package MeshX.HypeLink.direct_store.order.model.dto.response;

import MeshX.HypeLink.direct_store.order.model.entity.DirectOrder;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class DirectOrderInfoListRes {
    private final List<DirectOrderInfoRes> directOrders;

    public static DirectOrderInfoListRes toDto(List<DirectOrder> entity) {
        return DirectOrderInfoListRes.builder()
                .directOrders(entity.stream()
                                  .map(DirectOrderInfoRes::toDto)
                                  .toList()
                )
                .build();
    }

    @Builder
    private DirectOrderInfoListRes(List<DirectOrderInfoRes> directOrders) {
        this.directOrders = directOrders;
    }
}
