package MeshX.HypeLink.head_office.item.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.head_office.item.model.dto.response.ColorInfoListRes;
import MeshX.HypeLink.head_office.item.service.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/color")
@RequiredArgsConstructor
public class ColorController {
    private final ColorService colorService;

    @GetMapping("/all")
    public ResponseEntity<BaseResponse<ColorInfoListRes>> getCategories() {
        ColorInfoListRes dto = colorService.findAll();

        return ResponseEntity.status(200).body(BaseResponse.of(dto));
    }
}
