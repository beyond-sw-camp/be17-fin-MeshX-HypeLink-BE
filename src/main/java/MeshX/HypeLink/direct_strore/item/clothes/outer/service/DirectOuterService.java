package MeshX.HypeLink.direct_strore.item.clothes.outer.service;



import MeshX.HypeLink.direct_strore.item.clothes.outer.model.dto.request.DirectOuterCreateReq;
import MeshX.HypeLink.direct_strore.item.clothes.outer.model.dto.response.DirectOuterInfoListRes;
import MeshX.HypeLink.direct_strore.item.clothes.outer.model.dto.response.DirectOuterInfoRes;
import MeshX.HypeLink.direct_strore.item.clothes.outer.model.entity.DirectOuterClothes;
import MeshX.HypeLink.direct_strore.item.clothes.outer.model.entity.OuterClothesCategory;
import MeshX.HypeLink.direct_strore.item.clothes.outer.repository.DirectOuterJpaRepositoryVerify;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("directStoreOuterService")
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DirectOuterService {
    private final DirectOuterJpaRepositoryVerify repository;

    @Transactional
    public void createOuter(DirectOuterCreateReq dto) {
        repository.createOuter(dto.toEntity());
    }

    public DirectOuterInfoListRes readList() {
        List<DirectOuterClothes> outers = repository.findAll();
        return DirectOuterInfoListRes.toDto(outers);
    }

    public DirectOuterInfoRes readDetails(Integer id) {
        DirectOuterClothes outer = repository.findById(id);
        return DirectOuterInfoRes.toDto(outer);
    }

    public void delete(Integer id) {
        DirectOuterClothes outer = repository.findById(id);
        repository.delete(outer);
    }

    @Transactional
    public DirectOuterInfoRes update(Integer id, OuterClothesCategory category, Integer amount, String name, String content, String company, String itemCode, Integer stock, Boolean hooded, Boolean waterproof, String size, String season, String gender) {
        DirectOuterClothes outer = repository.findById(id);

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

         DirectOuterClothes update = repository.update(outer);
        return DirectOuterInfoRes.toDto(update);
    }
}