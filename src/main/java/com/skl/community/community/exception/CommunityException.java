package com.skl.community.community.exception;


public class CommunityException extends RuntimeException{
  private String message;

  private Integer code;

  public CommunityException(IComminityErrorCode errorCode) {
    this.code=errorCode.getCode();
    this.message = errorCode.getMessage();
  }

  @Override
  public String getMessage() {
    return message;
  }

  public Integer getCode() {
    return code;
  }
}
