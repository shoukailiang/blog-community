package com.skl.community.community.dto;

import lombok.Data;

/**
 * https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/
 * 对应 第二步  2. Users are redirected back to your site by GitHub
 */
@Data
public class AccessTokenDto {
  private String client_id;
  private String client_secret;
  private String code;
  private String redirect_uri;
  private String state;
}
