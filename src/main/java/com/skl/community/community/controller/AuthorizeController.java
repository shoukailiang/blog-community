package com.skl.community.community.controller;

import com.skl.community.community.dto.AccessTokenDto;
import com.skl.community.community.dto.GithubUser;
import com.skl.community.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

  @Autowired
  private GithubProvider githubProvider;
  @GetMapping("/callback")
  public String callback(@RequestParam(name="code") String code,
                         @RequestParam(name="state") String state){
    AccessTokenDto accessTokenDto = new AccessTokenDto();
    accessTokenDto.setClient_id("34249f8e850476059776");
    accessTokenDto.setClient_secret("cd0658500146924c4c57a09ded6e74cb31a4f66b");
    accessTokenDto.setCode(code);
    accessTokenDto.setRedirect_uri("http://localhost:8888/callback");
    accessTokenDto.setState(state);
    String accessToken = githubProvider.getAccessToken(accessTokenDto);
    GithubUser user = githubProvider.getGithubUser(accessToken);
    System.out.println(user.getName());
    return "index";
  }


}
