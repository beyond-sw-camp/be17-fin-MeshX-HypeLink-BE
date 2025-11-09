package com.example.apiitem.item.adaptor.in;

import MeshX.common.BaseResponse;
import MeshX.common.Page.PageRes;
import MeshX.common.WebAdapter;
import com.example.apiitem.item.usecase.port.in.ItemDetailWebPort;
import com.example.apiitem.item.usecase.port.in.request.CreateItemDetailsCommand;
import com.example.apiitem.item.usecase.port.out.response.ItemAndItemDetailInfoDto;
import com.example.apiitem.item.usecase.port.out.response.ItemDetailsInfoListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@WebAdapter
@RequestMapping("/api/item/detail")
@RequiredArgsConstructor
public class ItemDetailWebAdaptor {
    private final ItemDetailWebPort itemDetailWebPort;

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ItemAndItemDetailInfoDto>> getItemDetailInfoById(@PathVariable Integer id) {
        ItemAndItemDetailInfoDto result = itemDetailWebPort.findItemDetailById(id);

        return ResponseEntity.status(200).body(BaseResponse.of(result));
    }

    @GetMapping("/detailcode/{detailCode}")
    public ResponseEntity<BaseResponse<ItemAndItemDetailInfoDto>> getItemDetailInfoByDetailCode(@PathVariable String detailCode) {
        ItemAndItemDetailInfoDto result = itemDetailWebPort.findItemDetailByItemDetailCode(detailCode);

        return ResponseEntity.status(200).body(BaseResponse.of(result));
    }

    @GetMapping("/item/{id}")
    public ResponseEntity<BaseResponse<ItemDetailsInfoListDto>> getItemDetailsByItem(@PathVariable Integer id) {
        ItemDetailsInfoListDto result = itemDetailWebPort.findItemDetailsByItem(id);

        return ResponseEntity.status(200).body(BaseResponse.of(result));
    }

    @PostMapping("/creates")
    public ResponseEntity<BaseResponse<String>> saveDetails(@RequestBody CreateItemDetailsCommand dto) {
        itemDetailWebPort.saveItemDetails(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("수정이 완료되었습니다."));
    }
}
