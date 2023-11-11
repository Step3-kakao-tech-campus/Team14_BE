## Chookting

포토부스의 사진을 공유하고, 만남을 연결하는 서비스 축팅입니다.

[축팅](https://ka53958d06e25a.user-app.krampoline.com/)에 방문해보세요!

[BE 배포 주소](https://ka53958d06e25a.user-app.krampoline.com/)


## 팀 소개

<table>
  <tr>
    <td align="center"><a href="https://github.com/hwangdaesun"><img src="https://github.com/hwangdaesun.png" width="50"></a></td>
    <td align="center"><a href="https://github.com/GoBeromsu"><img src="https://github.com/GoBeromsu.png" width="50"></a></td>
    <td align="center"><a href="https://github.com/yuseonkim"><img src="https://github.com/yuseonkim.png" width="50"></a></td>
    <td align="center"><a href="https://github.com/SongGaEun16"><img src="https://github.com/SongGaEun16.png" width="50"></a></td>
  </tr>
  <tr>
    <td align="center"><a href="https://github.com/hwangdaesun">hwangdaesun</a></td>
    <td align="center"><a href="https://github.com/GoBeromsu">GoBeromsu</a></td>
    <td align="center"><a href="https://github.com/yuseonkim">yuseonkim</a></td>
    <td align="center"><a href="https://github.com/SongGaEun16">SongGaEun16</a></td>
  </tr>
  <tr>
    <td align="center">조장</td>
    <td align="center">테크리더</td>
    <td align="center">리마인더</td>
    <td align="center">리액셔너</td>
  </tr>
<table>


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


## DB 설계

![ERD_chookting](https://github.com/Step3-kakao-tech-campus/Team14_BE/assets/91835827/ed1dac78-b475-4c53-a312-99b1fb7450f4)



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

