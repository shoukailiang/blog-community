package com.skl.community.community.advice;


import com.skl.community.community.exception.CommunityException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class CommunityExceptionHandler {
  @ExceptionHandler(Exception.class)
  ModelAndView handle(Throwable e, Model model) {
    if(e instanceof CommunityException){
      model.addAttribute("message",e.getMessage());
    }else {
      model.addAttribute("message","服务太热啦，要不然稍等下再来试试~");
    }
    return new ModelAndView("error");
  }
}
