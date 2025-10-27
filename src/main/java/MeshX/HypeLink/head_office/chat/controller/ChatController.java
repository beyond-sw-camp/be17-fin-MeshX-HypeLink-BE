package MeshX.HypeLink.head_office.chat.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.common.Page.PageReq;
import MeshX.HypeLink.common.Page.PageRes;
import MeshX.HypeLink.head_office.chat.model.dto.req.ChatMessageReqDto;
import MeshX.HypeLink.head_office.chat.model.dto.res.ChatMessageResDto;
import MeshX.HypeLink.head_office.chat.model.dto.res.MessageUserListResDto;
import MeshX.HypeLink.head_office.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/send")
    public void sendMessage(@Payload ChatMessageReqDto chatMessageReqDto, Principal principal) {

        String senderEmail = principal.getName();
        chatService.saveAndSendMessage(chatMessageReqDto, senderEmail);
    }

    @PostMapping("/send")
    public ResponseEntity<BaseResponse<ChatMessageResDto>> sendMessageHttp(
            @RequestBody ChatMessageReqDto chatMessageReqDto,
            @AuthenticationPrincipal UserDetails userDetails) {

        ChatMessageResDto result = chatService.saveMessage(chatMessageReqDto, userDetails.getUsername());

        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @GetMapping("/list/{otherid}")
    public ResponseEntity<BaseResponse<PageRes<ChatMessageResDto>>> getChatHistory(
            @PathVariable Integer otherid,
            @AuthenticationPrincipal UserDetails userDetails,
            PageReq pageReq) {

        PageRes<ChatMessageResDto> history = chatService.getChatHistory(userDetails.getUsername(), otherid, pageReq);

        return ResponseEntity.ok(BaseResponse.of(history));
    }

    @PostMapping("/read/{senderId}")
    public ResponseEntity<BaseResponse<Void>> markMessagesAsRead(
            @PathVariable Integer senderId,
            @AuthenticationPrincipal UserDetails userDetails) {

        chatService.markMessagesAsRead(userDetails.getUsername(), senderId);

        return ResponseEntity.ok(BaseResponse.of(null));
    }

    @GetMapping("/users")
    public ResponseEntity<BaseResponse<List<MessageUserListResDto>>> getChatUserList(
            @AuthenticationPrincipal UserDetails userDetails) {

        List<MessageUserListResDto> result = chatService.getChatUserList(userDetails.getUsername());

        return ResponseEntity.ok(BaseResponse.of(result));
    }
}
