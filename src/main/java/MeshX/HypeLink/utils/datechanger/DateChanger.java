package MeshX.HypeLink.utils.datechanger;

import java.time.LocalDate;

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
}
