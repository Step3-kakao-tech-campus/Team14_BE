package com.kakaotech.team14backend.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HashTagUtilsTest {

  @Test
  void attachHashTags() {
    //when
    List<String> hashTags = List.of("해시태그1", "해시태그2", "해시태그3");

    //then
    String attachHashTags = HashTagUtils.attachHashTags(hashTags);

    Assertions.assertEquals("해시태그1 해시태그2 해시태그3", attachHashTags);

  }

  @Test
  void splitHashTags(){
    //when
    String hashTags = "해시태그1 해시태그2 해시태그3 ";

    //then
    List<String> splitHashTags = HashTagUtils.splitHashtag(hashTags);
    System.out.println(splitHashTags);

    Assertions.assertEquals(List.of("해시태그1", "해시태그2", "해시태그3"), splitHashTags);
  }
}
