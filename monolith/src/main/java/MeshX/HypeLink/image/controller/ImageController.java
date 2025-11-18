package MeshX.HypeLink.image.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.common.s3.PresignedUrlRequestDto;
import MeshX.HypeLink.image.model.dto.request.ImageCreateRequest;
import MeshX.HypeLink.image.model.dto.response.PresignedUrlResponse;
import MeshX.HypeLink.image.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "이미지 관리", description = "S3 Presigned URL 생성 및 이미지 업로드 API")
@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/presigned/notice")
    public ResponseEntity<BaseResponse<PresignedUrlResponse>> getNoticeImagePresignedUrl(@RequestBody PresignedUrlRequestDto requestDto) {
        PresignedUrlResponse presigned = imageService.getNoticeImagePresignedUrl(requestDto);
        return ResponseEntity.ok(BaseResponse.of(presigned, ""));
    }

    @PostMapping("/presigned/item")
    public ResponseEntity<BaseResponse<PresignedUrlResponse>> getItemImagePresignedUrl(@RequestBody PresignedUrlRequestDto requestDto) {
        PresignedUrlResponse presigned = imageService.getItemImagePresignedUrl(requestDto);
        return ResponseEntity.ok(BaseResponse.of(presigned, ""));
    }

    @PostMapping("/presigned/promotion")
    public ResponseEntity<BaseResponse<PresignedUrlResponse>> getPromotionImagePresignedUrl(@RequestBody PresignedUrlRequestDto requestDto) {
        PresignedUrlResponse presigned = imageService.getPromotionImagePresignedUrl(requestDto);
        return ResponseEntity.ok(BaseResponse.of(presigned, ""));

    }


}
