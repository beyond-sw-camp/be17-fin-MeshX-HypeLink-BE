package MeshX.HypeLink.direct_store.item.backpack.service;

import MeshX.HypeLink.common.Page.PageReq;
import MeshX.HypeLink.common.Page.PageRes;
import MeshX.HypeLink.direct_store.item.backpack.model.dto.request.DirectBackpackCreateReq;
import MeshX.HypeLink.direct_store.item.backpack.model.dto.response.DirectBackpackInfoListRes;
import MeshX.HypeLink.direct_store.item.backpack.model.dto.response.DirectBackpackInfoRes;
import MeshX.HypeLink.direct_store.item.backpack.model.entity.BackPackCategory;
import MeshX.HypeLink.direct_store.item.backpack.model.entity.DirectBackPack;
import MeshX.HypeLink.direct_store.item.backpack.repository.DirectBackpackJpaRepositoryVerify;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("directStoreBackpackService")
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DirectBackpackService {
    private final DirectBackpackJpaRepositoryVerify repository;

    @Transactional
    public void createBackpack(DirectBackpackCreateReq dto) {
        repository.createBackpack(dto.toEntity());
    }

    public DirectBackpackInfoListRes readList() {//비페이징용
        List<DirectBackPack> backpacks = repository.findAll();
        return DirectBackpackInfoListRes.toDto(backpacks);
    }

    public PageRes<DirectBackpackInfoRes> readList(PageReq pageReq) {//페이징용
        pageReq.validate();
        Page<DirectBackPack> entityPage = repository.findAll(pageReq);
        Page<DirectBackpackInfoRes> dtoPage = DirectBackpackInfoRes.toDtoPage(entityPage);
        return PageRes.toDto(dtoPage);
    }


    public DirectBackpackInfoRes readDetails(Integer id) {
        DirectBackPack backpack = repository.findById(id);
        return DirectBackpackInfoRes.toDto(backpack);
    }

    public void delete(Integer id) {
        DirectBackPack backpack = repository.findById(id);
        repository.delete(backpack);
    }

    @Transactional
    public DirectBackpackInfoRes update(Integer id, BackPackCategory category, Integer amount, String name, String content, String company, String itemCode, Integer stock, Boolean waterproof, Integer capacity) {
        DirectBackPack backpack = repository.findById(id);

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

        DirectBackPack update = repository.update(backpack);
        return DirectBackpackInfoRes.toDto(update);
    }
}
