package com.skl.community.community.controller;

import com.skl.community.community.chche.HotTagCache;
import com.skl.community.community.dto.PaginationDTO;
import com.skl.community.community.mapper.UserMapper;
import com.skl.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

  @Autowired
  private QuestionService questionService;

  @Autowired
  private HotTagCache hotTagCache;

  @GetMapping("/")
  public String index(Model model,
                      // 第几页
                      @RequestParam(name = "page", defaultValue = "1") Integer page,
                      // 每页size
                      @RequestParam(name = "size", defaultValue = "5") Integer size,
                      @RequestParam(name = "search", required = false) String search,
                      // 标签
                      @RequestParam(name = "tag", required = false) String tag) {

    PaginationDTO pagination = questionService.list(search, tag, page, size);
    List<String> tags = hotTagCache.getHots();

    model.addAttribute("pagination", pagination);
    model.addAttribute("search", search);
    model.addAttribute("tag", tag);// 搜索用
    model.addAttribute("tags", tags);// 热门标签用
    return "index";
  }
}
