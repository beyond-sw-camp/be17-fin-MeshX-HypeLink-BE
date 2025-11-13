package com.example.apiitem.item.usecase;

import MeshX.common.UseCase;
import com.example.apiitem.item.domain.Category;
import com.example.apiitem.item.usecase.port.in.CategoryWebPort;
import com.example.apiitem.item.usecase.port.out.CategoryPersistencePort;
import com.example.apiitem.item.usecase.port.out.response.CategoryInfoListDto;
import com.example.apiitem.util.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryUseCase implements CategoryWebPort {
    private final CategoryPersistencePort categoryPersistencePort;

    @Override
    public CategoryInfoListDto findAll() {
        List<Category> categories = categoryPersistencePort.findAll();

        return CategoryMapper.toDto(categories);
    }
}
