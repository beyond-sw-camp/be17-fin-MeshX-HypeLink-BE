package MeshX.HypeLink.head_office.message.controller;

import MeshX.HypeLink.common.BaseEntity;
import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.head_office.message.model.dto.request.MessageCreateReq;
import MeshX.HypeLink.head_office.message.model.dto.request.MessageUpdateReq;
import MeshX.HypeLink.head_office.message.model.dto.response.MessageInfoListRes;
import MeshX.HypeLink.head_office.message.model.dto.response.MessageInfoRes;
import MeshX.HypeLink.head_office.message.model.entity.Message;
import MeshX.HypeLink.head_office.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<MessageInfoRes>> createMessage(@RequestBody MessageCreateReq dto) {
        return ResponseEntity.ok(BaseResponse.of(MessageInfoRes.builder().build()));
    }

    @GetMapping("/read/all")
    public ResponseEntity<BaseResponse<MessageInfoListRes>> readMessages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(BaseResponse.of(MessageInfoListRes.builder().build()));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<BaseResponse<MessageInfoListRes>> readMessage(@PathVariable Integer id) {
        return ResponseEntity.ok(BaseResponse.of(MessageInfoListRes.builder().build()));
    }

    @PatchMapping("/update")
    public ResponseEntity<BaseResponse<MessageInfoRes>> markAsRead(@RequestBody MessageUpdateReq dto) {
        return ResponseEntity.ok(BaseResponse.of(MessageInfoRes.builder().build()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<String>> deleteMessage(@PathVariable Integer id) {
        return ResponseEntity.ok(BaseResponse.of("삭제되었습니다."));
    }
}
