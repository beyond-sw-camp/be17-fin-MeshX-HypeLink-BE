package MeshX.HypeLink.direct_store.item.clothes.top.controller;


import MeshX.HypeLink.common.BaseResponse;

import MeshX.HypeLink.direct_store.item.clothes.top.model.dto.request.DirectTopCreateReq;
import MeshX.HypeLink.direct_store.item.clothes.top.model.dto.request.DirectTopUpdateReq;
import MeshX.HypeLink.direct_store.item.clothes.top.model.dto.response.DirectTopInfoListRes;
import MeshX.HypeLink.direct_store.item.clothes.top.model.dto.response.DirectTopInfoRes;
import MeshX.HypeLink.direct_store.item.clothes.top.service.DirectTopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("directStoreTopController")
@RequestMapping("/api/direct-store/item/top")
@RequiredArgsConstructor
public class DirectTopController {
    private final DirectTopService directTopService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<String>> createTop(@RequestBody DirectTopCreateReq dto){
        directTopService.createTop(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("상의 재고가 생성되었습니다."));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<BaseResponse<DirectTopInfoRes>> readTop(@PathVariable Integer id){
        DirectTopInfoRes directTopInfoRes = directTopService.readDetails(id);
        return ResponseEntity.status(200).body(BaseResponse.of(directTopInfoRes));
    }

    @GetMapping("/read/all")
    public ResponseEntity<BaseResponse<DirectTopInfoListRes>> readTop(){
        DirectTopInfoListRes directTopInfoListRes = directTopService.readList();
        return ResponseEntity.status(200).body(BaseResponse.of(directTopInfoListRes));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<String>> deleteTop(@PathVariable Integer id){
        directTopService.delete(id);
        return ResponseEntity.status(200).body(BaseResponse.of("상의 재고가 성공적으로 삭제 되었습니다."));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<BaseResponse<DirectTopInfoRes>>updateTop(@PathVariable Integer id,
                                                                   @RequestBody DirectTopUpdateReq dto){
        DirectTopInfoRes directTopInfoRes = directTopService.update(id, dto.getCategory(), dto.getAmount(), dto.getName(), dto.getContent(), dto.getCompany(), dto.getItemCode(), dto.getStock(), dto.getLongSleeve(), dto.getNeckline(), dto.getSize(), dto.getGender(), dto.getSeason());
        return ResponseEntity.status(200).body(BaseResponse.of(directTopInfoRes));
    }

}
