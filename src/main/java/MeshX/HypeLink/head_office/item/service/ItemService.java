package MeshX.HypeLink.head_office.item.service;

import MeshX.HypeLink.common.Page.PageRes;
import MeshX.HypeLink.common.s3.S3UrlBuilder;
import MeshX.HypeLink.head_office.item.model.dto.request.*;
import MeshX.HypeLink.head_office.item.model.dto.response.ItemInfoListRes;
import MeshX.HypeLink.head_office.item.model.dto.response.ItemInfoRes;
import MeshX.HypeLink.head_office.item.model.entity.*;
import MeshX.HypeLink.head_office.item.repository.*;
import MeshX.HypeLink.image.exception.ImageException;
import MeshX.HypeLink.image.model.entity.Image;
import MeshX.HypeLink.image.repository.ImageJpaRepositoryVerify;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemJpaRepositoryVerify itemRepository;
    private final ImageJpaRepositoryVerify imageRepository;
    private final ItemImageJpaRepositoryVerify itemImageRepository;
    private final ItemDetailJpaRepositoryVerify itemDetailRepository;

    private final ColorJpaRepositoryVerify colorRepository;
    private final SizeJpaRepositoryVerify sizeRepository;
    private final CategoryJpaRepositoryVerify categoryRepository;
    private final S3UrlBuilder s3UrlBuilder;

    @Transactional
    public void saveItem(CreateItemReq dto) {
        Category category = categoryRepository.findByName(dto.getCategory());

        if(!itemRepository.isExist(dto.getItemCode())) {
            Item entity = dto.toEntity(category);
            Item saveItem = itemRepository.save(entity);

            dto.getItemImages().forEach(one -> {
                saveItemImage(one, saveItem);
            });

            saveItemDetails(dto, entity);
        }
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
        return ItemInfoRes.toDto(item, this::exportS3Url);
    }

    // QueryDSL로 업데이트 예정
    public ItemInfoListRes findItemsByCategory(String category) {
        Category findCategory = categoryRepository.findByName(category);

        List<Item> items = itemRepository.findItemsByCategory(findCategory);

        return ItemInfoListRes.toDto(items, this::exportS3Url);
    }

    // Paging 처리 완료
    public PageRes<ItemInfoRes> findItemsByCategoryWithPaging(String category, Pageable pageable) {
        Category findCategory = categoryRepository.findByName(category);
        Page<Item> items = itemRepository.findItemsByCategoryWithPaging(findCategory, pageable);

        Page<ItemInfoRes> mapped = items.map(item -> ItemInfoRes.toDto(item, this::exportS3Url));

        return PageRes.toDto(mapped);
    }

    // QueryDSL로 업데이트 예정
    public ItemInfoListRes findItems() {
        List<Item> items = itemRepository.findItems();
        return ItemInfoListRes.toDto(items, this::exportS3Url);
    }

    // Paging 처리 완료
    public PageRes<ItemInfoRes> findItemsWithPaging(Pageable pageable, String keyWord, String category) {
        Page<Item> items = itemRepository.findItemsWithPaging(pageable, keyWord, category);
        Page<ItemInfoRes> mapped = items.map(item -> ItemInfoRes.toDto(item, this::exportS3Url));
        return PageRes.toDto(mapped);
    }

    // QueryDSL로 업데이트 예정
    public ItemInfoListRes findItemsByName(String name) {
        List<Item> items = itemRepository.findItemsByName(name);
        return ItemInfoListRes.toDto(items, this::exportS3Url);
    }

    // Paging 처리 완료
    public PageRes<ItemInfoRes> findItemsByNameWithPaging(String name, Pageable pageable) {
        Page<Item> items = itemRepository.findItemsByNameWithPaging(name, pageable);
        Page<ItemInfoRes> mapped = items.map(item -> ItemInfoRes.toDto(item, this::exportS3Url));
        return PageRes.toDto(mapped);
    }

    // QueryDSL로 업데이트 예정
    public ItemInfoRes findItemsByItemCode(String itemCode) {
        Item item = itemRepository.findByItemCode(itemCode);

        return ItemInfoRes.toDto(item, this::exportS3Url);
    }

    @Transactional
    public void updateContents(UpdateItemContentReq dto) {
        Item item = itemRepository.findById(dto.getItemId());

        item.updateContent(dto.getContent());

        itemRepository.merge(item);
    }

    @Transactional
    public void updateEnName(UpdateItemEnNameReq dto) {
        Item item = itemRepository.findById(dto.getItemId());

        item.updateEnName(dto.getEnName());

        itemRepository.merge(item);
    }

    @Transactional
    public void updateKoName(UpdateItemKoNameReq dto) {
        Item item = itemRepository.findById(dto.getItemId());

        item.updateKoName(dto.getKoName());

        itemRepository.merge(item);
    }

    @Transactional
    public void updateAmount(UpdateItemAmountReq dto) {
        Item item = itemRepository.findById(dto.getItemId());

        item.updateAmount(dto.getAmount());

        itemRepository.merge(item);
    }

    @Transactional
    public void updateUnitPrice(UpdateItemUnitPriceReq dto) {
        Item item = itemRepository.findById(dto.getItemId());

        item.updateUnitPrice(dto.getUnitPrice());

        itemRepository.merge(item);
    }

    @Transactional
    public void updateCompany(UpdateItemCompanyReq dto) {
        Item item = itemRepository.findById(dto.getItemId());

        item.updateCompany(dto.getCompany());

        itemRepository.merge(item);
    }

    @Transactional
    public void updateCategory(UpdateItemCategoryReq dto) {
        Item item = itemRepository.findById(dto.getItemId());
        Category category = categoryRepository.findByName(dto.getCategory());

        item.updateCategory(category);

        itemRepository.merge(item);
    }

    // Image 로직 수정
    @Transactional
    public void updateImages(UpdateItemImagesReq dto) {
        Item item = itemRepository.findById(dto.getItemId());
        List<ItemImage> itemImages = itemImageRepository.findByItem(item);

        // 1. 프론트에서 넘어온 originalFilename 목록 추출
        Set<String> requestedFilenames = dto.getImages().stream()
                .map(CreateItemImageReq::getOriginalFilename)
                .collect(Collectors.toSet());

        // 2. 기존 DB에 있으나 프론트 요청에는 없는 이미지 삭제
        itemImages.stream()
                .filter(itemImage -> !requestedFilenames.contains(itemImage.getImage().getOriginalFilename()))
                .forEach(itemImageRepository::delete);

        dto.getImages().forEach(one -> {
            try {
                updateImages(one, item);
            } catch (ImageException e) {
                saveItemImage(one, item);
            }
        });
    }

    private void updateImages(CreateItemImageReq one, Item item) {
        // ✅ id로 이미지를 찾도록 변경 (originalFilename으로 찾으면 중복 가능)
        Image image = imageRepository.findById(one.getId());

        ItemImage itemImage = itemImageRepository.findByItemAndImage(item, image);

        if(!Objects.equals(itemImage.getSortIndex(), one.getIndex())) {
            itemImage.updateIndex(one.getIndex());
            itemImageRepository.save(itemImage);
        }
    }

    private void saveItemImage(CreateItemImageReq one, Item item) {
        Image entity = one.toEntity();
        Image save = imageRepository.save(entity);

        ItemImage itemImage = one.toItemImage(item, save);
        itemImageRepository.save(itemImage);
    }

    public String exportS3Url(Image image) {
        return s3UrlBuilder.buildPublicUrl(image.getSavedPath());
    }
}
