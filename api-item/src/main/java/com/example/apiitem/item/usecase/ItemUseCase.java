package com.example.apiitem.item.usecase;

import MeshX.common.Page.PageRes;
import MeshX.common.UseCase;
import MeshX.common.exception.BaseException;
import com.example.apiitem.item.domain.*;
import com.example.apiitem.item.usecase.port.in.WebPort;
import com.example.apiitem.item.usecase.port.in.request.*;
import com.example.apiitem.item.usecase.port.out.CategoryPersistencePort;
import com.example.apiitem.item.usecase.port.out.ItemDetailPersistencePort;
import com.example.apiitem.item.usecase.port.out.ItemImagePersistencePort;
import com.example.apiitem.item.usecase.port.out.ItemPersistencePort;
import com.example.apiitem.item.usecase.port.out.response.ItemInfoDto;
import com.example.apiitem.item.usecase.util.S3UrlBuilder;
import com.example.apiitem.util.ItemDetailMapper;
import com.example.apiitem.util.ItemImageMapper;
import com.example.apiitem.util.ItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@UseCase
@RequiredArgsConstructor
public class ItemUseCase implements WebPort {
    private final ItemPersistencePort itemPersistencePort;
    private final CategoryPersistencePort categoryPersistencePort;
    private final ItemImagePersistencePort itemImagePersistencePort;
    private final ItemDetailPersistencePort itemDetailPersistencePort;

    private final S3UrlBuilder s3UrlBuilder;

    @Override
    public ItemInfoDto findItemById(Integer id) {
        Item domain = ItemMapper.toDomain(id);
        Item item = itemPersistencePort.findById(domain);
        return ItemMapper.toDto(item, this::exportS3Url);
    }

    @Override
    public ItemInfoDto findItemsByItemCode(String code) {
        Item domain = ItemMapper.toDomain(code);
        Item item = itemPersistencePort.findByItemCode(domain);

        return ItemMapper.toDto(item, this::exportS3Url);
    }

    @Override
    public PageRes<ItemInfoDto> findItemsWithPaging(Pageable pageable, String keyWord, String category) {
        Page<Item> items = itemPersistencePort.findItemsWithPaging(pageable, keyWord, category);
        Page<ItemInfoDto> mapped = items.map(item -> ItemMapper.toDto(item, this::exportS3Url));
        return PageRes.toDto(mapped);
    }

    @Override
    public void saveItem(CreateItemCommand command) {
        Item domain = ItemMapper.toDomain(command);
        if(!itemPersistencePort.isExist(domain)) {
            Item saveItem = itemPersistencePort.save(domain);

            command.getItemImages().forEach(one -> {
                saveItemImage(one, saveItem.getId());
            });

            saveItemDetails(command, saveItem);
        }
    }

    private void saveItemDetails(CreateItemCommand dto, Item entity) {
        List<ItemDetail> itemDetails = dto.getItemDetailList()
                .stream().map(ItemDetailMapper::toDomain).toList();

        itemDetailPersistencePort.saveAll(itemDetails, entity);
    }

    @Override
    public void updateContents(UpdateItemContentCommand dto) {
        itemPersistencePort.updateContents(dto.getItemId(), dto.getContent());
    }

    @Override
    public void updateEnName(UpdateItemEnNameCommand dto) {
        itemPersistencePort.updateEnName(dto.getItemId(), dto.getEnName());
    }

    @Override
    public void updateKoName(UpdateItemKoNameCommand dto) {
        itemPersistencePort.updateKoName(dto.getItemId(), dto.getKoName());
    }

    @Override
    public void updateAmount(UpdateItemAmountCommand dto) {
        itemPersistencePort.updateAmount(dto.getItemId(), dto.getAmount());
    }

    @Override
    public void updateUnitPrice(UpdateItemUnitPriceCommand dto) {
        itemPersistencePort.updateUnitPrice(dto.getItemId(), dto.getUnitPrice());
    }

    @Override
    public void updateCompany(UpdateItemCompanyCommand dto) {
        itemPersistencePort.updateCompany(dto.getItemId(), dto.getCompany());
    }

    @Override
    public void updateCategory(UpdateItemCategoryCommand dto) {
        Category category = categoryPersistencePort.findByName(dto.getCategory());
        itemPersistencePort.updateCategory(dto.getItemId(), category);
    }

    @Override
    public void updateImages(UpdateItemImagesCommand dto) {
        Item domain = ItemMapper.toDomain(dto.getItemId());
        Item item = itemPersistencePort.findById(domain);
        List<ItemImage> itemImages = itemImagePersistencePort.findByItem(item);

        // 1. 프론트에서 넘어온 originalFilename 목록 추출
        Set<String> requestedFilenames = dto.getImages().stream()
                .map(CreateItemImageCommand::getOriginalFilename)
                .collect(Collectors.toSet());

        // 2. 기존 DB에 있으나 프론트 요청에는 없는 이미지 삭제
        itemImages.stream()
                .filter(itemImage -> !requestedFilenames.contains(itemImage.getOriginalFilename()))
                .forEach(itemImagePersistencePort::delete);

        dto.getImages().forEach(one -> {
            try {
                updateImageIndex(one);
            } catch (BaseException e) {
                saveItemImage(one, dto.getItemId());
            }
        });
    }


    private void updateImageIndex(CreateItemImageCommand one) {
        // id로 이미지를 찾도록 변경 (originalFilename으로 찾으면 중복 가능)
        itemImagePersistencePort.updateImageIndex(one);
    }

    private void saveItemImage(CreateItemImageCommand one, Integer itemId) {
        ItemImage entity = ItemImageMapper.toDomain(one);
        itemImagePersistencePort.save(entity, itemId);
    }

    public String exportS3Url(String imagePath) {
        return s3UrlBuilder.buildPublicUrl(imagePath);
    }
}
