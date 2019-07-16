package com.skl.community.community.provider;

import com.alibaba.fastjson.JSON;
import com.skl.community.community.dto.AccessTokenDTO;
import com.skl.community.community.dto.GithubUser;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class GithubProvider {
  public String getAccessToken(AccessTokenDTO accessTokenDto){
    MediaType mediaType = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();
    // 这里需要把类转出json，用到一个jar包fastjson
    RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDto));// 吧类对象转出json
    Request request = new Request.Builder()
        .url("https://github.com/login/oauth/access_token")
        .post(body)
        .build();
    try (Response response = client.newCall(request).execute()) {
      String string = response.body().string();
      //System.out.println(string);
      //access_token=d6dea89e60050e5ce1779e47b6b253f938dd1cd5&scope=user&token_type=bearer
      String[] split = string.split("&");
      String token = split[0].split("=")[1];
      return token;
    } catch (Exception e) {
      e.printStackTrace();
      log.error("getAccessToken error,{}", accessTokenDto, e);
    }
    return null;
  }

  public GithubUser getGithubUser(String accessToken) {
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder()
        .url("https://api.github.com/user?access_token="+accessToken)
        .build();
    try {
      Response response = client.newCall(request).execute();
      String string = response.body().string();
      GithubUser githubUser = JSON.parseObject(string, GithubUser.class);// 可以吧string自动转成java类对象
      return githubUser;
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e){
      log.error("getUser error,{}", accessToken, e);
    }
    return null;
  }
}
