import requests
import time
import statistics
from concurrent.futures import ThreadPoolExecutor, as_completed

BASE_URL = "http://192.0.11.114:31953"

# 🔥 itemDetailId만 사용 (코드 제거 완료)
ITEM_DETAIL_IDS = [
    16,  # 그레이-L
    15,  # 그레이-M
    14,  # 그레이-S
    17,  # 그레이-XL
    13,  # 그레이-XS
    18,  # 그레이-XXL
    34,  # 그린-L
    33,  # 그린-M
    32,  # 그린-S
    35   # 그린-XL
]

ORDERS_PER_ID = 10             # 각 ID당 10개 생성 = 총 100개
ORDER_ID_START = 4545          # 요청한 시작 orderId
UPDATE_STATE = "COMPLETED"

LOGIN_ACCOUNTS = [
    {"email": "manager@company.com", "password": "1234"},
    {"email": "manager2@company.com", "password": "1234"},
    {"email": "manager3@company.com", "password": "1234"},
]

TOKENS = [None, None, None]

# -------------------------------------------------------
# 로그인 + 토큰 캐싱
# -------------------------------------------------------
def login(account_index):
    account = LOGIN_ACCOUNTS[account_index]
    while True:
        try:
            r = requests.post(f"{BASE_URL}/api/auth/login", json=account)
            if r.status_code == 200:
                token = r.json().get("data", {}).get("accessToken")
                if token:
                    TOKENS[account_index] = token
                    print(f"🔐 로그인 성공 → {account['email']}")
                    return token
            print("❌ 로그인 실패 → 재시도 중...")
        except Exception as e:
            print(f"⚠️ 로그인 오류 → 재시도:", e)

        time.sleep(0.3)


def get_token(account_index):
    if TOKENS[account_index] is None:
        return login(account_index)
    return TOKENS[account_index]


# -------------------------------------------------------
# 멀티스레드 발주 생성
# -------------------------------------------------------
def create_order_thread(item_detail_id, manual_order_id, account_index):
    while True:
        token = get_token(account_index)
        headers = {"Authorization": f"Bearer {token}"}

        payload = {
            "description": "재고 부족으로 인한 발주",
            "itemDetailId": item_detail_id,
            "quantity": 1
        }

        try:
            r = requests.post(f"{BASE_URL}/api/order/head/create", json=payload, headers=headers)

            if r.status_code == 200:
                return manual_order_id

            if r.status_code in (401, 403):
                TOKENS[account_index] = None
                print("🔁 발주 생성 토큰 만료 → 재로그인")
                continue

            print(f"❌ 발주 생성 실패({r.status_code}) → 재시도")
        except Exception as e:
            print(f"⚠️ 생성 오류:", e)

        time.sleep(0.1)


# -------------------------------------------------------
# 멀티스레드 업데이트
# -------------------------------------------------------
def update_order_thread(order_id, account_index):
    while True:
        token = get_token(account_index)
        headers = {"Authorization": f"Bearer {token}"}

        payload = {"orderId": order_id, "orderState": UPDATE_STATE}

        try:
            start = time.time()
            r = requests.patch(f"{BASE_URL}/api/order/update", json=payload, headers=headers)
            elapsed = time.time() - start

            if r.status_code == 200:
                return elapsed

            if r.status_code in (401, 403):
                TOKENS[account_index] = None
                print("🔁 업데이트 토큰 만료 → 재로그인")
                continue

            print(f"❌ update 실패({r.status_code}) → 재시도")
        except Exception as e:
            print("⚠️ update 오류:", e)

        time.sleep(0.1)


# -------------------------------------------------------
# 전체 테스트 실행
# -------------------------------------------------------
def run_test():
    global TOKENS

    print("🔐 Step1: 3개 계정 로그인\n")
    for i in range(3):
        login(i)

    print("\n📌 Step2: 멀티스레드 발주 생성 시작\n")

    executor = ThreadPoolExecutor(max_workers=20)
    futures = []
    created_orders = []

    manual_order_id = ORDER_ID_START
    account_idx = 0

    # 발주 생성 (10개 ID * 각 10개 = 100개)
    for item_detail_id in ITEM_DETAIL_IDS:
        for _ in range(ORDERS_PER_ID):
            futures.append(
                executor.submit(create_order_thread, item_detail_id, manual_order_id, account_idx)
            )
            manual_order_id += 1
            account_idx = (account_idx + 1) % 3

    # 생성 완료 처리
    for f in as_completed(futures):
        oid = f.result()
        created_orders.append(oid)
        print(f"  ✔ 발주 생성 성공 → orderId={oid}")

    print("\n🎉 모든 발주 생성 완료!")
    print(f"총 생성 수: {len(created_orders)}\n")

    print("===================================================")
    print(" 🚀 Step3: 멀티스레드 UPDATE 테스트 시작")
    print("===================================================\n")

    update_futures = []
    time_records = []
    account_idx = 0

    for oid in created_orders:
        update_futures.append(
            executor.submit(update_order_thread, oid, account_idx)
        )
        account_idx = (account_idx + 1) % 3

    for f in as_completed(update_futures):
        elapsed = f.result()
        time_records.append(elapsed)
        print(f"✅ update 성공 ({elapsed:.4f}초)")

    # -------------------------------------------------------
    # 통계 출력
    # -------------------------------------------------------
    print("\n===============================")
    print(" 📊 UPDATE 결과 통계")
    print("===============================")
    print(f"전체 요청 수: {len(time_records)}")
    print(f"성공 수:     {len(time_records)}")
    print("-------------------------------")
    print(f"최소 응답시간: {min(time_records):.4f}초")
    print(f"최대 응답시간: {max(time_records):.4f}초")
    print(f"평균 응답시간: {statistics.mean(time_records):.4f}초")
    print(f"중앙값:       {statistics.median(time_records):.4f}초")
    print("===============================\n")


if __name__ == "__main__":
    run_test()
