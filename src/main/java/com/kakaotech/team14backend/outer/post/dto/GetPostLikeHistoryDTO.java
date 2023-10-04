package com.kakaotech.team14backend.outer.post.dto;

import com.kakaotech.team14backend.inner.member.model.Member;
import com.kakaotech.team14backend.inner.post.model.Post;

public record GetPostLikeHistoryDTO(Member member, Post post) {

}
