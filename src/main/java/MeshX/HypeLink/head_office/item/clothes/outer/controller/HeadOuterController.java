package MeshX.HypeLink.head_office.item.clothes.outer.controller;


import MeshX.HypeLink.common.BaseResponse;

import MeshX.HypeLink.head_office.item.clothes.outer.model.dto.request.HeadOuterCreateReq;
import MeshX.HypeLink.head_office.item.clothes.outer.model.dto.request.HeadOuterUpdateReq;
import MeshX.HypeLink.head_office.item.clothes.outer.model.dto.response.HeadOuterInfoListRes;
import MeshX.HypeLink.head_office.item.clothes.outer.model.dto.response.HeadOuterInfoRes;
import MeshX.HypeLink.head_office.item.clothes.outer.service.HeadOuterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("headOfficeOuterController")
@RequestMapping("/api/head-office/item/outer")
@RequiredArgsConstructor
public class HeadOuterController {
    private final HeadOuterService headOuterService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<String>> createOuter(@RequestBody HeadOuterCreateReq dto){
        headOuterService.createOuter(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("아우터 재고가 생성되었습니다."));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<BaseResponse<HeadOuterInfoRes>> readOuter(@PathVariable Integer id){
        HeadOuterInfoRes headOuterInfoRes = headOuterService.readDetails(id);
        return ResponseEntity.status(200).body(BaseResponse.of(headOuterInfoRes));
    }

    @GetMapping("/read/all")
    public ResponseEntity<BaseResponse<HeadOuterInfoListRes>> readOuter(){
        HeadOuterInfoListRes headOuterInfoListRes = headOuterService.readList();
        return ResponseEntity.status(200).body(BaseResponse.of(headOuterInfoListRes));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<String>> deleteOuter(@PathVariable Integer id){
        headOuterService.delete(id);
        return ResponseEntity.status(200).body(BaseResponse.of("아우터 재고가 성공적으로 삭제 되었습니다."));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<BaseResponse<HeadOuterInfoRes>>updateOuter(@PathVariable Integer id,
                                                                     @RequestBody HeadOuterUpdateReq dto){
        HeadOuterInfoRes headOuterInfoRes = headOuterService.update(id, dto.getCategory(), dto.getAmount(), dto.getName(), dto.getContent(), dto.getCompany(), dto.getItemCode(), dto.getStock(), dto.getWaterproof(), dto.getHooded(), dto.getSize(), dto.getGender(), dto.getSeason());
        return ResponseEntity.status(200).body(BaseResponse.of(headOuterInfoRes));
    }

}
