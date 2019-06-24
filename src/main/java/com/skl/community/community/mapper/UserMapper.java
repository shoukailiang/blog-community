package com.skl.community.community.mapper;

import com.skl.community.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {
  @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,avatar_url) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
  void insert(User user);

  @Select("select * from user where token=#{token}")
  User findByToken(@Param("token") String token);// 参数不是类的时候，需要前面加上一个注解，会把token放入。上面是一个类，所以不需要一个注解

  @Select("select * from user where id=#{id}")
  User findById(@Param("id") Integer id);
}
