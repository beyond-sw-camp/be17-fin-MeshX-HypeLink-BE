package MeshX.HypeLink.head_office.shipment.model.entity;

public enum ShipmentStatus {
    PREPARING,        // 출고 준비
    DRIVER_ASSIGNED,  // 기사 배정완
    IN_PROGRESS,      // 출고 중
    COMPLETED,        // 출고 완료
    DELAYED,          // 출고 지연
    CANCELLED         // 출고 취소
}