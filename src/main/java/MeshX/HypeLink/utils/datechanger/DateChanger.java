package MeshX.HypeLink.utils.datechanger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateChanger {

    // 시작일, 종료일을 하나의 변수로 합치는 메서드 (프론트에서 변수명은 period로 쓰임)
    public static String toPeriod(LocalDate startDate, LocalDate endDate) {
        return startDate + " ~ " + endDate;
    }

    //"YYYY-MM-DD ~ YYYY-MM-DD" 형식의 문자열을 LocalDate 배열로 변환
    // return [0]: startDate, [1]: endDate

    // period 데이터를 db에 저장할 수 있게 시작일, 종료일로 나누는 메서드
    public static LocalDate[] toDate(String period) {
        String[] dates = period.split(" ~ ");
        LocalDate startDate = LocalDate.parse(dates[0]);
        LocalDate endDate = LocalDate.parse(dates[1]);
        return new LocalDate[]{startDate, endDate};
    }

    public static String toKoreanDate(String isoDateTime) {
        if (isoDateTime == null || isoDateTime.isBlank()) {
            return "";
        }
        try {
            // 1️⃣ ISO 문자열을 LocalDateTime으로 변환
            LocalDateTime dateTime = LocalDateTime.parse(isoDateTime);

            // 2️⃣ 한국식 날짜 포맷 지정
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일");

            // 3️⃣ 문자열로 반환
            return dateTime.format(formatter);
        } catch (Exception e) {
            // 혹시 "yyyy-MM-dd" 형식만 들어오는 경우도 대비
            try {
                LocalDate date = LocalDate.parse(isoDateTime.substring(0, 10));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일");
                return date.format(formatter);
            } catch (Exception ignored) {
                return isoDateTime; // 변환 실패 시 원본 반환
            }
        }
    }
}
