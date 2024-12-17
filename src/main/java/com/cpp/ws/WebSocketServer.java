package com.cpp.ws;

import com.cpp.config.WebsocketConfig;
import com.cpp.pojo.Message;
import com.cpp.pojo.User;
import com.cpp.service.MessageService;
import com.cpp.utils.SpringContextUtil;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint(value = "/chat/{user_id_F}/{user_id_S}")
@Slf4j
public class WebSocketServer {

    private static final Map<String, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("user_id_F") String userIdF, @PathParam("user_id_S") String userIdS) {
        sessions.put(session.getId(), session);
        WebsocketConfig websocketConfig = SpringContextUtil.getBean(WebsocketConfig.class);
        MessageService messageService = SpringContextUtil.getBean(MessageService.class);

        List<Message> messages = messageService.selectListMessage(userIdF, userIdS);
        for (Message m : messages) {
            log.info("Message: {}", m.getMessage());
            try {
                session.getBasicRemote().sendText(m.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        log.info("用户 {} 连接建立", userIdF);
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session.getId());
        log.info("用户 {} 连接关闭", session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session, @PathParam("user_id_F") String userIdF, @PathParam("user_id_S") String userIdS) {
        try {
            log.info("收到消息: {}", message);
            Message m = new Message();
            m.setUserIdF(Integer.parseInt(userIdF));
            m.setUserIdS(Integer.parseInt(userIdS));
            MessageService messageService = SpringContextUtil.getBean(MessageService.class);
            String i = messageService.SelectUserById(userIdF).getUsername();
            m.setMessage(i+":"+message);
            messageService.insertMessage(m);
            broadcast(m.getMessage());
        } catch (Exception e) {
            log.error("发送消息失败", e);
        }
    }

    public static void broadcast(String message) {
        for (Session session : sessions.values()) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                log.error("广播消息失败", e);
            }
        }
    }
}
