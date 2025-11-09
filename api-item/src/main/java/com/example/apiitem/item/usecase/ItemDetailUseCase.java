package com.example.apiitem.item.usecase;

import MeshX.common.Page.PageRes;
import MeshX.common.UseCase;
import com.example.apiitem.item.domain.Item;
import com.example.apiitem.item.domain.ItemDetail;
import com.example.apiitem.item.usecase.port.in.ItemDetailWebPort;
import com.example.apiitem.item.usecase.port.in.request.CreateItemDetailsCommand;
import com.example.apiitem.item.usecase.port.out.CategoryPersistencePort;
import com.example.apiitem.item.usecase.port.out.ItemDetailPersistencePort;
import com.example.apiitem.item.usecase.port.out.ItemPersistencePort;
import com.example.apiitem.item.usecase.port.out.SizePersistencePort;
import com.example.apiitem.item.usecase.port.out.response.ItemAndItemDetailInfoDto;
import com.example.apiitem.item.usecase.port.out.response.ItemDetailsInfoListDto;
import com.example.apiitem.util.ItemDetailMapper;
import com.example.apiitem.util.ItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemDetailUseCase implements ItemDetailWebPort {
    private final ItemPersistencePort itemPersistencePort;
    private final ItemDetailPersistencePort itemDetailPersistencePort;

    @Override
    public ItemAndItemDetailInfoDto findItemDetailById(Integer id) {
        ItemDetail itemDetail = itemDetailPersistencePort.findById(id);
        return ItemDetailMapper.toDto(itemDetail);
    }

    @Override
    public ItemAndItemDetailInfoDto findItemDetailByItemDetailCode(String detailCode) {
        ItemDetail itemDetail = itemDetailPersistencePort.findByItemDetailCode(detailCode);
        return ItemDetailMapper.toDto(itemDetail);
    }

    @Override
    public ItemDetailsInfoListDto findItemDetailsByItem(Integer id) {
        List<ItemDetail> itemDetails = itemDetailPersistencePort.findByItemId(id);

        return ItemDetailMapper.toDto(itemDetails);
    }

    @Override
    @Transactional
    public void saveItemDetails(CreateItemDetailsCommand command) {
        Item domain = ItemMapper.toDomain(command.getItemId());
        Item item = itemPersistencePort.findById(domain);
        List<ItemDetail> itemDetails = ItemDetailMapper.toDomains(command);
        itemDetailPersistencePort.saveAll(itemDetails, item);
    }
}
