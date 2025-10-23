package MeshX.HypeLink.head_office.item.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.common.Page.PageRes;
import MeshX.HypeLink.head_office.item.model.dto.request.CreateItemDetailsReq;
import MeshX.HypeLink.head_office.item.model.dto.response.ItemDetailsInfoListRes;
import MeshX.HypeLink.head_office.item.model.dto.response.ItemAndItemDetailInfoRes;
import MeshX.HypeLink.head_office.item.service.ItemDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/item/detail")
@RequiredArgsConstructor
public class ItemDetailController {
    private final ItemDetailService itemDetailService;

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ItemAndItemDetailInfoRes>> getItemDetailInfoById(@PathVariable Integer id) {
        ItemAndItemDetailInfoRes result = itemDetailService.findItemDetailById(id);

        return ResponseEntity.status(200).body(BaseResponse.of(result));
    }

    @GetMapping("/detailcode/{detailCode}")
    public ResponseEntity<BaseResponse<ItemAndItemDetailInfoRes>> getItemDetailInfoByDetailCode(@PathVariable String detailCode) {
        ItemAndItemDetailInfoRes result = itemDetailService.findItemDetailByItemDetailCode(detailCode);

        return ResponseEntity.status(200).body(BaseResponse.of(result));
    }

    @GetMapping("/all")
    public ResponseEntity<BaseResponse<PageRes<ItemAndItemDetailInfoRes>>> getItemDetails(Pageable pageable) {
        PageRes<ItemAndItemDetailInfoRes> result = itemDetailService.findItemDetails(pageable);

        return ResponseEntity.status(200).body(BaseResponse.of(result));
    }

    @GetMapping("/item/{id}")
    public ResponseEntity<BaseResponse<ItemDetailsInfoListRes>> getItemDetailsByItem(@PathVariable Integer id) {
        ItemDetailsInfoListRes result = itemDetailService.findItemDetailsByItem(id);

        return ResponseEntity.status(200).body(BaseResponse.of(result));
    }

    @PostMapping("/creates")
    public ResponseEntity<BaseResponse<String>> saveDetails(@RequestBody CreateItemDetailsReq dto) {
        itemDetailService.saveItemDetails(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("수정이 완료되었습니다."));
    }
}
