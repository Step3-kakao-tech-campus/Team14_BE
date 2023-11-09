package com.kakaotech.team14backend.config;

import com.kakaotech.team14backend.filter.FilterResponseUtils;
import com.kakaotech.team14backend.jwt.JwtAuthenticationFilter;
import com.kakaotech.team14backend.jwt.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Value("${common.frontend-server}")
  private String FE_SERVER_URL;
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
      FilterResponseUtils.unAuthorized(response);
    });


    http.exceptionHandling().accessDeniedHandler((request, response, accessDeniedException) -> {
      boolean isRoleNotUser = request.isUserInRole("BEGINNER");
      FilterResponseUtils.forbidden(response, isRoleNotUser);
    });

    http.cors()
        .configurationSource(corsConfigurationSource());
    http.apply(new CustomSecurityFilterManager());
    http.headers().frameOptions().disable();

    http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.authorizeRequests()
        //인스타그램을 안한 사람만 접근 가능 "ROLE_BEGINNER"
        .antMatchers("/api/user/instagram").access("hasRole('BEGINNER')")
        //카카오로그인을 해야 접근 가능
        .antMatchers("/api/user/**", "/api/post/*/like", "/api/popular-post/*", "/api/popular-post", "/api/point", "/api/point/popular-post").authenticated()
        // POST 메서드에 대한 /api/post 는 "ROLE_USER" 역할이 필요
        .antMatchers(HttpMethod.POST, "/api/post").access("hasRole('USER')")
        //인스타그램 연동 한 사람만 접근 가능 "ROLE_USER"
        .antMatchers("/api/board/point", "/api/points/**").access("hasRole('USER')")
        //모든 사용자 접근 가능
        .antMatchers("/", "/api/login", "/api/reissue", "/h2-console/*","/api/post/**").permitAll()
        // GET 메서드에 대한 /api/post 는 모든 사용자가 접근 가능
        .antMatchers(HttpMethod.GET, "/api/post").permitAll();

    return http.build();
  }


  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOriginPatterns(Collections.singletonList(FE_SERVER_URL));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
    configuration.setAllowCredentials(true);
    configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
    configuration.setExposedHeaders(Arrays.asList("Authorization"));

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }


}
