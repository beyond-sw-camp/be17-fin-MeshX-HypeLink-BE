package MeshX.HypeLink.head_office.notice.repository;

import MeshX.HypeLink.head_office.notice.model.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {
}
