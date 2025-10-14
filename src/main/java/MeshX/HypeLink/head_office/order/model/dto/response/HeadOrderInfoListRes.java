package MeshX.HypeLink.head_office.order.model.dto.response;

import MeshX.HypeLink.head_office.order.model.entity.HeadOrder;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class HeadOrderInfoListRes {
    private final List<HeadOrderInfoRes> headOrders;

    public static HeadOrderInfoListRes toDto(List<HeadOrder> entity) {
        return HeadOrderInfoListRes.builder()
                .headOrders(entity.stream()
                                  .map(HeadOrderInfoRes::toDto)
                                  .toList()
                )
                .build();
    }

    @Builder
    private HeadOrderInfoListRes(List<HeadOrderInfoRes> headOrders) {
        this.headOrders = headOrders;
    }
}
