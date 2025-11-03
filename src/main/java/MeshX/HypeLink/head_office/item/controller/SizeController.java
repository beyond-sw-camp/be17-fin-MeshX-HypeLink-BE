package MeshX.HypeLink.head_office.item.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.head_office.item.model.dto.response.SizeInfoListRes;
import MeshX.HypeLink.head_office.item.service.SizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/size")
@RequiredArgsConstructor
public class SizeController {
    private final SizeService sizeService;

    @GetMapping("/all")
    public ResponseEntity<BaseResponse<SizeInfoListRes>> getSizes() {
        SizeInfoListRes dto = sizeService.findAll();
        return ResponseEntity.status(200).body(BaseResponse.of(dto));
    }
}
