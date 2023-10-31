package com.kakaotech.team14backend.outer.post.dto;

import java.util.List;

public record GetHomePostListResponseDTO(Long lastPostId,
                                         List<? extends GetHomePostInterface> postList,
                                         Boolean hasNext) {

}
