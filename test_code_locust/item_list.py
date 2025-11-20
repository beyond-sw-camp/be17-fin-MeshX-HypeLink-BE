import json
import itertools
from locust import HttpUser, task, between

# 계정 목록 (무한 반복)
ACCOUNTS = itertools.cycle([
    ("manager@company.com", "1234"),
    ("manager2@company.com", "1234"),
    ("manager3@company.com", "1234"),
])


class LoginUser(HttpUser):
    wait_time = between(1, 3)

    def on_start(self):
        """
        각 User가 실행될 때마다 ACCOUNTS 리스트에서
        다음 계정(email, pw) 가져옴
        """
        self.email, self.password = next(ACCOUNTS)
        self.access_token = None
        self.login()

    def login(self):
        payload = {
            "email": self.email,
            "password": self.password
        }

        res = self.client.post("/api/auth/login", json=payload)

        if res.status_code == 200:
            data = res.json()

            # 응답 전체 한번 찍어서 구조 확인해보는 게 좋음
            # print("로그인 응답:", data)

            # 만약 { "code": 200, "message": "...", "data": { "accessToken": "..." } } 구조라면:
            self.access_token = data.get("data", {}).get("accessToken")

            if not self.access_token:
                print(f"토큰 파싱 실패: {self.email}, 응답={data}")
            else:
                print(f"로그인 성공: {self.email}, token={self.access_token}")

        else:
            print(f"로그인 실패 {self.email}: {res.status_code}, {res.text}")
            self.access_token = None

    @task
    def call_protected_api(self):
        if not self.access_token:
            self.login()

        headers = {"Authorization": f"Bearer {self.access_token}"}

        params = {
            "page": 0,
            "size": 10,
            "sort": "id,asc",
            "keyWord": "",
            "category": "all"
        }

        res = self.client.get("/api/item/list", headers=headers, params=params)

        print(f"{res.status_code}")

        # 인증 만료 처리
        if res.status_code == 401:
            print(f"토큰 만료 → 재로그인: {self.email}")
            self.login()
        else:
            print(f"{self.email} → 응답 코드: {res.status_code}")
