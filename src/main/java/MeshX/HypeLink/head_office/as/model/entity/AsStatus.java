package MeshX.HypeLink.head_office.as.model.entity;

import lombok.Getter;

@Getter
public enum AsStatus {
    PENDING("요청 대기 중"),
    APPROVED("승인됨"),
    REJECTED("거절됨"),
    COMPLETED("처리 완료");

    private final String description;

    AsStatus(String description) {
        this.description = description;
    }
}
