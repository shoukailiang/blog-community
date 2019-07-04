package com.skl.community.community.dto;


import lombok.Data;

/**
 * {
 *     success : 0 | 1,           // 0 表示上传失败，1 表示上传成功
 *     message : "提示的信息，上传成功或上传失败及错误信息等。",
 *     url     : "图片地址"        // 上传成功时才返回
 * }
 */
@Data
public class FileDTO {
  private int success;
  private String message;
  private String url;
}
