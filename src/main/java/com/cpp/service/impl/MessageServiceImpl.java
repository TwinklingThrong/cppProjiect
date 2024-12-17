package com.cpp.service.impl;

import com.cpp.mapper.JobMapper;
import com.cpp.mapper.MessageMapper;
import com.cpp.mapper.UserMapper;
import com.cpp.pojo.Message;
import com.cpp.pojo.User;
import com.cpp.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageMapper messageMapper;
    @Autowired
    UserMapper userMapper;

    //发送信息
    @Override
    public void insertMessage(Message m) {
        messageMapper.insertMessage(m);
    }
    //读取信息
    @Override
    public List<Message> selectListMessage(String f, String s) {
        List<Message> list = messageMapper.selectListMessage(f,s);
        for (Message m : list) {
            m.setUserIdFString(userMapper.getById(m.getUserIdF()).getUsername());
            m.setUserIdSString(userMapper.getById(m.getUserIdS()).getUsername());
        }
        return list;

    }
    //获取用户名
    @Override
    public User SelectUserById(String userIdF) {
        return userMapper.getById(Integer.parseInt(userIdF));
    }
}
