package MeshX.HypeLink.common.Page;

import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Getter
public class PageReq {
    private  Integer page; //시작되는 번호 페이지
    private  Integer pageSize; //한 페이지에 포함될 데이터 개수
    private String sortBy = "id"; // 어떤 컬럼을 기준으로 정렬할지 지정 필드
    private String direction = "desc"; // asc = 오름차순, desc = 내림차순

    public PageRequest toPageRequest() {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        return PageRequest.of(page, pageSize, sort);
    }
}
