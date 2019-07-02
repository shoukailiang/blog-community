package com.skl.community.community.dto;

import lombok.Data;

import java.util.List;

@Data
public class TagDTO {
  private String categoryName;
  private List<String> tags;
}
