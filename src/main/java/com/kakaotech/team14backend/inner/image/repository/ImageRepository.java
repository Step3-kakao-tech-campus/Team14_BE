package com.kakaotech.team14backend.inner.image.repository;

import com.kakaotech.team14backend.image.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long>{

}
