package MeshX.HypeLink.direct_store.item.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.common.Page.PageRes;
import MeshX.HypeLink.direct_store.item.model.dto.request.SaveStoreItemListReq;
import MeshX.HypeLink.direct_store.item.service.StoreItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/store/item")
@RequiredArgsConstructor
public class StoreItemController {
    private final StoreItemService storeItemService;

    @PostMapping("/create/all")
    public ResponseEntity<BaseResponse<String>> saveStoreItem(@RequestBody SaveStoreItemListReq dto) {
        storeItemService.saveAll(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("본사 아이템 리스트와 동기화가 완료되었습니다."));
    }

//    @GetMapping()
//    public ResponseEntity<BaseResponse<PageRes<>>> getItemDetails() {
//
//    }
}
