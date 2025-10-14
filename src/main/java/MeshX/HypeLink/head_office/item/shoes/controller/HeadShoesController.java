package MeshX.HypeLink.head_office.item.shoes.controller;


import MeshX.HypeLink.common.BaseResponse;

import MeshX.HypeLink.head_office.item.shoes.model.dto.request.HeadShoesCreateReq;
import MeshX.HypeLink.head_office.item.shoes.model.dto.request.HeadShoesUpdateReq;
import MeshX.HypeLink.head_office.item.shoes.model.dto.response.HeadShoesInfoListRes;
import MeshX.HypeLink.head_office.item.shoes.model.dto.response.HeadShoesInfoRes;
import MeshX.HypeLink.head_office.item.shoes.service.HeadShoesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("headOfficeShoesController")
@RequestMapping("/api/head-office/item/shoes")
@RequiredArgsConstructor
public class HeadShoesController {
    private final HeadShoesService headShoesService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<String>> createShoes(@RequestBody HeadShoesCreateReq dto){
        headShoesService.createShoes(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("신발 재고가 생성되었습니다."));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<BaseResponse<HeadShoesInfoRes>> readShoes(@PathVariable Integer id){
        HeadShoesInfoRes headShoesInfoRes = headShoesService.readDetails(id);
        return ResponseEntity.status(200).body(BaseResponse.of(headShoesInfoRes));
    }

    @GetMapping("/read/all")
    public ResponseEntity<BaseResponse<HeadShoesInfoListRes>> readShoes(){
        HeadShoesInfoListRes headShoesInfoListRes = headShoesService.readList();
        return ResponseEntity.status(200).body(BaseResponse.of(headShoesInfoListRes));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<String>> deleteShoes(@PathVariable Integer id){
        headShoesService.delete(id);
        return ResponseEntity.status(200).body(BaseResponse.of("신발재고가 성공적으로 삭제 되었습니다."));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<BaseResponse<HeadShoesInfoRes>>updateShoes(@PathVariable Integer id,
                                                                     @RequestBody HeadShoesUpdateReq dto){
        HeadShoesInfoRes headShoesInfoRes = headShoesService.update(id, dto.getCategory(), dto.getAmount(), dto.getName(), dto.getContent(), dto.getCompany(), dto.getItemCode(), dto.getStock(), dto.getWaterproof(), dto.getSize());
        return ResponseEntity.status(200).body(BaseResponse.of(headShoesInfoRes));
    }

}
