package com.skl.community.community.strategy;

import com.skl.community.community.dto.AccessTokenDTO;
import com.skl.community.community.provider.GiteeProvider;
import com.skl.community.community.provider.dto.GiteeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author shoukailiang
 * @version 1.0
 * @date 2021/7/15 20:19
 */

@Service
public class GiteeUserStragegy implements UserStrategy {
    @Autowired
    private GiteeProvider giteeProvider;

    @Override
    public LoginUserInfo getUser(String code, String state) {
        AccessTokenDTO accessTokenDto = new AccessTokenDTO();
        accessTokenDto.setCode(code);
        accessTokenDto.setState(state);
        String accessToken = giteeProvider.getAccessToken(accessTokenDto);
        GiteeUser giteeUser = giteeProvider.getUser(accessToken);
        LoginUserInfo loginUserInfo = new LoginUserInfo();
        loginUserInfo.setName(giteeUser.getName());
        loginUserInfo.setAvatar_url(giteeUser.getAvatar_url());
        loginUserInfo.setBio(giteeUser.getBio());
        loginUserInfo.setId(giteeUser.getId());
        return loginUserInfo;
    }

    @Override
    public String getSupportedType() {
        return "gitee";
    }
}
