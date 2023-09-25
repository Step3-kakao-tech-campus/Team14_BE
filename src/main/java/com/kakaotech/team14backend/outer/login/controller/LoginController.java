package com.kakaotech.team14backend.outer.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

  @GetMapping("/api/login")
  public String loginOAuth2(){
    return "redirect:/oauth2/authorization/kakao";
  }
}
