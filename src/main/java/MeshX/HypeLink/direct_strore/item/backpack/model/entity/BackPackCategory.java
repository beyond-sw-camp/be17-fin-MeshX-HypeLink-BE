package MeshX.HypeLink.direct_strore.item.backpack.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BackPackCategory {
    DAYPACK("데이팩"),          // 가볍게 메는 백팩
    LAPTOP("노트북 백팩"),      // 노트북 전용 수납
    HIKING("등산용 배낭"),      // 아웃도어/등산
    TRAVEL("여행용 백팩"),      // 장거리 여행/백패킹
    FASHION("패션 백팩");       // 스타일 위주

    private final String koreanName;
}
