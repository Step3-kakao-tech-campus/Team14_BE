package com.kakaotech.team14backend.outer.post.dto;

import java.util.List;

public record GetPostListResponseDTO(Long lastPostId, List<GetPostResponseDTO> postList,
                                     Boolean hasNext) {

}
