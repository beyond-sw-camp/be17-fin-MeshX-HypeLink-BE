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

import MeshX.HypeLink.head_office.chat.constansts.ChatSwaggerConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "본사 채팅 관리", description = "본사에서 사용자 간의 채팅 메시지를 관리하는 API")
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

    @Operation(summary = "HTTP를 통한 메시지 전송", description = "HTTP POST 요청을 통해 채팅 메시지를 전송하고 저장합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "메시지 전송 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ChatSwaggerConstants.CHAT_MESSAGE_RES_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "요청 데이터가 유효하지 않습니다.", content = @Content),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content)
    })
    @PostMapping("/send")
    public ResponseEntity<BaseResponse<ChatMessageResDto>> sendMessageHttp(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "전송할 채팅 메시지 정보",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ChatMessageReqDto.class),
                            examples = @ExampleObject(value = ChatSwaggerConstants.CHAT_MESSAGE_REQ_EXAMPLE)
                    )
            )
            @RequestBody ChatMessageReqDto chatMessageReqDto,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails) {

        ChatMessageResDto result = chatService.saveMessage(chatMessageReqDto, userDetails.getUsername());

        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @Operation(summary = "채팅 내역 조회", description = "특정 사용자와의 채팅 내역을 페이지네이션하여 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "채팅 내역 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ChatSwaggerConstants.CHAT_MESSAGE_PAGE_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.", content = @Content)
    })
    @GetMapping("/list/{otherid}")
    public ResponseEntity<BaseResponse<PageRes<ChatMessageResDto>>> getChatHistory(
            @Parameter(description = "상대방 사용자 ID") @PathVariable Integer otherid,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails,
            @Parameter(description = "페이지 요청 정보 (페이지 번호, 페이지 크기)", hidden = true) PageReq pageReq) {

        PageRes<ChatMessageResDto> history = chatService.getChatHistory(userDetails.getUsername(), otherid, pageReq);

        return ResponseEntity.ok(BaseResponse.of(history));
    }

    @Operation(summary = "메시지 읽음 처리", description = "특정 발신자와의 메시지를 모두 읽음 처리합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "메시지 읽음 처리 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ChatSwaggerConstants.MESSAGE_READ_SUCCESS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.", content = @Content)
    })
    @PostMapping("/read/{senderId}")
    public ResponseEntity<BaseResponse<Void>> markMessagesAsRead(
            @Parameter(description = "메시지를 보낸 사용자 ID") @PathVariable Integer senderId,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails) {

        chatService.markMessagesAsRead(userDetails.getUsername(), senderId);

        return ResponseEntity.ok(BaseResponse.of(null));
    }

    @Operation(summary = "채팅 사용자 목록 조회", description = "현재 사용자와 채팅한 사용자 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "채팅 사용자 목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ChatSwaggerConstants.MESSAGE_USER_LIST_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content)
    })
    @GetMapping("/users")
    public ResponseEntity<BaseResponse<List<MessageUserListResDto>>> getChatUserList(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails) {

        List<MessageUserListResDto> result = chatService.getChatUserList(userDetails.getUsername());

        return ResponseEntity.ok(BaseResponse.of(result));
    }
}
