package MeshX.HypeLink.head_office.as.service;

import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.auth.model.entity.MemberRole;
import MeshX.HypeLink.auth.repository.MemberJpaRepositoryVerify;
import MeshX.HypeLink.head_office.as.exception.AsException;
import MeshX.HypeLink.head_office.as.model.dto.req.AsStatusUpdateReq;
import MeshX.HypeLink.head_office.as.model.dto.req.CommentCreateReq;
import MeshX.HypeLink.head_office.as.model.dto.res.AsDetailRes;
import MeshX.HypeLink.head_office.as.model.dto.res.AsListRes;
import MeshX.HypeLink.head_office.as.model.entity.As;
import MeshX.HypeLink.head_office.as.model.entity.AsComment;
import MeshX.HypeLink.head_office.as.model.entity.AsStatus;
import MeshX.HypeLink.head_office.as.repository.AsCommentJpaRepositoryVerify;
import MeshX.HypeLink.head_office.as.repository.AsJpaRepositoryVerify;
import MeshX.HypeLink.head_office.as.repository.AsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static MeshX.HypeLink.head_office.as.exception.AsExceptionMessage.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AsService {
    private final AsRepository asRepository;
    private final AsJpaRepositoryVerify asJpaRepositoryVerify;
    private final AsCommentJpaRepositoryVerify asCommentJpaRepositoryVerify;
    private final MemberJpaRepositoryVerify memberJpaRepositoryVerify;

    // 전체 AS 목록 조회
    public List<AsListRes> getAllAsRequests() {
        List<As> asList = asRepository.findAll();
        return AsListRes.fromList(asList);
    }

    // AS 상세 조회
    public AsDetailRes getAsDetail(Integer asId) {
        As as = asJpaRepositoryVerify.findById(asId);
        return AsDetailRes.from(as);
    }

    // AS 상태 변경
    @Transactional
    public AsDetailRes updateAsStatus(Integer asId, AsStatusUpdateReq req) {
        As as = asJpaRepositoryVerify.findById(asId);
        as.updateStatus(req.getStatus());
        return AsDetailRes.from(as);
    }

    // AS에 댓글 작성
    @Transactional
    public AsDetailRes createComment(Integer asId, CommentCreateReq req, Member member) {
        As as = asJpaRepositoryVerify.findById(asId);
        AsComment comment = req.toEntity(as, member);
        asCommentJpaRepositoryVerify.save(comment);
        return AsDetailRes.from(as);
    }

    public Member getMemberByUserNameAndValidate(String username) {
        Member member = memberJpaRepositoryVerify.findByEmail(username);
        if(member.getRole() == MemberRole.ADMIN || member.getRole() == MemberRole.MANAGER)
        {
            return member;
        }
        else throw new AsException(NO_PERMISSION);
    }

}
