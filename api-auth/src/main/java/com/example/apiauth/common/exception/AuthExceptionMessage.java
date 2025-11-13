package com.example.apiauth.common.exception;

import MeshX.common.exception.ExceptionType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AuthExceptionMessage implements ExceptionType {
    EMAIL_ALREADY_EXISTS("이미 존재하는 EMAIL 입니다.", "이미 존재하는 EMAIL 입니다 다시 입력해 주세요."),
    USER_NAME_NOT_FOUND("사용자를 찾을 수 없습니다.", "사용자를 찾을 수 없습니다. ID와 PASSWORD를 확인해주세요"),
    INVALID_CREDENTIALS("인증 정보가 일치하지 않습니다.", "아이디 또는 비밀번호가 일치하지 않습니다."),
    UNAUTHORIZED("인증 실패", "인증 자격 증명이 유효하지 않습니다. 로그인이 필요합니다."),
    ACCESS_DENIED("접근 거부", "해당 리소스에 접근할 권한이 없습니다."),
    STORE_NOT_FOUND("지점을 찾을 수 없습니다.", "요청한 ID에 해당하는 지점을 찾을 수 없습니다."),
    POS_NOT_FOUND("POS를 찾을 수 없습니다.", "요청한 ID에 해당하는 POS를 찾을 수 없습니다."),
    DRIVER_NOT_FOUND("기사를 찾을 수 없습니다.", "요청한 ID에 해당하는 기사를 찾을 수 없습니다."),
    POS_CODE_NOT_MATCH("POS CODE가 올바른 형식이 아닙니다", "PosCode는 대문자3자리+숫자3자리+언더스코어+숫자2자리 형식이어야 합니다. 예: POS123_12 "),
    CANNOT_DELETE_STORE_WITH_POS("매장 삭제 불가", "POS 기기가 등록된 매장은 삭제할 수 없습니다. 먼저 모든 POS를 삭제해주세요."),
    ID_MISMATCH("ID 불일치", "요청한 ID와 실제 데이터의 ID가 일치하지 않습니다."),
    CANNOT_DELETE_LAST_ADMIN("마지막 관리자 삭제 불가", "시스템에 최소 한 명의 관리자가 필요합니다. 마지막 관리자는 삭제할 수 없습니다."),
    CANNOT_DELETE_DRIVER_WITH_ACTIVE_SHIPMENT("기사 삭제 불가", "진행 중인 배송이 있는 기사는 삭제할 수 없습니다. 모든 배송을 완료한 후 삭제해주세요.");


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
