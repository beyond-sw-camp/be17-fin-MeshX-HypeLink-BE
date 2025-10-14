package MeshX.HypeLink.head_office.item.backpack.service;


import MeshX.HypeLink.head_office.item.backpack.model.dto.request.HeadBackpackCreateReq;
import MeshX.HypeLink.head_office.item.backpack.model.dto.response.HeadBackpackInfoListRes;
import MeshX.HypeLink.head_office.item.backpack.model.dto.response.HeadBackpackInfoRes;
import MeshX.HypeLink.head_office.item.backpack.model.entity.BackPack;
import MeshX.HypeLink.head_office.item.backpack.model.entity.BackPackCategory;
import MeshX.HypeLink.head_office.item.backpack.repository.HeadBackpackJpaRepositoryVerify;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("headOfficeBackpackService")
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HeadBackpackService {
    private final HeadBackpackJpaRepositoryVerify repository;

    @Transactional
    public void createBackpack(HeadBackpackCreateReq dto) {
        repository.createBackpack(dto.toEntity());
    }

    public HeadBackpackInfoListRes readList() {
        List<BackPack> backpacks = repository.findAll();
        return HeadBackpackInfoListRes.toDto(backpacks);
    }

    public HeadBackpackInfoRes readDetails(Integer id) {
        BackPack backpack = repository.findById(id);
        return HeadBackpackInfoRes.toDto(backpack);
    }

    public void delete(Integer id) {
        BackPack backpack = repository.findById(id);
        repository.delete(backpack);
    }

    @Transactional
    public HeadBackpackInfoRes update(Integer id, BackPackCategory category, Integer amount, String name, String content, String company, String itemCode, Integer stock, Boolean waterproof, Integer capacity) {
        BackPack backpack = repository.findById(id);

        if(!category.equals(backpack.getCategory())){
            backpack.updateCategory(category);
        }
        if(!capacity.equals(backpack.getCapacity())){
            backpack.updateCapacity(capacity);
        }
        if(!content.isEmpty()){
            backpack.updateContent(content);
        }
        if(!company.isEmpty()){
            backpack.updateCompany(company);
        }
        if(!itemCode.isEmpty()){
            backpack.updateItemCode(itemCode);
        }
        if(!stock.equals(backpack.getStock())){
            backpack.updateStock(stock);
        }
        if(!name.isEmpty()){
            backpack.updateName(name);
        }
        if(!amount.equals(backpack.getAmount())){
            backpack.updateAmount(amount);
        }
        if(!waterproof.equals(backpack.getWaterproof())){
            backpack.updateWaterproof(waterproof);
        }

        BackPack update = repository.update(backpack);
        return HeadBackpackInfoRes.toDto(update);
    }
}
