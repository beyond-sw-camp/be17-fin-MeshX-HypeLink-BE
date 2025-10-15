package MeshX.HypeLink.direct_store.posOrder.service;

import MeshX.HypeLink.direct_store.pos.posOrder.exception.PosOrderException;
import MeshX.HypeLink.direct_store.pos.posOrder.model.dto.request.PosOrderCreateReq;
import MeshX.HypeLink.direct_store.pos.posOrder.model.dto.request.PosOrderItemDto;
import MeshX.HypeLink.direct_store.pos.posOrder.model.dto.response.PosOrderDetailRes;
import MeshX.HypeLink.direct_store.pos.posOrder.model.dto.response.PosOrderInfoRes;
import MeshX.HypeLink.direct_store.pos.posOrder.model.entity.PosOrder;
import MeshX.HypeLink.direct_store.pos.posOrder.model.entity.PosOrderItem;
import MeshX.HypeLink.direct_store.pos.posOrder.model.entity.PosOrderStatus;
import MeshX.HypeLink.direct_store.pos.posOrder.repository.PosOrderJpaRepositoryVerify;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static MeshX.HypeLink.direct_store.pos.posOrder.exception.PosOrderExceptionMessage.ORDER_ITEMS_EMPTY;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PosOrderService {
    private final PosOrderJpaRepositoryVerify repository;

    // Private record to hold calculated amounts
    private record OrderAmounts(int total, int discount, int finalAmount) {}

    @Transactional
    public PosOrderDetailRes createOrder(PosOrderCreateReq dto) {
        validateOrderRequest(dto);

        OrderAmounts amounts = calculateAmounts(dto);

        String orderNumber = generateOrderNumber();

        PosOrder order = buildOrder(dto, orderNumber, amounts);

        PosOrder savedOrder = repository.save(order);
        return PosOrderDetailRes.toDto(savedOrder);
    }

    private void validateOrderRequest(PosOrderCreateReq dto) {
        if (dto.getItems() == null || dto.getItems().isEmpty()) {
            throw new PosOrderException(ORDER_ITEMS_EMPTY);
        }
    }

    private OrderAmounts calculateAmounts(PosOrderCreateReq dto) {
        int totalAmount = dto.getItems().stream()
                .mapToInt(PosOrderItemDto::getSubtotal)
                .sum();

        int discountAmount = dto.getItems().stream()
                .mapToInt(item -> {
                    if (item.getDiscountPrice() != null && item.getDiscountPrice() > 0) {
                        return (item.getUnitPrice() - item.getDiscountPrice()) * item.getQuantity();
                    }
                    return 0;
                })
                .sum();

        int finalAmount = totalAmount
                - (dto.getPointsUsed() != null ? dto.getPointsUsed() : 0)
                - (dto.getCouponDiscount() != null ? dto.getCouponDiscount() : 0);

        return new OrderAmounts(totalAmount, discountAmount, finalAmount);
    }

    private PosOrder buildOrder(PosOrderCreateReq dto, String orderNumber, OrderAmounts amounts) {
        PosOrder order = dto.toEntity(orderNumber, amounts.total(), amounts.discount(), amounts.finalAmount());

        for (PosOrderItemDto itemDto : dto.getItems()) {
            PosOrderItem posOrderItem = itemDto.toEntity();
            order.addOrderItem(posOrderItem);
        }
        return order;
    }

    public PosOrderDetailRes readOrderDetail(Integer id) {
        PosOrder order = repository.findById(id);
        return PosOrderDetailRes.toDto(order);
    }

    public PosOrderDetailRes readOrderByOrderNumber(String orderNumber) {
        PosOrder order = repository.findByOrderNumber(orderNumber);
        return PosOrderDetailRes.toDto(order);
    }

    public List<PosOrderInfoRes> readAllOrders() {
        List<PosOrder> orders = repository.findAll();
        return orders.stream()
                .map(PosOrderInfoRes::toDto)
                .collect(Collectors.toList());
    }

    public List<PosOrderInfoRes> readTodayOrders() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(23, 59, 59);

        List<PosOrder> orders = repository.findByDateRange(startOfDay, endOfDay);
        return orders.stream()
                .map(PosOrderInfoRes::toDto)
                .collect(Collectors.toList());
    }

    public List<PosOrderInfoRes> readOrdersByMemberId(Integer memberId) {
        List<PosOrder> orders = repository.findByMemberId(memberId);
        return orders.stream()
                .map(PosOrderInfoRes::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateOrderStatus(Integer id, PosOrderStatus status) {
        PosOrder order = repository.findById(id);
        order.updateStatus(status);
        repository.save(order);
    }

    @Transactional
    public void cancelOrder(Integer id) {
        PosOrder order = repository.findById(id);
        order.updateStatus(PosOrderStatus.CANCELLED);
        repository.save(order);
    }

    // 주문 번호 생성 (ORD + YYYYMMDD + 3자리 시퀀스)
    private String generateOrderNumber() {
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String randomSeq = String.format("%03d", (int)(Math.random() * 1000));
        return "ORD" + dateStr + randomSeq;
    }
}