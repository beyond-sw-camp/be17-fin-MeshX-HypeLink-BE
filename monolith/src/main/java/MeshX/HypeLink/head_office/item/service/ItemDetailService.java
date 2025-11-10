package MeshX.HypeLink.head_office.item.service;

import MeshX.HypeLink.common.Page.PageRes;
import MeshX.HypeLink.head_office.item.model.dto.request.CreateItemDetailsReq;
import MeshX.HypeLink.head_office.item.model.dto.request.SaveItemDetailsReq;
import MeshX.HypeLink.head_office.item.model.dto.response.ItemDetailsInfoListRes;
import MeshX.HypeLink.head_office.item.model.dto.response.ItemAndItemDetailInfoRes;
import MeshX.HypeLink.head_office.item.model.entity.Item;
import MeshX.HypeLink.head_office.item.model.entity.ItemDetail;
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
public class ItemDetailService {
    private final ItemJpaRepositoryVerify itemRepository;
    private final ItemDetailJpaRepositoryVerify itemDetailRepository;
    private final ColorJpaRepositoryVerify colorRepository;
    private final SizeJpaRepositoryVerify sizeRepository;

    public ItemAndItemDetailInfoRes findItemDetailById(Integer id) {
        ItemDetail itemDetail = itemDetailRepository.findById(id);
        return ItemAndItemDetailInfoRes.toDto(itemDetail);
    }

    public ItemAndItemDetailInfoRes findItemDetailByItemDetailCode(String detailCode) {
        ItemDetail itemDetail = itemDetailRepository.findByItemDetailCode(detailCode);
        return ItemAndItemDetailInfoRes.toDto(itemDetail);
    }

    public PageRes<ItemAndItemDetailInfoRes> findItemDetails(Pageable pageable) {
        Page<ItemDetail> itemDetailPage = itemDetailRepository.findByAll(pageable);
        Page<ItemAndItemDetailInfoRes> mapped = itemDetailPage.map(ItemAndItemDetailInfoRes::toDto);
        return PageRes.toDto(mapped);
    }

    public ItemDetailsInfoListRes findItemDetailsByItem(Integer itemId) {
        List<ItemDetail> itemDetails = itemDetailRepository.findByItemId(itemId);

        return ItemDetailsInfoListRes.toDto(itemDetails);
    }

    @Transactional
    public void createItemDetails(CreateItemDetailsReq dto) {
        Item item = itemRepository.findById(dto.getItemId());
        List<ItemDetail> itemDetails = toEntitiesByItemDetails(dto, item);
        itemDetailRepository.saveAll(itemDetails);
    }

    @Transactional
    public void saveItemDetails(SaveItemDetailsReq dto) {
        Item item = itemRepository.findById(dto.getItemId());
        List<ItemDetail> itemDetails = toEntitiesByItemDetails(dto, item);
        itemDetailRepository.saveAllWithId(itemDetails);
    }

    private List<ItemDetail> toEntitiesByItemDetails(CreateItemDetailsReq dto, Item item) {
        return dto.getDetails().stream().map(one -> ItemDetail.builder()
                        .item(item)
                        .stock(one.getStock())
                        .color(colorRepository.findByName(one.getColor()))
                        .size(sizeRepository.findByName(one.getSize()))
                        .itemDetailCode(one.getItemDetailCode())
                        .build()
                )
                .toList();
    }

    private List<ItemDetail> toEntitiesByItemDetails(SaveItemDetailsReq dto, Item item) {
        return dto.getDetails().stream().map(one -> ItemDetail.builder()
                        .item(item)
                        .stock(one.getStock())
                        .color(colorRepository.findByName(one.getColor()))
                        .size(sizeRepository.findByName(one.getSize()))
                        .itemDetailCode(one.getItemDetailCode())
                        .build()
                )
                .toList();
    }
}
