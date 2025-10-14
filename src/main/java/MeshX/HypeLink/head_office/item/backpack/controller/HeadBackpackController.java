package MeshX.HypeLink.head_office.item.backpack.controller;


import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.head_office.item.backpack.model.dto.request.HeadBackpackCreateReq;
import MeshX.HypeLink.head_office.item.backpack.model.dto.request.HeadBackpackUpdateReq;
import MeshX.HypeLink.head_office.item.backpack.model.dto.response.HeadBackpackInfoListRes;
import MeshX.HypeLink.head_office.item.backpack.model.dto.response.HeadBackpackInfoRes;
import MeshX.HypeLink.head_office.item.backpack.service.HeadBackpackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("headOfficeBackpackController")
@RequestMapping("/api/head-office/item/backpack")
@RequiredArgsConstructor
public class HeadBackpackController {
    private final HeadBackpackService headBackpackService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<String>> createBackpack(@RequestBody HeadBackpackCreateReq dto){
        headBackpackService.createBackpack(dto);
        return ResponseEntity.status(200).body(BaseResponse.of("가방 재고가 생성되었습니다."));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<BaseResponse<HeadBackpackInfoRes>> readBackpack(@PathVariable Integer id){
        HeadBackpackInfoRes headBackpackInfoRes = headBackpackService.readDetails(id);
        return ResponseEntity.status(200).body(BaseResponse.of(headBackpackInfoRes));
    }

    @GetMapping("/read/all")
    public ResponseEntity<BaseResponse<HeadBackpackInfoListRes>> readBackpacks(){
        HeadBackpackInfoListRes headBackpackInfoListRes = headBackpackService.readList();
        return ResponseEntity.status(200).body(BaseResponse.of(headBackpackInfoListRes));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<String>> deleteBackpack(@PathVariable Integer id){
        headBackpackService.delete(id);
        return ResponseEntity.status(200).body(BaseResponse.of("가방재고가 성공적으로 삭제 되었습니다."));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<BaseResponse<HeadBackpackInfoRes>>updateBackpack(@PathVariable Integer id,
                                                                           @RequestBody HeadBackpackUpdateReq dto){
        HeadBackpackInfoRes headBackpackInfoRes = headBackpackService.update(id, dto.getCategory(), dto.getAmount(), dto.getName(), dto.getContent(), dto.getCompany(), dto.getItemCode(), dto.getStock(), dto.getWaterproof(), dto.getCapacity());
        return ResponseEntity.status(200).body(BaseResponse.of(headBackpackInfoRes));
    }

}
