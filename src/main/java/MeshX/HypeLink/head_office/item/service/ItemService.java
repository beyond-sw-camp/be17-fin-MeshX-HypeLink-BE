package MeshX.HypeLink.head_office.item.service;

import MeshX.HypeLink.common.Page.PageRes;
import MeshX.HypeLink.head_office.item.model.dto.request.*;
import MeshX.HypeLink.head_office.item.model.dto.response.ItemInfoListRes;
import MeshX.HypeLink.head_office.item.model.dto.response.ItemInfoRes;
import MeshX.HypeLink.head_office.item.model.entity.*;
import MeshX.HypeLink.head_office.item.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemJpaRepositoryVerify itemRepository;
    private final ItemImageJpaRepositoryVerify itemImageRepository;
    private final ItemDetailJpaRepositoryVerify itemDetailRepository;
    private final ColorJpaRepositoryVerify colorRepository;
    private final SizeJpaRepositoryVerify sizeRepository;
    private final CategoryJpaRepositoryVerify categoryRepository;

    @Transactional
    public void saveItem(CreateItemReq dto) {
        Category category = categoryRepository.findByName(dto.getCategory());
        itemRepository.isExist(dto.getItemCode());

        Item entity = dto.toEntity(category);
        itemRepository.save(entity);

        // Images 저장 로직 필요

        saveItemDetails(dto, entity);
    }

    private void saveItemDetails(CreateItemReq dto, Item entity) {
        List<ItemDetail> itemDetails = dto.getItemDetailList()
                .stream().map(one -> {
            Color findColor = colorRepository.findByName(one.getColor());
            Size findSize = sizeRepository.findByName(one.getSize());
            return one.toEntity(findSize, findColor, entity);
        }).toList();

        itemDetailRepository.saveAll(itemDetails);
    }

    public ItemInfoRes findItemById(Integer id) {
        Item item = itemRepository.findById(id);
        return ItemInfoRes.toDto(item);
    }

    // QueryDSL로 업데이트 예정
    public ItemInfoListRes findItemsByCategory(String category) {
        Category findCategory = categoryRepository.findByName(category);

        List<Item> items = itemRepository.findItemsByCategory(findCategory);

        return ItemInfoListRes.toDto(items);
    }

    // Paging 처리 완료
    public PageRes<ItemInfoRes> findItemsByCategoryWithPaging(String category, Pageable pageable) {
        Category findCategory = categoryRepository.findByName(category);
        Page<Item> items = itemRepository.findItemsByCategoryWithPaging(findCategory, pageable);

        Page<ItemInfoRes> mapped = items.map(ItemInfoRes::toDto);

        return PageRes.toDto(mapped);
    }

    // QueryDSL로 업데이트 예정
    public ItemInfoListRes findItems() {
        List<Item> items = itemRepository.findItems();
        return ItemInfoListRes.toDto(items);
    }

    // Paging 처리 완료
    public PageRes<ItemInfoRes> findItemsWithPaging(Pageable pageable) {
        Page<Item> items = itemRepository.findItemsWithPaging(pageable);
        Page<ItemInfoRes> mapped = items.map(ItemInfoRes::toDto);
        return PageRes.toDto(mapped);
    }

    // QueryDSL로 업데이트 예정
    public ItemInfoListRes findItemsByName(String name) {
        List<Item> items = itemRepository.findItemsByName(name);
        return ItemInfoListRes.toDto(items);
    }

    // Paging 처리 완료
    public PageRes<ItemInfoRes> findItemsByNameWithPaging(String name, Pageable pageable) {
        Page<Item> items = itemRepository.findItemsByNameWithPaging(name, pageable);
        Page<ItemInfoRes> mapped = items.map(ItemInfoRes::toDto);
        return PageRes.toDto(mapped);
    }

    // QueryDSL로 업데이트 예정
    public ItemInfoRes findItemsByItemCode(String itemCode) {
        Item item = itemRepository.findByItemCode(itemCode);

        return ItemInfoRes.toDto(item);
    }

    @Transactional
    public void updateStock(UpdateItemStockReq dto) {
        ItemDetail itemDetail = itemDetailRepository.findByItemDetailCode(dto.getItemDetailCode());

        itemDetail.updateStock(dto.getStock());

        itemDetailRepository.merge(itemDetail);
    }

    @Transactional
    public void updateContents(UpdateItemContentReq dto) {
        Item item = itemRepository.findByItemCode(dto.getItemCode());

        item.updateContent(dto.getContent());

        itemRepository.merge(item);
    }

    @Transactional
    public void updateEnName(UpdateItemEnNameReq dto) {
        Item item = itemRepository.findByItemCode(dto.getItemCode());

        item.updateEnName(dto.getEnName());

        itemRepository.merge(item);
    }

    @Transactional
    public void updateKoName(UpdateItemKoNameReq dto) {
        Item item = itemRepository.findByItemCode(dto.getItemCode());

        item.updateKoName(dto.getKoName());

        itemRepository.merge(item);
    }

    @Transactional
    public void updateAmount(UpdateItemAmountReq dto) {
        Item item = itemRepository.findByItemCode(dto.getItemCode());

        item.updateAmount(dto.getAmount());

        itemRepository.merge(item);
    }

    @Transactional
    public void updateUnitPrice(UpdateItemUnitPriceReq dto) {
        Item item = itemRepository.findByItemCode(dto.getItemCode());

        item.updateUnitPrice(dto.getUnitPrice());

        itemRepository.merge(item);
    }

    @Transactional
    public void updateCompany(UpdateItemCompanyReq dto) {
        Item item = itemRepository.findByItemCode(dto.getItemCode());

        item.updateCompany(dto.getCompany());

        itemRepository.merge(item);
    }

    @Transactional
    public void updateCategory(UpdateItemCategoryReq dto) {
        Item item = itemRepository.findByItemCode(dto.getItemCode());
        Category category = categoryRepository.findByName(dto.getCategory());

        item.updateCategory(category);

        itemRepository.merge(item);
    }

    // Image 로직 수정
    @Transactional
    public void updateImages(UpdateItemImagesReq dto) {
        Item item = itemRepository.findByItemCode(dto.getItemCode());
//        Category category = categoryRepository.findByName(dto.getCategory());
//
//        item.updateCategory(category);

        itemRepository.merge(item);
    }
}
