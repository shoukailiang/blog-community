package com.skl.community.community.exception;

public enum CommunityErrorCode implements IComminityErrorCode {
  QUESTION_NOT_FOUND(2001,"你找到问题不在了，要不要换个试试？"),
  TARGET_PARAM_NOT_FIND(2002,"未选中任何问题或者评论进行回复"),
  NO_LOGIN(2003,"未登录，不能评论"),
  SYSTEM_ERROR(2004,"服务太热啦，要不然稍等下再来试试~"),
  TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
  COMMENT_NOT_FOUND(2006,"你回复的评论不存在"),
  CONTENT_IS_EMPTY(2007,"你回复的内容为空"),
  ;

  private String message;
  private Integer code;

  CommunityErrorCode(Integer code, String message) {
    this.message = message;
    this.code=code;
  }

  @Override
  public String getMessage() {
    return message;
  }

  @Override
  public Integer getCode() {
    return code;
  }
}
