package com.yash.chat_app.controller;

import com.yash.chat_app.entities.Message;
import com.yash.chat_app.entities.Room;
import com.yash.chat_app.payload.MessageRequest;
import com.yash.chat_app.repositories.RoomRepositories;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@CrossOrigin("http://localhost:5173")
public class ChatController {

    private RoomRepositories roomRepositories;

    public ChatController(RoomRepositories roomRepositories){
        this.roomRepositories = roomRepositories;
    }

    @MessageMapping("/sendMessage/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public Message sendMessage(
            @DestinationVariable String roomId,
            @RequestBody MessageRequest request
    ) throws Exception {
        Room room = roomRepositories.findByRoomId(roomId);

        Message message = new Message();

        message.setContent(request.getContent());
        message.setSender(request.getSender());
        message.setTime(LocalDateTime.now());

        if(room != null){
            room.getMessages().add(message);
            roomRepositories.save(room);
        }else{
            throw new Exception("Room not found!");
        }
        return message;
    }
}
