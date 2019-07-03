package com.skl.community.community.controller;


import com.skl.community.community.dto.PaginationDTO;
import com.skl.community.community.model.Notification;
import com.skl.community.community.model.User;
import com.skl.community.community.service.NotificationService;
import com.skl.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {


  @Autowired
  private QuestionService questionService;

  @Autowired
  private NotificationService notificationService;

  @GetMapping("profile/{action}")
  public String profile(@PathVariable(name = "action") String action,
                        Model model,
                        HttpServletRequest request,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size) {


    User user = (User) request.getSession().getAttribute("user");

    if (user == null) {
      return "redirect:/";
    }

    if ("questions".equals(action)) {
      model.addAttribute("section", "questions");
      model.addAttribute("sectionName", "我的提问");
      PaginationDTO paginationDTO = questionService.list(user.getId(), page, size);
      model.addAttribute("pagination", paginationDTO);
    } else if ("replies".equals(action)) {
      model.addAttribute("section", "replies");
      model.addAttribute("sectionName", "我的最新回复");
      PaginationDTO paginationDTO = notificationService.list(user.getId(), page, size);
      model.addAttribute("pagination", paginationDTO);
    }


    return "profile";
  }
}
