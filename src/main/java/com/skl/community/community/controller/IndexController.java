package com.skl.community.community.controller;

import com.skl.community.community.dto.PaginationDTO;
import com.skl.community.community.mapper.UserMapper;
import com.skl.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

  @Autowired
  private QuestionService questionService;

  @GetMapping("/")
  public String index(Model model,
                      // 第几页
                      @RequestParam(name = "page", defaultValue = "1") Integer page,
                      // 每页size
                      @RequestParam(name = "size", defaultValue = "5") Integer size,
                      @RequestParam(name = "search", required = false) String search) {


    PaginationDTO pagination = questionService.list(search, page, size);
    model.addAttribute("pagination", pagination);
    model.addAttribute("search", search);
    return "index";
  }
}
