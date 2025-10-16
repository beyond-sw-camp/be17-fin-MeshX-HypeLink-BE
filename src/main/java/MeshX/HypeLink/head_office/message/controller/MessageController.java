package MeshX.HypeLink.head_office.message.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.head_office.message.constansts.SwaggerConstants;
import MeshX.HypeLink.head_office.message.model.dto.request.MessageCreateReq;
import MeshX.HypeLink.head_office.message.model.dto.request.MessageUpdateReq;
import MeshX.HypeLink.head_office.message.model.dto.response.MessageInfoListRes;
import MeshX.HypeLink.head_office.message.model.dto.response.MessageInfoRes;
import MeshX.HypeLink.head_office.message.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "본사 메시지 관리", description = "본사에서 각 지점 또는 전체에 전달하는 메시지를 관리하는 API")
@RestController
@RequestMapping("/api/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @Operation(summary = "메시지 발송", description = "새로운 메시지를 특정 대상(지점) 또는 전체에 발송합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "메시지 발송 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = SwaggerConstants.MESSAGE_INFO_RESPONSE))),
            @ApiResponse(responseCode = "400", description = "요청 데이터가 유효하지 않습니다.", content = @Content)
    })
    @PostMapping("/create")
    public ResponseEntity<BaseResponse<MessageInfoRes>> createMessage(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "발송할 메시지의 제목과 내용",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageCreateReq.class),
                            examples = @ExampleObject(value = SwaggerConstants.MESSAGE_CREATE_REQUEST)
                    )
            )
            @RequestBody MessageCreateReq dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.of(MessageInfoRes.builder().build()));
    }

    @Operation(summary = "메시지 목록 조회 (페이징)", description = "발송된 메시지 목록을 페이징하여 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "메시지 목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = SwaggerConstants.MESSAGE_INFO_LIST_RESPONSE)))
    })
    @GetMapping("/read/all")
    public ResponseEntity<BaseResponse<MessageInfoListRes>> readMessages(
            @Parameter(description = "페이지 번호 (0부터 시작)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 당 메시지 수") @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(BaseResponse.of(MessageInfoListRes.builder().build()));
    }

    @Operation(summary = "특정 메시지 조회", description = "메시지 ID를 사용하여 특정 메시지의 상세 내용을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "메시지 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = SwaggerConstants.MESSAGE_INFO_RESPONSE))),
            @ApiResponse(responseCode = "404", description = "해당 ID의 메시지를 찾을 수 없습니다.", content = @Content)
    })
    @GetMapping("/read/{id}")
    public ResponseEntity<BaseResponse<MessageInfoRes>> readMessage(@Parameter(description = "조회할 메시지의 ID") @PathVariable Integer id) {
        // In the original code, this returned MessageInfoListRes, which seems incorrect.
        // I'm changing it to MessageInfoRes to match the likely intent.
        return ResponseEntity.ok(BaseResponse.of(MessageInfoRes.builder().build()));
    }

    @Operation(summary = "메시지 수정 (읽음 처리 등)", description = "메시지의 상태를 변경합니다. 예를 들어, '읽음' 상태로 표시할 때 사용될 수 있습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "메시지 상태 변경 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = SwaggerConstants.MESSAGE_INFO_RESPONSE))),
            @ApiResponse(responseCode = "400", description = "요청 데이터가 유효하지 않습니다.", content = @Content),
            @ApiResponse(responseCode = "404", description = "해당 메시지를 찾을 수 없습니다.", content = @Content)
    })
    @PatchMapping("/update")
    public ResponseEntity<BaseResponse<MessageInfoRes>> markAsRead(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "수정할 메시지의 정보",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageUpdateReq.class),
                            examples = @ExampleObject(value = SwaggerConstants.MESSAGE_UPDATE_REQUEST)
                    )
            )
            @RequestBody MessageUpdateReq dto) {
        return ResponseEntity.ok(BaseResponse.of(MessageInfoRes.builder().build()));
    }

    @Operation(summary = "메시지 삭제", description = "메시지 ID를 사용하여 특정 메시지를 시스템에서 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "메시지 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "해당 ID의 메시지를 찾을 수 없습니다.", content = @Content)
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<String>> deleteMessage(@Parameter(description = "삭제할 메시지의 ID") @PathVariable Integer id) {
        return ResponseEntity.ok(BaseResponse.of("삭제되었습니다."));
    }
}
