package MeshX.HypeLink.head_office.item.clothes.top.service;




import MeshX.HypeLink.head_office.item.clothes.top.model.dto.request.HeadTopCreateReq;
import MeshX.HypeLink.head_office.item.clothes.top.model.dto.response.HeadTopInfoListRes;
import MeshX.HypeLink.head_office.item.clothes.top.model.dto.response.HeadTopInfoRes;
import MeshX.HypeLink.head_office.item.clothes.top.model.entity.TopClothes;
import MeshX.HypeLink.head_office.item.clothes.top.model.entity.TopClothesCategory;
import MeshX.HypeLink.head_office.item.clothes.top.repository.HeadTopJpaRepositoryVerify;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("headOfficeTopService")
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HeadTopService {
    private final HeadTopJpaRepositoryVerify repository;

    @Transactional
    public void createTop(HeadTopCreateReq dto) {
        repository.createTop(dto.toEntity());
    }

    public HeadTopInfoListRes readList() {
        List<TopClothes> tops = repository.findAll();
        return HeadTopInfoListRes.toDto(tops);
    }

    public HeadTopInfoRes readDetails(Integer id) {
        TopClothes top = repository.findById(id);
        return HeadTopInfoRes.toDto(top);
    }

    public void delete(Integer id) {
        TopClothes top = repository.findById(id);
        repository.delete(top);
    }

    @Transactional
    public HeadTopInfoRes update(Integer id, TopClothesCategory category, Integer amount, String name, String content, String company, String itemCode, Integer stock, Boolean longSleeve, String neckline, String size, String season, String gender) {
        TopClothes top = repository.findById(id);

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

         TopClothes update = repository.update(top);
        return HeadTopInfoRes.toDto(update);
    }
}