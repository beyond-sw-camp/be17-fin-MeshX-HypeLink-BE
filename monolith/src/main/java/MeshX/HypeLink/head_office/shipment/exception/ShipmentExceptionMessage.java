package MeshX.HypeLink.head_office.shipment.exception;

import MeshX.HypeLink.common.exception.ExceptionType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ShipmentExceptionMessage implements ExceptionType {
    NOT_FOUND_SHIPMENT("SHIPMENT Error", "해당하는 배송리스트가 없습니다 다시 시도해 주세요.")
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
