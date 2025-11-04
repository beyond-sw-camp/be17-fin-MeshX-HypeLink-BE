package MeshX.HypeLink.common.Page;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
public class PageRes<T> {
    private List<T> content; //실제 데이터 목록을 담는 필드
    private Integer currentPage; //현재페이지
    private Integer totalPages; //전체페이지
    private Integer totalElements; //전체데이터 개수
    private Integer pageSize; // 한페이지에 들어있는 데이터 갯수
    private boolean isFirst; //현재페이지가 첫번째? - 다음페이지 버튼 비활성화 이용
    private boolean isLast; //현재페이지가 마지막? - 다음페이지 버튼 비활성화 이용

    public static <T> PageRes<T> toDto(Page<T> page) {
        return PageRes.<T>builder()
                .content(page.getContent())
                .currentPage(page.getNumber())
                .totalPages(page.getTotalPages())
                .totalElements((int)page.getTotalElements())
                .pageSize(page.getSize())
                .isFirst(page.isFirst())
                .isLast(page.isLast())
                .build();
    }
}
