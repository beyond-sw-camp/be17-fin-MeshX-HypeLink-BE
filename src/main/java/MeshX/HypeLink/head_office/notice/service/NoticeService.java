package MeshX.HypeLink.head_office.notice.service;

import MeshX.HypeLink.common.Page.PageReq;
import MeshX.HypeLink.common.Page.PageRes;
import MeshX.HypeLink.common.s3.S3UrlBuilder;
import MeshX.HypeLink.head_office.notice.model.dto.request.NoticeCreateReq;
import MeshX.HypeLink.head_office.notice.model.dto.request.NoticeUpdateReq;
import MeshX.HypeLink.head_office.notice.model.dto.response.NoticeInfoListRes;
import MeshX.HypeLink.head_office.notice.model.dto.response.NoticeInfoRes;
import MeshX.HypeLink.head_office.notice.model.entity.Notice;
import MeshX.HypeLink.head_office.notice.repository.NoticeJpaRepositoryVerify;
import MeshX.HypeLink.image.model.entity.Image;
import MeshX.HypeLink.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeJpaRepositoryVerify repository;
    private final S3UrlBuilder s3UrlBuilder;
    private final ImageService imageService;

    @Transactional
    public void createNotice(NoticeCreateReq dto) {
        Notice notice = dto.toEntity();

        if (dto.getImages() != null && !dto.getImages().isEmpty()) {
            List<Image> images = imageService.createImagesFromRequest(dto.getImages());
            images.forEach(notice::addImage);
        }

        repository.createNotice(notice);
    }

    public NoticeInfoListRes readList() {
        List<Notice> notices = repository.findAll();
        return NoticeInfoListRes.toDto(notices, this::exportS3Url);
    }

    public PageRes<NoticeInfoRes> readList(PageReq pageReq){
        Page<Notice> entityPage = repository.findAll(pageReq);
        Page<NoticeInfoRes> dtoPage = NoticeInfoRes.toDtoPage(entityPage, this::exportS3Url);
        return PageRes.toDto(dtoPage);
    }

    public NoticeInfoRes readDetails(Integer id) {
        Notice notice = repository.findById(id);
        return NoticeInfoRes.toDto(notice, this::exportS3Url);
    }

    @Transactional
    public NoticeInfoRes update(Integer id, NoticeUpdateReq dto) {
        Notice notice = repository.findById(id);

        if(dto.getTitle() != null && !dto.getTitle().isEmpty()) {
            notice.updateTitle(dto.getTitle());
        }
        if(dto.getContents() != null && !dto.getContents().isEmpty()){
            notice.updateContents(dto.getContents());
        }
        if(dto.getIsOpen() != null) {
            notice.changeOpen(dto.getIsOpen());
        }
        if(dto.getAuthor() != null && !dto.getAuthor().isEmpty()) {
            notice.updateAuthor(dto.getAuthor());
        }
        // 이미지 업데이트
        if (dto.getImages() != null) {
            notice.clearImages();
            List<Image> images = imageService.createImagesFromRequest(dto.getImages());
            images.forEach(notice::addImage);
        }

        Notice updated = repository.update(notice);
        return NoticeInfoRes.toDto(updated, this::exportS3Url);
    }

    @Transactional
    public void delete(Integer id) {
        Notice notice = repository.findById(id);
        repository.delete(notice);
    }

    public String exportS3Url(Image image) {
        return s3UrlBuilder.buildPublicUrl(image.getSavedPath());
    }
}

