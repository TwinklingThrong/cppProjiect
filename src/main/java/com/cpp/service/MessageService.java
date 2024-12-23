package com.cpp.service;

import com.cpp.pojo.Message;
import com.cpp.pojo.User;

import java.util.List;

public interface MessageService {
    void insertMessage(Message m);

    List<Message> selectListMessage(String f, String s);

    User SelectUserById(String userIdF);

    List<User> selectChatUserListByMyUserId(Integer f);
}
