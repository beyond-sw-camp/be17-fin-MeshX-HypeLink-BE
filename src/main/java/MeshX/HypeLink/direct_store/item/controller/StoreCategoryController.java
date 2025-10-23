package MeshX.HypeLink.direct_store.item.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.direct_store.item.model.dto.request.SaveStoreCategoriesReq;
import MeshX.HypeLink.direct_store.item.model.dto.request.SaveStoreCategoryReq;
import MeshX.HypeLink.direct_store.item.model.dto.response.StoreCategoryInfoListRes;
import MeshX.HypeLink.direct_store.item.service.StoreCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/direct/category")
@RequiredArgsConstructor
public class StoreCategoryController {
    private final StoreCategoryService storeCategoryService;

    @PostMapping("/save/all")
    public ResponseEntity<BaseResponse<String>> saveCategoryList(@RequestBody SaveStoreCategoriesReq dto) {
        storeCategoryService.saveAll(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("카테고리를 저장하였습니다."));
    }

    @GetMapping("/list")
    public ResponseEntity<BaseResponse<StoreCategoryInfoListRes>> getCategoryList() {
        StoreCategoryInfoListRes result = storeCategoryService.getList();
        return ResponseEntity.status(200).body(BaseResponse.of(result));
    }
}
