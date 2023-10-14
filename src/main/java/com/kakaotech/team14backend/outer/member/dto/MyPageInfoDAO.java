package com.kakaotech.team14backend.outer.member.dto;

import com.kakaotech.team14backend.inner.post.model.Post;
import java.util.ArrayList;
import java.util.List;

public class MyPageInfoDAO {
  // 데이터베이스 연결 및 관련 메서드들

  public List<Post> getPostsByAuthor(String author) {
    List<Post> posts = new ArrayList<>();
    String query = "SELECT * FROM posts WHERE author = ?";//Repository pattern 으로 옮기기
//
//    try (Connection conn = DriverManager.getConnection(url, username, password);
//         PreparedStatement preparedStatement = conn.prepareStatement(query)) {
//
//      preparedStatement.setString(1, author);
//
//      try (ResultSet resultSet = preparedStatement.executeQuery()) {
//        while (resultSet.next()) {
//          Post post = new Post();
//          post.postId(resultSet.getInt("id"));
//          post.hashtag(resultSet.getString("hashtag"));
//          post.university(resultSet.getString("university"));
//          post.image(resultSet.getString("image"));
//          posts.add(post);
//        }
//      }
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }

    return posts;
  }
}
