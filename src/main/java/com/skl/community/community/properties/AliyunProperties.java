package com.skl.community.community.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;

/**
 * 阿里云相关配置
 */
@Data
public class AliyunProperties implements Serializable {

    // 阿里云地域端点
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    // 存储空间名字
    private String bucketName;

    // Bucket域名 访问文件时作为url前缀
    private String bucketDomain;
}
