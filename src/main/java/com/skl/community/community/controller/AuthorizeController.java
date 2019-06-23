package com.skl.community.community.controller;

import com.skl.community.community.dto.AccessTokenDto;
import com.skl.community.community.dto.GithubUser;
import com.skl.community.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

  @Autowired
  private GithubProvider githubProvider;

  @Value("${github.client.id}")
  private String clientId;

  @Value("${github.client.secret}")
  private String clientSecret;

  @Value("${github.redirect.uri}")
  private String redirectUri;

  @GetMapping("/callback")
  public String callback(@RequestParam(name="code") String code,
                         @RequestParam(name="state") String state){
    AccessTokenDto accessTokenDto = new AccessTokenDto();
    accessTokenDto.setClient_id(clientId);
    accessTokenDto.setClient_secret(clientSecret);
    accessTokenDto.setCode(code);
    accessTokenDto.setRedirect_uri(redirectUri);
    accessTokenDto.setState(state);
    String accessToken = githubProvider.getAccessToken(accessTokenDto);
    GithubUser user = githubProvider.getGithubUser(accessToken);
    System.out.println(user.getName());
    return "index";
  }


}
