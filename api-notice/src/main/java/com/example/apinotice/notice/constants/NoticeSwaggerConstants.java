package com.example.apinotice.notice.constants;

public class NoticeSwaggerConstants {
    // Notice Create API Examples
    public static final String NOTICE_CREATE_REQ_EXAMPLE = """
            {
              "title": "신상품 입고 안내",
              "content": "2025 S/S 시즌 신상품이 입고되었습니다. 다양한 스타일의 의류를 만나보세요!",
              "authorName": "관리자",
              "images": [
                {
                  "imageUrl": "https://storage.example.com/notices/notice1-img1.jpg",
                  "imageOrder": 1
                },
                {
                  "imageUrl": "https://storage.example.com/notices/notice1-img2.jpg",
                  "imageOrder": 2
                }
              ]
            }
            """;

    public static final String NOTICE_CREATE_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "생성되었습니다.",
              "result": "생성되었습니다."
            }
            """;

    // Notice Read API Examples
    public static final String NOTICE_READ_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "공지사항 조회 성공",
              "result": {
                "id": 1,
                "title": "신상품 입고 안내",
                "content": "2025 S/S 시즌 신상품이 입고되었습니다. 다양한 스타일의 의류를 만나보세요!",
                "authorName": "관리자",
                "viewCount": 152,
                "images": [
                  {
                    "id": 1,
                    "imageUrl": "https://storage.example.com/notices/notice1-img1.jpg",
                    "imageOrder": 1
                  },
                  {
                    "id": 2,
                    "imageUrl": "https://storage.example.com/notices/notice1-img2.jpg",
                    "imageOrder": 2
                  }
                ],
                "createdAt": "2025-01-18T10:00:00",
                "updatedAt": "2025-01-18T10:00:00"
              }
            }
            """;

    // Notice List (Paging) API Examples
    public static final String NOTICE_PAGE_LIST_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "공지사항 목록 조회 성공",
              "result": {
                "content": [
                  {
                    "id": 1,
                    "title": "신상품 입고 안내",
                    "authorName": "관리자",
                    "viewCount": 152,
                    "createdAt": "2025-01-18T10:00:00"
                  },
                  {
                    "id": 2,
                    "title": "겨울 시즌 세일 공지",
                    "authorName": "관리자",
                    "viewCount": 245,
                    "createdAt": "2025-01-15T09:00:00"
                  },
                  {
                    "id": 3,
                    "title": "매장 영업시간 변경 안내",
                    "authorName": "관리자",
                    "viewCount": 89,
                    "createdAt": "2025-01-10T14:30:00"
                  }
                ],
                "totalElements": 50,
                "totalPages": 5,
                "currentPage": 0,
                "size": 10
              }
            }
            """;

    // Notice List (All) API Examples
    public static final String NOTICE_LIST_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "전체 공지사항 목록 조회 성공",
              "result": {
                "notices": [
                  {
                    "id": 1,
                    "title": "신상품 입고 안내",
                    "authorName": "관리자",
                    "viewCount": 152,
                    "createdAt": "2025-01-18T10:00:00"
                  },
                  {
                    "id": 2,
                    "title": "겨울 시즌 세일 공지",
                    "authorName": "관리자",
                    "viewCount": 245,
                    "createdAt": "2025-01-15T09:00:00"
                  },
                  {
                    "id": 3,
                    "title": "매장 영업시간 변경 안내",
                    "authorName": "관리자",
                    "viewCount": 89,
                    "createdAt": "2025-01-10T14:30:00"
                  }
                ],
                "totalCount": 50
              }
            }
            """;

    // Notice Update API Examples
    public static final String NOTICE_UPDATE_REQ_EXAMPLE = """
            {
              "title": "신상품 입고 안내 (수정)",
              "content": "2025 S/S 시즌 신상품이 대량 입고되었습니다. 더 많은 스타일의 의류를 만나보세요!",
              "images": [
                {
                  "imageUrl": "https://storage.example.com/notices/notice1-img1-updated.jpg",
                  "imageOrder": 1
                },
                {
                  "imageUrl": "https://storage.example.com/notices/notice1-img2-updated.jpg",
                  "imageOrder": 2
                },
                {
                  "imageUrl": "https://storage.example.com/notices/notice1-img3-new.jpg",
                  "imageOrder": 3
                }
              ]
            }
            """;

    public static final String NOTICE_UPDATE_RES_EXAMPLE = """
            {
              "code": "200",
              "message": "공지사항 수정 성공",
              "result": {
                "id": 1,
                "title": "신상품 입고 안내 (수정)",
                "content": "2025 S/S 시즌 신상품이 대량 입고되었습니다. 더 많은 스타일의 의류를 만나보세요!",
                "authorName": "관리자",
                "viewCount": 152,
                "images": [
                  {
                    "id": 1,
                    "imageUrl": "https://storage.example.com/notices/notice1-img1-updated.jpg",
                    "imageOrder": 1
                  },
                  {
                    "id": 2,
                    "imageUrl": "https://storage.example.com/notices/notice1-img2-updated.jpg",
                    "imageOrder": 2
                  },
                  {
                    "id": 3,
                    "imageUrl": "https://storage.example.com/notices/notice1-img3-new.jpg",
                    "imageOrder": 3
                  }
                ],
                "createdAt": "2025-01-18T10:00:00",
                "updatedAt": "2025-01-18T15:30:00"
              }
            }
            """;
}
