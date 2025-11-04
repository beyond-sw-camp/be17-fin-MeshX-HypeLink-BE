package MeshX.HypeLink.head_office.item.service;

import MeshX.HypeLink.head_office.item.model.dto.response.CategoryInfoListRes;
import MeshX.HypeLink.head_office.item.model.entity.Category;
import MeshX.HypeLink.head_office.item.repository.CategoryJpaRepositoryVerify;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryJpaRepositoryVerify categoryRepository;

    public CategoryInfoListRes findAll() {
        List<Category> categories = categoryRepository.findAll();
        return CategoryInfoListRes.toDto(categories);
    }

}
