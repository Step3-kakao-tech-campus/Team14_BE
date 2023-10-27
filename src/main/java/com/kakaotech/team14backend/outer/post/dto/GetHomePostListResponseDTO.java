package com.kakaotech.team14backend.outer.post.dto;

import java.util.List;

public record GetHomePostListResponseDTO(Long lastPostId, List<GetPostResponseDTO> postList,
                                         Boolean hasNext) {

}
