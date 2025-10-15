package MeshX.HypeLink.direct_store.item.clothes.outer.controller;


import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.direct_store.item.clothes.outer.model.dto.request.DirectOuterCreateReq;
import MeshX.HypeLink.direct_store.item.clothes.outer.model.dto.request.DirectOuterUpdateReq;
import MeshX.HypeLink.direct_store.item.clothes.outer.model.dto.response.DirectOuterInfoListRes;
import MeshX.HypeLink.direct_store.item.clothes.outer.model.dto.response.DirectOuterInfoRes;
import MeshX.HypeLink.direct_store.item.clothes.outer.service.DirectOuterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("directStoreOuterController")
@RequestMapping("/api/direct-store/item/outer")
@RequiredArgsConstructor
public class DirectOuterController {
    private final DirectOuterService directOuterService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<String>> createOuter(@RequestBody DirectOuterCreateReq dto){
        directOuterService.createOuter(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("아우터 재고가 생성되었습니다."));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<BaseResponse<DirectOuterInfoRes>> readOuter(@PathVariable Integer id){
        DirectOuterInfoRes directOuterInfoRes = directOuterService.readDetails(id);
        return ResponseEntity.status(200).body(BaseResponse.of(directOuterInfoRes));
    }

    @GetMapping("/read/all")
    public ResponseEntity<BaseResponse<DirectOuterInfoListRes>> readOuter(){
        DirectOuterInfoListRes directOuterInfoListRes = directOuterService.readList();
        return ResponseEntity.status(200).body(BaseResponse.of(directOuterInfoListRes));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<String>> deleteOuter(@PathVariable Integer id){
        directOuterService.delete(id);
        return ResponseEntity.status(200).body(BaseResponse.of("아우터 재고가 성공적으로 삭제 되었습니다."));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<BaseResponse<DirectOuterInfoRes>>updateOuter(@PathVariable Integer id,
                                                                       @RequestBody DirectOuterUpdateReq dto){
        DirectOuterInfoRes directOuterInfoRes = directOuterService.update(id, dto.getCategory(), dto.getAmount(), dto.getName(), dto.getContent(), dto.getCompany(), dto.getItemCode(), dto.getStock(), dto.getWaterproof(), dto.getHooded(), dto.getSize(), dto.getGender(), dto.getSeason());
        return ResponseEntity.status(200).body(BaseResponse.of(directOuterInfoRes));
    }

}
