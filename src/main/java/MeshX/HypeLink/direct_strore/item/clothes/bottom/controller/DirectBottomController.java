package MeshX.HypeLink.direct_strore.item.clothes.bottom.controller;


import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.direct_strore.item.clothes.bottom.model.dto.request.DirectBottomCreateReq;
import MeshX.HypeLink.direct_strore.item.clothes.bottom.model.dto.request.DirectBottomUpdateReq;
import MeshX.HypeLink.direct_strore.item.clothes.bottom.model.dto.response.DirectBottomInfoListRes;
import MeshX.HypeLink.direct_strore.item.clothes.bottom.model.dto.response.DirectBottomInfoRes;
import MeshX.HypeLink.direct_strore.item.clothes.bottom.service.DirectBottomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("directStoreBottomController")
@RequestMapping("/api/direct-store/item/bottom")
@RequiredArgsConstructor
public class DirectBottomController {
    private final DirectBottomService directBottomService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<String>> createBottom(@RequestBody DirectBottomCreateReq dto){
        directBottomService.createBottom(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("하의 재고가 생성되었습니다."));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<BaseResponse<DirectBottomInfoRes>> readBottom(@PathVariable Integer id){
        DirectBottomInfoRes directBottomInfoRes = directBottomService.readDetails(id);
        return ResponseEntity.status(200).body(BaseResponse.of(directBottomInfoRes));
    }

    @GetMapping("/read/all")
    public ResponseEntity<BaseResponse<DirectBottomInfoListRes>> readBottom(){
        DirectBottomInfoListRes directBottomInfoListRes = directBottomService.readList();
        return ResponseEntity.status(200).body(BaseResponse.of(directBottomInfoListRes));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<String>> deleteBottom(@PathVariable Integer id){
        directBottomService.delete(id);
        return ResponseEntity.status(200).body(BaseResponse.of("하의 재고가 성공적으로 삭제 되었습니다."));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<BaseResponse<DirectBottomInfoRes>>updateTop(@PathVariable Integer id,
                                                                      @RequestBody DirectBottomUpdateReq dto){
        DirectBottomInfoRes directBottomInfoRes = directBottomService.update(id, dto.getCategory(), dto.getAmount(), dto.getName(), dto.getContent(), dto.getCompany(), dto.getItemCode(), dto.getStock(), dto.getLength(), dto.getWaist(), dto.getSize(), dto.getGender(), dto.getSeason());
        return ResponseEntity.status(200).body(BaseResponse.of(directBottomInfoRes));
    }

}
