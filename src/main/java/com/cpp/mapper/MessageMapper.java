package com.cpp.mapper;

import com.cpp.pojo.Message;
import com.cpp.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageMapper {
    @Insert("insert into messages(user_id_F, user_id_S, message) values ( #{userIdF},#{userIdS},#{message})")
    void insertMessage(Message m);
    @Select("select *from messages where user_id_S=#{s} and user_id_F = #{f} or user_id_F = #{s} and user_id_S = #{f} order by time")
    List<Message> selectListMessage(String f, String s);

    @Select("select * from users where id in ((select distinct user_id_S from messages where user_id_F = #{id} )Union (select distinct user_id_F from messages where user_id_S = #{id} ))")
    List<User> selectChatUserListByMyUserId(Integer f);
}
