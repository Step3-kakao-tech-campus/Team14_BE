package com.kakaotech.team14backend.config;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.kakaotech.team14backend.common.CookieUtils;
import com.kakaotech.team14backend.filter.FilterResponseUtils;
import com.kakaotech.team14backend.jwt.JwtAuthenticationFilter;
import com.kakaotech.team14backend.jwt.dto.ReissueDTO;
import com.kakaotech.team14backend.jwt.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final TokenService tokenService;

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
      boolean isRoleNotUser = request.isUserInRole("ROLE_BEGINNER");
      FilterResponseUtils.forbidden(response,isRoleNotUser);
    });
//
    http.apply(new CustomSecurityFilterManager());
    http.cors();
    http.cors().configurationSource(configurationSource());
    http.headers().frameOptions().disable();

    http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.authorizeRequests()
        .antMatchers("/api/user/**", "/api/board/*/like", "/api/kakao").authenticated()
        .antMatchers("/api/user/instagram").access("hasRole('ROLE_BEGINNER')") //인스타그램 연동X "ROLE_BEGINNER"
        .antMatchers("/api/board/point").access("hasRole('ROLE_USER')") //인스타그램 연동시 "ROLE_USER"
        .antMatchers("/", "/api/login","/api/reissue", "/h2-console/*", "api/post", "api/popluar-post").permitAll();
    return http.build();
  }


  @Bean
  public CorsConfigurationSource configurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    // 쿠키 사용 허용
    configuration.setAllowCredentials(true);
    configuration.setAllowedOrigins(
        Arrays.asList("http://localhost:3000", "https://k576830a43f26a.user-app.krampoline.com")
    );
    // 메서드 허용
    configuration.setAllowedMethods(
        Arrays.asList(HttpMethod.POST.name(), HttpMethod.GET.name(),
            HttpMethod.PUT.name(), HttpMethod.DELETE.name(),
            HttpMethod.OPTIONS.name())
    );
    // 요청헤더 허용
    configuration.setAllowedHeaders(
        Arrays.asList("Authorization")
        );
    // 응답 헤더 허용
    configuration.setExposedHeaders(
        Arrays.asList("Content-Type")
        );

    UrlBasedCorsConfigurationSource source
        = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);

    FilterRegistrationBean bean
        = new FilterRegistrationBean(new CorsFilter(source));
    bean.setOrder(0);
    return source;
  }


}
