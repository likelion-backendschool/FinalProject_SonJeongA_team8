# 4Week_SonJeongA.md

## Title: [4Week] 손정아

### 미션 요구사항 분석 & 체크리스트

---
### 필수과제
-[x] JWT 로그인 구현(POST /api/v1/member/login)
-[x] 내 도서 리스트 구현(GET /api/v1/myBooks)
-[x] 내 도서 상세정보 구현(GET /api/v1/myBooks/{id})
-[x] 로그인 한 회원의 정보 구현(GET /api/v1/member/me)
-[x] Srping Doc 으로 API 문서화(크롬 /swagger-ui/index.html )

### 추가과제
-[x] 엑세스 토큰 화이트리스트 구현(Member 엔티티에 accessToken 필드 추가)
-[x] 리액트에서 작동되도록
  - 아래 API 명세에 맞게 구현하시면 강사가 제공해 드린 리액트 코드가 작동합니다.

### 4주차 미션 요약

---

- 4주차에서는 REST API를 구현해야 합니다.
  - 새 스프링부트 프로젝트에서 구현해도 되고, 기존 스프링부트 프로젝트에서 구현해도 됩니다.
- 3주차 프로젝트의 DB를 공유합니다.
- 강사가 구현한 프론트엔드 소스코드가 제공됩니다.
  - 클라이언트는 리액트로 구현되었습니다.
  - 여러분은 REST API 서버만 구현해주세요.

## 주요 **엔드 포인트**

## **REST API**

### **GET /api/v1/myBooks**

- 로그인 필요
- 현재 로그인한 회원이 소유한 책정보들을 리스팅
- 현재 1번 회원이 로그인 되었다고 가정
  - 로그인 방식은 JWT 엑세스 토큰

### **GET /api/v1/myBooks/{myBookId}**

- 로그인 필요

### **POST /api/v1/member/login**

- username과 password 를 입력하면 그에 해당하는 accessToken 을 생성

### **GET /api/v1/member/me**

- 로그인 필요
- 현재 로그인된 회원의 정보를 출력


**[접근 방법]**

#### **🔻JWT 로그인 구현**


<br>

#### **🔻내 도서 리스트 구현**


<br>

#### **🔻내 도서 상세정보 구현**



<br>

#### **🔻로그인 한 회원의 정보 구현**


<br>

#### **🔻Spring Doc 으로 API 문서화**


<br>


**[특이사항]**
