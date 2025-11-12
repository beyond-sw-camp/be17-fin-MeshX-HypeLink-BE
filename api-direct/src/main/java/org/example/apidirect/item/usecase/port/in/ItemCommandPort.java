package org.example.apidirect.item.usecase.port.in;

public interface ItemCommandPort {
    void updateStock(String itemDetailCode, Integer stockChange);
}
