package com.cpp.controller;

import com.cpp.pojo.Message;
import com.cpp.pojo.Result;
import com.cpp.pojo.User;
import com.cpp.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/message")
@Slf4j
public class MessageController {
    @Autowired
    private MessageService messageService;
    //收发信息，读取信息（废弃）
    @GetMapping("/{f}/{s}")
    public Result getMessage(@PathVariable String f, @PathVariable String s) {
        log.info("f:{},s:{}", f, s);
        List<Message> messages = messageService.selectListMessage(f, s);
        return Result.success(messages);
    }
    @GetMapping("/chat/{f}")
    public Result selectChatUserListByMyUserId(@PathVariable Integer f){
        List<User> users = messageService.selectChatUserListByMyUserId(f);
        return Result.success(users);
    }

}
