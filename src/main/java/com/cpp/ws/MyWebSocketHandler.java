package com.cpp.ws;
import com.cpp.pojo.Message;
import com.cpp.pojo.User;
import com.cpp.service.MessageService;
import com.cpp.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
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
    private static final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    UriHandle urlHandle = new UriHandle();
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //拿到id，找到对应的对象
        String uri = session.getUri().toString();
        urlHandle.chuLi(uri);
        String f = urlHandle.getFid();
        String s = urlHandle.getSid();
        User fi = messageService.SelectUserById(f);
        User si = messageService.SelectUserById(s);
        //存起来
        String sessionId = f+":"+s+":"+session.getId();
        sessions.put(sessionId,session);
        //找到消息，然后合成
        List<Message> messages = messageService.selectListMessage(f,s);
        for (Message m : messages) {
            log.info("Message: {}", m.getMessage());
            try {
                String ms;
                if (Integer.parseInt(f) == m.getUserIdF()) {
                    ms = fi.getUsername() + ":" + m.getMessage();
                } else {
                    ms = si.getUsername() + ":" + m.getMessage();
                }
                session.sendMessage(new TextMessage(ms));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String uri = session.getUri().toString();
        urlHandle.chuLi(uri);
        String f = urlHandle.getFid();
        String s = urlHandle.getSid();

        User fi = messageService.SelectUserById(f);
        User si = messageService.SelectUserById(s);

        //存起来
        Message mg = new Message();
        mg.setMessage(message.getPayload());
        mg.setUserIdF(Integer.parseInt(f));
        mg.setUserIdS(Integer.parseInt(s));
        messageService.insertMessage(mg);
        String ms;

            if (Integer.parseInt(f)==fi.getId()){
                ms = fi.getUsername() +":"+message.getPayload();
            }else {
                ms = si.getUsername() +":"+message.getPayload();
            }
            sendToUsers(f,s,new TextMessage(ms));
        }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 移除关闭的会话
        sessions.values().removeIf(wsSession -> wsSession.getId().equals(session.getId()));
        log.info("用户会话关闭");
    }
    private void sendToUsers(String user1, String user2, TextMessage message) {
        for (WebSocketSession session : sessions.values()) {
            //sessions.values()获得Map的值的集合
            try {
                String uri = session.getUri().toString();
                  urlHandle.chuLi(uri);
                  String f = urlHandle.getFid();
                  String s = urlHandle.getSid();
                // 检查是否是目标用户之一
                if ((f.equals(user1) && s.equals(user2)) || (f.equals(user2) && s.equals(user1))) {
                    if (session.isOpen()) {
                        session.sendMessage(message);
                    } else {
                        log.warn("会话已关闭，跳过发送消息: {}", session.getId());
                    }
                }
            } catch (IOException e) {
                log.error("广播消息失败", e);
            }
        }
    }}


