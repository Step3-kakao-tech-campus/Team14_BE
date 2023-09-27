package com.kakaotech.team14backend.inner.image.model;

import static lombok.AccessLevel.PROTECTED;

import com.kakaotech.team14backend.inner.post.model.Post;
import javax.persistence.*;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;


@Entity
@Getter
@NoArgsConstructor(access=PROTECTED)
public class Image {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long imageId; // 사진 ID

  @Column(nullable = false, length = 20)
  private String imageUri; // 서버용 사진 저장 이름

  @Column(nullable = false)
  @CreationTimestamp
  private LocalDateTime createdAt; // 생성일

  public static Image createImage(String imageUri){
    return Image.builder()
        .imageUri(imageUri)
        .build();
  }

  @Builder
  public Image(String imageUri) {
    this.imageUri = imageUri;
  }
}
