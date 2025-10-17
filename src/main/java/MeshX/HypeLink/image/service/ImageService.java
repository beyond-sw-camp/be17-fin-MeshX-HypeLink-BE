package MeshX.HypeLink.image.service;

import MeshX.HypeLink.common.s3.PresignedUrlRequestDto;
import MeshX.HypeLink.common.s3.S3FileManager;
import MeshX.HypeLink.common.s3.S3PresignedUrlInformationDto;
import MeshX.HypeLink.image.model.dto.ImageCreateRequestDto;
import MeshX.HypeLink.image.model.dto.PresignedUrlResponseDto;
import MeshX.HypeLink.image.model.entity.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ImageService {

    private static final String NOTICE_IMAGE_FILE_PATH = "images/notice";
    private static final String ITEM_IMAGE_FILE_PATH = "images/item";

    private final S3FileManager s3FileManager;


    public PresignedUrlResponseDto getNoticeImagePresignedUrl(PresignedUrlRequestDto requestDto) {
        return createPresignedUrl(requestDto, NOTICE_IMAGE_FILE_PATH);
    }

    public PresignedUrlResponseDto getItemImagePresignedUrl(PresignedUrlRequestDto requestDto) {
        return createPresignedUrl(requestDto, ITEM_IMAGE_FILE_PATH);
    }



//    @Transactional
//    public Integer createMemberImage(ImageCreateRequestDto requestDto, Integer memberIdx) {
//        Member member = memberRepository.findById(memberIdx)
//                .orElseThrow(() -> new MemberException(MEMBER_NOT_FOUND));
//
//        Image image = createImageEntity(requestDto);
//
//        switch (requestDto.imageType()) {
//            case PROFILE -> member.updateProfileImage(image);
//            case BANNER -> member.updateBannerImage(image);
//        }
//
//        return member.getProfileImage().getIdx();
//    }
//
//    @Transactional
//    public Integer createThumbnailImage(ImageCreateRequestDto requestDto, Integer videoIdx) {
//        Video video = videoRepository.findById(videoIdx)
//                .orElseThrow(() -> new VideoException(VIDEO_NOT_FOUND));
//
//        Image thumbnail = createImageEntity(requestDto);
//        video.updateThumbnailImage(thumbnail);
//
//        return video.getThumbnailImage().getIdx();
//    }

    private Image createImageEntity(ImageCreateRequestDto requestDto) {
        return Image.builder()
                .originalFilename(requestDto.originalFilename())
                .savedPath(requestDto.s3Key())
                .fileSize(requestDto.fileSize())
                .contentType(requestDto.contentType())
                .imageType(requestDto.imageType())
                .build();
    }

    private PresignedUrlResponseDto createPresignedUrl(PresignedUrlRequestDto requestDto, String directoryPath) {
        String s3Key = s3FileManager.generateS3Key(requestDto.originalFilename(), directoryPath);

        S3PresignedUrlInformationDto presignedUrl =
                s3FileManager.createPresignedUrl(s3Key, requestDto.contentType());

        return PresignedUrlResponseDto.builder()
                .uploadUrl(presignedUrl.uploadUrl())
                .s3Key(s3Key)
                .expiresIn(presignedUrl.expiresIn())
                .build();
    }
}