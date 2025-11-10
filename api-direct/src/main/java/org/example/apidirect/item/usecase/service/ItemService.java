package org.example.apidirect.item.usecase.service;

import MeshX.common.UseCase;
import lombok.RequiredArgsConstructor;
import org.example.apidirect.common.exception.ItemException;
import org.example.apidirect.common.exception.ItemExceptionMessage;
import org.example.apidirect.item.adapter.in.web.dto.response.StoreItemDetailResponse;
import org.example.apidirect.item.domain.StoreItemDetail;
import org.example.apidirect.item.usecase.port.in.ItemCommandUseCase;
import org.example.apidirect.item.usecase.port.in.ItemQueryUseCase;
import org.example.apidirect.item.usecase.port.out.ItemDetailPersistencePort;
import org.example.apidirect.item.usecase.port.out.ItemDetailQueryPort;
import org.example.apidirect.item.usecase.port.out.ItemPersistencePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService implements ItemQueryUseCase, ItemCommandUseCase {

    private final ItemDetailQueryPort itemDetailQueryPort;
    private final ItemDetailPersistencePort itemDetailPersistencePort;
    private final ItemPersistencePort itemPersistencePort;

    @Override
    public Page<StoreItemDetailResponse> findItemsByStoreId(Integer storeId, Pageable pageable) {
        Page<StoreItemDetail> details = itemDetailQueryPort.findByStoreId(storeId, pageable);
        return details.map(this::toResponse);
    }

    @Override
    public Page<StoreItemDetailResponse> searchItems(Integer storeId, String keyword, Pageable pageable) {
        Page<StoreItemDetail> details = itemDetailQueryPort.findByStoreIdAndKeyword(storeId, keyword, pageable);
        return details.map(this::toResponse);
    }

    @Override
    public Page<StoreItemDetailResponse> findItemsByCategory(Integer storeId, String category, Pageable pageable) {
        Page<StoreItemDetail> details = itemDetailQueryPort.findByStoreIdAndCategory(storeId, category, pageable);
        return details.map(this::toResponse);
    }

    @Override
    public Page<StoreItemDetailResponse> findLowStockItems(Integer storeId, Integer minStock, Pageable pageable) {
        Page<StoreItemDetail> details = itemDetailQueryPort.findByStoreIdAndLowStock(storeId, minStock, pageable);
        return details.map(this::toResponse);
    }

    @Override
    public StoreItemDetailResponse findItemByBarcode(String itemDetailCode) {
        StoreItemDetail detail = itemDetailQueryPort.findByItemDetailCode(itemDetailCode)
                .orElseThrow(() -> new ItemException(ItemExceptionMessage.NOT_FOUND));
        return toResponse(detail);
    }

    @Override
    @Transactional
    public void updateStock(String itemDetailCode, Integer stockChange) {
        StoreItemDetail detail = itemDetailQueryPort.findByItemDetailCodeWithLock(itemDetailCode)
                .orElseThrow(() -> new ItemException(ItemExceptionMessage.NOT_FOUND));

        detail.updateStock(stockChange);
        itemDetailPersistencePort.save(detail);
    }

    private StoreItemDetailResponse toResponse(StoreItemDetail detail) {
        // StoreItem 정보 조회
        var item = itemPersistencePort.findByItemCode(detail.getItemCode())
                .orElseThrow(() -> new ItemException(ItemExceptionMessage.NOT_FOUND));

        return StoreItemDetailResponse.from(
                detail,
                item.getKoName(),
                item.getCategory(),
                item.getAmount()
        );
    }
}
