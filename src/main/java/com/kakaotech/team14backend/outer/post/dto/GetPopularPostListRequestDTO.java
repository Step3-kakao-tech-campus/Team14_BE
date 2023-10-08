package com.kakaotech.team14backend.outer.post.dto;

import java.util.Map;

public record GetPopularPostListRequestDTO(Map<Integer,Integer> levelSize) {
}
