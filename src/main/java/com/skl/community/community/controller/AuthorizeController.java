package com.skl.community.community.controller;

import com.skl.community.community.dto.AccessTokenDto;
import com.skl.community.community.dto.GithubUser;
import com.skl.community.community.mapper.UserMapper;
import com.skl.community.community.model.User;
import com.skl.community.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

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

  @Autowired
  private UserMapper userMapper;

  @GetMapping("/callback")
  public String callback(@RequestParam(name="code") String code,
                         @RequestParam(name="state") String state,
                         HttpServletRequest request){
    AccessTokenDto accessTokenDto = new AccessTokenDto();
    accessTokenDto.setClient_id(clientId);
    accessTokenDto.setClient_secret(clientSecret);
    accessTokenDto.setCode(code);
    accessTokenDto.setRedirect_uri(redirectUri);
    accessTokenDto.setState(state);
    String accessToken = githubProvider.getAccessToken(accessTokenDto);
    GithubUser githubUser = githubProvider.getGithubUser(accessToken);
    if(githubUser!=null){
      User user = new User();
      user.setToken(UUID.randomUUID().toString());
      user.setName(githubUser.getName());
      user.setAccountId(String.valueOf(githubUser.getId()));
      user.setGmtCreate(System.currentTimeMillis());
      user.setGmtModified(user.getGmtCreate());
      userMapper.insert(user);
      // 登陆成功，写cookie和session
      request.getSession().setAttribute("user",githubUser);
      return "redirect:/";
    }else {
      // 登录失败，重新登录
      return "redirect:/";
    }
//    System.out.println(user.getName()); goodLiang
  }
}
