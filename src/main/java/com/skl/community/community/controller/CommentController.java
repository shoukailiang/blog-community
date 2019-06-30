package com.skl.community.community.controller;


import com.skl.community.community.dto.CommentDTO;
import com.skl.community.community.dto.ResultDTO;
import com.skl.community.community.exception.CommunityErrorCode;
import com.skl.community.community.model.Comment;
import com.skl.community.community.model.User;
import com.skl.community.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
public class CommentController {



  @Autowired
  private CommentService commentService;

  @ResponseBody
  @RequestMapping(value = "/comment",method = RequestMethod.POST)
  // RequestBody,自动反序列化
  public Object post(@RequestBody CommentDTO commentDTO,
                     HttpServletRequest request){

    User user = (User) request.getSession().getAttribute("user");

    if(user==null){
      return ResultDTO.errorOf(CommunityErrorCode.NO_LOGIN);
    }

    Comment comment = new Comment();
    comment.setParentId(commentDTO.getParentId());
    comment.setContent(commentDTO.getContent());
    comment.setType(commentDTO.getType());
    comment.setGmtCreate(System.currentTimeMillis());
    comment.setGmtModified(System.currentTimeMillis());
    comment.setLikeCount(0L);
    comment.setCommentator(user.getId());
    commentService.insert(comment);
    return ResultDTO.okOf();
  }
}
