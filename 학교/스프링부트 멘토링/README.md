## 2022 개발자 멘토링 - 스프링 부트를 활용한 API 개발
- Spring Boot 2.6.3
- Java 11
- Spring Data JPA
- H2 Database

### 목표
- 스프링 부트로 회원을 관리하는 API를 개발한다.
- H2 데이터베이스를 통해 회원을 관리하고, 포스트맨을 통해 API를 테스트한다.
- End-Point와 Repository의 테스트 코드를 작성하고 테스트한다.

### API
- 회원 생성
  - [POST] /api/members
- 특정 회원 조회
  - [GET] /api/members/{memberId}
- 전체 회원 조회
  - [GET] /api/members
- 회원 수정
  - [PATCH] /api/members/{memberId}
- 특정 회원 삭제
  - [DELETE] /api/members/{memberId}
- 전체 회원 삭제
  - [DELETE] /api/members