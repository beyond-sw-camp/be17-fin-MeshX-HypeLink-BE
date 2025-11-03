package MeshX.HypeLink.image.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.common.s3.PresignedUrlRequestDto;
import MeshX.HypeLink.image.model.dto.request.ImageCreateRequest;
import MeshX.HypeLink.image.model.dto.response.PresignedUrlResponse;
import MeshX.HypeLink.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
