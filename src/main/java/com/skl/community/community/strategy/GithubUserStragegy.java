package com.skl.community.community.strategy;

import com.skl.community.community.dto.AccessTokenDTO;
import com.skl.community.community.provider.GithubProvider;
import com.skl.community.community.provider.dto.GithubUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shoukailiang
 * @version 1.0
 * @date 2021/7/6 22:16
 */

@Service
public class GithubUserStragegy implements UserStrategy{
    @Autowired
    private GithubProvider githubProvider;

    @Override
    public LoginUserInfo getUser(String code, String state) {
        AccessTokenDTO accessTokenDto = new AccessTokenDTO();
        accessTokenDto.setCode(code);
        accessTokenDto.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDto);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        LoginUserInfo loginUserInfo = new LoginUserInfo();
        loginUserInfo.setName(githubUser.getName());
        loginUserInfo.setAvatar_url(githubUser.getAvatar_url());
        loginUserInfo.setBio(githubUser.getBio());
        loginUserInfo.setId(githubUser.getId());
        return loginUserInfo;
    }

    @Override
    public String getSupportedType() {
        return "github";
    }
}
