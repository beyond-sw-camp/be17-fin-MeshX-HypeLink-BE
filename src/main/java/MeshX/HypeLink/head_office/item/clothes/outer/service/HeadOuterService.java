package MeshX.HypeLink.head_office.item.clothes.outer.service;


import MeshX.HypeLink.head_office.item.clothes.outer.model.dto.request.HeadOuterCreateReq;
import MeshX.HypeLink.head_office.item.clothes.outer.model.dto.response.HeadOuterInfoListRes;
import MeshX.HypeLink.head_office.item.clothes.outer.model.dto.response.HeadOuterInfoRes;
import MeshX.HypeLink.head_office.item.clothes.outer.model.entity.OuterClothes;
import MeshX.HypeLink.head_office.item.clothes.outer.model.entity.OuterClothesCategory;
import MeshX.HypeLink.head_office.item.clothes.outer.repository.HeadOuterJpaRepositoryVerify;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("headOfficeOuterService")
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HeadOuterService {
    private final HeadOuterJpaRepositoryVerify repository;

    @Transactional
    public void createOuter(HeadOuterCreateReq dto) {
        repository.createOuter(dto.toEntity());
    }

    public HeadOuterInfoListRes readList() {
        List<OuterClothes> outers = repository.findAll();
        return HeadOuterInfoListRes.toDto(outers);
    }

    public HeadOuterInfoRes readDetails(Integer id) {
        OuterClothes outer = repository.findById(id);
        return HeadOuterInfoRes.toDto(outer);
    }

    public void delete(Integer id) {
        OuterClothes outer = repository.findById(id);
        repository.delete(outer);
    }

    @Transactional
    public HeadOuterInfoRes update(Integer id, OuterClothesCategory category, Integer amount, String name, String content, String company, String itemCode, Integer stock, Boolean hooded, Boolean waterproof, String size, String season, String gender) {
        OuterClothes outer = repository.findById(id);

        if(!category.equals(outer.getCategory())){
            outer.updateCategory(category);
        }
        if(!content.isEmpty()){
            outer.updateContent(content);
        }
        if(!company.isEmpty()){
            outer.updateCompany(company);
        }
        if(!itemCode.isEmpty()){
            outer.updateItemCode(itemCode);
        }
        if(!stock.equals(outer.getStock())){
            outer.updateStock(stock);
        }
        if(!name.isEmpty()){
            outer.updateName(name);
        }
        if(!amount.equals(outer.getAmount())){
            outer.updateAmount(amount);
        }
        if(!hooded.equals(outer.getHooded())){
            outer.updateHooded(hooded);
        }
        if(!waterproof.equals(outer.getWaterproof())){
            outer.updateWaterproof(waterproof);
        }
        if(!size.isEmpty()){
            outer.updateSize(size);
        }
        if(!season.isEmpty()){
            outer.updateSeason(season);
        }
        if(!gender.equals(outer.getGender())){
            outer.updateGender(gender);
        }

         OuterClothes update = repository.update(outer);
        return HeadOuterInfoRes.toDto(update);
    }
}