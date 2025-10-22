package MeshX.HypeLink.head_office.coupon.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.common.Page.PageReq;
import MeshX.HypeLink.common.Page.PageRes;
import MeshX.HypeLink.head_office.coupon.model.dto.request.CouponCreateReq;
import MeshX.HypeLink.head_office.coupon.model.dto.response.CouponInfoRes;
import MeshX.HypeLink.head_office.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupon")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<String>> create(@RequestBody CouponCreateReq dto) {
        couponService.save(dto);
        return ResponseEntity.ok(BaseResponse.of("쿠폰 생성 완료"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<String>> delete(@PathVariable Integer id) {
        couponService.delete(id);
        return ResponseEntity.ok(BaseResponse.of("쿠폰 삭제 완료"));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<BaseResponse<CouponInfoRes>> read(@PathVariable Integer id) {
        CouponInfoRes result = couponService.read(id);
        return ResponseEntity.ok(BaseResponse.of(result, "검색 완료"));
    }

    @GetMapping("/read/all")
    public ResponseEntity<BaseResponse<List<CouponInfoRes>>> readAll() {
        List<CouponInfoRes> result = couponService.readAll();
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @GetMapping("/read/page/all")
    public ResponseEntity<BaseResponse<PageRes<CouponInfoRes>>> readAllPage(PageReq pageReq) {
        PageRes<CouponInfoRes> pageRes = couponService.readAll(pageReq);
        return ResponseEntity.ok(BaseResponse.of(pageRes));
    }

}
