package MeshX.HypeLink.head_office.order.model.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PurchaseOrderStateInfoListRes {
    List<PurchaseOrderStateInfoRes> purchaseOrderStates;
}
