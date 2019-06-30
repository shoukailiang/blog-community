package com.skl.community.community.dto;

import lombok.Data;

@Data
public class CommentDTO {
  private long parentId;
  private String content;
  private int type;
}
