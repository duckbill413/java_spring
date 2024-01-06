# Spring package 구조
## 도메인형 디렉터리 구조
스프링의 웹 계층 보다 도메인에 주목하여 패키지를 구조화.  
각각의 도메인들은 서로를 의존하는 코드가 없도록 설계하는 것이 핵심.  
`JPA`와 `MSA`를 사용, 고려하는 경우 특히 유용할 수 있는 패키지 구조.  
물론, 비지니스 로직에 있어서 유연함은 필요하고 명확한 정답은 없다.  

## Global
특정 domain에 종속되지 않고, 프로젝트 전방위적으로 사용 가능한 클래스의 집합
- auth: `인증`, `인가`와 관련된 패키지
- common: `공통 클래스` 혹은 공통 `value`클래스로 구성
- config: 각종 `configuration` class
- error: `exception`, `error`와 관련된 클래스
- infra: `외부 모듈`, `api`등을 사용하는 클래스
- util: 공통 `util`성 클래스의 집합(ex: JWT, S3)

## Domain
domain entity 별로 패키지를 구성
- api: `controller` 클래스
- service: `service` 클래스 (비지니스 로직 담당)
- dao: `dao`, `repository` 클래스
- domain: `entity` 클래스
- dto: `dto` 클래스
  - `request` dto
  - `response` dto
- exception: `exception` 클래스

## Application
domain간 강한 결합을 방지하기 위해서 사용되는 패키지 구조
- api: `공통 controller` 클래스
- usecase: `여러 domain의 service를 사용하는 usecase` 클래스
  - ex) 어떤 메뉴를 가진 음식점 정보를 호출하는 경우
    - `GetRestaurantInMenuUsecase.java`

---
# JDK
우리가 활용할 만한 변경점 확인

## JDK 17
1. switch 문의 변경
2. record class
3. var type (java 10)
4. multiline String
5. String method
   - `isBlank`: 문자열이 비어 있거나 공백이면 `true`반환
   - `strip`: `strip()` 문자열 앞뒤 공백 제거
     - trim(): ASCII 범위 내의 공백 문자(공백, 탭, 개행 등)만을 제거합니다.
     - strip(): trim()과 달리 유니코드의 공백 문자(예: 중간 공백, 비틀린 공백)도 제거합니다. Java 11부터 도입되었습니다.
   - `stripLeading`: 문자열 앞의 공백 제거
   - `stripTrailing`: 문자열 뒤의 공백 제거
   - `repeat`: 문자열을 주어진 수 만큼 반복

### 새로운 JDK LTS 버전 JDK21
- 안타깝지만 lombok 지원 논란이 존재
- 덩달아서 JUnit도...
- https://github.com/projectlombok/lombok/issues/3393