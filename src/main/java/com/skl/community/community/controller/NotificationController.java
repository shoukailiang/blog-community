package com.skl.community.community.controller;


import com.skl.community.community.dto.NotificationDTO;
import com.skl.community.community.enums.NotificationTypeEnum;
import com.skl.community.community.model.User;
import com.skl.community.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {
  @Autowired
  private NotificationService notificationService;

  // read
  @GetMapping("/notification/{id}")
  public String profile(HttpServletRequest request,
                        @PathVariable(name = "id") Long id) {

    User user = (User) request.getSession().getAttribute("user");
    if (user == null) {
      return "redirect:/";
    }
    NotificationDTO notificationDTO = notificationService.read(id, user);

    if (NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType()
        || NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType()) {
      return "redirect:/question/" + notificationDTO.getOuterid();
    } else {
      return "redirect:/";
    }
  }
}
