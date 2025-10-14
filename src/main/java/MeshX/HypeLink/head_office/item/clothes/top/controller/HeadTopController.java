package MeshX.HypeLink.head_office.item.clothes.top.controller;


import MeshX.HypeLink.common.BaseResponse;

import MeshX.HypeLink.head_office.item.clothes.top.model.dto.request.HeadTopCreateReq;
import MeshX.HypeLink.head_office.item.clothes.top.model.dto.request.HeadTopUpdateReq;
import MeshX.HypeLink.head_office.item.clothes.top.model.dto.response.HeadTopInfoListRes;
import MeshX.HypeLink.head_office.item.clothes.top.model.dto.response.HeadTopInfoRes;
import MeshX.HypeLink.head_office.item.clothes.top.service.HeadTopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("headOfficeTopController")
@RequestMapping("/api/head-office/item/top")
@RequiredArgsConstructor
public class HeadTopController {
    private final HeadTopService headTopService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<String>> createTop(@RequestBody HeadTopCreateReq dto){
        headTopService.createTop(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("상의 재고가 생성되었습니다."));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<BaseResponse<HeadTopInfoRes>> readTop(@PathVariable Integer id){
        HeadTopInfoRes headTopInfoRes = headTopService.readDetails(id);
        return ResponseEntity.status(200).body(BaseResponse.of(headTopInfoRes));
    }

    @GetMapping("/read/all")
    public ResponseEntity<BaseResponse<HeadTopInfoListRes>> readTop(){
        HeadTopInfoListRes headTopInfoListRes = headTopService.readList();
        return ResponseEntity.status(200).body(BaseResponse.of(headTopInfoListRes));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<String>> deleteTop(@PathVariable Integer id){
        headTopService.delete(id);
        return ResponseEntity.status(200).body(BaseResponse.of("상의 재고가 성공적으로 삭제 되었습니다."));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<BaseResponse<HeadTopInfoRes>>updateTop(@PathVariable Integer id,
                                                                 @RequestBody HeadTopUpdateReq dto){
        HeadTopInfoRes headTopInfoRes = headTopService.update(id, dto.getCategory(), dto.getAmount(), dto.getName(), dto.getContent(), dto.getCompany(), dto.getItemCode(), dto.getStock(), dto.getLongSleeve(), dto.getNeckline(), dto.getSize(), dto.getGender(), dto.getSeason());
        return ResponseEntity.status(200).body(BaseResponse.of(headTopInfoRes));
    }

}
