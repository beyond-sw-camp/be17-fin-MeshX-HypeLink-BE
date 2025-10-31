package MeshX.HypeLink.image.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.common.s3.PresignedUrlRequestDto;
import MeshX.HypeLink.image.constants.ImageSwaggerConstants;
import MeshX.HypeLink.image.model.dto.request.ImageCreateRequest;
import MeshX.HypeLink.image.model.dto.response.PresignedUrlResponse;
import MeshX.HypeLink.image.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "이미지 관리", description = "S3 이미지 업로드를 위한 Presigned URL 생성 API")
@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @Operation(summary = "공지사항 이미지 업로드 URL 생성", description = "공지사항 이미지를 업로드하기 위한 Presigned URL을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Presigned URL 생성 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ImageSwaggerConstants.PRESIGNED_URL_RES_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content)
    })
    @PostMapping("/presigned/notice")
    public ResponseEntity<BaseResponse<PresignedUrlResponse>> getNoticeImagePresignedUrl(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "업로드할 이미지 정보",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PresignedUrlRequestDto.class),
                            examples = @ExampleObject(value = ImageSwaggerConstants.PRESIGNED_URL_REQ_EXAMPLE)
                    )
            )
            @RequestBody PresignedUrlRequestDto requestDto) {
        PresignedUrlResponse presigned = imageService.getNoticeImagePresignedUrl(requestDto);
        return ResponseEntity.ok(BaseResponse.of(presigned, ""));
    }

    @Operation(summary = "상품 이미지 업로드 URL 생성", description = "상품 이미지를 업로드하기 위한 Presigned URL을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Presigned URL 생성 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ImageSwaggerConstants.PRESIGNED_URL_RES_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content)
    })
    @PostMapping("/presigned/item")
    public ResponseEntity<BaseResponse<PresignedUrlResponse>> getItemImagePresignedUrl(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "업로드할 이미지 정보",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PresignedUrlRequestDto.class),
                            examples = @ExampleObject(value = ImageSwaggerConstants.PRESIGNED_URL_REQ_EXAMPLE)
                    )
            )
            @RequestBody PresignedUrlRequestDto requestDto) {
        PresignedUrlResponse presigned = imageService.getItemImagePresignedUrl(requestDto);
        return ResponseEntity.ok(BaseResponse.of(presigned, ""));
    }

    @Operation(summary = "프로모션 이미지 업로드 URL 생성", description = "프로모션 이미지를 업로드하기 위한 Presigned URL을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Presigned URL 생성 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = ImageSwaggerConstants.PRESIGNED_URL_RES_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content)
    })
    @PostMapping("/presigned/promotion")
    public ResponseEntity<BaseResponse<PresignedUrlResponse>> getPromotionImagePresignedUrl(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "업로드할 이미지 정보",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PresignedUrlRequestDto.class),
                            examples = @ExampleObject(value = ImageSwaggerConstants.PRESIGNED_URL_REQ_EXAMPLE)
                    )
            )
            @RequestBody PresignedUrlRequestDto requestDto) {
        PresignedUrlResponse presigned = imageService.getPromotionImagePresignedUrl(requestDto);
        return ResponseEntity.ok(BaseResponse.of(presigned, ""));

    }


}
