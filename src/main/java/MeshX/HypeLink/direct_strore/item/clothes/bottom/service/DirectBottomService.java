package MeshX.HypeLink.direct_strore.item.clothes.bottom.service;

import MeshX.HypeLink.direct_strore.item.clothes.bottom.model.dto.request.DirectBottomCreateReq;
import MeshX.HypeLink.direct_strore.item.clothes.bottom.model.dto.response.DirectBottomInfoListRes;
import MeshX.HypeLink.direct_strore.item.clothes.bottom.model.dto.response.DirectBottomInfoRes;
import MeshX.HypeLink.direct_strore.item.clothes.bottom.model.entity.BottomClothesCategory;
import MeshX.HypeLink.direct_strore.item.clothes.bottom.model.entity.DirectBottomClothes;
import MeshX.HypeLink.direct_strore.item.clothes.bottom.repository.DirectBottomJpaRepositoryVerify;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("directStoreBottomService")
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DirectBottomService {
    private final DirectBottomJpaRepositoryVerify repository;

    @Transactional
    public void createBottom(DirectBottomCreateReq dto) {
        repository.createBottom(dto.toEntity());
    }

    public DirectBottomInfoListRes readList() {
        List<DirectBottomClothes> bottoms = repository.findAll();
        return DirectBottomInfoListRes.toDto(bottoms);
    }

    public DirectBottomInfoRes readDetails(Integer id) {
        DirectBottomClothes bottom = repository.findById(id);
        return DirectBottomInfoRes.toDto(bottom);
    }

    public void delete(Integer id) {
        DirectBottomClothes bottom = repository.findById(id);
        repository.delete(bottom);
    }

    @Transactional
    public DirectBottomInfoRes update(Integer id, BottomClothesCategory category, Integer amount, String name, String content, String company, String itemCode, Integer stock, Integer waist, Integer length, String size, String season, String gender) {
        DirectBottomClothes bottom = repository.findById(id);

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

        DirectBottomClothes update = repository.update(bottom);
        return DirectBottomInfoRes.toDto(update);
    }
}