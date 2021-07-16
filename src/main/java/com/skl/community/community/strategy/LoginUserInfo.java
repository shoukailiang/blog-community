package com.skl.community.community.strategy;

import lombok.Data;

/**
 * @author shoukailiang
 * @version 1.0
 * @date 2021/7/6 21:59
 */

@Data
public class LoginUserInfo {
    private String name;
    private Long id;
    private String bio;// 描述
    private String avatar_url;
}
