package MeshX.HypeLink.head_office.notice.model.dto.response;

import MeshX.HypeLink.common.s3.S3UrlBuilder;
import MeshX.HypeLink.head_office.notice.model.entity.Notice;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class NoticeInfoRes {
    private String title;
    private String contents;
    private Boolean isOpen;
    private List<ImageDto> images;

    public static NoticeInfoRes toDto(Notice entity, S3UrlBuilder urlBuilder) {
        List<ImageDto> imageDtos = entity.getImageList().stream()
                .map(image -> ImageDto.builder()
                        .id(image.getId())
                        .originalFilename(image.getOriginalFilename())
                        .imageUrl(urlBuilder.buildPublicUrl(image.getSavedPath()))
                        .fileSize(image.getFileSize())
                        .build())
                .collect(Collectors.toList());

        return NoticeInfoRes.builder()
                .title(entity.getTitle())
                .contents(entity.getContents())
                .isOpen(entity.getIsOpen())
                .images(imageDtos)
                .build();
    }

    @Builder
    private NoticeInfoRes(String title, String contents, Boolean isOpen, List<ImageDto> images) {
        this.title = title;
        this.contents = contents;
        this.isOpen = isOpen;
        this.images = images;
    }

    @Getter
    @Builder
    public static class ImageDto {
        private Integer id;
        private String originalFilename;
        private String imageUrl;
        private Long fileSize;
    }
}
