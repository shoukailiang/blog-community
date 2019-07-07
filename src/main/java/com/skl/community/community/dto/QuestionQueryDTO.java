package com.skl.community.community.dto;


import lombok.Data;

@Data
public class QuestionQueryDTO {
  private String search;
  private Integer page;
  private Integer size;
}
