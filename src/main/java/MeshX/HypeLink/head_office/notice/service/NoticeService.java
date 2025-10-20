package MeshX.HypeLink.head_office.notice.service;

import MeshX.HypeLink.common.s3.S3UrlBuilder;
import MeshX.HypeLink.head_office.notice.model.dto.request.NoticeCreateReq;
import MeshX.HypeLink.head_office.notice.model.dto.request.NoticeUpdateReq;
import MeshX.HypeLink.head_office.notice.model.dto.response.NoticeInfoListRes;
import MeshX.HypeLink.head_office.notice.model.dto.response.NoticeInfoRes;
import MeshX.HypeLink.head_office.notice.model.entity.Notice;
import MeshX.HypeLink.head_office.notice.repository.NoticeJpaRepositoryVerify;
import MeshX.HypeLink.image.model.entity.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeJpaRepositoryVerify repository;
    private final S3UrlBuilder s3UrlBuilder;

    @Transactional
    public void createNotice(NoticeCreateReq dto) {
        Notice notice = dto.toEntity();

        // 이미지 추가
        if (dto.getImages() != null && !dto.getImages().isEmpty()) {
            dto.getImages().forEach(imageReq -> notice.addImage(imageReq.toEntity()));
        }

        repository.createNotice(notice);
    }

    public NoticeInfoListRes readList() {
        List<Notice> notices = repository.findAll();
        return NoticeInfoListRes.toDto(notices, s3UrlBuilder);
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

        // 이미지 업데이트
        if (dto.getImages() != null) {
            notice.clearImages();
            dto.getImages().forEach(imageReq -> notice.addImage(imageReq.toEntity()));
        }

        Notice updated = repository.update(notice);
        return NoticeInfoRes.toDto(updated, s3UrlBuilder);
    }

    @Transactional
    public void delete(Integer id) {
        Notice notice = repository.findById(id);
        repository.delete(notice);
    }
}
