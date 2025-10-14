package MeshX.HypeLink.head_office.item.shoes.service;


import MeshX.HypeLink.head_office.item.shoes.model.dto.request.HeadShoesCreateReq;
import MeshX.HypeLink.head_office.item.shoes.model.dto.response.HeadShoesInfoListRes;
import MeshX.HypeLink.head_office.item.shoes.model.dto.response.HeadShoesInfoRes;
import MeshX.HypeLink.head_office.item.shoes.model.entity.Shoes;
import MeshX.HypeLink.head_office.item.shoes.model.entity.ShoesCategory;
import MeshX.HypeLink.head_office.item.shoes.repository.HeadShoesJpaRepositoryVerify;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("headOfficeShoesService")
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HeadShoesService {
    private final HeadShoesJpaRepositoryVerify repository;

    @Transactional
    public void createShoes(HeadShoesCreateReq dto) {
        repository.createShoes(dto.toEntity());
    }

    public HeadShoesInfoListRes readList() {
        List<Shoes> shoes = repository.findAll();
        return HeadShoesInfoListRes.toDto(shoes);
    }

    public HeadShoesInfoRes readDetails(Integer id) {
        Shoes shoes = repository.findById(id);
        return HeadShoesInfoRes.toDto(shoes);
    }

    public void delete(Integer id) {
        Shoes shoes = repository.findById(id);
        repository.delete(shoes);
    }

    @Transactional
    public HeadShoesInfoRes update(Integer id, ShoesCategory category, Integer amount, String name, String content, String company, String itemCode, Integer stock, Boolean waterproof, Integer size) {
        Shoes shoes = repository.findById(id);

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

        Shoes update = repository.update(shoes);
        return HeadShoesInfoRes.toDto(update);
    }
}
