package MeshX.HypeLink.head_office.coupon.repository;

import MeshX.HypeLink.common.Page.PageReq;
import MeshX.HypeLink.head_office.coupon.exception.CouponException;
import MeshX.HypeLink.head_office.coupon.model.dto.request.CouponCreateReq;
import MeshX.HypeLink.head_office.coupon.model.entity.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static MeshX.HypeLink.head_office.coupon.exception.CouponExceptionType.COUPON_NOT_FOUNT;

@Repository
@RequiredArgsConstructor
public class CouponJpaRepositoryVerify {

    private final CouponRepository repository;

    public void save(Coupon coupon) {
        repository.save(coupon);
    }

    public void delete(Integer id) {
        Coupon coupon = repository.findById(id)
                .orElseThrow(()-> new CouponException(COUPON_NOT_FOUNT));
        repository.delete(coupon);
    }

    public Coupon findById(Integer id) {
        Coupon coupon = repository.findById(id)
                .orElseThrow(()-> new CouponException(COUPON_NOT_FOUNT));
        return coupon;
    }

    public List<Coupon> findAll() {
        return repository.findAll();
    }

    public Page<Coupon> findAll(PageReq pageReq) {
        Page<Coupon> page = repository.findAll(pageReq.toPageRequest());
        if (page.hasContent()) {
            return page;
        }
        throw new CouponException(COUPON_NOT_FOUNT);
    }

    public Page<Coupon> findAll(Pageable pageable) {
        Page<Coupon> page = repository.findAll(pageable);
        if (page.isEmpty()) {
            throw new CouponException(COUPON_NOT_FOUNT);
        }
        return page;
    }

    public Coupon update(Coupon coupon) {
        return repository.save(coupon);
    }

}
