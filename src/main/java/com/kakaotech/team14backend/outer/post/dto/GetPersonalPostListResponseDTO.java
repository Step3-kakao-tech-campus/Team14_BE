package com.kakaotech.team14backend.outer.post.dto;

import java.util.List;

public record GetPersonalPostListResponseDTO(List<GetPersonalPostResponseDTO> postList, Boolean hasNext) {

}
