package org.example.apidirect.customer.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class Customer {
    private Integer id;
    private String name;
    private String phone;
    private LocalDate birthDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void updatePhone(String phone) {
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("전화번호는 필수입니다");
        }
        // 전화번호는 불변이어야 하므로 새로운 객체 생성 필요
        // Domain은 불변 객체이므로 setter 사용 불가
    }

    public void updateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 필수입니다");
        }
    }

    public int getAge() {
        if (birthDate == null) {
            return 0;
        }
        return LocalDate.now().getYear() - birthDate.getYear();
    }

    public String getAgeGroup() {
        int age = getAge();
        if (age < 20) return "10대";
        if (age < 30) return "20대";
        if (age < 40) return "30대";
        if (age < 50) return "40대";
        if (age < 60) return "50대";
        return "60대 이상";
    }

    public boolean isAdult() {
        return getAge() >= 19;
    }
}
