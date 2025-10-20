package MeshX.HypeLink.head_office.notice.service;

import MeshX.HypeLink.common.Page.PageReq;
import MeshX.HypeLink.common.Page.PageRes;

import MeshX.HypeLink.common.s3.S3UrlBuilder;

import MeshX.HypeLink.direct_store.order.exception.DirectOrderException;
import MeshX.HypeLink.direct_store.order.exception.DirectOrderExceptionMessage;

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

        List<Image> images = imageService.createImagesFromRequest(dto.getImages());
        images.forEach(notice::addImage);

        repository.createNotice(notice);
    }

    public NoticeInfoListRes readList() {
        List<Notice> notices = repository.findAll();
        return NoticeInfoListRes.toDto(notices, s3UrlBuilder);
    }

    public PageRes<NoticeInfoRes> readList(PageReq pageReq){
        Page<Notice> entityPage = repository.findAll(pageReq);
        Page<NoticeInfoRes> dtoPage = NoticeInfoRes.toDtoPage(entityPage, s3UrlBuilder);
        return PageRes.toDto(dtoPage);
    }

    public PageRes<NoticeInfoRes> readList(PageReq pageReq){
        Page<Notice>  entityPage = repository.findAll(pageReq);
        Page<NoticeInfoRes> dtoPage = NoticeInfoRes.toDtoPage(entityPage);
        return PageRes.toDto(dtoPage);
    }

    public NoticeInfoRes readDetails(Integer id) {
        Notice notice = repository.findById(id);
        return NoticeInfoRes.toDto(notice, s3UrlBuilder);
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
        if(!dto.getAuthor().isEmpty()) {
            notice.updateAuthor(dto.getAuthor());
        }

        // 이미지 업데이트
        if (dto.getImages() != null) {
            notice.clearImages();
            List<Image> images = imageService.createImagesFromRequest(dto.getImages());
            images.forEach(notice::addImage);
        }

        if(!author.isEmpty()) {
            notice.updateAuthor(author);
        }
        notice.changeOpen(isOpen);


        Notice updated = repository.update(notice);
        return NoticeInfoRes.toDto(updated, s3UrlBuilder);
    }

    @Transactional
    public void delete(Integer id) {
        Notice notice = repository.findById(id);
        repository.delete(notice);
    }
}

