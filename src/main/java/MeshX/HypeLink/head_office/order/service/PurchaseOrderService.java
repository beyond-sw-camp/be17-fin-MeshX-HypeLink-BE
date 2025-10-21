package MeshX.HypeLink.head_office.order.service;

import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.auth.repository.MemberJpaRepositoryVerify;
import MeshX.HypeLink.common.Page.PageReq;
import MeshX.HypeLink.common.Page.PageRes;
import MeshX.HypeLink.common.exception.BaseException;
import MeshX.HypeLink.head_office.item.model.entity.Item;
import MeshX.HypeLink.head_office.item.repository.ItemJpaRepositoryVerify;
import MeshX.HypeLink.head_office.order.model.dto.request.HeadPurchaseOrderCreateReq;
import MeshX.HypeLink.head_office.order.model.dto.request.PurchaseOrderCreateReq;
import MeshX.HypeLink.head_office.order.model.dto.request.PurchaseOrderUpdateReq;
import MeshX.HypeLink.head_office.order.model.dto.response.PurchaseOrderInfoDetailListRes;
import MeshX.HypeLink.head_office.order.model.dto.response.PurchaseOrderInfoDetailRes;
import MeshX.HypeLink.head_office.order.model.dto.response.PurchaseOrderInfoRes;
import MeshX.HypeLink.head_office.order.model.entity.PurchaseOrder;
import MeshX.HypeLink.head_office.order.model.entity.PurchaseOrderState;
import MeshX.HypeLink.head_office.order.repository.PurchaseOrderJpaRepositoryVerify;
import jakarta.persistence.LockTimeoutException;
import jakarta.persistence.PessimisticLockException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PurchaseOrderService {
    private static final int MAX_RETRY = 3;

    private final PurchaseOrderJpaRepositoryVerify orderRepository;
    private final ItemJpaRepositoryVerify itemRepository;
    private final MemberJpaRepositoryVerify memberRepository;

    @Transactional
    public void createHeadOrder(HeadPurchaseOrderCreateReq dto) {
        Item item = itemRepository.findById(dto.getItemId());
        Member requester = memberRepository.findByEmail("hq@company.com");
        Member supplier = memberRepository.findByEmail("hq@company.com");

        orderRepository.createOrder(dto.toEntity(item, requester, supplier));
    }

    @Transactional
    public void createOrder(PurchaseOrderCreateReq dto) {
        Item item = itemRepository.findById(dto.getItemId());
        Member requester = memberRepository.findByEmail(dto.getRequestEmail());
        Member supplier = memberRepository.findByEmail(dto.getSupplierEmail());

        orderRepository.createOrder(dto.toEntity(item, requester, supplier));
    }

    public PurchaseOrderInfoDetailRes readDetails(Integer id) {
        PurchaseOrder PurchaseOrder = orderRepository.findById(id);
        return PurchaseOrderInfoDetailRes.toDto(PurchaseOrder);
    }

    public PurchaseOrderInfoDetailListRes readList() {
        List<PurchaseOrder> purchaseOrders = orderRepository.findAll();
        return PurchaseOrderInfoDetailListRes.toDto(purchaseOrders);
    }

    public PageRes<PurchaseOrderInfoDetailRes> readList(Pageable pageReq) {
        Page<PurchaseOrder> orders = orderRepository.findAll(pageReq);
        Page<PurchaseOrderInfoDetailRes> dtoPage = PurchaseOrderInfoDetailRes.toDtoPage(orders);
        return PageRes.toDto(dtoPage);
    }

    public PageRes<PurchaseOrderInfoRes> readPurchaseOrderList(Pageable pageReq) {
        Page<PurchaseOrderInfoRes> page = itemRepository.findItemsAndPurchaseOrdersWithPaging(pageReq);
        return PageRes.toDto(page);
    }

    // 비관적 락을 못 얻었을 때 재시도 로직
    @Transactional(rollbackFor = Exception.class)
    public PurchaseOrderInfoDetailRes update(PurchaseOrderUpdateReq dto) {
        int attempt = 0;

        while (true) {
            try {
                return processOrderUpdate(dto); // 핵심 처리 메서드
            } catch (LockTimeoutException | PessimisticLockException e) {
                attempt++;
                if (attempt >= MAX_RETRY) {
                    throw new BaseException(null); // 커스텀 예외
                }
                try {
                    Thread.sleep(200L * attempt); // 점진적 backoff
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new BaseException(null);
                }
            }
        }
    }

    // 비관적 락으로 업데이트 로직
    private PurchaseOrderInfoDetailRes processOrderUpdate(PurchaseOrderUpdateReq dto) {
        PurchaseOrder purchaseOrder = orderRepository.findById(dto.getOrderId());
        PurchaseOrderState state = PurchaseOrderState.valueOf(dto.getOrderState());

        if(state.equals(PurchaseOrderState.COMPLETED)) {
            Item item = itemRepository.findByIdWithLock(purchaseOrder.getItem().getId());
            item.updateStock(purchaseOrder.getQuantity());
            itemRepository.merge(item);
        }

        purchaseOrder.updateOrderState(state);

        PurchaseOrder update = orderRepository.update(purchaseOrder);
        return PurchaseOrderInfoDetailRes.toDto(update);
    }
}
