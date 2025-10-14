package MeshX.HypeLink.direct_strore.item.backpack.controller;


import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.direct_strore.item.backpack.model.dto.request.DirectBackpackCreateReq;
import MeshX.HypeLink.direct_strore.item.backpack.model.dto.request.DirectBackpackUpdateReq;
import MeshX.HypeLink.direct_strore.item.backpack.model.dto.response.DirectBackpackInfoListRes;
import MeshX.HypeLink.direct_strore.item.backpack.model.dto.response.DirectBackpackInfoRes;
import MeshX.HypeLink.direct_strore.item.backpack.service.DirectBackpackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("directStoreBackpackController")
@RequestMapping("/api/direct-store/item/backpack")
@RequiredArgsConstructor
public class DirectBackpackController {
    private final DirectBackpackService directBackpackService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<String>> createBackpack(@RequestBody DirectBackpackCreateReq dto){
        directBackpackService.createBackpack(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("가방 재고가 생성되었습니다."));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<BaseResponse<DirectBackpackInfoRes>> readBackpack(@PathVariable Integer id){
        DirectBackpackInfoRes directBackpackInfoRes = directBackpackService.readDetails(id);
        return ResponseEntity.status(200).body(BaseResponse.of(directBackpackInfoRes));
    }

    @GetMapping("/read/all")
    public ResponseEntity<BaseResponse<DirectBackpackInfoListRes>> readBackpacks(){
        DirectBackpackInfoListRes directBackpackInfoListRes = directBackpackService.readList();
        return ResponseEntity.status(200).body(BaseResponse.of(directBackpackInfoListRes));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<String>> deleteBackpack(@PathVariable Integer id){
        directBackpackService.delete(id);
        return ResponseEntity.status(200).body(BaseResponse.of("가방재고가 성공적으로 삭제 되었습니다."));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<BaseResponse<DirectBackpackInfoRes>>updateBackpack(@PathVariable Integer id,
                                                                             @RequestBody DirectBackpackUpdateReq dto){
        DirectBackpackInfoRes directBackpackInfoRes = directBackpackService.update(id, dto.getCategory(), dto.getAmount(), dto.getName(), dto.getContent(), dto.getCompany(), dto.getItemCode(), dto.getStock(), dto.getWaterproof(), dto.getCapacity());
        return ResponseEntity.status(200).body(BaseResponse.of(directBackpackInfoRes));
    }

}
