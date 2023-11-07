package com.kakaotech.team14backend.common;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HashTagUtils {

  public static String attachHashTags(List<String> hashTags) {
    if (hashTags == null || hashTags.isEmpty()) {
      return "";
    }
    return String.join(" ", hashTags);
  }


  public static List<String> splitHashtag(String hashTag) {
    if (hashTag == null || hashTag.trim().isEmpty()) {
      return Collections.emptyList();
    }

    return Arrays.asList(hashTag.trim().split("\\s+"));
  }


}
