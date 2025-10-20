package MeshX.HypeLink.head_office.item.service;

import MeshX.HypeLink.common.Page.PageRes;
import MeshX.HypeLink.head_office.item.model.dto.request.*;
import MeshX.HypeLink.head_office.item.model.dto.response.ItemDetailInfoRes;
import MeshX.HypeLink.head_office.item.model.dto.response.ItemInfoRes;
import MeshX.HypeLink.head_office.item.model.dto.response.ItemSearchListRes;
import MeshX.HypeLink.head_office.item.model.dto.response.ItemSearchRes;
import MeshX.HypeLink.head_office.item.model.entity.Category;
import MeshX.HypeLink.head_office.item.model.entity.Color;
import MeshX.HypeLink.head_office.item.model.entity.Item;
import MeshX.HypeLink.head_office.item.model.entity.Size;
import MeshX.HypeLink.head_office.item.repository.CategoryJpaRepositoryVerify;
import MeshX.HypeLink.head_office.item.repository.ColorJpaRepositoryVerify;
import MeshX.HypeLink.head_office.item.repository.ItemJpaRepositoryVerify;
import MeshX.HypeLink.head_office.item.repository.SizeJpaRepositoryVerify;
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
    private final ColorJpaRepositoryVerify colorRepository;
    private final SizeJpaRepositoryVerify sizeRepository;
    private final CategoryJpaRepositoryVerify categoryRepository;

    @Transactional
    public void saveItem(CreateItemReq dto) {
        List<CreateItemDetailReq> itemDetails = dto.getItemDetailList();
        Category category = categoryRepository.findByName(dto.getCategory());

        itemRepository.isExist(dto.getItemCode());

        List<Item> items = itemDetails.stream().map(item -> {
            Color color = colorRepository.findByName(item.getColor());
            Size size = sizeRepository.findByName(item.getSize());
            return item.toEntity(dto.getAmount(), dto.getEnName(), dto.getKoName(), dto.getCompany(),
                    dto.getItemCode(), dto.getContent(), category, color,
                    size);
        }).toList();

        itemRepository.saveList(items);
    }

    public ItemInfoRes findItemById(Integer id) {
        Item item = itemRepository.findById(id);
        return ItemInfoRes.toDto(item);
    }

    // QueryDSL로 업데이트 예정
    public ItemSearchListRes findItemsByCategory(String category) {
        Category findCategory = categoryRepository.findByName(category);

        List<Item> items = itemRepository.findItemsByCategory(findCategory);

        return ItemSearchListRes.toDto(items);
    }

    // Paging 처리 완료
    public PageRes<ItemSearchRes> findItemsByCategoryWithPaging(String category, Pageable pageable) {
        Category findCategory = categoryRepository.findByName(category);
        Page<Item> items = itemRepository.findItemsByCategoryWithPaging(findCategory, pageable);

        Page<ItemSearchRes> mapped = items.map(ItemSearchRes::toDto);

        return PageRes.toDto(mapped);
    }

    // QueryDSL로 업데이트 예정
    public ItemSearchListRes findItems() {
        List<Item> items = itemRepository.findItems();
        return ItemSearchListRes.toDto(items);
    }

    // Paging 처리 완료
    public PageRes<ItemSearchRes> findItemsWithPaging(Pageable pageable) {
        Page<Item> items = itemRepository.findItemsWithPaging(pageable);
        Page<ItemSearchRes> mapped = items.map(ItemSearchRes::toDto);
        return PageRes.toDto(mapped);
    }

    // QueryDSL로 업데이트 예정
    public ItemSearchListRes findItemsByName(String name) {
        List<Item> items = itemRepository.findItemsByName(name);
        return ItemSearchListRes.toDto(items);
    }

    // Paging 처리 완료
    public PageRes<ItemSearchRes> findItemsByNameWithPaging(String name, Pageable pageable) {
        Page<Item> items = itemRepository.findItemsByNameWithPaging(name, pageable);
        Page<ItemSearchRes> mapped = items.map(ItemSearchRes::toDto);
        return PageRes.toDto(mapped);
    }

    // QueryDSL로 업데이트 예정
    public ItemDetailInfoRes findItemsByItemCode(String itemCode) {
        List<Item> items = itemRepository.findByItemCode(itemCode);
        return ItemDetailInfoRes.toDto(items);
    }

    // QueryDSL로 업데이트 예정
    public ItemInfoRes findItemByItemDetailCode(String itemDetailCode) {
        Item item = itemRepository.findByItemDetailCode(itemDetailCode);
        return ItemInfoRes.toDto(item);
    }

    @Transactional
    public void updateStock(UpdateItemStockReq dto) {
        Item item = itemRepository.findByItemDetailCode(dto.getItemDetailCode());

        item.updateStock(dto.getStock());

        itemRepository.merge(item);
    }

    @Transactional
    public void updateContents(UpdateItemContentReq dto) {
        Item item = itemRepository.findByItemDetailCode(dto.getItemDetailCode());

        item.updateContent(dto.getContent());

        itemRepository.merge(item);
    }

    @Transactional
    public void updateEnName(UpdateItemEnNameReq dto) {
        Item item = itemRepository.findByItemDetailCode(dto.getItemDetailCode());

        item.updateEnName(dto.getEnName());

        itemRepository.merge(item);
    }

    @Transactional
    public void updateKoName(UpdateItemKoNameReq dto) {
        Item item = itemRepository.findByItemDetailCode(dto.getItemDetailCode());

        item.updateKoName(dto.getKoName());

        itemRepository.merge(item);
    }

    @Transactional
    public void updateAmount(UpdateItemAmountReq dto) {
        Item item = itemRepository.findByItemDetailCode(dto.getItemDetailCode());

        item.updateAmount(dto.getAmount());

        itemRepository.merge(item);
    }

    @Transactional
    public void updateCompany(UpdateItemCompanyReq dto) {
        Item item = itemRepository.findByItemDetailCode(dto.getItemDetailCode());

        item.updateCompany(dto.getCompany());

        itemRepository.merge(item);
    }

    @Transactional
    public void updateCategory(UpdateItemCategoryReq dto) {
        Item item = itemRepository.findByItemDetailCode(dto.getItemDetailCode());
        Category category = categoryRepository.findByName(dto.getCategory());

        item.updateCategory(category);

        itemRepository.merge(item);
    }

    // Image 로직 수정
    @Transactional
    public void updateImages(UpdateItemImagesReq dto) {
        Item item = itemRepository.findByItemDetailCode(dto.getItemDetailCode());
//        Category category = categoryRepository.findByName(dto.getCategory());
//
//        item.updateCategory(category);

        itemRepository.merge(item);
    }
}
