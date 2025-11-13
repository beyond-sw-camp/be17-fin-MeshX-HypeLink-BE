package com.example.apiitem.item.adaptor.in;

import MeshX.common.BaseResponse;
import MeshX.common.WebAdapter;
import com.example.apiitem.item.usecase.port.in.CategoryWebPort;
import com.example.apiitem.item.usecase.port.out.response.CategoryInfoListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@WebAdapter
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryWebAdaptor {
    private final CategoryWebPort categoryWebPort;

    @GetMapping("/all")
    public ResponseEntity<BaseResponse<CategoryInfoListDto>> getCategories() {
        CategoryInfoListDto dto = categoryWebPort.findAll();

        return ResponseEntity.status(200).body(BaseResponse.of(dto));
    }
}
