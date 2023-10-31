package com.kakaotech.team14backend.outer.post.dto;

public record GetPersonalPostResponseDTO(Long postId, String imageUri, String nickname,
                                         String createdAt, Long viewCount, Long likeCount) {

//  {
//    "postid": "123456",
//      "imageUri": "12345",
//      "nickname": "가은",
//      "createdAt": "",
//      "viewCount": 10,
//      "likeCount": 50
//  },
}

// DTO 예시
// public record GetPersonalPostListResponseDTO(List<GetPostResponseDTO> postList, Boolean hasNext) {}
