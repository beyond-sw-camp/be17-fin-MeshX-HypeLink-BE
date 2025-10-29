package MeshX.HypeLink.direct_store.as.service;

import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.auth.model.entity.Store;
import MeshX.HypeLink.auth.repository.MemberJpaRepositoryVerify;
import MeshX.HypeLink.auth.repository.StoreJpaRepositoryVerify;
import MeshX.HypeLink.direct_store.as.model.dto.AsCreateReq;
import MeshX.HypeLink.direct_store.as.model.dto.AsUpdateReq;
import MeshX.HypeLink.direct_store.as.model.dto.MyAsDetailRes;
import MeshX.HypeLink.direct_store.as.model.dto.MyAsListRes;
import MeshX.HypeLink.head_office.as.exception.AsException;
import MeshX.HypeLink.head_office.as.model.entity.As;
import MeshX.HypeLink.head_office.as.model.entity.AsStatus;
import MeshX.HypeLink.head_office.as.repository.AsCommentJpaRepositoryVerify;
import MeshX.HypeLink.head_office.as.repository.AsJpaRepositoryVerify;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static MeshX.HypeLink.head_office.as.exception.AsExceptionMessage.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DirectStoreAsService {
    private final AsCommentJpaRepositoryVerify asCommentJpaRepositoryVerify;
    private final AsJpaRepositoryVerify asJpaRepositoryVerify;
    private final StoreJpaRepositoryVerify storeJpaRepositoryVerify;
    private final MemberJpaRepositoryVerify memberJpaRepositoryVerify;


    // AS 신청
    @Transactional
    public void create(Member member, AsCreateReq dto) {
        Store store = storeJpaRepositoryVerify.findByMember(member);
        asJpaRepositoryVerify.save(dto.toEntity(dto, store));
    }

    // 내 매장 AS 목록 조회
    public List<MyAsListRes> getMyAsList(Member member) {
        Store store = storeJpaRepositoryVerify.findByMember(member);
        List<As> asList = asJpaRepositoryVerify.findByStore(store);
        return MyAsListRes.fromList(asList);
    }

    // 내 AS 상세 조회
    public MyAsDetailRes getMyAsDetail(Integer asId, Member member) {
        Store store = storeJpaRepositoryVerify.findByMember(member);
        As as = asJpaRepositoryVerify.findById(asId);
        validateStoreOwnership(as, store.getId());
        return MyAsDetailRes.fromEntity(as);
    }
    // AS 수정
    @Transactional
    public MyAsDetailRes updateAsRequest(Integer asId, AsUpdateReq req, Member member) {
        Store store = storeJpaRepositoryVerify.findByMember(member);
        As as = asJpaRepositoryVerify.findById(asId);
        validateStoreOwnership(as, store.getId());
        validatePendingStatus(as);

        if (req.getTitle() != null) {
            as.updateTitle(req.getTitle());
        }
        if (req.getDescription() != null) {
            as.updateDescription(req.getDescription());
        }
        return MyAsDetailRes.fromEntity(as);
    }

    @Transactional
    public void deleteAsRequest(Integer asId, Member member) {
        Store store = storeJpaRepositoryVerify.findByMember(member);
        As as = asJpaRepositoryVerify.findById(asId);
        validateStoreOwnership(as, store.getId());
        validatePendingStatus(as);
        asJpaRepositoryVerify.delete(as);
    }

    private void validateStoreOwnership(As as, Integer storeId) {
        if (!as.getStore().getId().equals(storeId)) {
            throw new AsException(AS_UNAUTHORIZED);
        }
    }

    private void validatePendingStatus(As as) {
        if (as.getStatus() != AsStatus.PENDING) {
            throw new AsException(AS_CANNOT_MODIFY);
        }
    }

    public Member getMemberByUserName(String username) {
        return memberJpaRepositoryVerify.findByEmail(username);
    }
}