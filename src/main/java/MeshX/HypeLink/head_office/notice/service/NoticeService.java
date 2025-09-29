package MeshX.HypeLink.head_office.notice.service;

import MeshX.HypeLink.head_office.notice.model.dto.request.NoticeCreateReq;
import MeshX.HypeLink.head_office.notice.model.dto.response.NoticeInfoListRes;
import MeshX.HypeLink.head_office.notice.model.dto.response.NoticeInfoRes;
import MeshX.HypeLink.head_office.notice.model.entity.Notice;
import MeshX.HypeLink.head_office.notice.repository.NoticeJpaRepositoryVerify;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeJpaRepositoryVerify repository;

    @Transactional
    public void createNotice(NoticeCreateReq dto) {
        repository.createNotice(dto.toEntity());
    }

    public NoticeInfoListRes readList() {
        List<Notice> notices = repository.findAll();
        return NoticeInfoListRes.toDto(notices);
    }

    public NoticeInfoRes readDetails(Integer id) {
        Notice notice = repository.findById(id);
        return NoticeInfoRes.toDto(notice);
    }

    @Transactional
    public NoticeInfoRes update(Integer id, String title, String contents, Boolean isOpen) {
        Notice notice = repository.findById(id);

        if(!title.isEmpty()) {
            notice.updateTitle(title);
        }
        if(!contents.isEmpty()){
            notice.updateContents(contents);
        }
        notice.changeOpen(isOpen);

        Notice update = repository.update(notice);
        return NoticeInfoRes.toDto(update);
    }

    @Transactional
    public void delete(Integer id) {
        Notice notice = repository.findById(id);
        repository.delete(notice);
    }
}
