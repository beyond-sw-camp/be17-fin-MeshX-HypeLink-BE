package MeshX.HypeLink.head_office.notice.repository;

import MeshX.HypeLink.head_office.notice.exception.NoticeException;
import MeshX.HypeLink.head_office.notice.model.entity.Notice;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static MeshX.HypeLink.head_office.notice.exception.NoticeExceptionMessage.NOTICE_NOT_FOUND;
import static MeshX.HypeLink.head_office.promotion.exception.PromotionExceptionMessage.NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class NoticeJpaRepositoryVerify {
    private final NoticeRepository repository;

    public void createNotice(Notice entity) {
        repository.save(entity);
    }

    public Notice findById(Integer id) {
        Optional<Notice> optional = repository.findById(id);
        if(optional.isPresent()) {
            return optional.get();
        }
        throw new NoticeException(NOT_FOUND);
    }

    public List<Notice> findAll() {
        List<Notice> notices = repository.findAll();
        if(!notices.isEmpty()) {
            return notices;
        }
        throw new NoticeException(NOT_FOUND);
    }

    public Page<Notice> findAll(Pageable pageable){
        Page<Notice> page = repository.findAll(pageable);
        if (page.hasContent()) {
            return page;
        }
        throw new  NoticeException(NOTICE_NOT_FOUND);
    }

    public void delete(Notice entity) {
        repository.delete(entity);
    }

    public Notice update(Notice entity) {
        return repository.save(entity);
    }
}
