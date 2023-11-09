package com.kakaotech.team14backend.post.dto;

import java.util.List;

public record GetPersonalPostListResponseDTO(Long lastPostId,
                                             List<GetPersonalPostResponseDTO> postList,
                                             Boolean hasNext) {

}
