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
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "이미지 관리", description = "이미지 업로드를 위한 Presigned URL 발급 API")
@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService ImageService;

    @Operation(summary = "공지사항 이미지 Presigned URL 발급", description = "공지사항 이미지 업로드를 위한 S3 Presigned URL을 발급합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "발급 성공",
                    content = @Content(examples = @ExampleObject(value = ImageSwaggerConstants.PRESIGNED_URL_RES_EXAMPLE)))
    })
    @PostMapping("/presigned/notice")
    public ResponseEntity<BaseResponse<PresignedUrlResponse>> getNoticeImagePresignedUrl(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = @ExampleObject(value = ImageSwaggerConstants.PRESIGNED_URL_REQ_EXAMPLE)))
            @RequestBody PresignedUrlRequestDto requestDto) {
        PresignedUrlResponse presigned = ImageService.getNoticeImagePresignedUrl(requestDto);
        return ResponseEntity.ok(BaseResponse.of(presigned, ""));
    }

    @Operation(summary = "상품 이미지 Presigned URL 발급", description = "상품 이미지 업로드를 위한 S3 Presigned URL을 발급합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "발급 성공",
                    content = @Content(examples = @ExampleObject(value = ImageSwaggerConstants.PRESIGNED_URL_RES_EXAMPLE)))
    })
    @PostMapping("/presigned/item")
    public ResponseEntity<BaseResponse<PresignedUrlResponse>> getItemImagePresignedUrl(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = @ExampleObject(value = ImageSwaggerConstants.PRESIGNED_URL_REQ_EXAMPLE)))
            @RequestBody PresignedUrlRequestDto requestDto) {
        PresignedUrlResponse presigned = ImageService.getItemImagePresignedUrl(requestDto);
        return ResponseEntity.ok(BaseResponse.of(presigned, ""));
    }

    @PostMapping("/presigned/promotion")
    public ResponseEntity<BaseResponse<PresignedUrlResponse>> getPromotionImagePresignedUrl(@RequestBody PresignedUrlRequestDto requestDto) {
        PresignedUrlResponse presigned = ImageService.getPromotionImagePresignedUrl(requestDto);
        return ResponseEntity.ok(BaseResponse.of(presigned, ""));

    }

    @PostMapping
    public ResponseEntity<BaseResponse<Integer>> registerImage(@RequestBody ImageCreateRequest requestDto) {
        Integer imageIdx = ImageService.createNoticeImage(requestDto);
        return ResponseEntity.ok(BaseResponse.of(imageIdx, "이미지가 등록되었습니다."));
    }

}
