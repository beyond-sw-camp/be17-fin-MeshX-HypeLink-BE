package MeshX.HypeLink.image.service;

import MeshX.HypeLink.common.s3.PresignedUrlRequestDto;
import MeshX.HypeLink.common.s3.S3FileManager;
import MeshX.HypeLink.common.s3.S3PresignedUrlInformationDto;
import MeshX.HypeLink.image.model.dto.request.ImageCreateRequest;
import MeshX.HypeLink.image.model.dto.response.PresignedUrlResponse;
import MeshX.HypeLink.image.model.entity.Image;
import MeshX.HypeLink.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageService {

    private static final String NOTICE_IMAGE_FILE_PATH = "images/notice/";
    private static final String ITEM_IMAGE_FILE_PATH = "images/item/";
    private static final String PROMOTION_IMAGE_FILE_PATH = "images/promotion/";

    private final S3FileManager s3FileManager;
    private final ImageRepository imageRepository;

    // ... other methods ...

    @Transactional
    public List<Image> createImagesFromRequest(List<ImageCreateRequest> imageDtos) {
        if (imageDtos == null || imageDtos.isEmpty()) {
            return Collections.emptyList();
        }

        List<Image> images = imageDtos.stream()
                .map(ImageCreateRequest::toEntity)
                .collect(Collectors.toList());

        // Save each Image entity to the database
        return imageRepository.saveAll(images);
    }

    // ... private helper methods ...

    public PresignedUrlResponse getNoticeImagePresignedUrl(PresignedUrlRequestDto requestDto) {
        return createPresignedUrl(requestDto, NOTICE_IMAGE_FILE_PATH);
    }

    public PresignedUrlResponse getItemImagePresignedUrl(PresignedUrlRequestDto requestDto) {
        return createPresignedUrl(requestDto, ITEM_IMAGE_FILE_PATH);
    }

    public PresignedUrlResponse getPromotionImagePresignedUrl(PresignedUrlRequestDto requestDto) {
        return createPresignedUrl(requestDto, PROMOTION_IMAGE_FILE_PATH);
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

    private String extractExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex == -1) {
//            throw new Exception();
        }
        return fileName.substring(dotIndex + 1).toLowerCase();
    }
}
