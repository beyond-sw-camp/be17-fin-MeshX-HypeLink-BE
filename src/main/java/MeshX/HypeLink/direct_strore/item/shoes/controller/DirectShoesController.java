package MeshX.HypeLink.direct_strore.item.shoes.controller;


import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.direct_strore.item.shoes.model.dto.request.DirectShoesCreateReq;
import MeshX.HypeLink.direct_strore.item.shoes.model.dto.request.DirectShoesUpdateReq;
import MeshX.HypeLink.direct_strore.item.shoes.model.dto.response.DirectShoesInfoListRes;
import MeshX.HypeLink.direct_strore.item.shoes.model.dto.response.DirectShoesInfoRes;
import MeshX.HypeLink.direct_strore.item.shoes.service.DirectShoesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("directStoreShoesController")
@RequestMapping("/api/direct-store/item/shoes")
@RequiredArgsConstructor
public class DirectShoesController {
    private final DirectShoesService directShoesService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<String>> createShoes(@RequestBody DirectShoesCreateReq dto){
        directShoesService.createShoes(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("신발 재고가 생성되었습니다."));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<BaseResponse<DirectShoesInfoRes>> readShoes(@PathVariable Integer id){
        DirectShoesInfoRes directShoesInfoRes = directShoesService.readDetails(id);
        return ResponseEntity.status(200).body(BaseResponse.of(directShoesInfoRes));
    }

    @GetMapping("/read/all")
    public ResponseEntity<BaseResponse<DirectShoesInfoListRes>> readShoes(){
        DirectShoesInfoListRes directShoesInfoListRes = directShoesService.readList();
        return ResponseEntity.status(200).body(BaseResponse.of(directShoesInfoListRes));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<String>> deleteShoes(@PathVariable Integer id){
        directShoesService.delete(id);
        return ResponseEntity.status(200).body(BaseResponse.of("신발재고가 성공적으로 삭제 되었습니다."));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<BaseResponse<DirectShoesInfoRes>>updateShoes(@PathVariable Integer id,
                                                                       @RequestBody DirectShoesUpdateReq dto){
        DirectShoesInfoRes directShoesInfoRes = directShoesService.update(id, dto.getCategory(), dto.getAmount(), dto.getName(), dto.getContent(), dto.getCompany(), dto.getItemCode(), dto.getStock(), dto.getWaterproof(), dto.getSize());
        return ResponseEntity.status(200).body(BaseResponse.of(directShoesInfoRes));
    }

}
