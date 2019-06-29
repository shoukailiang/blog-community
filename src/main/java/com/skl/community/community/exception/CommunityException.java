package com.skl.community.community.exception;


public class CommunityException extends RuntimeException{
  private String message;

  public CommunityException(IComminityErrorCode errorCode) {
    this.message = errorCode.getMessage();
  }

  @Override
  public String getMessage() {
    return message;
  }
}
