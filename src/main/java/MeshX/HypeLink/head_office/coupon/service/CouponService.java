package MeshX.HypeLink.head_office.coupon.service;

import MeshX.HypeLink.common.Page.PageReq;
import MeshX.HypeLink.common.Page.PageRes;
import MeshX.HypeLink.head_office.coupon.model.dto.request.CouponCreateReq;
import MeshX.HypeLink.head_office.coupon.model.dto.response.CouponInfoListRes;
import MeshX.HypeLink.head_office.coupon.model.dto.response.CouponInfoRes;
import MeshX.HypeLink.head_office.coupon.model.entity.Coupon;
import MeshX.HypeLink.head_office.coupon.repository.CouponJpaRepositoryVerify;
import MeshX.HypeLink.utils.datechanger.DateChanger;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static MeshX.HypeLink.utils.datechanger.DateChanger.toDate;
import static MeshX.HypeLink.utils.datechanger.DateChanger.toPeriod;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponJpaRepositoryVerify couponRepository;

    public void save(CouponCreateReq dto) {
        Coupon coupon = dto.toEntity(DateChanger.toDate(dto.getPeriod()));
        couponRepository.save(coupon);
    }

    public void delete(Integer id) {
        couponRepository.delete(id);
    }

    public CouponInfoRes read(Integer id) {
        Coupon coupon = couponRepository.findById(id);
        String period = toPeriod(coupon.getStartDate(), coupon.getEndDate());
        return CouponInfoRes.toDto(coupon, period);
    }

    public List<CouponInfoRes> readAll() {
        List<Coupon> coupons = couponRepository.findAll();
        return coupons.stream()
                .map(coupon -> {
                    String period = toPeriod(coupon.getStartDate(), coupon.getEndDate());
                    return CouponInfoRes.toDto(coupon, period);
                })
                .collect(Collectors.toList());
    }

    public PageRes<CouponInfoRes> readAll(PageReq pageReq) {
        Page<Coupon> entityPage = couponRepository.findAll(pageReq);
        Page<CouponInfoRes> dtoPage = entityPage.map(coupon -> {
            String period = toPeriod(coupon.getStartDate(), coupon.getEndDate());
            return CouponInfoRes.toDto(coupon, period);
        });
        return PageRes.toDto(dtoPage);
    }

    public CouponInfoListRes readAll(Pageable pageable) {
        Page<Coupon> couponPage = couponRepository.findAll(pageable);
        List<CouponInfoRes> couponInfoResList = couponPage.getContent().stream()
                .map(coupon -> {
                    String period = toPeriod(coupon.getStartDate(), coupon.getEndDate());
                    return CouponInfoRes.toDto(coupon, period);
                })
                .collect(Collectors.toList());
        return CouponInfoListRes.builder()
                .couponInfoResList(couponInfoResList)
                .totalPages(couponPage.getTotalPages())
                .totalElements(couponPage.getTotalElements())
                .currentPage(couponPage.getNumber())
                .pageSize(couponPage.getSize())
                .build();
    }

}
