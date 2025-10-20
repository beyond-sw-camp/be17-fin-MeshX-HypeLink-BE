package MeshX.HypeLink.image.service;

import MeshX.HypeLink.common.s3.PresignedUrlRequestDto;
import MeshX.HypeLink.common.s3.S3FileManager;
import MeshX.HypeLink.common.s3.S3PresignedUrlInformationDto;
import MeshX.HypeLink.image.model.dto.response.PresignedUrlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {

    private static final String NOTICE_IMAGE_FILE_PATH = "images/notice/";
    private static final String ITEM_IMAGE_FILE_PATH = "images/item/";

    private final S3FileManager s3FileManager;


    public PresignedUrlResponse getNoticeImagePresignedUrl(PresignedUrlRequestDto requestDto) {
        return createPresignedUrl(requestDto, NOTICE_IMAGE_FILE_PATH);
    }

    public PresignedUrlResponse getItemImagePresignedUrl(PresignedUrlRequestDto requestDto) {
        return createPresignedUrl(requestDto, ITEM_IMAGE_FILE_PATH);
    }



    private PresignedUrlResponse createPresignedUrl(PresignedUrlRequestDto requestDto, String directoryPath) {
        String s3Key = s3FileManager.generateS3Key(requestDto.getOriginalFilename(), directoryPath);

        S3PresignedUrlInformationDto presignedUrl =
                s3FileManager.createPresignedUrl(s3Key, requestDto.getContentType());

        return PresignedUrlResponse.builder()
                .uploadUrl(presignedUrl.getUploadUrl())
                .s3Key(s3Key)
                .expiresIn(presignedUrl.getExpiresIn())
                .build();
    }
}