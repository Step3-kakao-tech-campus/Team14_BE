package com.kakaotech.team14backend.post.dto;

import java.util.Map;

public record GetPopularPostListRequestDTO(Map<Integer,Integer> levelSize) {
}
