package com.kakaotech.team14backend.outer.member.dto;
import java.util.ArrayList;

public class MyPage {

    private String userId;
    private String userName;
    private String kakaoId;
    private int totalLike;
    private String instagramId;
    private String image;
    private int points;
    private ArrayList<String> activityHistory;

    // 생성자
    public MyPage(String userName, String userId,String kakaoId, int totalLike, String instagramId, String image) {
      this.userName = userName;
      this.userId = userId;
      this.kakaoId = kakaoId;
      this.instagramId = instagramId;
      this.image = image;
      this.totalLike = totalLike;
      this.points = 0;
      this.activityHistory = new ArrayList<>();
    }

    // 사용자 정보 설정 메서드
    public void setUserInfo(String userName, String userId, String kakaoId, int totalLike, String instagramId, String image) {
      this.userName = userName;
      this.userId = userId;
      this.kakaoId = kakaoId;
      this.totalLike = totalLike;
      this.instagramId = instagramId;
      this.image = image;
    }

    // 포인트 추가 메서드
    public void addPoints(int points) {
      this.points += points;
    }

    // 활동 내역 추가 메서드
    public void addActivity(String activity) {
      this.activityHistory.add(activity);
    }

    // 마이페이지 정보 출력 메서드
    public void displayMyPage() {
      System.out.println("---------- 마이페이지 ----------");
      System.out.println("이름: " + this.userName);
      System.out.println("아이디: " + this.userId);
      System.out.println("나이: " + this.kakaoId);
      System.out.println("좋아요수: " + this.totalLike);
      System.out.println("인스타그램아이디: " + this.instagramId);
      System.out.println("사진: " + this.image);
      System.out.println("활동 내역:");
      for (String activity : activityHistory) {
        System.out.println("- " + activity);
      }
      System.out.println("-----------------------------");
    }

    // Getter 메서드 (필요에 따라 추가)
    public String getUserName() {
      return this.userName;
    }

    public String getUserId() {
      return this.userId;
    }

    public String getKakaoId() {
      return this.kakaoId;
    }

    public int getTotalLike() {
      return this.totalLike;
    }

    public String getInstagramId() {
      return this.instagramId;
    }

    public String getImage() {
      return this.image;
    }

    public ArrayList<String> getActivityHistory() {
      return this.activityHistory;
    }

    public static void main(String[] args) {
      // 예시: 마이페이지 객체 생성 및 정보 설정
      MyPage myPage = new MyPage("kakao", "kakao@example.com", "kakao@kakao", 33, "kakao@insta", "path_to_image");
      myPage.addPoints(100);
      myPage.addActivity("게시글 작성");
      myPage.addActivity("폭죽보냄");
      myPage.displayMyPage();
    }
  }
