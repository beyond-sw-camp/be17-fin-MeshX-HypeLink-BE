package MeshX.HypeLink.image.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.common.s3.PresignedUrlRequestDto;
import MeshX.HypeLink.image.model.dto.ImageCreateRequestDto;
import MeshX.HypeLink.image.model.dto.PresignedUrlResponseDto;
import MeshX.HypeLink.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<BaseResponse<PresignedUrlResponseDto>> getNoticeImagePresignedUrl(@RequestBody PresignedUrlRequestDto requestDto) {
        PresignedUrlResponseDto presigned = ImageService.getNoticeImagePresignedUrl(requestDto);
        return ResponseEntity.ok(BaseResponse.of(presigned, ""));
    }

    @PostMapping("/presigned/item")
    public ResponseEntity<BaseResponse<PresignedUrlResponseDto>> getItemImagePresignedUrl(@RequestBody PresignedUrlRequestDto requestDto) {
        PresignedUrlResponseDto presigned = ImageService.getItemImagePresignedUrl(requestDto);
        return ResponseEntity.ok(BaseResponse.of(presigned, ""));
    }


//    @PostMapping
//    public ResponseEntity<BaseResponse<Integer>> registerImage(@RequestBody ImageCreateRequestDto requestDto,
//                                                               @AuthenticationPrincipal MemberDetailsDto loginMember) {
//        Integer imageIdx = ImageService.createMemberImage(requestDto, loginMember.getIdx());
//        return ResponseEntity.ok(BaseResponse.of(imageIdx, "이미지가 등록되었습니다."));
//    }


}
