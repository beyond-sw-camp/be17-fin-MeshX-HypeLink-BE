package MeshX.HypeLink.head_office.coupon.exception;

import MeshX.HypeLink.common.exception.ExceptionType;

public enum CouponExceptionType implements ExceptionType {

    COUPON_NOT_FOUNT("쿠폰 검색 에러","해당 쿠폰을 찾을 수 없습니다");

    private final String title;
    private final String message;

    CouponExceptionType(String title, String message) {
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
