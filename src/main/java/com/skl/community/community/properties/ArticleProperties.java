package com.skl.community.community.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "community.article")
public class ArticleProperties implements Serializable {
    // 会将 community.article.aliyun 绑定到 AliyunProperties 对象上。
    private AliyunProperties aliyun;
}
