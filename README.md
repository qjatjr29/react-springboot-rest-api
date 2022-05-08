# [프로젝트] React - Spring Boot 상품 관리 API 구현

## 프로젝트 소개 😎

<img width="350" height="400 " alt="title" src="https://user-images.githubusercontent.com/74031333/167298031-db521e7f-f10a-41ec-9b05-a3d02108748a.png">

**사용자**는 가게에서 음식을 선택하고 주문할 수 있습니다.
카테고리별로 가게를 검색하고 조회할 수 있습니다.
각 가게는 여러가지 음식들이 있습니다.  
**관리자**는 가게 관리를 통해 가게를 추가 / 삭제 할 수 있고 가게에 있는 음식도 추가 / 삭제 할 수 있습니다.  
또, 사용자를 관리 할 수 있습니다.

## 프로젝트 구조

---

```
    front    : React.js
    back     : Spring boot
    database : MySQL
```

### 아키텍처

<img width="480" alt="아키" src="https://user-images.githubusercontent.com/74031333/167295795-49664cfa-c719-44ce-91cf-6ba170c3a509.png">

- 프론트는 리액트를 사용.
- axios를 통해 통신
- spring boot는 Jdbc를 사용해 MySQL 사용

### UML

<img width="480" height ="500" alt="UML" src="https://user-images.githubusercontent.com/74031333/167297750-7630f481-5d35-4c1d-aca0-94e711051732.png">

### 데이터베이스 테이블

![ER_diagram](https://user-images.githubusercontent.com/74031333/167295191-e5a14345-6e3f-4813-bae0-6f8a9af9367e.png)

## 사용방법

```
[Front]
cd front -> npm install ->  yarn start

[Back]

run application

```

## Branch 명명 규칙

1.  여러분 repo는 알아서 해주시고 😀(본인 레포니 main으로 하셔두 되져)
2.  prgrms-be-devcourse/spring-board 레포로 PR시 branch는 본인 username을 적어주세요 :)  
    base repo : `여기repo` base : `username` ← head repo : `여러분repo` compare : `main`
