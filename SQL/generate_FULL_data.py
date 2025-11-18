#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
HypeLink 완전한 초기 데이터 생성 스크립트 (모든 엔티티)
- 고객: 1000명
- 결제: ~10000건 (9800-10200 랜덤, 2024-01-01 ~ 2025-11-30)
- 매장: 10개 (실제 위도/경도)
- 매장별 상품: 5개씩
- AS, Message, Coupon, Notice, Promotion, PurchaseOrder, Shipment 등 모든 엔티티 포함
"""

import random
import datetime
from datetime import timedelta

# ============================================
# 1. 기본 설정
# ============================================

# 매장 정보 (실제 위도/경도)
STORES = [
    {"id": 1, "name": "강남점", "address": "서울특별시 강남구 강남대로 396", "lat": 37.4979, "lon": 127.0276, "region": "SEOUL_GYEONGGI"},
    {"id": 2, "name": "홍대점", "address": "서울특별시 마포구 양화로 160", "lat": 37.5563, "lon": 126.9233, "region": "SEOUL_GYEONGGI"},
    {"id": 3, "name": "잠실점", "address": "서울특별시 송파구 올림픽로 240", "lat": 37.5130, "lon": 127.1025, "region": "SEOUL_GYEONGGI"},
    {"id": 4, "name": "분당점", "address": "경기도 성남시 분당구 황새울로 360", "lat": 37.3595, "lon": 127.1052, "region": "SEOUL_GYEONGGI"},
    {"id": 5, "name": "인천점", "address": "인천광역시 남동구 인주대로 593", "lat": 37.4563, "lon": 126.7052, "region": "SEOUL_GYEONGGI"},
    {"id": 6, "name": "부산 서면점", "address": "부산광역시 부산진구 서면로 68", "lat": 35.1581, "lon": 129.0595, "region": "GYEONGSANG"},
    {"id": 7, "name": "대구 동성로점", "address": "대구광역시 중구 동성로2길 81", "lat": 35.8714, "lon": 128.6014, "region": "GYEONGSANG"},
    {"id": 8, "name": "광주 충장로점", "address": "광주광역시 동구 금남로5가 127", "lat": 35.1595, "lon": 126.8526, "region": "JEOLLA"},
    {"id": 9, "name": "대전 둔산점", "address": "대전광역시 서구 둔산로 100", "lat": 36.3504, "lon": 127.3845, "region": "CHUNGCHEONG"},
    {"id": 10, "name": "제주점", "address": "제주특별자치도 제주시 노형로 200", "lat": 33.4996, "lon": 126.5312, "region": "JEJU"},
]

COLORS = [
    {"id": 1, "name": "Black", "code": "#000000"},
    {"id": 2, "name": "White", "code": "#FFFFFF"},
    {"id": 3, "name": "Navy", "code": "#000080"},
    {"id": 4, "name": "Gray", "code": "#808080"},
    {"id": 5, "name": "Beige", "code": "#F5F5DC"},
    {"id": 6, "name": "Red", "code": "#FF0000"},
]

SIZES = ["XS", "S", "M", "L", "XL", "XXL", "240", "245", "250", "255", "260", "265", "270", "275", "280"]
CATEGORIES = ["아우터", "상의", "하의", "스니커즈", "백팩", "크로스백"]
BRANDS = ["Nike", "Adidas", "Puma", "New Balance", "Converse", "Vans"]
PG_PROVIDERS = ["portone", "toss"]
PAYMENT_STATUSES = ["PAID", "READY", "CANCELLED", "FAILED"]
AS_STATUSES = ["PENDING", "APPROVED", "REJECTED", "COMPLETED"]
MESSAGE_STATES = ["PADDING", "COMPLETE"]
SHIPMENT_STATUSES = ["PREPARING", "DRIVER_ASSIGNED", "IN_PROGRESS", "COMPLETED", "DELAYED", "CANCELLED"]

# 실제 운영 데이터 샘플
REAL_MESSAGES = [
    ("재고 부족 긴급 발주 요청", "안녕하세요. 강남점입니다. Nike Air Max 270 화이트 사이즈 250-270 재고가 모두 소진되었습니다. 주말 대비 긴급 발주 부탁드립니다."),
    ("POS 단말기 추가 요청", "매장 방문 고객이 증가하여 POS 단말기 1대 추가 설치가 필요합니다. 검토 부탁드립니다."),
    ("신상품 입고 일정 문의", "다음주 출시 예정인 Adidas Ultraboost 2024 모델 입고 일정 확인 부탁드립니다."),
    ("배송 지연 관련 문의", "어제 신청한 발주 건(PO-20241115-001) 배송이 지연되고 있습니다. 확인 부탁드립니다."),
    ("매장 영업시간 변경 안내", "12월부터 매장 영업시간을 오전 10시 30분으로 변경하려고 합니다. 승인 부탁드립니다."),
    ("행사 준비 협조 요청", "블랙프라이데이 행사 준비로 추가 인력 지원이 필요합니다. 협조 부탁드립니다."),
    ("재고 실사 결과 보고", "월말 재고 실사 완료했습니다. 전체적으로 재고 현황 양호하며, 보고서는 내일 전달드리겠습니다."),
    ("고객 클레임 처리 완료", "어제 접수된 제품 불량 건 처리 완료했습니다. 고객님께서 만족하셨습니다."),
    ("매장 수리 일정 안내", "다음주 화요일 에어컨 정기 점검이 예정되어 있습니다. 참고 부탁드립니다."),
    ("신규 직원 교육 요청", "신규 직원 POS 시스템 교육이 필요합니다. 교육 일정 조율 부탁드립니다."),
]

REAL_AS_REQUESTS = [
    ("POS 단말기 화면 불량", "POS 3번 단말기 화면이 깜빡이며 정상적으로 작동하지 않습니다. 긴급 수리 요청드립니다."),
    ("카드 결제 오류 발생", "신용카드 결제 시 승인 후 취소되는 현상이 반복됩니다. 시스템 점검 필요합니다."),
    ("매장 조명 고장", "매장 후면 조명 3개가 작동하지 않습니다. 교체 부탁드립니다."),
    ("냉난방 시스템 이상", "에어컨 작동이 원활하지 않습니다. 온도 조절이 되지 않아 점검 요청드립니다."),
    ("출입문 센서 오작동", "자동문 센서가 간헐적으로 작동하지 않습니다. 수리 필요합니다."),
    ("CCTV 화면 끊김", "CCTV 2번 카메라 화면이 자주 끊깁니다. 점검 부탁드립니다."),
    ("인터넷 연결 불안정", "오전부터 인터넷 연결이 불안정합니다. POS 시스템 사용에 지장이 있습니다."),
    ("재고 시스템 오류", "재고 관리 시스템에서 실제 재고와 시스템 재고가 일치하지 않습니다."),
    ("영수증 프린터 고장", "POS 2번 영수증 프린터가 용지 걸림이 자주 발생합니다."),
    ("바코드 스캐너 인식 불량", "바코드 스캐너가 제품을 인식하지 못하는 경우가 많습니다."),
]

REAL_AS_COMMENTS = [
    "확인했습니다. 내일 오전 중 방문 예정입니다.",
    "부품 주문 완료했습니다. 3일 내 수리 완료 예정입니다.",
    "원격으로 점검했으나 현장 방문이 필요합니다. 일정 조율하겠습니다.",
    "긴급 수리 접수했습니다. 오늘 오후 기사 방문 예정입니다.",
    "해당 증상 재현되지 않습니다. 추가 모니터링 부탁드립니다.",
    "수리 완료했습니다. 정상 작동 확인 부탁드립니다.",
    "교체 부품 발송했습니다. 내일 도착 예정입니다.",
    "시스템 업데이트로 해결 가능할 것 같습니다. 야간에 진행하겠습니다.",
    "점검 결과 노후로 인한 고장입니다. 신규 장비로 교체 권장드립니다.",
    "임시 조치 완료했습니다. 정식 수리는 다음주 예정입니다.",
]

REAL_NOTICES = [
    ("신제품 출시 안내 - Nike Air Max 2024", "12월 1일 Nike Air Max 2024 모델이 출시됩니다. 사전 예약 접수 시작하며, 조기 품절이 예상되니 재고 확보 부탁드립니다."),
    ("블랙프라이데이 행사 안내", "11월 마지막 주 블랙프라이데이 행사를 진행합니다. 전 품목 20-50% 할인 예정이며, 매장별 목표 매출 달성 시 인센티브 지급됩니다."),
    ("겨울 시즌 신상품 입고 완료", "겨울 시즌 패딩, 롱코트 등 아우터 신상품 입고 완료했습니다. 매장별 배송 일정 확인 부탁드립니다."),
    ("POS 시스템 업데이트 안내", "12월 5일 자정부터 새벽 5시까지 POS 시스템 정기 업데이트가 진행됩니다. 해당 시간 결제 불가하오니 참고 바랍니다."),
    ("연말 재고 실사 일정 안내", "12월 28일-30일 연말 재고 실사가 진행됩니다. 매장별 협조 부탁드립니다."),
    ("설 명절 배송 일정 안내", "설 연휴 기간 물류센터 휴무로 1월 20일-25일 배송이 중단됩니다. 미리 발주 부탁드립니다."),
    ("고객 만족도 조사 실시", "12월 한 달간 고객 만족도 조사를 실시합니다. 적극적인 참여 부탁드립니다."),
    ("신규 매장 오픈 안내", "12월 15일 대전 둔산점이 새롭게 오픈합니다. 많은 관심 부탁드립니다."),
    ("안전 교육 이수 안내", "전 직원 대상 안전 교육 이수가 필수입니다. 12월 말까지 완료 부탁드립니다."),
    ("하절기 근무 복장 안내", "6월 1일부터 하절기 근무 복장이 적용됩니다. 복장 규정 확인 부탁드립니다."),
]

REAL_PROMOTIONS = [
    ("겨울맞이 아우터 특가전", "겨울 시즌 대비 패딩, 코트 전품목 30% 할인! 12월 한 달간 진행되는 특별 프로모션입니다."),
    ("신발 2+1 프로모션", "운동화 2켤레 구매 시 1켤레 추가 증정! 인기 브랜드 모두 참여합니다."),
    ("회원 전용 더블 적립", "12월 회원 대상 포인트 2배 적립 이벤트! 연말 쇼핑 혜택 놓치지 마세요."),
    ("학생 할인 이벤트", "학생증 제시 시 전품목 15% 추가 할인! 개학 시즌 맞이 특별 혜택입니다."),
    ("오픈 기념 사은품 증정", "신규 매장 오픈 기념 10만원 이상 구매 고객 사은품 증정!"),
    ("친구 추천 이벤트", "친구 추천 시 추천인과 친구 모두 1만원 쿠폰 지급!"),
    ("조기 결제 할인", "오전 시간대(10-12시) 결제 시 5% 추가 할인!"),
    ("VIP 회원 특별전", "VIP 회원 대상 신상품 사전 공개 및 20% 할인 혜택!"),
]

REAL_CHAT_MESSAGES = [
    "재고 확인 부탁드립니다.",
    "네, 확인했습니다.",
    "배송은 언제쯤 도착하나요?",
    "내일 오전 중 도착 예정입니다.",
    "감사합니다!",
    "POS 오류 해결됐나요?",
    "네, 정상 작동합니다.",
    "수고하셨습니다.",
    "점심시간 조율 가능할까요?",
    "오늘은 어려울 것 같습니다.",
    "그럼 내일 다시 연락드릴게요.",
    "네, 좋습니다.",
    "행사 준비는 잘 되어가나요?",
    "거의 다 준비됐습니다.",
    "고생하셨어요!",
]

REAL_COUPONS = [
    ("신규 가입 환영 쿠폰", "FIXED", 10000),
    ("생일 축하 쿠폰", "FIXED", 15000),
    ("VIP 회원 특별 할인", "PERCENTAGE", 20),
    ("블랙프라이데이 특가", "PERCENTAGE", 30),
    ("첫 구매 감사 쿠폰", "FIXED", 5000),
    ("재구매 고객 쿠폰", "PERCENTAGE", 15),
    ("앱 설치 감사 쿠폰", "FIXED", 3000),
    ("친구 추천 쿠폰", "FIXED", 10000),
    ("겨울 시즌 할인", "PERCENTAGE", 25),
    ("봄맞이 특별 쿠폰", "FIXED", 20000),
    ("여름 휴가 쿠폰", "PERCENTAGE", 10),
    ("가을 신상품 할인", "FIXED", 15000),
    ("연말 감사 쿠폰", "PERCENTAGE", 30),
    ("설 명절 특별 쿠폰", "FIXED", 25000),
    ("추석 감사 쿠폰", "FIXED", 20000),
    ("매장 오픈 기념", "PERCENTAGE", 15),
    ("리뷰 작성 감사 쿠폰", "FIXED", 5000),
    ("SNS 공유 쿠폰", "FIXED", 3000),
    ("단골 고객 감사 쿠폰", "PERCENTAGE", 20),
    ("주말 특가 쿠폰", "FIXED", 10000),
]

# 실제 제품명 (카테고리별)
REAL_PRODUCTS = {
    "아우터": [
        "Duck Down Puffer Jacket", "Wool Long Coat", "Fleece Zip-up Jacket",
        "Denim Trucker Jacket", "Bomber Flight Jacket", "Hooded Parka",
        "Quilted Padding Jacket", "Windbreaker Anorak", "Leather Biker Jacket"
    ],
    "상의": [
        "Cotton Crew Neck T-Shirt", "Oversized Hoodie", "Oxford Button Shirt",
        "Striped Rugby Shirt", "Mock Neck Sweatshirt", "Polo Collar Knit",
        "Graphic Print Tee", "Half-Zip Pullover", "Turtle Neck Top"
    ],
    "하의": [
        "Wide Fit Denim Pants", "Slim Straight Chino", "Cargo Jogger Pants",
        "Classic Straight Jeans", "Pleated Wide Slacks", "Cotton Track Pants",
        "Corduroy Relaxed Pants", "Tapered Fit Trousers", "Vintage Denim Jeans"
    ],
    "스니커즈": [
        "Air Max 270", "Ultraboost 22", "Suede Classic", "990v5 Running Shoes",
        "Chuck Taylor All Star", "Old Skool Low", "Air Force 1", "NMD R1",
        "Court Legacy", "574 Core", "RS-X Sneakers", "Dunk Low Retro"
    ],
    "백팩": [
        "Classic Backpack 25L", "Urban Laptop Backpack", "Outdoor Hiking Pack",
        "Mini Daypack", "Sports Training Backpack", "Travel Backpack 40L"
    ],
    "크로스백": [
        "Messenger Shoulder Bag", "Crossbody Mini Bag", "Sling Chest Pack",
        "Hip Waist Bag", "Convertible Cross Bag", "Urban Crossbody Pouch"
    ]
}

def random_phone():
    return f"010-{random.randint(1000, 9999)}-{random.randint(1000, 9999)}"

def random_date(start_year=2024, end_year=2025, end_month=11, end_day=30):
    start = datetime.date(start_year, 1, 1)
    end = datetime.date(end_year, end_month, end_day)
    delta = end - start
    random_days = random.randint(0, delta.days)
    return start + timedelta(days=random_days)

def random_datetime(start_year=2024, end_year=2025, end_month=11, end_day=30):
    start = datetime.date(start_year, 1, 1)
    end = datetime.date(end_year, end_month, end_day)
    delta = end - start
    random_days = random.randint(0, delta.days)
    date = start + timedelta(days=random_days)
    hour = random.randint(10, 22)
    minute = random.randint(0, 59)
    second = random.randint(0, 59)
    return f"{date} {hour:02d}:{minute:02d}:{second:02d}"

def random_birth_date():
    return str(random_date(1970, 2005))

# ============================================
# SQL 생성 함수
# ============================================

def generate_header():
    return """-- ============================================
-- HypeLink 완전한 초기 데이터 (모든 엔티티)
-- Generated by Python Script
-- ============================================
-- 고객: 1000명, 결제: ~10000건 (9800-10200 랜덤), 매장: 10개
-- 날짜 범위: 2024-01-01 ~ 2025-11-30
-- AS, Message, Coupon, Notice, Promotion 등 모든 엔티티 포함
-- ============================================

SET FOREIGN_KEY_CHECKS = 0;

-- 기존 데이터 삭제
TRUNCATE TABLE as_comment;
TRUNCATE TABLE `as`;
TRUNCATE TABLE chat_message;
TRUNCATE TABLE message;
TRUNCATE TABLE parcel_item;
TRUNCATE TABLE parcel;
TRUNCATE TABLE shipment;
TRUNCATE TABLE purchase_order;
TRUNCATE TABLE promotion;
TRUNCATE TABLE notice;
TRUNCATE TABLE customer_coupon;
TRUNCATE TABLE coupon;
TRUNCATE TABLE payments;
TRUNCATE TABLE order_item;
TRUNCATE TABLE customer_receipt;
TRUNCATE TABLE store_item_detail;
TRUNCATE TABLE store_item;
TRUNCATE TABLE store_category;
TRUNCATE TABLE item_detail;
TRUNCATE TABLE item;
TRUNCATE TABLE customer;
TRUNCATE TABLE pos;
TRUNCATE TABLE driver;
TRUNCATE TABLE store;
TRUNCATE TABLE member;
TRUNCATE TABLE category;
TRUNCATE TABLE size;
TRUNCATE TABLE color;

"""

def generate_basic_data():
    sql = "-- ============================================\n"
    sql += "-- 기본 마스터 데이터 (Color, Size, Category)\n"
    sql += "-- ============================================\n"

    # Colors
    sql += "INSERT INTO color (id, color_name, color_code, created_at, updated_at) VALUES\n"
    sql += ",\n".join([f"({c['id']}, '{c['name']}', '{c['code']}', NOW(), NOW())" for c in COLORS]) + ";\n\n"

    # Sizes
    sql += "INSERT INTO size (id, size, created_at, updated_at) VALUES\n"
    sql += ",\n".join([f"({i}, '{s}', NOW(), NOW())" for i, s in enumerate(SIZES, 1)]) + ";\n\n"

    # Categories
    sql += "INSERT INTO category (id, category, created_at, updated_at) VALUES\n"
    sql += ",\n".join([f"({i}, '{c}', NOW(), NOW())" for i, c in enumerate(CATEGORIES, 1)]) + ";\n\n"

    return sql

def generate_members_stores():
    sql = "-- ============================================\n"
    sql += "-- 회원 + 매장 + POS + Driver\n"
    sql += "-- ============================================\n"

    # 본사 관리자 (비밀번호: 1234)
    bcrypt_1234 = "$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy"
    sql += "INSERT INTO member (id, email, password, name, phone, address, role, region, refresh_token, created_at, updated_at) VALUES\n"
    sql += f"(1, 'admin@hypelink.com', '{bcrypt_1234}', '김태현', '010-1234-5678', '서울특별시 강남구 테헤란로 123', 'ADMIN', 'SEOUL_GYEONGGI', NULL, NOW(), NOW()),\n"
    sql += f"(2, 'manager@hypelink.com', '{bcrypt_1234}', '이지은', '010-2345-6789', '서울특별시 강남구 역삼동 456', 'MANAGER', 'SEOUL_GYEONGGI', NULL, NOW(), NOW());\n\n"

    # 매장별 지점장 + 운전기사 (비밀번호: 1234)
    member_values = []
    member_id = 3
    for store in STORES:
        store_eng = store['name'].replace(' ', '_').replace('점', '').lower()
        member_values.append(f"({member_id}, '{store_eng}@hypelink.com', '{bcrypt_1234}', '{store['name']}_지점장', '{random_phone()}', '{store['address']}', 'BRANCH_MANAGER', '{store['region']}', NULL, NOW(), NOW())")
        member_id += 1
        member_values.append(f"({member_id}, 'driver.{store_eng}@hypelink.com', '{bcrypt_1234}', '{store['name']}_기사', '{random_phone()}', '{store['address']}', 'DRIVER', '{store['region']}', NULL, NOW(), NOW())")
        member_id += 1

    sql += "INSERT INTO member (id, email, password, name, phone, address, role, region, refresh_token, created_at, updated_at) VALUES\n"
    sql += ",\n".join(member_values) + ";\n\n"

    # 매장
    sql += "INSERT INTO store (id, lat, lon, pos_count, store_number, store_state, member_id) VALUES\n"
    store_values = []
    member_id = 3
    for store in STORES:
        pos_count = random.randint(2, 5)
        store_number = f"STR-2023-{store['id']:03d}"
        store_values.append(f"({store['id']}, {store['lat']}, {store['lon']}, {pos_count}, '{store_number}', 0, {member_id})")
        member_id += 2
    sql += ",\n".join(store_values) + ";\n\n"

    # Driver 테이블 (macAddress, carNumber)
    sql += "INSERT INTO driver (id, member_id, mac_address, car_number) VALUES\n"
    driver_values = []
    driver_id = 1
    member_id = 4  # 첫 번째 운전기사
    for i in range(10):
        mac_address = f"{random.randint(10, 99):02X}:{random.randint(10, 99):02X}:{random.randint(10, 99):02X}:{random.randint(10, 99):02X}:{random.randint(10, 99):02X}:{random.randint(10, 99):02X}"
        car_number = f"{random.randint(10, 99):02d}가{random.randint(1000, 9999)}"
        driver_values.append(f"({driver_id}, {member_id}, '{mac_address}', '{car_number}')")
        driver_id += 1
        member_id += 2
    sql += ",\n".join(driver_values) + ";\n\n"

    # POS 단말기 (posCode 패턴: [A-Z]{3}[0-9]{3}_[0-9]{2})
    sql += "INSERT INTO pos (id, pos_code, store_id, health_check, member_id) VALUES\n"
    pos_values = []
    pos_id = 1
    for store in STORES:
        pos_count = random.randint(2, 5)
        for i in range(pos_count):
            pos_code = f"STR{store['id']:03d}_{i+1:02d}"  # 예: STR001_01
            health_check = random.choice([True, False])
            member_id = "NULL"  # POS에 할당된 member는 NULL
            pos_values.append(f"({pos_id}, '{pos_code}', {store['id']}, {health_check}, {member_id})")
            pos_id += 1
    sql += ",\n".join(pos_values) + ";\n\n"

    return sql

def generate_items_and_details():
    sql = "-- ============================================\n"
    sql += "-- 본사 상품 + 상품 상세\n"
    sql += "-- ============================================\n"

    item_count = 50
    detail_id = 1

    # 본사 상품
    item_values = []
    for i in range(1, item_count + 1):
        category_id = random.randint(1, len(CATEGORIES))
        category_name = CATEGORIES[category_id - 1]
        brand = random.choice(BRANDS)
        item_code = f"ITEM-{i:03d}"

        # 카테고리별 실제 제품명 선택
        product_name = random.choice(REAL_PRODUCTS[category_name])
        en_name = f"{brand} {product_name}"
        ko_name = f"{brand} {product_name}"
        content = f"{brand}의 {product_name}. 고품질 소재와 세련된 디자인."

        unit_price = random.randint(50, 500) * 1000
        amount = int(unit_price * random.uniform(1.5, 2.5))
        item_values.append(f"({i}, {category_id}, '{item_code}', {unit_price}, {amount}, '{en_name}', '{ko_name}', '{content}', '{brand}', NOW(), NOW())")

    sql += "INSERT INTO item (id, category_id, item_code, unit_price, amount, en_name, ko_name, content, company, created_at, updated_at) VALUES\n"
    sql += ",\n".join(item_values) + ";\n\n"

    # 본사 상품 상세 (색상 2개 x 사이즈 3개 = 6개씩)
    sql += "INSERT INTO item_detail (id, color_id, size_id, stock, item_detail_code, item_id, created_at, updated_at) VALUES\n"
    detail_values = []
    for item_id in range(1, item_count + 1):
        item_code = f"ITEM-{item_id:03d}"
        for color in random.sample(COLORS, 2):
            for size_idx in random.sample(range(1, len(SIZES) + 1), 3):
                stock = random.randint(10, 100)
                detail_code = f"{item_code}-{color['name'][:3].upper()}-{SIZES[size_idx-1]}"
                detail_values.append(f"({detail_id}, {color['id']}, {size_idx}, {stock}, '{detail_code}', {item_id}, NOW(), NOW())")
                detail_id += 1
    sql += ",\n".join(detail_values) + ";\n\n"

    return sql

def generate_store_items():
    sql = "-- ============================================\n"
    sql += "-- 매장별 상품 + 상품 상세 (매장당 5개)\n"
    sql += "-- ============================================\n"

    item_id = 1
    detail_id = 1
    category_id = 1

    for store in STORES:
        # 매장 카테고리
        sql += f"\n-- {store['name']} 카테고리\n"
        sql += "INSERT INTO store_category (id, store_id, category, created_at, updated_at) VALUES\n"
        cat_values = []
        for i, cat in enumerate(CATEGORIES[:3], category_id):
            cat_values.append(f"({i}, {store['id']}, '{cat}', NOW(), NOW())")
        sql += ",\n".join(cat_values) + ";\n\n"

        # 매장 상품 5개
        for _ in range(5):
            item_code = f"ITEM-{random.randint(1, 50):03d}"
            cat_id = category_id + random.randint(0, 2)
            category_name = CATEGORIES[(cat_id - 1) % len(CATEGORIES)]
            unit_price = random.randint(50, 500) * 1000
            amount = int(unit_price * random.uniform(1.5, 2.5))
            brand = random.choice(BRANDS)

            # 카테고리별 실제 제품명 선택
            product_name = random.choice(REAL_PRODUCTS[category_name])
            en_name = f"{brand} {product_name}"
            ko_name = f"{brand} {product_name}"
            content = f"{brand}의 {product_name}. 고품질 소재와 세련된 디자인."

            sql += f"INSERT INTO store_item (id, category_id, store_id, item_code, unit_price, amount, en_name, ko_name, content, company, created_at, updated_at) VALUES\n"
            sql += f"({item_id}, {cat_id}, {store['id']}, '{item_code}', {unit_price}, {amount}, '{en_name}', '{ko_name}', '{content}', '{brand}', NOW(), NOW());\n\n"

            # 상품 상세
            sql += "INSERT INTO store_item_detail (id, color, color_code, size, stock, item_detail_code, item_id, created_at, updated_at) VALUES\n"
            detail_values = []
            for color in random.sample(COLORS, 2):
                for size in random.sample(SIZES, 3):
                    stock = random.randint(5, 30)
                    detail_code = f"{item_code}-{color['name'][:3].upper()}-{size}"
                    detail_values.append(f"({detail_id}, '{color['name']}', '{color['code']}', '{size}', {stock}, '{detail_code}', {item_id}, NOW(), NOW())")
                    detail_id += 1
            sql += ",\n".join(detail_values) + ";\n\n"
            item_id += 1

        category_id += 3

    return sql

def generate_customers():
    sql = "-- ============================================\n"
    sql += "-- 고객 데이터 (1000명)\n"
    sql += "-- ============================================\n"
    sql += "INSERT INTO customer (id, name, phone, birth_date, created_at, updated_at) VALUES\n"
    values = [f"({i}, '고객{i}', '{random_phone()}', '{random_birth_date()}', NOW(), NOW())" for i in range(1, 1001)]
    sql += ",\n".join(values) + ";\n\n"
    return sql

def generate_coupons():
    sql = "-- ============================================\n"
    sql += "-- 쿠폰 + 고객 쿠폰\n"
    sql += "-- ============================================\n"

    # 쿠폰 20개
    sql += "INSERT INTO coupon (id, name, coupon_type, value, start_date, end_date) VALUES\n"
    coupon_values = []
    for i in range(1, 21):
        coupon = REAL_COUPONS[i-1]
        name = coupon[0]
        coupon_type = coupon[1]
        value = coupon[2]
        start_date = random_date()
        end_date = start_date + timedelta(days=random.randint(30, 90))
        coupon_values.append(f"({i}, '{name}', '{coupon_type}', {value}, '{start_date}', '{end_date}')")
    sql += ",\n".join(coupon_values) + ";\n\n"

    # 고객 쿠폰 (랜덤 배정 200개)
    sql += "INSERT INTO customer_coupon (id, customer_id, coupon_id, issued_date, expiration_date, is_used, used_date, created_at, updated_at) VALUES\n"
    customer_coupon_values = []
    for i in range(1, 201):
        customer_id = random.randint(1, 1000)
        coupon_id = random.randint(1, 20)
        is_used = random.choice([True, False])
        issued_date = random_date()
        expiration_date = issued_date + timedelta(days=random.randint(30, 90))
        used_date = f"'{random_date()}'" if is_used else "NULL"
        customer_coupon_values.append(f"({i}, {customer_id}, {coupon_id}, '{issued_date}', '{expiration_date}', {is_used}, {used_date}, NOW(), NOW())")
    sql += ",\n".join(customer_coupon_values) + ";\n\n"

    return sql

def generate_notices():
    sql = "-- ============================================\n"
    sql += "-- 공지사항 (30개)\n"
    sql += "-- ============================================\n"
    sql += "INSERT INTO notice (id, title, contents, author, is_open, created_at, updated_at) VALUES\n"
    values = []
    for i in range(1, 31):
        notice = random.choice(REAL_NOTICES)
        title = notice[0]
        contents = notice[1]
        author = random.choice(["김태현", "이지은", "본사 운영팀", "본부장"])
        is_open = random.choice([True, False])
        values.append(f"({i}, '{title}', '{contents}', '{author}', {is_open}, NOW(), NOW())")
    sql += ",\n".join(values) + ";\n\n"
    return sql

def generate_promotions():
    sql = "-- ============================================\n"
    sql += "-- 프로모션 (20개)\n"
    sql += "-- ============================================\n"
    sql += "INSERT INTO promotion (id, title, contents, coupon_id, start_date, end_date, status, created_at, updated_at) VALUES\n"
    values = []
    for i in range(1, 21):
        promo = random.choice(REAL_PROMOTIONS)
        title = promo[0]
        contents = promo[1]
        coupon_id = random.randint(1, 20)  # 쿠폰 ID 1-20
        start_date = random_date()
        end_date = start_date + timedelta(days=random.randint(7, 30))
        status = random.choice(["UPCOMING", "ONGOING", "ENDED"])
        values.append(f"({i}, '{title}', '{contents}', {coupon_id}, '{start_date}', '{end_date}', '{status}', NOW(), NOW())")
    sql += ",\n".join(values) + ";\n\n"
    return sql

def generate_as_requests():
    sql = "-- ============================================\n"
    sql += "-- A/S 요청 + 댓글\n"
    sql += "-- ============================================\n"

    # AS 요청 50개
    sql += "INSERT INTO `as` (id, title, description, status, store_id, created_at, updated_at) VALUES\n"
    as_values = []
    for i in range(1, 51):
        as_req = random.choice(REAL_AS_REQUESTS)
        title = as_req[0]
        description = as_req[1]
        status = random.choice(AS_STATUSES)
        store_id = random.randint(1, 10)
        as_values.append(f"({i}, '{title}', '{description}', '{status}', {store_id}, NOW(), NOW())")
    sql += ",\n".join(as_values) + ";\n\n"

    # AS 댓글 100개
    sql += "INSERT INTO as_comment (id, as_id, member_id, description, created_at, updated_at) VALUES\n"
    comment_values = []
    for i in range(1, 101):
        description = random.choice(REAL_AS_COMMENTS)
        as_id = random.randint(1, 50)
        member_id = random.choice([1, 2])  # 본사 관리자가 댓글 작성
        comment_values.append(f"({i}, {as_id}, {member_id}, '{description}', NOW(), NOW())")
    sql += ",\n".join(comment_values) + ";\n\n"

    return sql

def generate_messages():
    sql = "-- ============================================\n"
    sql += "-- 메시지 (100개)\n"
    sql += "-- ============================================\n"
    sql += "INSERT INTO message (id, title, contents, message_state, sender_id, receiver_id, created_at, updated_at) VALUES\n"
    values = []
    for i in range(1, 101):
        msg = random.choice(REAL_MESSAGES)
        title = msg[0]
        contents = msg[1]
        message_state = random.choice(MESSAGE_STATES)
        sender_id = random.randint(1, 10)  # 매장 ID
        receiver_id = random.randint(1, 10)
        while receiver_id == sender_id:
            receiver_id = random.randint(1, 10)
        values.append(f"({i}, '{title}', '{contents}', '{message_state}', {sender_id}, {receiver_id}, NOW(), NOW())")
    sql += ",\n".join(values) + ";\n\n"
    return sql

def generate_chat_messages():
    sql = "-- ============================================\n"
    sql += "-- 채팅 메시지 (200개)\n"
    sql += "-- ============================================\n"
    sql += "INSERT INTO chat_message (id, sender_id, receiver_id, content, is_read, created_at, updated_at) VALUES\n"
    values = []
    for i in range(1, 201):
        sender_id = random.randint(1, 22)
        receiver_id = random.randint(1, 22)
        while receiver_id == sender_id:
            receiver_id = random.randint(1, 22)
        content = random.choice(REAL_CHAT_MESSAGES)
        is_read = random.choice([True, False])
        values.append(f"({i}, {sender_id}, {receiver_id}, '{content}', {is_read}, NOW(), NOW())")
    sql += ",\n".join(values) + ";\n\n"
    return sql

def generate_purchase_orders():
    sql = "-- ============================================\n"
    sql += "-- 발주 (100개)\n"
    sql += "-- ============================================\n"
    sql += "INSERT INTO purchase_order (id, quantity, total_price, purchase_order_state, purchase_detail_status, item_id, requester_id, supplier_id, created_at, updated_at) VALUES\n"
    values = []
    for i in range(1, 101):
        item_id = random.randint(1, 300)  # item_detail_id (본사 상품 상세)
        quantity = random.randint(10, 100)
        unit_price = random.randint(50, 500) * 1000
        total_price = unit_price * quantity
        purchase_order_state = random.choice(["REQUESTED", "COMPLETED", "CANCELED"])
        purchase_detail_status = random.choice([
            "STOCK_SHORTAGE", "NEW_PRODUCT", "SEASON_CHANGE", "STORE_OPEN",
            "STORE_EVENT", "DAMAGED_REPLACEMENT", "URGENT_REQUEST", "HQ_PLANNED_ORDER"
        ])
        requester_id = random.randint(3, 22)  # 지점장 또는 본사 멤버
        supplier_id = random.choice([1, 2])  # 본사 관리자
        values.append(f"({i}, {quantity}, {total_price}, '{purchase_order_state}', '{purchase_detail_status}', {item_id}, {requester_id}, {supplier_id}, NOW(), NOW())")
    sql += ",\n".join(values) + ";\n\n"
    return sql

def generate_shipments():
    sql = "-- ============================================\n"
    sql += "-- 택배 + 배송 + 택배 아이템 (미배정/할당 구분)\n"
    sql += "-- ============================================\n"

    # 택배 100개 (충분한 데이터)
    parcel_count = 100
    sql += "INSERT INTO parcel (id, tracking_number, requester_id, supplier_id, created_at, updated_at) VALUES\n"
    parcel_values = []
    for i in range(1, parcel_count + 1):
        tracking_number = f"TRK-{random.randint(100000, 999999)}"
        requester_id = random.randint(3, 22)  # 지점장 또는 본사 멤버
        supplier_id = random.choice([1, 2])  # 본사 관리자
        parcel_values.append(f"({i}, '{tracking_number}', {requester_id}, {supplier_id}, NOW(), NOW())")
    sql += ",\n".join(parcel_values) + ";\n\n"

    # 배송 100개 (parcel_id 참조)
    # 30개는 미배정 (driver_id = NULL, status = PREPARING)
    # 40개는 할당됨 (status = DRIVER_ASSIGNED or IN_PROGRESS)
    # 30개는 완료/지연/취소 (나머지 상태)
    sql += "INSERT INTO shipment (id, parcel_id, driver_id, shipment_status, created_at, updated_at) VALUES\n"
    shipment_values = []

    for i in range(1, parcel_count + 1):
        parcel_id = i  # 1:1 관계

        if i <= 30:
            # 첫 30개: 미배정 택배 (driver = NULL, status = PREPARING)
            driver_id = "NULL"
            shipment_status = "PREPARING"
        elif i <= 70:
            # 다음 40개: 할당된 택배 (status = DRIVER_ASSIGNED or IN_PROGRESS)
            driver_id = random.randint(1, 10)
            shipment_status = random.choice(["DRIVER_ASSIGNED", "IN_PROGRESS"])
        else:
            # 나머지 30개: 완료/지연/취소 상태
            driver_id = random.randint(1, 10)
            shipment_status = random.choice(["COMPLETED", "DELAYED", "CANCELLED"])

        shipment_values.append(f"({i}, {parcel_id}, {driver_id}, '{shipment_status}', NOW(), NOW())")

    sql += ",\n".join(shipment_values) + ";\n\n"

    # 택배 아이템 100개 (purchase_order_id는 OneToOne 관계이므로 중복 불가)
    sql += "INSERT INTO parcel_item (id, purchase_order_id, parcel_id, created_at, updated_at) VALUES\n"
    parcel_item_values = []
    used_purchase_orders = set()
    for i in range(1, parcel_count + 1):
        parcel_id = i
        # 중복되지 않는 purchase_order_id 선택
        purchase_order_id = random.randint(1, 100)
        while purchase_order_id in used_purchase_orders:
            purchase_order_id = random.randint(1, 100)
        used_purchase_orders.add(purchase_order_id)
        parcel_item_values.append(f"({i}, {purchase_order_id}, {parcel_id}, NOW(), NOW())")
    sql += ",\n".join(parcel_item_values) + ";\n\n"

    return sql

def generate_receipts_and_payments():
    sql = "-- ============================================\n"
    sql += "-- 결제 내역 + 결제 + 주문 아이템 (~10000건)\n"
    sql += "-- ============================================\n"

    receipt_count = random.randint(9800, 10200)  # 더 자연스러운 데이터
    receipt_values = []
    payment_values = []
    order_item_id = 1

    for receipt_id in range(1, receipt_count + 1):
        pg_provider = random.choice(PG_PROVIDERS)
        pg_tid = f"{pg_provider}_{datetime.datetime.now().strftime('%Y%m%d')}{receipt_id:05d}"
        merchant_uid = f"ORDER-{datetime.datetime.now().strftime('%Y%m%d')}{receipt_id:05d}"

        item_count = random.randint(1, 3)
        total_amount = 0
        for _ in range(item_count):
            total_amount += random.randint(50, 500) * 1000

        discount_amount = int(total_amount * random.uniform(0.1, 0.15)) if random.random() < 0.2 else 0
        coupon_discount = random.choice([10000, 15000, 20000, 25000, 30000, 50000]) if random.random() < 0.1 else 0
        final_amount = total_amount - discount_amount - coupon_discount

        store_id = random.randint(1, 10)
        customer_id = random.randint(1, 1000)
        status = random.choices(PAYMENT_STATUSES, weights=[70, 10, 15, 5])[0]
        paid_at = f"'{random_datetime()}'" if status == "PAID" else "NULL"

        receipt_values.append(f"({receipt_id}, '{pg_provider}', '{pg_tid}', '{merchant_uid}', {total_amount}, {discount_amount}, {coupon_discount}, {final_amount}, {store_id}, {customer_id}, NULL, NULL, '{status}', {paid_at}, NOW(), NOW())")

        # Payments 테이블 (receipt_id, payment_id, status, amount, paid_at만 필수)
        if status == "PAID":
            paid_at_clean = paid_at.strip("'")
            payment_id = f"PAY-{datetime.datetime.now().strftime('%Y%m%d')}{receipt_id:05d}"
            payment_values.append(f"({receipt_id}, {receipt_id}, '{payment_id}', NULL, NULL, NULL, {final_amount}, '{status}', '{paid_at_clean}', NULL, NOW(), NOW())")

    sql += "INSERT INTO customer_receipt (id, pg_provider, pg_tid, merchant_uid, total_amount, discount_amount, coupon_discount, final_amount, store_id, customer_id, member_name, member_phone, status, paid_at, created_at, updated_at) VALUES\n"
    sql += ",\n".join(receipt_values) + ";\n\n"

    sql += "INSERT INTO payments (id, receipt_id, payment_id, transaction_id, store_id, channel_key, amount, status, paid_at, failure_reason, created_at, updated_at) VALUES\n"
    sql += ",\n".join(payment_values) + ";\n\n"

    # 주문 아이템
    sql += "INSERT INTO order_item (id, customer_receipt_id, store_item_detail_id, quantity, unit_price, total_price) VALUES\n"
    order_values = []
    for receipt_id in range(1, receipt_count + 1):
        item_count = random.randint(1, 3)
        for _ in range(item_count):
            detail_id = random.randint(1, 300)
            quantity = random.randint(1, 3)
            unit_price = random.randint(50, 500) * 1000
            total_price = unit_price * quantity
            order_values.append(f"({order_item_id}, {receipt_id}, {detail_id}, {quantity}, {unit_price}, {total_price})")
            order_item_id += 1
    sql += ",\n".join(order_values) + ";\n\n"

    return sql

def generate_footer():
    return """
SET FOREIGN_KEY_CHECKS = 1;

-- ============================================
-- 데이터 생성 완료! (모든 엔티티)
-- ============================================
"""

def main():
    print("HypeLink Complete Data Generation (ALL ENTITIES) Started...")
    print("NOTE: Member, Store, POS, Driver data are managed by BaseMember.java")

    with open("complete_init_data_FULL.sql", "w", encoding="utf-8") as f:
        f.write(generate_header())

        print("[1/13] Generating basic data (Color, Size, Category)...")
        f.write(generate_basic_data())

        # SKIPPED: Member, Store, POS, Driver (managed by BaseMember.java)
        print("[2/13] SKIPPED: Members, stores, POS, drivers (managed by BaseMember.java)")

        print("[3/13] Generating items and item details...")
        f.write(generate_items_and_details())

        print("[4/13] Generating store items...")
        f.write(generate_store_items())

        print("[5/13] Generating customers (1000)...")
        f.write(generate_customers())

        print("[6/13] Generating coupons and customer coupons...")
        f.write(generate_coupons())

        print("[7/13] Generating notices...")
        f.write(generate_notices())

        print("[8/13] Generating promotions...")
        f.write(generate_promotions())

        print("[9/13] Generating AS requests and comments...")
        f.write(generate_as_requests())

        print("[10/13] Generating messages...")
        f.write(generate_messages())

        print("[11/13] Generating chat messages...")
        f.write(generate_chat_messages())

        print("[12/14] Generating purchase orders...")
        f.write(generate_purchase_orders())

        print("[13/14] Generating shipments (parcels, deliveries)...")
        f.write(generate_shipments())

        print("[14/14] Generating receipts, payments, order items (~10000)...")
        f.write(generate_receipts_and_payments())

        print("[15/14] Finalizing...")
        f.write(generate_footer())

    print("\n=== COMPLETE! ===")
    print("File: complete_init_data_FULL.sql")
    print("\nAll Entities Included:")
    print("   - Members, Stores, POS, Drivers: Managed by BaseMember.java (auto-generated on server start)")
    print("   - Customers: 1000")
    print("   - Items: 50, ItemDetails: 300")
    print("   - StoreItems: 50, StoreItemDetails: 300")
    print("   - Receipts: ~10000 (9800-10200), Payments: ~7000, OrderItems: ~20000")
    print("   - Shipments: 100 (Unassigned: 30, Assigned: 40, Completed/etc: 30)")
    print("   - Coupons: 20, CustomerCoupons: 200")
    print("   - Notices: 30, Promotions: 20")
    print("   - AS Requests: 50, AS Comments: 100")
    print("   - Messages: 100, ChatMessages: 200")
    print("   - PurchaseOrders: 100")
    print("   - Date Range: 2024-01-01 ~ 2025-11-30")
    print("\nHow to use:")
    print("   1. Start the Spring Boot server first (BaseMember.java will create Member/Store/POS/Driver)")
    print("   2. Then run: mariadb -u root -p hypelink < complete_init_data_FULL.sql")

if __name__ == "__main__":
    main()
