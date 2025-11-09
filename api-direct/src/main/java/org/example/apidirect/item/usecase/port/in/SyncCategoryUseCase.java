package org.example.apidirect.item.usecase.port.in;

import java.util.List;

public interface SyncCategoryUseCase {
    void syncCategories(List<String> categories);
}
