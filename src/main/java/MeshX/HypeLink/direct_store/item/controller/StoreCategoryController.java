package MeshX.HypeLink.direct_store.item.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.direct_store.item.model.dto.response.StoreCategoryInfoListRes;
import MeshX.HypeLink.direct_store.item.model.dto.response.StoreCategoryInfoRes;
import MeshX.HypeLink.direct_store.item.service.StoreCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/direct/category")
@RequiredArgsConstructor
public class StoreCategoryController {
    private final StoreCategoryService storeCategoryService;

//    @PostMapping("/save")
//    public ResponseEntity<BaseResponse<String>> saveCategory() {
//
//    }
//
//    @PostMapping("/save/all")
//    public ResponseEntity<BaseResponse<String>> saveCategoryList() {
//
//    }
//
//    @GetMapping("/list")
//    public ResponseEntity<BaseResponse<StoreCategoryInfoListRes>> getCategoryList() {
//
//    }
//
//    @GetMapping("/detail/{id}")
//    public ResponseEntity<BaseResponse<StoreCategoryInfoRes>> getCategory() {
//
//    }
}
