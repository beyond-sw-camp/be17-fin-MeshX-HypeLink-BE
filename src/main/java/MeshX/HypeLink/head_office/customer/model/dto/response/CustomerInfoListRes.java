package MeshX.HypeLink.head_office.customer.model.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CustomerInfoListRes {
    private List<CustomerInfoRes> customerInfoResList;

    @Builder
    private CustomerInfoListRes(List<CustomerInfoRes> customerInfoResList) {
        this.customerInfoResList = customerInfoResList;
    }
}
