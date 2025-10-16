package MeshX.HypeLink.direct_store.posOrder.model.entity;

public enum PosOrderStatus {
    PENDING,      // 대기 (결제 전)
    PAID,         // 결제 완료
    COMPLETED,    // 주문 완료
    CANCELLED,    // 취소
    REFUNDED      // 환불
}