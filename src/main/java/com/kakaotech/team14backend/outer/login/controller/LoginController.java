package com.kakaotech.team14backend.outer.login.controller;

import com.kakaotech.team14backend.common.ApiResponseGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

  @GetMapping("/api/login")
  public String loginOAuth2() {
    return "redirect:/oauth2/authorization/kakao";
  }

  @ResponseBody
  @GetMapping("/api/user/instagram")
  public String info() {
    return "instagram";

  }


}
