package com.cpp.ws;

import com.cpp.pojo.Message;
import com.cpp.pojo.User;
import com.cpp.service.MessageService;
import com.cpp.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Slf4j
@Component
public class MyWebSocketHandler extends TextWebSocketHandler {
    MessageService messageService = SpringContextUtil.getBean(MessageService.class);
    String f;
    String s;
    User fi;
    User si;

    private static final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    //private final UriTemplateHandler uriTemplateHandler = new DefaultUriTemplateHandler();
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String uri = session.getUri().toString();
        sessions.put(session.getId(), session);
        System.out.println(uri);
        int last = uri.lastIndexOf('/');
        int lastSecond = uri.lastIndexOf('/',last-1);
        f = uri.substring(lastSecond+1,last);
        s = uri.substring(last+1);
        fi = messageService.SelectUserById(f);
        si = messageService.SelectUserById(s);
        System.out.println(f+""+s);
        messageService.SelectUserById(String.valueOf(f));
        List<Message> messages = messageService.selectListMessage(f, s);
        for (Message m : messages) {
            log.info("Message: {}", m.getMessage());
            try {
                String ms;
                if (Integer.parseInt(f) == m.getUserIdF() ){
                    ms = fi.getUsername() +":"+m.getMessage();
                }else {
                    ms = si.getUsername() +":"+m.getMessage();
                }
                session.sendMessage(new TextMessage(ms));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        log.info("用户 {} 连接建立", f);

    }
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 处理接收到的消息
        try {
            log.info("收到消息: {}", message);
            Message m = new Message();
            m.setUserIdF(Integer.parseInt(f));
            m.setUserIdS(Integer.parseInt(s));
            //声明MessageService
            //MessageService messageService = SpringContextUtil.getBean(MessageService.class);
            String ms;
            if (Integer.parseInt(f) == m.getUserIdF() ){
                ms = fi.getUsername() +":"+message.getPayload();
            }else {
                ms = si.getUsername() +":"+message.getPayload();
            }
            m.setMessage(message.getPayload());
            messageService.insertMessage(m);
            //session.sendMessage(new TextMessage(ms));
            broadcast(new TextMessage(ms));
        } catch (Exception e) {
            log.error("发送消息失败", e);
        }
    }
    public static void broadcast(TextMessage message) {
        for (WebSocketSession session : sessions.values()) {
            try {

                session.sendMessage(message);
            } catch (IOException e) {
                log.error("广播消息失败", e);
            }
        }
    }
}
