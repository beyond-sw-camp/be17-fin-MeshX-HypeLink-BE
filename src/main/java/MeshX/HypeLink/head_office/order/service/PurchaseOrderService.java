package MeshX.HypeLink.head_office.order.service;

import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.auth.repository.MemberJpaRepositoryVerify;
import MeshX.HypeLink.common.Page.PageRes;
import MeshX.HypeLink.common.exception.BaseException;
import MeshX.HypeLink.head_office.item.model.entity.ItemDetail;
import MeshX.HypeLink.head_office.item.repository.ItemDetailJpaRepositoryVerify;
import MeshX.HypeLink.head_office.order.exception.PurchaseOrderException;
import MeshX.HypeLink.head_office.order.model.dto.request.HeadPurchaseOrderCreateReq;
import MeshX.HypeLink.head_office.order.model.dto.request.PurchaseOrderCreateReq;
import MeshX.HypeLink.head_office.order.model.dto.request.PurchaseOrderUpdateReq;
import MeshX.HypeLink.head_office.order.model.dto.response.*;
import MeshX.HypeLink.head_office.order.model.entity.PurchaseDetailStatus;
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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static MeshX.HypeLink.head_office.order.exception.PurchaseOrderExceptionMessage.UNDER_ZERO;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PurchaseOrderService {
    private static final int MAX_RETRY = 3;

    private final PurchaseOrderJpaRepositoryVerify orderRepository;
    private final ItemDetailJpaRepositoryVerify itemDetailRepository;
    private final MemberJpaRepositoryVerify memberRepository;

    @Transactional
    public void createHeadOrder(HeadPurchaseOrderCreateReq dto) {
        ItemDetail itemDetail = itemDetailRepository.findById(dto.getItemDetailId());
        Member requester = memberRepository.findByEmail("hq@company.com");
        Member supplier = memberRepository.findByEmail("hq@company.com");

        orderRepository.createOrder(dto.toEntity(itemDetail, requester, supplier));
    }

    @Transactional
    public void createOrder(PurchaseOrderCreateReq dto) {
        ItemDetail itemDetail = itemDetailRepository.findById(dto.getItemDetailId());
        Member requester = memberRepository.findByEmail(dto.getRequestEmail());
        Member supplier = memberRepository.findByEmail(dto.getSupplierEmail());

        if(itemDetail.getStock() < dto.getQuantity()) {
            throw new PurchaseOrderException(UNDER_ZERO);
        }

        orderRepository.createOrder(dto.toEntity(itemDetail, requester, supplier));
    }

    public PurchaseOrderInfoDetailRes getDetails(Integer id) {
        PurchaseOrder PurchaseOrder = orderRepository.findById(id);
        return PurchaseOrderInfoDetailRes.toDto(PurchaseOrder);
    }

    public PurchaseOrderInfoDetailListRes getList() {
        List<PurchaseOrder> purchaseOrders = orderRepository.findAll();
        return PurchaseOrderInfoDetailListRes.toDto(purchaseOrders);
    }

    public PageRes<PurchaseOrderInfoDetailRes> getList(Pageable pageReq) {
        Page<PurchaseOrder> orders = orderRepository.findAll(pageReq);
        Page<PurchaseOrderInfoDetailRes> dtoPage = PurchaseOrderInfoDetailRes.toDtoPage(orders);
        return PageRes.toDto(dtoPage);
    }

    public PageRes<PurchaseOrderInfoRes> getPurchaseOrderList(Pageable pageReq, String keyWord, String category) {
        Page<PurchaseOrderInfoRes> page = itemDetailRepository.findItemsAndPurchaseOrdersWithPaging(pageReq, keyWord, category);
        return PageRes.toDto(page);
    }

    public PurchaseOrderStateInfoListRes getPurchaseStateList() {
        List<PurchaseOrderStateInfoRes> states = Arrays.stream(PurchaseOrderState.values())
                .map(state -> PurchaseOrderStateInfoRes.builder()
                        .description(state.getDescription())
                        .build())
                .collect(Collectors.toList());

        return PurchaseOrderStateInfoListRes.builder()
                .purchaseOrderStates(states)
                .build();
    }

    public PurchaseDetailsStatusInfoListRes getPurchaseDetailStatuses() {
        List<PurchaseDetailsStatusInfoRes> states = Arrays.stream(PurchaseDetailStatus.values())
                .map(state -> PurchaseDetailsStatusInfoRes.builder()
                        .description(state.getDescription())
                        .build())
                .collect(Collectors.toList());

        return PurchaseDetailsStatusInfoListRes.builder()
                .purchaseDetailsStatusInfos(states)
                .build();
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
            ItemDetail itemDetail = itemDetailRepository.findByIdWithLock(purchaseOrder.getItemDetail().getId());
            itemDetail.updateStock(purchaseOrder.getQuantity());
            if(itemDetail.getStock() < 0) {
                throw new PurchaseOrderException(UNDER_ZERO);
            }
            itemDetailRepository.merge(itemDetail);
        }

        purchaseOrder.updateOrderState(state);

        PurchaseOrder update = orderRepository.update(purchaseOrder);
        return PurchaseOrderInfoDetailRes.toDto(update);
    }
}
