package com.skl.community.community.controller;


import com.skl.community.community.dto.FileDTO;
import com.skl.community.community.provider.UCloudProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class FileController {

  @Autowired
  private UCloudProvider uCloudProvider;

  @RequestMapping("/file/upload")
  @ResponseBody
  public FileDTO upload(HttpServletRequest request) {
    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
    MultipartFile file = multipartRequest.getFile("editormd-image-file");// html 的name
    try {
      String fileName = uCloudProvider.upload(file.getInputStream(), file.getContentType(), file.getOriginalFilename());
      FileDTO fileDTO = new FileDTO();
      fileDTO.setSuccess(1);
      fileDTO.setUrl(fileName);
      return fileDTO;
    } catch (Exception e) {
      e.printStackTrace();
      log.error("upload error", e);
      FileDTO fileDTO = new FileDTO();
      fileDTO.setSuccess(0);
      fileDTO.setMessage("上传失败");
      return fileDTO;
    }
  }
}
