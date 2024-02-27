package com.websocket.demo.config;

import com.websocket.demo.chat.ChatMessage;
import com.websocket.demo.chat.MessageType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.time.LocalDate;
import java.time.LocalTime;
import  java.time.format.DateTimeFormatter;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {
    private final SimpMessageSendingOperations messageSendingOperations;
    public static int OnlineUsers = 0;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("username");
        OnlineUsers--;
        if (username != null) {
            var chatMessage = ChatMessage.builder()
                    .type(MessageType.LEAVE)
                    .content("has left the chat")
                    .sender(username)
                    .OnlineUsers(OnlineUsers)
                    .timestamp(LocalTime.now().format(DateTimeFormatter.ofPattern("h:mm:ss a")))
                    .build();

            messageSendingOperations.convertAndSend("/topic/public", chatMessage);
        }
        messageSendingOperations.convertAndSend("/topic/numUser", OnlineUsers);
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event){
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//        String username = (String) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("username");
        OnlineUsers++;

//        if (username != null) {
//            var chatMessage = ChatMessage.builder()
//                    .type(MessageType.JOIN)
//                    .sender(username)
//                    .OnlineUsers(OnlineUsers)
//                    .build();
//
//            messageSendingOperations.convertAndSend("/topic/public", chatMessage);
//        }
        messageSendingOperations.convertAndSend("/topic/numUser", OnlineUsers);
    }


}
