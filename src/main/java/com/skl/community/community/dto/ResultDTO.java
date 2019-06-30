package com.skl.community.community.dto;


import com.skl.community.community.exception.CommunityErrorCode;
import com.skl.community.community.exception.CommunityException;
import lombok.Data;

@Data
public class ResultDTO {
  private Integer code;
  private String message;

  public static ResultDTO errorOf(Integer code,String message){
    ResultDTO resultDTO = new ResultDTO();
    resultDTO.setCode(code);
    resultDTO.setMessage(message);
    return resultDTO;
  }

  public static ResultDTO errorOf(CommunityErrorCode errorCode) {
    return errorOf(errorCode.getCode(),errorCode.getMessage());
  }

  public static ResultDTO okOf() {
    ResultDTO resultDTO = new ResultDTO();
    resultDTO.setCode(200);
    resultDTO.setMessage("请求成功");
    return resultDTO;
  }

  public static ResultDTO errorOf(CommunityException e) {
    return errorOf(e.getCode(),e.getMessage());
  }
}
