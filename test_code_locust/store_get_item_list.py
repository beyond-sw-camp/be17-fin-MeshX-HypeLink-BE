import json
import itertools
from locust import HttpUser, task, between

ACCOUNTS = itertools.cycle([
    ("pos1_1@store.com", "1234"),
    ("pos1_2@store.com", "1234"),
    ("pos2_1@store.com", "1234"),
    ("pos2_2@store.com", "1234"),
    ("pos3_1@store.com", "1234"),
    ("pos3_2@store.com", "1234"),
])

class GetItemListInDirectStore(HttpUser):
    wait_time = between(1, 3)

    def on_start(self):
        self.email, self.password = next(ACCOUNTS)
        self.access_token = None
        self.login()

    # @task
    def login(self):
        payload = {
            "email": "manager3@company.com",
            "password": "1234"
        }
        res = self.client.post("/api/auth/login", json=payload)
        if res.status_code == 200:
            # 응답 body에서 accessToken 추출 (예시)
            data = res.json()
            if data.get("code") == 400:
                print(f"⚠️ 서버 에러 응답: {data.get('message')} (code={data.get('code')})")
            # data["result"]["authTokens"]["accessToken"] 이런 식으로 꺼낼 수도 있음
            self.access_token = data.get("result", {}).get("accessToken")
            print("로그인 성공 : ", data.get("result", {}).get("accessToken"))
        else:
            print(f"HTTP 에러: {res.status_code}, {res.text}")

    @task
    def call_protected_api(self):
        """로그인 후 보호된 API 호출"""
        if not self.access_token:
            # 토큰이 없거나 만료된 경우 재로그인 시도
            self.login()

        headers = {"Authorization": f"Bearer {self.access_token}"}

        # page=0, size=20 으로 요청 (쿼리스트링 방식)
        params = {"page": 0, "size": 20}

        res = self.client.get("/api/store/item/list", headers=headers, params=params)

        if res.status_code == 200:
            print(f"요청 성공: {res.json()}")
        # 인증 만료 시 자동 재로그인
        if res.status_code == 401:
            print(f"토큰 만료로 재로그인: {self.email}")
            self.login()
        else:
            print(f"{self.email} → 응답 코드: {res.status_code}")