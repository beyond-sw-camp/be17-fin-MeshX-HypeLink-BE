import json
import itertools
from locust import HttpUser, task, between

# 계정 목록 (라운드 로빈 사용)
ACCOUNTS = itertools.cycle([
    ("gangnam@hypelink.com", "1234"),
    ("hongdae@hypelink.com", "1234"),
    ("jamsil@hypelink.com", "1234"),
])


class MemberListUser(HttpUser):
    # 요청 간 대기 시간(부하 패턴)
    wait_time = between(1, 3)

    def on_start(self):
        """
        User 인스턴스가 처음 시작될 때 1회만 로그인
        """
        self.email, self.password = next(ACCOUNTS)
        self.access_token = None
        self.login()

    def login(self):
        """
        Access Token 발급
        """
        payload = {
            "email": self.email,
            "password": self.password
        }

        res = self.client.post("/api/auth/login", json=payload)

        if res.status_code == 200:
            body = res.json()
            self.access_token = body.get("data", {}).get("accessToken")

            if self.access_token:
                print(f"[로그인 성공] {self.email}")
            else:
                print(f"[토큰 파싱 실패] {self.email} → 응답: {body}")
        else:
            print(f"[로그인 실패] {self.email}: {res.status_code}, {res.text}")
            self.access_token = None

    @task
    def load_member_list(self):
        """
        /api/member/member/list 요청
        """
        if not self.access_token:
            self.login()

        headers = {"Authorization": f"Bearer {self.access_token}"}

        res = self.client.get("/api/member/storepos/list", headers=headers)

        # 응답 코드 출력
        print(f"{self.email} → 응답: {res.status_code}")

        # 토큰 만료되면 재로그인
        if res.status_code == 401:
            print(f"[401] 토큰 만료 → 재로그인: {self.email}")
            self.login()
