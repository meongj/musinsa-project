# 무신사 카테고리별 최저가격 브랜드 조회 서비스

## 프로젝트 개요

이 프로젝트는 Spring Boot를 기반으로 만들어진 8개의 상품 카테고리에서 최저가격 브랜드를 조회하고 관리하는 서비스입니다.


## 기술 스택
- Java 21
- Spring boot(3.3.5), JPA, H2
- Junit, Mockito
- bootstrap, thymeleaf
- gradle


## 주요 기능

1. 카테고리별 최저가격 브랜드와 가격 조회
2. 단일 브랜드 전체 카테고리 최저가격 조회
3. 특정 카테고리의 최저가/최고가 브랜드 조회
4. 브랜드와 상품 관리 (추가/수정/삭제)

## Build & Run

### Prerequisites

- Java 21

### Project Clone
```bash
git clone https://github.com/meongj/musinsa-project.git
cd project
```

### Build & Run

```bash
# 빌드
./gradlew clean build

# 전체 테스트 실행
./gradlew test

# 서버 실행
./gradlew bootRun
```

## API Documentation & Demo

서버 실행 후 다음 URL을 통해 접근 가능합니다:

- 클라이언트 페이지: `http://localhost:8080/client`
- 관리자 페이지: `http://localhost:8080/admin`
- Swagger UI (API 문서): `http://localhost:8080/swagger-ui.html`

## 프로젝트 구조

1. **도메인 중심 설계**

- 비즈니스 규칙을 도메인 객체에 캡슐화
- 불변성과 유효성 검사

2. **테스트 주도 개발**

- 테스트를 통한 도메인 규칙 정의
- 실패하는 테스트 작성 후 구현
- java-test-fixtures 플러그인 활용해 중복 코드 줄이기
- 프로필을 통해 테스트 환경용 DB 분리

2. **계층형 아키텍처**

- Controller: API 엔드포인트 및 웹 페이지 제공
- Service: 비즈니스 로직 처리
- Repository: 데이터 접근 계층
- Domain: 핵심 비즈니스 규칙

3. **예외 처리**

- 도메인별 예외 분류
- 일관된 예외 응답 구조
- 전역 예외 처리

4. **웹 인터페이스**

- 클라이언트 페이지: 가격 조회 및 API 테스트
- 관리자 페이지: 브랜드와 상품 관리
- API Documentation by Swagger

