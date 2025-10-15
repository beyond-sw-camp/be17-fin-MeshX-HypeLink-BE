package MeshX.HypeLink.direct_store.item.shoes.service;

import MeshX.HypeLink.direct_store.item.shoes.model.dto.request.DirectShoesCreateReq;
import MeshX.HypeLink.direct_store.item.shoes.model.dto.response.DirectShoesInfoListRes;
import MeshX.HypeLink.direct_store.item.shoes.model.dto.response.DirectShoesInfoRes;

import MeshX.HypeLink.direct_store.item.shoes.model.entity.DirectShoes;
import MeshX.HypeLink.direct_store.item.shoes.model.entity.ShoesCategory;
import MeshX.HypeLink.direct_store.item.shoes.repository.DirectShoesJpaRepositoryVerify;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("directStoreShoesService")
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DirectShoesService {
    private final DirectShoesJpaRepositoryVerify repository;

    @Transactional
    public void createShoes(DirectShoesCreateReq dto) {
        repository.createShoes(dto.toEntity());
    }

    public DirectShoesInfoListRes readList() {
        List<DirectShoes> shoes = repository.findAll();
        return DirectShoesInfoListRes.toDto(shoes);
    }

    public DirectShoesInfoRes readDetails(Integer id) {
        DirectShoes shoes = repository.findById(id);
        return DirectShoesInfoRes.toDto(shoes);
    }

    public void delete(Integer id) {
        DirectShoes shoes = repository.findById(id);
        repository.delete(shoes);
    }

    @Transactional
    public DirectShoesInfoRes update(Integer id, ShoesCategory category, Integer amount, String name, String content, String company, String itemCode, Integer stock, Boolean waterproof, Integer size) {
        DirectShoes shoes = repository.findById(id);

        if(!category.equals(shoes.getCategory())){
            shoes.updateCategory(category);
        }
        if(!size.equals(shoes.getSize())){
            shoes.updateSize(size);
        }
        if(!content.isEmpty()){
            shoes.updateContent(content);
        }
        if(!company.isEmpty()){
            shoes.updateCompany(company);
        }
        if(!itemCode.isEmpty()){
            shoes.updateItemCode(itemCode);
        }
        if(!stock.equals(shoes.getStock())){
            shoes.updateStock(stock);
        }
        if(!name.isEmpty()){
            shoes.updateName(name);
        }
        if(!amount.equals(shoes.getAmount())){
            shoes.updateAmount(amount);
        }
        if(!waterproof.equals(shoes.getWaterproof())){
            shoes.updateWaterproof(waterproof);
        }

        DirectShoes update = repository.update(shoes);
        return DirectShoesInfoRes.toDto(update);
    }
}
