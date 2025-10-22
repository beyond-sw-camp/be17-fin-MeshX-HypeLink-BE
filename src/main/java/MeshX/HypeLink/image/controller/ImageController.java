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

    private final ImageService ImageService;

    @PostMapping("/presigned/notice")
    public ResponseEntity<BaseResponse<PresignedUrlResponse>> getNoticeImagePresignedUrl(@RequestBody PresignedUrlRequestDto requestDto) {
        PresignedUrlResponse presigned = ImageService.getNoticeImagePresignedUrl(requestDto);
        return ResponseEntity.ok(BaseResponse.of(presigned, ""));
    }

    @PostMapping("/presigned/item")
    public ResponseEntity<BaseResponse<PresignedUrlResponse>> getItemImagePresignedUrl(@RequestBody PresignedUrlRequestDto requestDto) {
        PresignedUrlResponse presigned = ImageService.getItemImagePresignedUrl(requestDto);
        return ResponseEntity.ok(BaseResponse.of(presigned, ""));
    }

//
//    @PostMapping
//    public ResponseEntity<BaseResponse<Integer>> registerImage(@RequestBody ImageCreateRequest requestDto) {
//        Integer imageIdx = ImageService.createNoticeImage(requestDto);
//        return ResponseEntity.ok(BaseResponse.of(imageIdx, "이미지가 등록되었습니다."));
//    }


}
