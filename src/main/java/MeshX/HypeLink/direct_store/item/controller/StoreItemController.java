package MeshX.HypeLink.direct_store.item.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.direct_store.item.model.dto.request.CreateItemReq;
import MeshX.HypeLink.direct_store.item.model.dto.request.UpdateItemContentReq;
import MeshX.HypeLink.direct_store.item.model.dto.request.UpdateItemStockReq;
import MeshX.HypeLink.direct_store.item.model.dto.response.ItemDetailInfoRes;
import MeshX.HypeLink.direct_store.item.model.dto.response.ItemInfoListRes;
import MeshX.HypeLink.direct_store.item.model.dto.response.ItemUpdateInfoRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "직영점 상품", description = "직영점 상품 관련 API")
@RestController
@RequestMapping("/api/store/item")
@RequiredArgsConstructor
public class StoreItemController {
    @Operation(summary = "상품 상세 정보 조회", description = "ID로 상품의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ItemDetailInfoRes>> getItemInfo(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(BaseResponse.of(ItemDetailInfoRes.builder().build()));
    }

    @Operation(summary = "상품 코드로 상세 정보 조회", description = "상품 코드로 상품의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping("/code/{code}")
    public ResponseEntity<BaseResponse<ItemDetailInfoRes>> getItemInfoFromCode(@PathVariable String code) {
        return ResponseEntity.status(200).body(BaseResponse.of(ItemDetailInfoRes.builder().build()));
    }

    @Operation(summary = "카테고리로 상품 목록 조회", description = "특정 카테고리에 속하는 상품 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping("/category/{category}")
    public ResponseEntity<BaseResponse<ItemInfoListRes>> getItemsfromCategory(@PathVariable String category) {
        return ResponseEntity.status(200).body(BaseResponse.of(ItemInfoListRes.builder().build()));
    }

    @Operation(summary = "제조사로 상품 목록 조회", description = "특정 제조사의 상품 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping("/company/{company}")
    public ResponseEntity<BaseResponse<ItemInfoListRes>> getItemsfromCompany(@PathVariable String company) {
        return ResponseEntity.status(200).body(BaseResponse.of(ItemInfoListRes.builder().build()));
    }

    @Operation(summary = "상품명으로 상품 목록 조회", description = "특정 상품명을 포함하는 상품 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping("/name/{name}")
    public ResponseEntity<BaseResponse<ItemInfoListRes>> getItemsfromName(@PathVariable String name) {
        return ResponseEntity.status(200).body(BaseResponse.of(ItemInfoListRes.builder().build()));
    }

    @Operation(summary = "신규 상품 등록", description = "새로운 상품을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "등록 성공")
    })
    @PostMapping("/create")
    public ResponseEntity<BaseResponse<ItemDetailInfoRes>> createItem(@RequestBody CreateItemReq dto) {
        return ResponseEntity.status(200).body(BaseResponse.of(ItemDetailInfoRes.builder().build()));
    }

    @Operation(summary = "상품 정보 수정", description = "기존 상품의 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공")
    })
    @PatchMapping("/content")
    public ResponseEntity<BaseResponse<ItemUpdateInfoRes>> updateContents(@RequestBody UpdateItemContentReq dto) {
        return ResponseEntity.status(200).body(BaseResponse.of(ItemUpdateInfoRes.builder().build()));
    }

    @Operation(summary = "상품 재고 수정", description = "상품의 재고 수량을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공")
    })
    @PatchMapping("/stock")
    public ResponseEntity<BaseResponse<ItemUpdateInfoRes>> updateStock(@RequestBody UpdateItemStockReq dto) {
        return ResponseEntity.status(200).body(BaseResponse.of(ItemUpdateInfoRes.builder().build()));
    }

    @Operation(summary = "상품 삭제", description = "상품을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<String>> deleteItem(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(BaseResponse.of(""));
    }
}
