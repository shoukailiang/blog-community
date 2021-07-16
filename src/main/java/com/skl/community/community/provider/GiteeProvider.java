package com.skl.community.community.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.skl.community.community.dto.AccessTokenDTO;
import com.skl.community.community.provider.dto.GiteeUser;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class GiteeProvider {

    @Value("${gitee.client.id}")
    private String clientId;

    @Value("${gitee.client.secret}")
    private String clientSecret;

    @Value("${gitee.redirect.uri}")
    private String redirectUri;

    public String getAccessToken(AccessTokenDTO accessTokenDto) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        // 这里需要把类转出json，用到一个jar包fastjson
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDto));// 吧类对象转出json
        String url = "https://gitee.com/oauth/token?grant_type=authorization_code&code=%s&client_id=%s&redirect_uri=%s&client_secret=%s";
        url = String.format(url, accessTokenDto.getCode(), clientId, redirectUri, clientSecret);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            JSONObject jsonObject = JSON.parseObject(string);
            return jsonObject.getString("access_token");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getAccessToken error,{}", accessTokenDto, e);
        }
        return null;
    }

    public GiteeUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://gitee.com/api/v5/user?access_token=" + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GiteeUser giteeUser = JSON.parseObject(string, GiteeUser.class);// 可以吧string自动转成java类对象
            return giteeUser;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            log.error("getUser error,{}", accessToken, e);
        }
        return null;
    }
}
