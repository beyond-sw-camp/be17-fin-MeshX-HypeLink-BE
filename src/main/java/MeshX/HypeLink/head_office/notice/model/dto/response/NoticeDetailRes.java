package MeshX.HypeLink.head_office.notice.model.dto.response;

import MeshX.HypeLink.head_office.notice.model.entity.Notice;
import MeshX.HypeLink.image.model.dto.response.ImageUploadResponse;
import MeshX.HypeLink.image.model.entity.Image;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public class NoticeDetailRes {
    private Integer id;
    private String title;
    private String contents;
    private String author;
    private Boolean isOpen;
    private LocalDateTime date;
    private List<ImageUploadResponse> images;

    public static NoticeDetailRes toDto(Notice entity, Function<Image, String> urlGenerator) {
        LocalDateTime displayDate = entity.getUpdatedAt() != null
                ? entity.getUpdatedAt()
                : entity.getCreatedAt();

        // Use the new getImages() helper method
        List<ImageUploadResponse> imageDtos = entity.getImages().stream()
                .map(image -> ImageUploadResponse.builder()
                        .id(image.getId())
                        .originalName(image.getOriginalFilename())
                        .imageUrl(urlGenerator.apply(image))
                        .imageSize(image.getFileSize())
                        .build())
                .collect(Collectors.toList());

        return NoticeDetailRes.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .contents(entity.getContents())
                .isOpen(entity.getIsOpen())
                .images(imageDtos)
                .author(entity.getAuthor())
                .date(displayDate)
                .build();
    }

    @Builder
    private NoticeDetailRes(String title, String contents, Boolean isOpen, List<ImageUploadResponse> images, String author, Integer id, LocalDateTime date) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.contents = contents;
        this.isOpen = isOpen;
        this.images = images;
        this.date = date;
    }

    public static Page<NoticeDetailRes> toDtoPage(Page<Notice> page, Function<Image, String> urlGenerator) {
        return page.map(notice -> NoticeDetailRes.toDto(notice, urlGenerator));
    }
}