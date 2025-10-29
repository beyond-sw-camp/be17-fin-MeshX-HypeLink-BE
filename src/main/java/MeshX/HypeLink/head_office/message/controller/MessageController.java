package MeshX.HypeLink.head_office.message.controller;

import MeshX.HypeLink.common.BaseEntity;
import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.head_office.message.constants.MessageSwaggerConstants;
import MeshX.HypeLink.head_office.message.model.dto.request.MessageCreateReq;
import MeshX.HypeLink.head_office.message.model.dto.request.MessageUpdateReq;
import MeshX.HypeLink.head_office.message.model.dto.response.MessageInfoListRes;
import MeshX.HypeLink.head_office.message.model.dto.response.MessageInfoRes;
import MeshX.HypeLink.head_office.message.model.entity.Message;
import MeshX.HypeLink.head_office.message.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "본부 메시지 관리", description = "본부에서 메시지를 관리하는 API")
@RestController
@RequestMapping("/api/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @Operation(summary = "메시지 생성", description = "새로운 메시지를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "생성 성공",
                    content = @Content(examples = @ExampleObject(value = MessageSwaggerConstants.MESSAGE_INFO_RES_EXAMPLE)))
    })
    @PostMapping("/create")
    public ResponseEntity<BaseResponse<MessageInfoRes>> createMessage(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = @ExampleObject(value = MessageSwaggerConstants.MESSAGE_CREATE_REQ_EXAMPLE)))
            @RequestBody MessageCreateReq dto) {
        return ResponseEntity.ok(BaseResponse.of(MessageInfoRes.builder().build()));
    }

    @Operation(summary = "메시지 목록 조회", description = "모든 메시지 목록을 조회합니다.")
    @GetMapping("/read/all")
    public ResponseEntity<BaseResponse<MessageInfoListRes>> readMessages(
            @Parameter(description = "페이지 번호") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기") @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(BaseResponse.of(MessageInfoListRes.builder().build()));
    }

    @Operation(summary = "메시지 상세 조회", description = "특정 메시지를 조회합니다.")
    @GetMapping("/read/{id}")
    public ResponseEntity<BaseResponse<MessageInfoListRes>> readMessage(
            @Parameter(description = "메시지 ID") @PathVariable Integer id) {
        return ResponseEntity.ok(BaseResponse.of(MessageInfoListRes.builder().build()));
    }

    @Operation(summary = "메시지 수정", description = "메시지를 읽음 처리합니다.")
    @PatchMapping("/update")
    public ResponseEntity<BaseResponse<MessageInfoRes>> markAsRead(@RequestBody MessageUpdateReq dto) {
        return ResponseEntity.ok(BaseResponse.of(MessageInfoRes.builder().build()));
    }

    @Operation(summary = "메시지 삭제", description = "메시지를 삭제합니다.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<String>> deleteMessage(
            @Parameter(description = "삭제할 메시지 ID") @PathVariable Integer id) {
        return ResponseEntity.ok(BaseResponse.of("삭제되었습니다."));
    }
}
