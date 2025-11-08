package com.example.apinotice.notice.adaptor.in;

import MeshX.common.BaseResponse;
import MeshX.common.WebAdapter;
import com.example.apinotice.notice.usecase.port.in.WebPort;
import com.example.apinotice.notice.usecase.port.in.request.NoticeSaveCommand;
import com.example.apinotice.notice.usecase.port.out.response.NoticeInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@WebAdapter
@RequestMapping("/api/notice")
@RequiredArgsConstructor
public class WebAdaptor {
    private final WebPort webPort;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<String>> createNotice(@RequestBody NoticeSaveCommand noticeSaveCommand) {
        webPort.create(noticeSaveCommand);
        return ResponseEntity.status(200).body(BaseResponse.of("생성되었습니다."));

    }

    @GetMapping("/read/{id}")
    public ResponseEntity<BaseResponse<NoticeInfoDto>> readNotice(@PathVariable Integer id) {
         NoticeInfoDto result = webPort.read(id);
         return ResponseEntity.status(200).body(BaseResponse.of(result));

    }

    


}
