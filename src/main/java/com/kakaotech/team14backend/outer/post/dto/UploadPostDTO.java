package com.kakaotech.team14backend.outer.post.dto;

import org.springframework.web.multipart.MultipartFile;

public record UploadPostDTO(Long memberId, MultipartFile image,UploadPostRequestDTO uploadPostRequestDTO) {
}
