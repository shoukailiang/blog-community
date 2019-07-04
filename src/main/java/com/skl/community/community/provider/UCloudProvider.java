package com.skl.community.community.provider;

import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.auth.UfileObjectLocalAuthorization;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;
import com.skl.community.community.exception.CommunityErrorCode;
import com.skl.community.community.exception.CommunityException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

@Service
public class UCloudProvider {
  @Value("${ucloud.ufile.public-key}")
  private String publicKey;

  @Value("${ucloud.ufile.private-key}")
  private String privateKey;

  @Value("${ucloud.ufile.bucket-name}")
  private String bucketName;

  @Value("${ucloud.ufile.region}")
  private String region;

  @Value("${ucloud.ufile.suffix}")
  private String suffix;

  // 有效期 -> 秒
  @Value("${ucloud.ufile.expires}")
  private Integer expires;

  public String upload(InputStream fileStream, String mimeType, String fileName) {
    // 避免重名
    String generatedFileName;
    String[] filePaths = fileName.split("\\.");
    if (filePaths.length > 1) {
      generatedFileName = UUID.randomUUID().toString() + "." + filePaths[filePaths.length - 1];  // 后缀
    } else {
      throw new CommunityException(CommunityErrorCode.FILE_UPLOAD_FAIL);
    }
    try {
      ObjectAuthorization objectAuthorization = new UfileObjectLocalAuthorization(publicKey, privateKey);
      ObjectConfig config = new ObjectConfig(region, suffix);

      PutObjectResultBean response = UfileClient.object(objectAuthorization, config)
          .putObject(fileStream, mimeType)
          .nameAs(generatedFileName)
          .toBucket(bucketName)
          .setOnProgressListener((bytesWritten, contentLength) -> {
          })
          .execute();
      if (response != null && response.getRetCode() == 0) {
        String url = UfileClient.object(objectAuthorization, config)
            .getDownloadUrlFromPrivateBucket(generatedFileName, bucketName, expires)
            .createUrl();
        return url;
      } else {
        throw new CommunityException(CommunityErrorCode.FILE_UPLOAD_FAIL);
      }
    } catch (UfileClientException e) {
      e.printStackTrace();
      throw new CommunityException(CommunityErrorCode.FILE_UPLOAD_FAIL);
    } catch (UfileServerException e) {
      e.printStackTrace();
      throw new CommunityException(CommunityErrorCode.FILE_UPLOAD_FAIL);
    }
  }
}

