package MeshX.HypeLink.image.exception;

import MeshX.HypeLink.common.exception.ExceptionType;
import org.springframework.http.HttpStatus;

public enum ImageExceptionType implements ExceptionType {
    FILE_EMPTY("파일 크기 에러", "파일이 비어있습니다."),
    FILE_TOO_LARGE("파일 크기 에러", "파일 크기는 10MB를 초과할 수 없습니다."),
    UNSUPPORTED_FILE_TYPE("지원 형식 에러", "지원하지 않는 파일 형식입니다. (jpg, jpeg, png, gif, webp만 가능)"),
    UPLOAD_FAILED("파일 업로드 에러", "파일 업로드에 실패했습니다."),
    IMAGE_NOT_FOUND("이미지 검색 에러", "이미지를 찾을 수 없습니다."),
    INVALID_IMAGE_IDX("이미지 id 에러", "유효하지 않은 이미지 ID입니다.");

    private final String title;
    private final String message;

    ImageExceptionType(String title, String message) {
        this.title = title;
        this.message = message;
    }


    @Override
    public String title() {
        return title;
    }

    @Override
    public String message() {
        return message;
    }


}