package MeshX.HypeLink.direct_strore.order.service;


import MeshX.HypeLink.direct_strore.order.model.dto.request.DirectOrderCreateReq;
import MeshX.HypeLink.direct_strore.order.model.dto.response.DirectOrderInfoListRes;
import MeshX.HypeLink.direct_strore.order.model.dto.response.DirectOrderInfoRes;
import MeshX.HypeLink.direct_strore.order.model.entity.DirectOrder;
import MeshX.HypeLink.direct_strore.order.repository.DirectOrderJpaRepositoryVerify;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class DirectOrderService {
    private final DirectOrderJpaRepositoryVerify repository;

    @Transactional
    public void createOrder(DirectOrderCreateReq dto) {
        repository.createOrder(dto.toEntity());
    }


    @Transactional
    public void delete(Integer id) {
        DirectOrder directOrder = repository.findById(id);
        repository.delete(directOrder);
    }

    public DirectOrderInfoRes readDetails(Integer id) {
        DirectOrder DirectOrder = repository.findById(id);
        return DirectOrderInfoRes.toDto(DirectOrder);
    }

    public DirectOrderInfoListRes readList() {
        List<DirectOrder> directOrders = repository.findAll();
        return DirectOrderInfoListRes.toDto(directOrders);
    }

    public DirectOrderInfoRes update(Integer id, String itemName, Integer unitPrice, Integer quantity, Integer totalPrice, String deliveryAddress, String deliveryRequest, String orderRequest) {
        DirectOrder directOrder = repository.findById(id);

        if (!itemName.isEmpty()) {
            directOrder.updateItemName(itemName);
        }
        if (!unitPrice.equals(directOrder.getUnitPrice())) {
            directOrder.updateUnitPrice(unitPrice);
        }
        if (!quantity.equals(directOrder.getQuantity())) {
            directOrder.updateQuantity(quantity);
        }
        if (!totalPrice.equals(directOrder.getTotalPrice())) {
            directOrder.updateTotalPrice(totalPrice);
        }
        if(!deliveryAddress.isEmpty()){
            directOrder.updateDeliveryAddress(deliveryAddress);
        }
        if(!deliveryRequest.isEmpty()){
            directOrder.updateDeliveryRequest(deliveryRequest);
        }
        if(!orderRequest.isEmpty()){
            directOrder.updateOrderRequest(orderRequest);
        }

        DirectOrder update = repository.update(directOrder);
        return DirectOrderInfoRes.toDto(update);
    }
}
