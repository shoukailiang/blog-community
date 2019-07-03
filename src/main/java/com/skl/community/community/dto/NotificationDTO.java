package com.skl.community.community.dto;

import lombok.Data;

@Data
public class NotificationDTO {
  private Long id;
  private Long gmtCreate;
  private Integer status;
  private Long notifier;
  // 发送通知的哪位仁兄
  private String notifierName;
  // 问题的名称
  private String outerTitle;
  // 跳转使用
  private Long outerid;
  // typename ---> 回复了问题。回复了评论
  private String typeName;
  private Integer type;
}
