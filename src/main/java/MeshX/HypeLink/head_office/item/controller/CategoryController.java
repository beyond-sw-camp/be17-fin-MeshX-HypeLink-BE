package MeshX.HypeLink.head_office.item.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.head_office.item.model.dto.response.CategoryInfoListRes;
import MeshX.HypeLink.head_office.item.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<BaseResponse<CategoryInfoListRes>> getCategories() {
        CategoryInfoListRes dto = categoryService.findAll();

        return ResponseEntity.status(200).body(BaseResponse.of(dto));
    }
}
