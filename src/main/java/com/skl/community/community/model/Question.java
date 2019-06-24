package com.skl.community.community.model;

import lombok.Data;

@Data
public class Question {
  private Integer id;
  private String title;
  private String description;
  private Long gmtCreate;
  private Long gmtModified;
  private Integer creator;
  private Integer viewCount;
  private Integer commentCount;
  private Integer likeCount;
  private String tag;
}

