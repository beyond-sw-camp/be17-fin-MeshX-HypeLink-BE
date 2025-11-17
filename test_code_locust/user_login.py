import json
from locust import HttpUser, task, between

class LoginUser(HttpUser):
    wait_time = between(1, 3)

    def on_start(self):
        self.access_token = None
        self.login()

    @task
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
            print("✅ 로그인 성공 : ", data.get("result", {}).get("accessToken"))
        else:
            print(f"❌ HTTP 에러: {res.status_code}, {res.text}")
