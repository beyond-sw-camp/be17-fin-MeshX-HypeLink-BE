package MeshX.HypeLink.head_office.item.exception;

import MeshX.HypeLink.common.exception.ExceptionType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoryExceptionMessage implements ExceptionType {
    NOTFOUND_ID("카테고리 Error", "해당하는 Id를 가진 데이터가 존재하지 않습니다. Id를 확인해주세요."),
    NOTFOUND_NAME("카테고리 Error", "해당하는 이름를 가진 데이터가 존재하지 않습니다. 입력값을 확인해주세요.");

    private final String title;
    private final String messages;

    @Override
    public String title() {
        return title;
    }

    @Override
    public String message() {
        return messages;
    }
}
