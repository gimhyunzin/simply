# Simply

### 프로젝트 소개
입력받은 URL 와 short-code 를 매핑하여, redirect 하도록 제공한다. 

### 프로젝트 환경
* Java, Javascript
* Spring boot
* Gradle
* thymeleaf
* H2 Database

### 프로젝트 빌드 및 실행

* cd {project root}
* 빌드 : ./gradlew build
* 실행 : ./gradlew bootRun

### 요구사항 해결과정
1. short-code 8자리 영문 대소문자
    - ASCII 코드 값을 이용하여 랜덤한 문자 생성하여 사용
2. 동일한 URL 요청 시 동일한 short-code
    - origin-url 기준으로 중복 불가하도록 조회/생성 
3. short-code 입력 시 redirect
    - @PathVariable 사용하여, 해당 short-code 데이터 조회 후 origin-url 로 리다이렉트 하도록 작업