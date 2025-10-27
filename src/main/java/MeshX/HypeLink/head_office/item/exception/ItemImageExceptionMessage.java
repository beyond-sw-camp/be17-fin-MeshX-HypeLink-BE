package MeshX.HypeLink.head_office.item.exception;

import MeshX.HypeLink.common.exception.ExceptionType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ItemImageExceptionMessage implements ExceptionType {
    NOTFOUND_ID("아이템 이미지 Error", "해당하는 Id를 가진 데이터가 존재하지 않습니다. Id를 확인해주세요."),
    NOTFOUND_ITEM_AND_IMAGE("아이템 이미지 Error", "해당하는 아이템과 이미지를 가진 데이터가 존재하지 않습니다. 입력값을 확인해주세요."),
    NOTFOUND_ITEM_CODE("아이템 이미지 Error", "해당하는 아이템 코드를 가진 데이터가 존재하지 않습니다. 입력값을 확인해주세요.")
    ;

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
