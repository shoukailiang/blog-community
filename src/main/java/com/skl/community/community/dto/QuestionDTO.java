package com.skl.community.community.dto;

import com.skl.community.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
  private Long id;
  private String title;
  private String description;
  private Long gmtCreate;
  private Long gmtModified;
  private Long creator;
  private Integer viewCount;
  private Integer commentCount;
  private Integer likeCount;
  private String tag;
  private User user;
}
