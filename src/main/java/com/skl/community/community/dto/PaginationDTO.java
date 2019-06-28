package com.skl.community.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
  private List<QuestionDTO> questions;
  private boolean showPrevious;// 是否有向前按钮
  private boolean showFirstPage;
  private boolean showNext;
  private boolean showEndPage;
  private Integer page; // 当前页
  private List<Integer> pages = new ArrayList<>();
  private Integer totalPage;

  public void setPagination(Integer totalPage, Integer page) {

    this.totalPage=totalPage;
    this.page=page;

    pages.add(page);
    for (int i = 1; i < 3; i++) {
      if(page-i>0){
        pages.add(0,page-i);// 加到0的位置
      }
      if(page+i<=this.totalPage){
        pages.add(page+i);// 加到屁股
      }
    }



    //当page==1 没上一页
    if(page==1){
      showPrevious=false;
    }else {
      showPrevious=true;
    }

    // 当page==最后一页，没下一页
    if(page==totalPage){
      showNext=false;
    }else {
      showNext=true;
    }

    // 是否展示第一页
    if(pages.contains(1)){
      showFirstPage=false;
    }else {
      showFirstPage=true;
    }

    // 是否展示最后一页
    if(pages.contains(totalPage)){
      showEndPage=false;
    }else {
      showEndPage=true;
    }

  }
}
