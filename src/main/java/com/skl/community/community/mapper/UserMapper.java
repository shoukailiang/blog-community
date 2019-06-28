package com.skl.community.community.mapper;

import com.skl.community.community.model.User;
import org.apache.ibatis.annotations.*;
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

  @Select("select * from user where account_id=#{accountId}")
  User findByAccountId(@Param("accountId") String accountId);

  @Update("update user set name = #{name},token=#{token},gmt_modified=#{gmtModified},avatar_url=#{avatarUrl} where id =#{id}")
  void update(User user);
}
