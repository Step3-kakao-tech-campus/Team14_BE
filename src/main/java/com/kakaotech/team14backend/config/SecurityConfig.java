package com.kakaotech.team14backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaotech.team14backend.common.ApiResponse;
import com.kakaotech.team14backend.common.ApiResponseGenerator;
import com.kakaotech.team14backend.jwt.JwtAuthenticationFilter;
import com.kakaotech.team14backend.outer.login.service.PrincipalOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final PrincipalOauth2UserService principalOauth2UserService;
  private final AuthenticationSuccessHandler authenticationSuccessHandler;

  public class CustomSecurityFilterManager extends AbstractHttpConfigurer<CustomSecurityFilterManager, HttpSecurity> {
    @Override
    public void configure(HttpSecurity builder) throws Exception {
      AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
      builder.addFilter(new JwtAuthenticationFilter(authenticationManager));
      super.configure(builder);
    }
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
//      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized 상태 코드 설정
//      response.setContentType("application/json"); // JSON 응답을 전송한다는 것을 명시
//      response.setCharacterEncoding("UTF-8"); // 문자 인코딩 설정

      // JSON 형태의 응답 메시지를 생성하여 응답에 쓰기
      ApiResponse<?> apiResponse = ApiResponseGenerator.fail("401", "로그인이 필요합니다", HttpStatus.UNAUTHORIZED);
      response.setCharacterEncoding("UTF-8");
      response.setContentType("application/json");
      response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));
    });


    http.exceptionHandling().accessDeniedHandler((request, response, accessDeniedException) -> {
      response.setCharacterEncoding("UTF-8");
      response.setContentType("application/json");
      // 현재 사용자의 권한에 따라 다른 에러 메시지를 설정
      if (request.isUserInRole("ROLE_BEGINNER")) {
        ApiResponse<?> apiResponse = ApiResponseGenerator.fail("403", "인스타연동이 필요합니다", HttpStatus.FORBIDDEN);
        response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));
//
      }
      ApiResponse<?> apiResponse = ApiResponseGenerator.fail("401", "로그인이 필요합니다", HttpStatus.UNAUTHORIZED);
      response.setCharacterEncoding("UTF-8");
      response.setContentType("application/json");
      response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));
    });
//
    http.apply(new CustomSecurityFilterManager());

    http.cors();
    http.headers().frameOptions().disable();

    http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.authorizeRequests()
        .antMatchers("/api/user/**", "/api/board/*/like", "/api/kakao").authenticated()
        .antMatchers("/api/user/instagram").access("hasRole('ROLE_BEGINNER')") //인스타그램 연동X "ROLE_BEGINNER"
        .antMatchers("/api/board/point").access("hasRole('ROLE_USER')") //인스타그램 연동시 "ROLE_USER"
        .antMatchers("/", "/api/login", "/h2-console/*", "api/board", "api/popluar-board").permitAll()
        .and()
        .oauth2Login()
        .successHandler(authenticationSuccessHandler)
        .userInfoEndpoint()
        .userService(principalOauth2UserService);
    return http.build();
  }


  public CorsConfigurationSource configurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.addAllowedHeader("*");
    configuration.addAllowedMethod("*"); // GET, POST, PUT, DELETE (Javascript 요청 허용)
    configuration.addAllowedOriginPattern("*"); // 모든 IP 주소 허용 (프론트 앤드 IP만 허용 react)
    configuration.setAllowCredentials(true); // 클라이언트에서 쿠키 요청 허용
    configuration.addExposedHeader("Authorization"); // 옛날에는 디폴트 였다. 지금은 아닙니다.
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }


}
