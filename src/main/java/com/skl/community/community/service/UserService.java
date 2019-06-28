package com.skl.community.community.service;


import com.skl.community.community.mapper.UserMapper;
import com.skl.community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  @Autowired
  private UserMapper userMapper;

  public void createOrUpdate(User user) {
    User dbUser = userMapper.findByAccountId(user.getAccountId());
    if(dbUser==null){
      // 插入
      user.setGmtCreate(System.currentTimeMillis());
      user.setGmtModified(user.getGmtCreate());
      userMapper.insert(user);

    }else {
      //跟新
      dbUser.setGmtModified(System.currentTimeMillis());
      // 头像可能会变化
      dbUser.setAvatarUrl(user.getAvatarUrl());
      dbUser.setName(user.getName());
      dbUser.setToken(user.getToken());
      userMapper.update(dbUser);
    }
  }
}
