package com.kakaotech.team14backend.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan// ComponentScan은 @Component를 스캔해서 스프링 빈으로 등록,이거 안쓰면 @Bean으로 직접 등록해야함, 자원 절약 가능
@Configuration
public class AutoAppConfig {
}
