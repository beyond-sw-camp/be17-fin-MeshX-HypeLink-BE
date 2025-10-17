package MeshX.HypeLink.head_office.item.model.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class CreateItemReq {
    private String enName; // 이름
    private String koName; // 이름
    private Integer amount; // 가격
    private String category;
    private String itemCode;
    private String content; // 아이템 설명
    private String company; // 회사
    private List<CreateItemDetailReq> itemDetailList;
}
