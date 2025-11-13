package com.example.apiitem.item.adaptor.in;

import MeshX.common.BaseResponse;
import MeshX.common.WebAdapter;
import com.example.apiitem.item.usecase.port.in.SizeWebPort;
import com.example.apiitem.item.usecase.port.out.response.SizeInfoListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@WebAdapter
@RequestMapping("/api/size")
@RequiredArgsConstructor
public class SizeWebAdaptor {
    private final SizeWebPort sizeWebPort;

    @GetMapping("/all")
    public ResponseEntity<BaseResponse<SizeInfoListDto>> getSizes() {
        SizeInfoListDto dto = sizeWebPort.findAll();
        return ResponseEntity.status(200).body(BaseResponse.of(dto));
    }
}
