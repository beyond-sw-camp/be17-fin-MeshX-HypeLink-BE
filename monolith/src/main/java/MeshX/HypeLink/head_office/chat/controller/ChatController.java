package MeshX.HypeLink.head_office.chat.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.common.Page.PageReq;
import MeshX.HypeLink.common.Page.PageRes;
import MeshX.HypeLink.head_office.chat.model.dto.req.ChatMessageReqDto;
import MeshX.HypeLink.head_office.chat.model.dto.res.ChatMessageResDto;
import MeshX.HypeLink.head_office.chat.model.dto.res.MessageUserListResDto;
import MeshX.HypeLink.head_office.chat.service.ChatService;
import com.example.apiclients.annotation.GetMemberEmail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/send")
    public void sendMessage(@Payload ChatMessageReqDto chatMessageReqDto, @GetMemberEmail String email) {

//        String senderEmail = principal.getName();
        chatService.saveAndSendMessage(chatMessageReqDto, email);
    }

    @PostMapping("/send")
    public ResponseEntity<BaseResponse<ChatMessageResDto>> sendMessageHttp(
            @RequestBody ChatMessageReqDto chatMessageReqDto,
            @GetMemberEmail String email) {

        ChatMessageResDto result = chatService.saveMessage(chatMessageReqDto, email);

        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @GetMapping("/list/{otherid}")
    public ResponseEntity<BaseResponse<PageRes<ChatMessageResDto>>> getChatHistory(
            @PathVariable Integer otherid,
            @GetMemberEmail String email,
            PageReq pageReq) {

        PageRes<ChatMessageResDto> history = chatService.getChatHistory(email, otherid, pageReq);

        return ResponseEntity.ok(BaseResponse.of(history));
    }

    @PostMapping("/read/{senderId}")
    public ResponseEntity<BaseResponse<Void>> markMessagesAsRead(
            @PathVariable Integer senderId,
            @GetMemberEmail String email) {

        for (int i = 0; i <= 30; i++) {
            System.out.println(email);
        }

        chatService.markMessagesAsRead(email, senderId);

        return ResponseEntity.ok(BaseResponse.of(null));
    }

    @GetMapping("/users")
    public ResponseEntity<BaseResponse<List<MessageUserListResDto>>> getChatUserList(@GetMemberEmail String email) {

        log.warn(email);

        List<MessageUserListResDto> result = chatService.getChatUserList(email);

        return ResponseEntity.ok(BaseResponse.of(result));
    }
}
