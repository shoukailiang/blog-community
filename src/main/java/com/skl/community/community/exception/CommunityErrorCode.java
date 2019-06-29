package com.skl.community.community.exception;

public enum CommunityErrorCode implements IComminityErrorCode {
  QUESTION_NOT_FOUND("你找到问题不在了，要不要换个试试？");

  private String message;

  CommunityErrorCode(String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
