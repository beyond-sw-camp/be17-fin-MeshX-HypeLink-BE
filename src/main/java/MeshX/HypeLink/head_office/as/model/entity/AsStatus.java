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

    // description(한글 설명)으로 Enum 찾기
    public static AsStatus fromDescription(String description) {
        for (AsStatus status : values()) {
            if (status.getDescription().equals(description)) {
                return status;
            }
        }
        throw new IllegalArgumentException("유효하지 않은 상태입니다: " + description);
    }
}
