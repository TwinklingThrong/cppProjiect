package com.cpp.controller;

import com.cpp.pojo.Message;
import com.cpp.pojo.Result;
import com.cpp.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class MessageController {
    @Autowired
    private MessageService messageService;
    @GetMapping("/message/{f}/{s}")
    public Result getMessage(@PathVariable String f, @PathVariable String s) {
        log.info("f:{},s:{}", f, s);
        List<Message> messages = messageService.selectListMessage(f, s);
        return Result.success(messages);
    }

}
