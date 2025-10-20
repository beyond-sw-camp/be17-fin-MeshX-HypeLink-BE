package MeshX.HypeLink.head_office.order.service;


import MeshX.HypeLink.common.Page.PageReq;
import MeshX.HypeLink.common.Page.PageRes;
import MeshX.HypeLink.head_office.notice.model.entity.Notice;
import MeshX.HypeLink.head_office.order.model.dto.request.HeadOrderCreateReq;
import MeshX.HypeLink.head_office.order.model.dto.response.HeadOrderInfoListRes;
import MeshX.HypeLink.head_office.order.model.dto.response.HeadOrderInfoRes;
import MeshX.HypeLink.head_office.order.model.entity.HeadOrder;
import MeshX.HypeLink.head_office.order.repository.HeadOrderJpaRepositoryVerify;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class HeadOrderService {
    private final HeadOrderJpaRepositoryVerify repository;

    @Transactional
    public void createOrder(HeadOrderCreateReq dto) {
        repository.createOrder(dto.toEntity());
    }


    @Transactional
    public void delete(Integer id) {
        HeadOrder headOrder = repository.findById(id);
        repository.delete(headOrder);
    }

    public HeadOrderInfoRes readDetails(Integer id) {
        HeadOrder HeadOrder = repository.findById(id);
        return HeadOrderInfoRes.toDto(HeadOrder);
    }

    public HeadOrderInfoListRes readList() {
        List<HeadOrder> headOrders = repository.findAll();
        return HeadOrderInfoListRes.toDto(headOrders);
    }

    public PageRes<HeadOrderInfoRes> readList(PageReq pageReq) {
        Page<HeadOrder> entityPage = repository.findAll(pageReq);
        Page<HeadOrderInfoRes> dtoPage = HeadOrderInfoRes.toDtoPage(entityPage);
        return PageRes.toDto(dtoPage);
    }

    public HeadOrderInfoRes update(Integer id, String itemName, Integer unitPrice, Integer quantity, Integer totalPrice, String deliveryAddress, String deliveryRequest, String orderRequest) {
        HeadOrder headOrder = repository.findById(id);

        if (!itemName.isEmpty()) {
            headOrder.updateItemName(itemName);
        }
        if (!unitPrice.equals(headOrder.getUnitPrice())) {
            headOrder.updateUnitPrice(unitPrice);
        }
        if (!quantity.equals(headOrder.getQuantity())) {
            headOrder.updateQuantity(quantity);
        }
        if (!totalPrice.equals(headOrder.getTotalPrice())) {
            headOrder.updateTotalPrice(totalPrice);
        }
        if(!deliveryAddress.isEmpty()){
            headOrder.updateDeliveryAddress(deliveryAddress);
        }
        if(!deliveryRequest.isEmpty()){
            headOrder.updateDeliveryRequest(deliveryRequest);
        }
        if(!orderRequest.isEmpty()){
            headOrder.updateOrderRequest(orderRequest);
        }

        HeadOrder update = repository.update(headOrder);
        return HeadOrderInfoRes.toDto(update);
    }
}
