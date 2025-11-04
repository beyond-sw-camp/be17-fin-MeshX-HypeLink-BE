package MeshX.HypeLink.direct_store.as.model.dto;

import MeshX.HypeLink.head_office.as.model.entity.As;
import MeshX.HypeLink.head_office.as.model.entity.AsStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class MyAsListRes {

    private Integer id;
    private String title;
    private AsStatus status;
    private LocalDateTime createdAt;
    private Integer commentCount;

    public static MyAsListRes from(As as) {
        return MyAsListRes.builder()
                .id(as.getId())
                .title(as.getTitle())
                .status(as.getStatus())
                .createdAt(as.getCreatedAt())
                .commentCount(as.getComments().size())
                .build();
    }

    public static List<MyAsListRes> fromList(List<As> asList) {
        return asList.stream()
                .map(MyAsListRes::from)
                .collect(Collectors.toList());
    }
}
