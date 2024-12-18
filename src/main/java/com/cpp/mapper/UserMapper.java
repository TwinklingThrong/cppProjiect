package com.cpp.mapper;

import com.cpp.pojo.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into users(username, password, email, phone, image, role, gender) " +
            "values(#{username}, #{password}, #{email}, #{phone}, #{image}, #{role}, #{gender})")
    void insert(User user);
    @Select("select * from users where username = #{username} and password = #{password}")
    User loginByUserNamePassword(User user);

    void updataById(User user);

    @Select("select * from users where id = #{id}")
    User getById(Integer id);
    @Select("select * from users where phone = #{username} and password =#{password}")
    User loginByPhonePassword(User user);
}
