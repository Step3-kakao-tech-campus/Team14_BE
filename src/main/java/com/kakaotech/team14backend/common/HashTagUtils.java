package com.kakaotech.team14backend.common;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HashTagUtils {

  public static String attachHashTags(List<String> hashTags) {
    StringBuilder sb = new StringBuilder();
    for (String hashTag : hashTags) {
      sb.append(hashTag + " ");
    }
    return sb.toString();
  }

  public static List<String> splitHashtag(String hashTag) {
    String[] splitHashtags = hashTag.split(" ");
    return Arrays.stream(splitHashtags).collect(Collectors.toList());
  }

}
