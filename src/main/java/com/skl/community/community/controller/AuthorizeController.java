package com.skl.community.community.controller;

import com.skl.community.community.dto.AccessTokenDTO;
import com.skl.community.community.dto.GithubUser;
import com.skl.community.community.model.User;
import com.skl.community.community.provider.GithubProvider;
import com.skl.community.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
  private UserService userService;

  @GetMapping("/callback")
  public String callback(@RequestParam(name = "code") String code,
                         @RequestParam(name = "state") String state,
                         HttpServletResponse response) {
    AccessTokenDTO accessTokenDto = new AccessTokenDTO();
    accessTokenDto.setClient_id(clientId);
    accessTokenDto.setClient_secret(clientSecret);
    accessTokenDto.setCode(code);
    accessTokenDto.setRedirect_uri(redirectUri);
    accessTokenDto.setState(state);
    String accessToken = githubProvider.getAccessToken(accessTokenDto);
    GithubUser githubUser = githubProvider.getGithubUser(accessToken);
    if (githubUser != null && githubUser.getId() != null) {
      User user = new User();
      String token = UUID.randomUUID().toString();
      user.setToken(token);
      user.setName(githubUser.getName());
      user.setAccountId(String.valueOf(githubUser.getId()));

      user.setAvatarUrl(githubUser.getAvatar_url());
      userService.createOrUpdate(user);
      response.addCookie(new Cookie("token", token));
      return "redirect:/";
    } else {
      // 登录失败，重新登录
      return "redirect:/";
    }
//    System.out.println(user.getName()); goodLiang
  }

  @GetMapping("/logout")
  public String logout(HttpServletRequest request,
                       HttpServletResponse response) {
    request.getSession().removeAttribute("user");
    Cookie cookie = new Cookie("token", null);
    cookie.setMaxAge(0);
    response.addCookie(cookie);
    return "redirect:/";
  }
}
