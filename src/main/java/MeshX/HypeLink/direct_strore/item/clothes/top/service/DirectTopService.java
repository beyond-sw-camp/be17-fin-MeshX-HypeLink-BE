package MeshX.HypeLink.direct_strore.item.clothes.top.service;



import MeshX.HypeLink.direct_strore.item.clothes.top.model.dto.request.DirectTopCreateReq;
import MeshX.HypeLink.direct_strore.item.clothes.top.model.dto.response.DirectTopInfoListRes;
import MeshX.HypeLink.direct_strore.item.clothes.top.model.dto.response.DirectTopInfoRes;
import MeshX.HypeLink.direct_strore.item.clothes.top.model.entity.DirectTopClothes;
import MeshX.HypeLink.direct_strore.item.clothes.top.model.entity.TopClothesCategory;
import MeshX.HypeLink.direct_strore.item.clothes.top.repsoitory.DirectTopJpaRepositoryVerify;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("directStoreTopService")
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DirectTopService {
    private final DirectTopJpaRepositoryVerify repository;

    @Transactional
    public void createTop(DirectTopCreateReq dto) {
        repository.createTop(dto.toEntity());
    }

    public DirectTopInfoListRes readList() {
        List<DirectTopClothes> tops = repository.findAll();
        return DirectTopInfoListRes.toDto(tops);
    }

    public DirectTopInfoRes readDetails(Integer id) {
        DirectTopClothes top = repository.findById(id);
        return DirectTopInfoRes.toDto(top);
    }

    public void delete(Integer id) {
        DirectTopClothes top = repository.findById(id);
        repository.delete(top);
    }

    @Transactional
    public DirectTopInfoRes update(Integer id, TopClothesCategory category, Integer amount, String name, String content, String company, String itemCode, Integer stock, Boolean longSleeve, String neckline, String size, String season, String gender) {
        DirectTopClothes top = repository.findById(id);

        if(!category.equals(top.getCategory())){
            top.updateCategory(category);
        }
        if(!content.isEmpty()){
            top.updateContent(content);
        }
        if(!company.isEmpty()){
            top.updateCompany(company);
        }
        if(!itemCode.isEmpty()){
            top.updateItemCode(itemCode);
        }
        if(!stock.equals(top.getStock())){
            top.updateStock(stock);
        }
        if(!name.isEmpty()){
            top.updateName(name);
        }
        if(!amount.equals(top.getAmount())){
            top.updateAmount(amount);
        }
        if(!longSleeve.equals(top.getLongSleeve())){
            top.updateLongSleeve(longSleeve);
        }
        if(!neckline.isEmpty()){
            top.updateNeckLine(neckline);
        }
        if(!size.isEmpty()){
            top.updateSize(size);
        }
        if(!season.isEmpty()){
            top.updateSeason(season);
        }
        if(!gender.equals(top.getGender())){
            top.updateGender(gender);
        }

         DirectTopClothes update = repository.update(top);
        return DirectTopInfoRes.toDto(update);
    }
}