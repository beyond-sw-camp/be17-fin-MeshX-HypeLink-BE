package com.example.apiitem.item.adaptor.in;

import MeshX.common.BaseResponse;
import MeshX.common.WebAdapter;
import com.example.apiitem.item.usecase.port.in.ColorWebPort;
import com.example.apiitem.item.usecase.port.out.response.ColorInfoListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@WebAdapter
@RequestMapping("/api/color")
@RequiredArgsConstructor
public class ColorWebAdaptor {
    private final ColorWebPort colorWebPort;

    @GetMapping("/all")
    public ResponseEntity<BaseResponse<ColorInfoListDto>> getCategories() {
        ColorInfoListDto dto = colorWebPort.findAll();

        return ResponseEntity.status(200).body(BaseResponse.of(dto));
    }
}
