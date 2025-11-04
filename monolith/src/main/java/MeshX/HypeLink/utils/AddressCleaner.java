package MeshX.HypeLink.utils;

import java.util.regex.Pattern;

public class AddressCleaner {

    // 괄호 안 내용 제거: (역삼동, 강남파이낸스센터)
    private static final Pattern PAREN_PATTERN = Pattern.compile("\\(.*?\\)");

    // 층/호/동 정보 제거: 101동, 1203호, 3층
    private static final Pattern FLOOR_ROOM_PATTERN = Pattern.compile("\\d+(층|호|동)");

    // 지하/별관/신관/본관/별채/신축관 등 제거
    private static final Pattern BUILDING_PART_PATTERN = Pattern.compile("(지하\\d*층?|별관|신관|본관|별채|신축관)");

    // 특수문자 제거: #, ~, ,, .
    private static final Pattern SPECIAL_CHAR_PATTERN = Pattern.compile("[#~,.]");

    // 영문 B1, B2 같은 지하 표기 제거
    private static final Pattern BASEMENT_PATTERN = Pattern.compile("B\\d+");

    public static String clean(String address) {
        if (address == null) return null;

        String result = address;

        // 괄호 제거
        result = PAREN_PATTERN.matcher(result).replaceAll("");

        // 층/호/동 제거
        result = FLOOR_ROOM_PATTERN.matcher(result).replaceAll("");

        // 지하/별관/신관/본관/별채/신축관 제거
        result = BUILDING_PART_PATTERN.matcher(result).replaceAll("");

        // 영문 B1, B2 제거
        result = BASEMENT_PATTERN.matcher(result).replaceAll("");

        // 특수문자 제거
        result = SPECIAL_CHAR_PATTERN.matcher(result).replaceAll("");

        // 공백 정리
        result = result.trim().replaceAll("\\s{2,}", " ");

        return result;
    }
}
