package com.kakaotech.team14backend.image.infrastructure;

import com.kakaotech.team14backend.image.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long>{

}
