package com.example.apiitem.item.adaptor.out;

import MeshX.common.PersistenceAdapter;
import MeshX.common.exception.BaseException;
import com.example.apiitem.item.adaptor.out.jpa.*;
import com.example.apiitem.item.domain.Category;
import com.example.apiitem.item.domain.Item;
import com.example.apiitem.item.usecase.port.out.ItemPersistencePort;
import com.example.apiitem.util.ItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class ItemPersistenceAdaptor implements ItemPersistencePort {
    private final ItemRepository itemRepository;
    private final ItemQueryDSLRepository itemQueryDSLRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Item findById(Item domain) {
        ItemEntity entity = findEntityById(domain.getId());
        return ItemMapper.toDomain(entity);
    }

    @Override
    public Item findByItemCode(Item domain) {
        ItemEntity entity = findEntityByItemCode(domain);
        return ItemMapper.toDomain(entity);
    }

    @Override
    public Page<Item> findItemsWithPaging(Pageable pageable, String keyWord, String category) {
        Page<ItemEntity> paging = itemQueryDSLRepository.findItemsWithPaging(pageable, keyWord, category);
        return paging.map(ItemMapper::toDomain);
    }

    @Override
    public boolean isExist(Item domain) {
        try {
            findEntityByItemCode(domain);
            return true;
        } catch (BaseException e) {
            return false;
        }
    }

    @Override
    public Item save(Item item) {
        CategoryEntity category = findCategoryByCategoryName(item.getCategory());
        ItemEntity entity = ItemMapper.toEntity(category, item);

        ItemEntity save = itemRepository.save(entity);

        return ItemMapper.toDomain(save);
    }

    private CategoryEntity findCategoryByCategoryName(String category) {
        Optional<CategoryEntity> optional = categoryRepository.findByCategory(category);
        if(optional.isPresent()) {
            return optional.get();
        }
        throw new BaseException(null);
    }

    @Override
    public void updateContents(Integer itemId, String content) {
        ItemEntity entity = findEntityById(itemId);

        entity.updateContent(content);

        itemRepository.save(entity);
    }

    @Override
    public void updateEnName(Integer itemId, String enName) {
        ItemEntity entity = findEntityById(itemId);

        entity.updateEnName(enName);

        itemRepository.save(entity);
    }

    @Override
    public void updateKoName(Integer itemId, String koName) {
        ItemEntity entity = findEntityById(itemId);

        entity.updateKoName(koName);

        itemRepository.save(entity);
    }

    @Override
    public void updateAmount(Integer itemId, Integer amount) {
        ItemEntity entity = findEntityById(itemId);

        entity.updateAmount(amount);

        itemRepository.save(entity);
    }

    @Override
    public void updateUnitPrice(Integer itemId, Integer unitPrice) {
        ItemEntity entity = findEntityById(itemId);

        entity.updateUnitPrice(unitPrice);

        itemRepository.save(entity);
    }

    @Override
    public void updateCompany(Integer itemId, String company) {
        ItemEntity entity = findEntityById(itemId);

        entity.updateCompany(company);

        itemRepository.save(entity);
    }

    @Override
    public void updateCategory(Integer itemId, Category category) {
        CategoryEntity categoryEntity = findCategoryById(category);
        ItemEntity entity = findEntityById(itemId);

        entity.updateCategory(categoryEntity);

        itemRepository.save(entity);
    }

    private CategoryEntity findCategoryById(Category category) {
        Optional<CategoryEntity> optionalCategory = categoryRepository.findById(category.getId());
        if(optionalCategory.isPresent()) {
            return optionalCategory.get();
        }
        throw new BaseException(null);
    }

    private ItemEntity findEntityById(Integer id) {
        Optional<ItemEntity> item = itemRepository.findById(id);
        if(item.isPresent()) {
            return item.get();
        }
        throw new BaseException(null);
    }

    private ItemEntity findEntityByItemCode(Item domain) {
        Optional<ItemEntity> item = itemRepository.findByItemCode(domain.getItemCode());
        if(item.isPresent()) {
            return item.get();
        }
        throw new BaseException(null);
    }
}
