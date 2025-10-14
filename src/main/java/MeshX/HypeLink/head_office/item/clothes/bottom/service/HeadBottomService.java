package MeshX.HypeLink.head_office.item.clothes.bottom.service;


import MeshX.HypeLink.head_office.item.clothes.bottom.model.dto.request.HeadBottomCreateReq;
import MeshX.HypeLink.head_office.item.clothes.bottom.model.dto.response.HeadBottomInfoListRes;
import MeshX.HypeLink.head_office.item.clothes.bottom.model.dto.response.HeadBottomInfoRes;
import MeshX.HypeLink.head_office.item.clothes.bottom.model.entity.BottomClothes;
import MeshX.HypeLink.head_office.item.clothes.bottom.model.entity.BottomClothesCategory;
import MeshX.HypeLink.head_office.item.clothes.bottom.repository.HeadBottomJpaRepositoryVerify;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("headOfficeBottomService")
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HeadBottomService {
    private final HeadBottomJpaRepositoryVerify repository;

    @Transactional
    public void createBottom(HeadBottomCreateReq dto) {
        repository.createBottom(dto.toEntity());
    }

    public HeadBottomInfoListRes readList() {
        List<BottomClothes> bottoms = repository.findAll();
        return HeadBottomInfoListRes.toDto(bottoms);
    }

    public HeadBottomInfoRes readDetails(Integer id) {
        BottomClothes bottom = repository.findById(id);
        return HeadBottomInfoRes.toDto(bottom);
    }

    public void delete(Integer id) {
        BottomClothes bottom = repository.findById(id);
        repository.delete(bottom);
    }

    @Transactional
    public HeadBottomInfoRes update(Integer id, BottomClothesCategory category, Integer amount, String name, String content, String company, String itemCode, Integer stock, Integer waist, Integer length, String size, String season, String gender) {
        BottomClothes bottom = repository.findById(id);

        if(!category.equals(bottom.getCategory())){
            bottom.updateCategory(category);
        }
        if(!content.isEmpty()){
            bottom.updateContent(content);
        }
        if(!company.isEmpty()){
            bottom.updateCompany(company);
        }
        if(!itemCode.isEmpty()){
            bottom.updateItemCode(itemCode);
        }
        if(!stock.equals(bottom.getStock())){
            bottom.updateStock(stock);
        }
        if(!name.isEmpty()){
            bottom.updateName(name);
        }
        if(!amount.equals(bottom.getAmount())){
            bottom.updateAmount(amount);
        }
        if(!waist.equals(bottom.getWaist())){
            bottom.updateWaist(waist);
        }
        if(!length.equals(bottom.getLength())){
            bottom.updateLength(length);
        }
        if(!size.isEmpty()){
            bottom.updateSize(size);
        }
        if(!season.isEmpty()){
            bottom.updateSeason(season);
        }
        if(!gender.equals(bottom.getGender())){
            bottom.updateGender(gender);
        }

        BottomClothes update = repository.update(bottom);
        return HeadBottomInfoRes.toDto(update);
    }
}