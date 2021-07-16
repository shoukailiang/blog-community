package com.skl.community.community.strategy;

/**
 * @author shoukailiang
 * @version 1.0
 * @date 2021/7/6 14:37
 */
public interface UserStrategy {
    LoginUserInfo getUser(String code,String state);
    String getSupportedType();
}
