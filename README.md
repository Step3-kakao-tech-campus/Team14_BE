# 카카오 테크 캠퍼스 3단계 과제: 축팅

<div style="display: flex; justify-content: center;" align="center">
  <img src="https://github.com/Step3-kakao-tech-campus/Team14_FE/assets/37897508/274aa1d1-a513-43d3-91a2-c555fe6b523c" alt="홈페이지" width="250px" />
  <img src="https://github.com/Step3-kakao-tech-campus/Team14_BE/assets/37897508/c3a123ae-00df-4e76-8185-ec2add65ad42" alt="인기페이지" width="250px" />
  <img src="https://github.com/Step3-kakao-tech-campus/Team14_BE/assets/37897508/3e3a9fc8-2d1d-4256-841f-3ad01514b313" alt="마이페이지" width="250px" />
</div>

포토부스의 사진을 공유하고, 만남을 연결하는 서비스 **축팅**입니다.

축팅은 네컷사진을 중심으로 한 만남의 플랫폼입니다.

남성 사용자가 많은 데이팅 앱 시장의 문제점을 해결하기 위해 여성 사용자가 많은 네컷사진 시장을 접목시켰습니다.

사용자는 네컷사진을 게시하고, 축팅의 재화인 폭죽을 사용하여 마음에 드는 상대의 인스타그램에 방문할 수 있습니다.

<br />

## 🏁 시작하기

- [FE 배포 주소](https://k2bf481c846ffa.user-app.krampoline.com/)에 방문해보세요!
- [BE 배포 주소](https://ka53958d06e25a.user-app.krampoline.com/)


<br />

## 👋 팀 소개

프론트엔드 3인, 백엔드 4인으로 이루어진 7인 팀 SPARK 입니다.
### BE

<table>
  <tr>
    <td align="center"><img src="https://github.com/GoBeromsu.png" width="50"></td>
    <td align="center"><img src="https://github.com/yuseonkim.png" width="50"></td>
    <td align="center"><img src="https://github.com/SongGaEun16.png" width="50"></td>
    <td align="center"><img src="https://github.com/hwangdaesun.png" width="50"></td>
  </tr>
  <tr>
    <td align="center">고범수</td>
    <td align="center">김유선</td>
    <td align="center">송가은</td>
    <td align="center">황대선</td>
  </tr>
  <tr>
    <td align="center">테크리더</td>
    <td align="center">타임키퍼</td>
    <td align="center">리마인더</td>
    <td align="center">조장</td>
  </tr>
<table>

### FE

<table>
  <tr>
    <td align="center"><img src="https://github.com/MINJOO-KIM.png" width="50"></td>
    <td align="center"><img src="https://github.com/GhoRid.png" width="50"></td>
    <td align="center"><img src="https://github.com/iam454.png" width="50"></td>
  </tr>
  <tr>
    <td align="center">김민주</td>
    <td align="center">박건형</td>
    <td align="center">서완석</td>
  </tr>
  <tr>
    <td align="center">리액셔너</td>
    <td align="center">기획리더</td>
    <td align="center">테크리더</td>
  </tr>
<table>

<br />
## 🧐 프로젝트 소개

개발 기간 : 2023년 8월 25일 ~ 2023년 11월 11일

카카오 테크 캠퍼스(이하 카테캠) 3단계 과제 수행 프로젝트 중 BE 개발을 다루고 있습니다.

기획부터 배포까지 서비스 개발에 필요한 전반적인 과정을 경험했습니다.

### 📚 ERD
<img src="https://github.com/Step3-kakao-tech-campus/Team14_BE/assets/91835827/ed1dac78-b475-4c53-a312-99b1fb7450f4" width="800px" >

# 🤔 개발 주안점
## 클린 아키텍처 적용 도전

진행 상황을 "행동 - 문제인식 - 해결"의 순서로 정리해보겠습니다.
### 행동: 클린 아키텍처 적용 시도
- **시도한 행동**: 프로젝트 초기에는 클린 아키텍처를 적용하려고 시도했습니다. 이는 코드의 유지보수성과 확장성을 높이기 위한 목적이었습니다

### 문제 인식: 클린 아키텍처의 한계와 문제점
**문제 발생**
프로젝트의 크기와 복잡성을 고려했을 때, 클린 아키텍처의 적용은 불필요한 계층을 만들어내고, 인터페이스의 역할만 강조되는 문제를 발생시켰습니다.

**인식된 문제**
클린 아키텍처는 이론적으로는 이상적이지만, 프로젝트의 실제 요구사항과 규모에 비해 과도한 구조적 복잡성을 초래했습니다.
이는 팀내의 인지 과부화를 발생시키고, 학습 곡선을 가팔라게 만들었습니다.

### 해결: 레이어드 아키텍처의 버티컬 슬라이싱으로 전환
**왜 이 방법을 선택했는가**:
- **결합도 낮춤**
	- 전체 시스템의 결합도를 낮추기 위해 각 기능을 독립적인 단위로 나누어, 모듈 간의 강한 결합을 피하고 유연한 구조를 만들고 싶었습니다
- **인지 과부화 방지**
	- 불필요한 복잡성을 제거하고,개발자가 각 모듈의 기능과 역할을 더 명확하게 이해할 수 있게 하기 위함입니다

Controller에서는 사용자의 행위 단위를 반영하여 각 엔드포인트에 usecase를 할당했습니다.

이는 비즈니스 로직을 usecase에 위임함으로써 효율적인 관리를 하기 위함이었습니다

복잡성이 낮은 경우 usecase가 바로 repository에 의존할 수 있으며, 복잡도가 높아질 경우 command라는 하위 모듈을 사용해 의존성을 낮추는 전략을 사용했습니다

이러한 접근 방식은 시스템의 각 부분이 효과적으로 상호 작용하면서도 필요에 따라 독립적으로 작동할 수 있도록 하여, 전체적인 시스템의 유연성과 확장성을 강화하려는 의도였습니다.


## 사용기술
- JDK 17
- Springboot 2.7.15
- SpringSecurity
- MySQL : 8.0.33
- Redis 7.2
- java-jwt 4.3.0


## 프로젝트 기능 및 역할

| 기능 | 설명 | 담당자 |
|------|------|--------|
| 게시물 업로드 | 해시태그, 닉네임, 이미지를 포함한 게시글을 업로드하는 기능 구현. | 황대선, 고범수 |
| 인기 피드 조회 | 클라이언트가 level1, level2, level3 게시물을 각각 최대 10개까지 요청. MariaDB에 저장된 게시글들의 개수 확인 (30개 이상 여부). PostRandomFetcher 클래스를 통해 랜덤하게 게시물 선택. Redis의 SortedSet 자료구조를 이용하여 순위 조회. | 황대선 |
|조회수|Redis의 Set 자료구조를 사용하여 게시글 조회 시 회원의 Primary Key를 저장함으로써, 한 회원이 조회수를 악의적으로 조작하는 것을 방지합니다.<br> 스케줄러를 활용하여 10분마다 Redis의 Set 자료구조에 저장된 데이터를 기반으로 MariaDB에 게시글의 조회수를 업데이트하고, 이후 Set 자료구조의 데이터를 초기화(롤백)합니다|황대선|
| 인기 게시글 선정 | 인기도는 게시글의 연령, 조회수, 그리고 추천 수를 기반으로 하여 10분마다 갱신. Redis의 SortedSet 자료구조에 저장. | 황대선 |
| 폭죽 사용 | 해당 인기 게시글의 레벨을 검증한 후, 그에 해당하는 포인트를 사용. 사용자의 포인트가 충분할 경우, 해당 게시물의 소유자의 인스타그램 아이디 반환. | 황대선, 고범수 |
| 예외 처리 | RuntimeException 클래스 상속하여 커스텀 예외 생성. GlobalExceptionHander와 PostExceptionHandler 클래스를 이용한 예외 핸들링. MessageCode 클래스를 통해 오류 코드와 설명 제공. | 황대선 |
| API 응답 | ApiResponse 클래스와 ApiResponseGenerator 클래스를 생성하여 API 응답 표준화. | 황대선 |
| 아키텍처 설계 | Layred Architecture에 따른 설계. Presentation, Application, Infrastructure Layer 및 예외 처리 클래스 구성. | 황대선, 고범수 |
| 각종 컨벤션 | 네이밍 및 커밋 컨벤션 정의. | 황대선, 고범수 |
| 카카오 로그인 | 카카오 OAuth2 Rest API를 사용한 로그인 및 사용자 정보 조회 기능 구현. | 김유선 |
| 인스타그램 연동 | Instagram Graph OAuth2 Rest API를 사용한 로그인 및 사용자 정보 조회 기능 구현. | 김유선 |
| JWT 토큰 | 사용자 식별 UID 및 권한을 담은 토큰 발급 기능 구현. | 김유선 |
| 토큰 관리 | 엑세스토큰과 리프레시토큰 발급 기능 개발. | 김유선 |
| CORS 설정 | 프론트배포URL에서만 백엔드서버 API 요청 및 응답 가능하도록 CORS 설정. | 김유선 |
| 접근권한 설정 | API 별 접근권한 설정 및 사용자 ROLE 별 기능 제한. | 김유선 |
|피드 조회 공통 클래스 구현|	PostFetchResponse 클래스는 홈피드와 마이피드 등 다양한 피드 조회 기능에서 사용되는 공통 구조를 정의합니다.이 클래스를 사용함으로써 다양한 피드 조회 시나리오에서 코드 중복을 줄였습니다.	|고범수|
| 홈 피드 | 사용자 인증 상태에 따라 다른 정보를 제공하는 홈 피드 구현. | 고범수 |
| 자신이 올린 게시물 조회 | 커서 기반 무한 스크롤 방식을 사용한 게시물 조회 기능. | 고범수, 송가은 |
| 토글 방식의 좋아요 기능 | 단일 엔드포인트를 통한 좋아요 토글 기능 구현. | 고범수 |
| 폭죽 사용 기록 추적 | 폭죽 사용 기록 추적 및 `instacount` 테이블 도입으로 데이터 처리 개선. | 고범수 |
| 유저 계정 정보 조회 | 도메인 중심 전략을 통한 유저 계정 정보 조회 기능 구현. | 고범수, 송가은 |
| 쿠버네티스 환경 배포 | 쿠버네티스 환경에서의 백엔드 프로젝트 배포 및 관리. | 고범수 |


## 컨벤션

- Commit Convention
  [Commit Convention · Issue #20 · Step3-kakao-tech-campus/Team14_BE](https://github.com/Step3-kakao-tech-campus/Team14_BE/issues/20)

- Branch Naming Convention
  [Branch Naming Convention · Issue #9 · Step3-kakao-tech-campus/Team14_BE](https://github.com/Step3-kakao-tech-campus/Team14_BE/issues/9)

- Naming Convention
  [Naming Convention · Issue #15 · Step3-kakao-tech-campus/Team14_BE](https://github.com/Step3-kakao-tech-campus/Team14_BE/issues/15)


## 프로젝트 진행 시 고민하였던 부분

- [hwangdaesun](https://github.com/hwangdaesun/Chookting/blob/master/README.md)
- [GoBeromsu](https://github.com/GoBeromsu)
- [yuseonkim](https://github.com/yuseonkim)
- [SongGaEun16](https://github.com/SongGaEun16)

