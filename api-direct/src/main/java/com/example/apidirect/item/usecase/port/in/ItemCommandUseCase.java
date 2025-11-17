package com.example.apidirect.item.usecase.port.in;

public interface ItemCommandUseCase {
    void updateStock(String itemDetailCode, Integer stockChange);
}
