package MeshX.HypeLink.head_office.coupon.controller;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.head_office.coupon.constants.CouponSwaggerConstants;
import MeshX.HypeLink.head_office.coupon.model.dto.request.CouponCreateReq;
import MeshX.HypeLink.head_office.coupon.model.dto.request.CouponUpdateReq;
import MeshX.HypeLink.head_office.coupon.model.dto.response.CouponInfoListRes;
import MeshX.HypeLink.head_office.coupon.model.dto.response.CouponInfoRes;
import MeshX.HypeLink.head_office.coupon.service.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "본부 쿠폰 관리", description = "본부에서 쿠폰을 생성, 조회, 삭제하는 API")
@RestController
@RequestMapping("/api/coupon")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @Operation(summary = "쿠폰 생성", description = "새로운 쿠폰을 생성합니다. 쿠폰 타입은 PERCENTAGE(퍼센트 할인) 또는 FIXED(고정 금액 할인)입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "쿠폰 생성 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = CouponSwaggerConstants.COUPON_CREATE_SUCCESS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "요청 데이터가 유효하지 않습니다.", content = @Content),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content)
    })
    @PostMapping("/create")
    public ResponseEntity<BaseResponse<String>> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "쿠폰 생성 정보",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CouponCreateReq.class),
                            examples = @ExampleObject(value = CouponSwaggerConstants.COUPON_CREATE_REQ_EXAMPLE)
                    )
            )
            @RequestBody CouponCreateReq dto) {
        couponService.save(dto);
        return ResponseEntity.ok(BaseResponse.of("쿠폰 생성 완료"));
    }

    @Operation(summary = "쿠폰 삭제", description = "특정 쿠폰을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "쿠폰 삭제 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = CouponSwaggerConstants.COUPON_DELETE_SUCCESS_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content),
            @ApiResponse(responseCode = "404", description = "해당 쿠폰을 찾을 수 없습니다.", content = @Content)
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<String>> delete(
            @Parameter(description = "삭제할 쿠폰의 ID") @PathVariable Integer id) {
        couponService.delete(id);
        return ResponseEntity.ok(BaseResponse.of("쿠폰 삭제 완료"));
    }

    @Operation(summary = "쿠폰 상세 조회", description = "특정 쿠폰의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "쿠폰 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = CouponSwaggerConstants.COUPON_INFO_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content),
            @ApiResponse(responseCode = "404", description = "해당 쿠폰을 찾을 수 없습니다.", content = @Content)
    })
    @GetMapping("/read/{id}")
    public ResponseEntity<BaseResponse<CouponInfoRes>> read(
            @Parameter(description = "조회할 쿠폰의 ID") @PathVariable Integer id) {
        CouponInfoRes result = couponService.read(id);
        return ResponseEntity.ok(BaseResponse.of(result, "검색 완료"));
    }

    @Operation(summary = "전체 쿠폰 목록 조회", description = "모든 쿠폰의 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "쿠폰 목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = CouponSwaggerConstants.COUPON_INFO_LIST_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content)
    })
    @GetMapping("/read/all")
    public ResponseEntity<BaseResponse<List<CouponInfoRes>>> readAll() {
        List<CouponInfoRes> result = couponService.readAll();
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @Operation(summary = "쿠폰 목록 조회 (페이징)", description = "쿠폰 목록을 페이징하여 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "쿠폰 목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = CouponSwaggerConstants.COUPON_INFO_LIST_PAGING_RES_EXAMPLE))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content)
    })
    @GetMapping("/list")
    public ResponseEntity<BaseResponse<CouponInfoListRes>> getCouponList(
            @Parameter(description = "페이징 정보 (page, size, sort)", example = "page=0&size=20") Pageable pageable) {
        CouponInfoListRes result = couponService.readAll(pageable);
        return ResponseEntity.status(200).body(BaseResponse.of(result));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<BaseResponse<CouponInfoRes>> update(
            @PathVariable Integer id,
            @RequestBody CouponUpdateReq dto) {
        CouponInfoRes result = couponService.update(id, dto);
        return ResponseEntity.ok(BaseResponse.of(result, "쿠폰 수정 완료"));
    }
}
