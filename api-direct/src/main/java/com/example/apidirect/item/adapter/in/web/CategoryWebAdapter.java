package com.example.apidirect.item.adapter.in.web;

import MeshX.common.BaseResponse;
import MeshX.common.WebAdapter;
import com.example.apidirect.item.adapter.in.web.dto.request.SaveStoreCategoriesRequest;
import com.example.apidirect.item.usecase.port.in.ItemCommandPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@WebAdapter
@RequiredArgsConstructor
@RequestMapping("/api/direct/category")
public class CategoryWebAdapter {

    private final ItemCommandPort itemCommandPort;

    @PostMapping("/save/all")
    public ResponseEntity<BaseResponse<String>> saveCategoriesFromHeadOffice(@RequestBody SaveStoreCategoriesRequest request) {
        itemCommandPort.saveAllCategoriesFromHeadOffice(request);
        return ResponseEntity.ok(BaseResponse.of("카테고리를 저장하였습니다."));
    }
}
