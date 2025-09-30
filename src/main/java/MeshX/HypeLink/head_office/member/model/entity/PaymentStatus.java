package MeshX.HypeLink.head_office.member.model.entity;

public enum PaymentStatus {
    READY,       // 결제 대기
    PAID,        // 결제 완료
    CANCELLED,   // 결제 취소
    FAILED       // 결제 실패
}
