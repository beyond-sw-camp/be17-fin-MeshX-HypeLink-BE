package MeshX.HypeLink.head_office.item.clothes.bottom.controller;


import MeshX.HypeLink.common.BaseResponse;

import MeshX.HypeLink.head_office.item.clothes.bottom.model.dto.request.HeadBottomCreateReq;
import MeshX.HypeLink.head_office.item.clothes.bottom.model.dto.request.HeadBottomUpdateReq;
import MeshX.HypeLink.head_office.item.clothes.bottom.model.dto.response.HeadBottomInfoListRes;
import MeshX.HypeLink.head_office.item.clothes.bottom.model.dto.response.HeadBottomInfoRes;
import MeshX.HypeLink.head_office.item.clothes.bottom.service.HeadBottomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("headOfficeBottomController")
@RequestMapping("/api/head-office/item/bottom")
@RequiredArgsConstructor
public class HeadBottomController {
    private final HeadBottomService headBottomService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<String>> createBottom(@RequestBody HeadBottomCreateReq dto){
        headBottomService.createBottom(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("하의 재고가 생성되었습니다."));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<BaseResponse<HeadBottomInfoRes>> readBottom(@PathVariable Integer id){
        HeadBottomInfoRes headBottomInfoRes = headBottomService.readDetails(id);
        return ResponseEntity.status(200).body(BaseResponse.of(headBottomInfoRes));
    }

    @GetMapping("/read/all")
    public ResponseEntity<BaseResponse<HeadBottomInfoListRes>> readBottom(){
        HeadBottomInfoListRes headBottomInfoListRes = headBottomService.readList();
        return ResponseEntity.status(200).body(BaseResponse.of(headBottomInfoListRes));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<String>> deleteBottom(@PathVariable Integer id){
        headBottomService.delete(id);
        return ResponseEntity.status(200).body(BaseResponse.of("하의 재고가 성공적으로 삭제 되었습니다."));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<BaseResponse<HeadBottomInfoRes>>updateTop(@PathVariable Integer id,
                                                                    @RequestBody HeadBottomUpdateReq dto){
        HeadBottomInfoRes headBottomInfoRes = headBottomService.update(id, dto.getCategory(), dto.getAmount(), dto.getName(), dto.getContent(), dto.getCompany(), dto.getItemCode(), dto.getStock(), dto.getLength(), dto.getWaist(), dto.getSize(), dto.getGender(), dto.getSeason());
        return ResponseEntity.status(200).body(BaseResponse.of(headBottomInfoRes));
    }

}
