package com.kakaotech.team14backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {


  @Bean(name = "proxyRestTemplate")
  public RestTemplate restTemplate() {
    HttpHost proxy = new HttpHost("krmp-proxy.9rum.cc", 3128);
    CloseableHttpClient httpClient = HttpClients.custom()
        .setProxy(proxy)
        .build();
    HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
    factory.setConnectTimeout(5000);
    factory.setReadTimeout(10000);
    return new RestTemplate(factory);
  }
}


