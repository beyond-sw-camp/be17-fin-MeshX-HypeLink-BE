package MeshX.HypeLink.auth.exception;

import MeshX.HypeLink.common.exception.ExceptionType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MemberExceptionMessage implements ExceptionType {
    ID_MISMATCH("잘못된 요청", "요청하신 리소스의 ID와 본문의 ID가 일치하지 않습니다."),
    CANNOT_DELETE_STORE_WITH_POS("삭제 불가", "매장에 연결된 POS 기기가 있어 해당 지점장을 삭제할 수 없습니다."),
    CANNOT_DELETE_LAST_ADMIN("삭제 불가", "시스템에 마지막으로 남은 총괄 관리자는 삭제할 수 없습니다.");


    private final String title;
    private final String messages;

    @Override
    public String title() {
        return this.title;
    }

    @Override
    public String message() {
        return this.messages;
    }
}
