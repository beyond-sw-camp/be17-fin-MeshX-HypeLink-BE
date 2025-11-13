package MeshX.HypeLink.head_office.order.service;

import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.auth.model.entity.Store;
import MeshX.HypeLink.auth.repository.MemberJpaRepositoryVerify;
import MeshX.HypeLink.common.TryCatchTemplate;
import MeshX.HypeLink.common.exception.BaseException;
import MeshX.HypeLink.head_office.item.model.entity.ItemDetail;
import MeshX.HypeLink.head_office.item.repository.ItemDetailJpaRepositoryVerify;
import MeshX.HypeLink.head_office.order.exception.PurchaseOrderException;
import MeshX.HypeLink.head_office.order.model.dto.request.PurchaseOrderUpdateReq;
import MeshX.HypeLink.head_office.order.model.dto.response.PurchaseOrderInfoDetailRes;
import MeshX.HypeLink.head_office.order.model.entity.PurchaseDetailStatus;
import MeshX.HypeLink.head_office.order.model.entity.PurchaseOrder;
import MeshX.HypeLink.head_office.order.model.entity.PurchaseOrderState;
import MeshX.HypeLink.head_office.order.repository.PurchaseOrderJpaRepositoryVerify;
import jakarta.persistence.LockTimeoutException;
import jakarta.persistence.PessimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static MeshX.HypeLink.head_office.order.exception.PurchaseOrderExceptionMessage.UNDER_ZERO;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PessimisticLockService {
    private static final int MAX_RETRY = 3;
    private static final long BASE_BACKOFF = 200L;

    private final PurchaseOrderJpaRepositoryVerify orderRepository;
    private final MemberJpaRepositoryVerify memberRepository;
    private final ItemDetailJpaRepositoryVerify itemDetailRepository;

    // 비관적 락 재시도 로직 (try-catch 완전 제거)
    @Transactional(rollbackFor = Exception.class)
    public PurchaseOrderInfoDetailRes update(PurchaseOrderUpdateReq dto) {
        int[] attempt = {0};

        return TryCatchTemplate.parse(
                () -> executeUpdateWithRetry(dto, attempt),
                this::handleException
        );
    }

    /**
     * 재시도 루프 수행
     */
    private PurchaseOrderInfoDetailRes executeUpdateWithRetry(PurchaseOrderUpdateReq dto, int[] attempt) throws Exception {
        while (attempt[0] < MAX_RETRY) {
            attempt[0]++;
            return tryProcessOrder(dto, attempt);
        }
        throw new BaseException(null);
    }

    /**
     * 락 충돌 감지 및 재시도 제어
     */
    private PurchaseOrderInfoDetailRes tryProcessOrder(PurchaseOrderUpdateReq dto, int[] attempt) throws Exception {
        return TryCatchTemplate.parse(
                () -> processOrderUpdate(dto),
                e -> handleLockOrRethrow(e, dto, attempt)
        );
    }

    /**
     * 락 예외 처리 및 백오프
     */
    private void handleLockOrRethrow(Exception e, PurchaseOrderUpdateReq dto, int[] attempt) {
        if (e instanceof LockTimeoutException || e instanceof PessimisticLockException) {
            log.warn("락 충돌 발생 (attempt={}) — 재시도 준비", attempt[0]);
            if (attempt[0] >= MAX_RETRY) {
                throw new BaseException(null);
            }
            backoff(attempt[0]);
            // 재귀 재시도
            update(dto);
        } else {
            handleException(e);
        }
    }

    /**
     * 점진적 백오프
     */
    private void backoff(int attempt) {
        long delay = BASE_BACKOFF * attempt;
        log.debug("⏳ 백오프 {}ms", delay);
        TryCatchTemplate.parse(() -> Thread.sleep(delay), e -> {
            Thread.currentThread().interrupt();
            throw new BaseException(null);
        });
    }

    /**
     * 공통 예외 처리
     */
    private void handleException(Exception e) {
        log.error("주문 업데이트 처리 중 예외 발생: {}", e.getMessage(), e);
        throw new BaseException(null);
    }

    /**
     * 실제 비즈니스 로직
     */
    private PurchaseOrderInfoDetailRes processOrderUpdate(PurchaseOrderUpdateReq dto) {
        PurchaseOrder purchaseOrder = orderRepository.findById(dto.getOrderId());
        Member headMember = memberRepository.findByEmail("hq@company.com");
        PurchaseOrderState state = PurchaseOrderState.valueOf(dto.getOrderState());

        if (state.equals(PurchaseOrderState.COMPLETED)) {
            if (purchaseOrder.getRequester().equals(headMember)) {
                ItemDetail itemDetail = itemDetailRepository.findByIdWithLock(purchaseOrder.getItemDetail().getId());
                itemDetail.updateStock(purchaseOrder.getQuantity());
                if (itemDetail.getStock() < 0) {
                    throw new PurchaseOrderException(UNDER_ZERO);
                }
                itemDetailRepository.merge(itemDetail);
            }
        } else {
            purchaseOrder.updateOrderDetailStatus(PurchaseDetailStatus.OTHER_CANCELLATION);
        }

        purchaseOrder.updateOrderState(state);
        PurchaseOrder updated = orderRepository.update(purchaseOrder);
        return PurchaseOrderInfoDetailRes.toDto(updated);
    }
}
