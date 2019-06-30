package com.skl.community.community.advice;


import com.alibaba.fastjson.JSON;
import com.skl.community.community.dto.ResultDTO;
import com.skl.community.community.exception.CommunityErrorCode;
import com.skl.community.community.exception.CommunityException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CommunityExceptionHandler {
  @ExceptionHandler(Exception.class)
  ModelAndView handle(Throwable e, Model model, HttpServletRequest request, HttpServletResponse response) {
    String contentType = request.getContentType();
    if ("application/json".equals(contentType)) {
      ResultDTO resultDTO = null;
      // 返回json
      if (e instanceof CommunityException) {
        resultDTO = ResultDTO.errorOf((CommunityException) e);
      } else {
        resultDTO = ResultDTO.errorOf(CommunityErrorCode.SYSTEM_ERROR);
      }

      try {
        response.setContentType("application/json");
        response.setStatus(200);
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(resultDTO));
        writer.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
      return null;
    } else {
      // 错误页面跳转
      if (e instanceof CommunityException) {
        model.addAttribute("message", e.getMessage());
      } else {
        model.addAttribute("message", CommunityErrorCode.SYSTEM_ERROR.getMessage());
      }
      return new ModelAndView("error");
    }
  }
}
