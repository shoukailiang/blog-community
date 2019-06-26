package com.skl.community.community.controller;

import com.skl.community.community.dto.PaginationDTO;
import com.skl.community.community.dto.QuestionDTO;
import com.skl.community.community.mapper.QuestionMapper;
import com.skl.community.community.mapper.UserMapper;
import com.skl.community.community.model.Question;
import com.skl.community.community.model.User;
import com.skl.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private QuestionService questionService;

  @GetMapping("/")
  public String index(HttpServletRequest request,
                      Model model,
                      // 第几页
                      @RequestParam(name = "page",defaultValue = "1") Integer page,
                      // 每页size
                      @RequestParam(name = "size",defaultValue = "5") Integer size) {
    Cookie[] cookies = request.getCookies();
    if(cookies!=null&&cookies.length!=0){
      for (Cookie cookie :cookies) {
        if(cookie.getName().equals("token")){
          String token = cookie.getValue();
          User user = userMapper.findByToken(token);
          if(user!=null){
            // 写到session
            request.getSession().setAttribute("user",user);
          }
          break;
        }
      }
    }


    PaginationDTO pagination = questionService.list(page,size);
    model.addAttribute("pagination",pagination);
    return "index";
  }
}
