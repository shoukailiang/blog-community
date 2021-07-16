package com.skl.community.community.controller;

import com.skl.community.community.dto.AccessTokenDTO;
import com.skl.community.community.provider.dto.GithubUser;
import com.skl.community.community.model.User;
import com.skl.community.community.provider.GithubProvider;
import com.skl.community.community.service.UserService;
import com.skl.community.community.strategy.LoginUserInfo;
import com.skl.community.community.strategy.UserStrategy;
import com.skl.community.community.strategy.UserStrategyFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
@Controller
public class AuthorizeController {

    @Autowired
    private UserStrategyFactory userStrategyFactory;

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

    @GetMapping("/callback/{type}")
    public String newCallback(
            @PathVariable(name = "type") String type,
            @RequestParam(name = "code") String code,
            // gitee 平台不需要state
            @RequestParam(name = "state",required = false) String state,
            HttpServletResponse response) {
        UserStrategy strategy = userStrategyFactory.getStrategy(type);
        LoginUserInfo loginUserInfo = strategy.getUser(code, state);
        if (loginUserInfo != null && loginUserInfo.getId() != null) {
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(loginUserInfo.getName());
            user.setAccountId(String.valueOf(loginUserInfo.getId()));
            user.setType(type);
            user.setAvatarUrl(loginUserInfo.getAvatar_url());
            userService.createOrUpdate(user);
            response.addCookie(new Cookie("token", token));
            return "redirect:/";
        } else {
            // 登录失败，重新登录
            log.error("callback get github error,{}", loginUserInfo);
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
