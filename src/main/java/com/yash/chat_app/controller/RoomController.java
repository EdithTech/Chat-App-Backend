package com.yash.chat_app.controller;

import com.yash.chat_app.entities.Message;
import com.yash.chat_app.entities.Room;
import com.yash.chat_app.repositories.RoomRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@CrossOrigin("http://localhost:3000")
public class RoomController {

    private RoomRepositories roomRepositories;

    public RoomController(RoomRepositories roomRepositories) {
        this.roomRepositories = roomRepositories;
    }

    // create room
    @PostMapping
    public ResponseEntity<?> createRoom(@RequestBody String roomId) {
        if (roomRepositories.findByRoomId(roomId) != null) {
            return ResponseEntity.badRequest().body("Room already existed");
        }

        Room room = new Room();
        room.setRoomId(roomId);
        Room savedRoom = roomRepositories.save(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRoom);
    }

    // get Room : Join
    @GetMapping("/{roomId}")
    public ResponseEntity<?> getRoomId(@PathVariable String roomId) {
        Room room = new Room();

        room = roomRepositories.findByRoomId(roomId);

        if (room == null) {
            return ResponseEntity.badRequest().body("Room not found!!");
        }

        return ResponseEntity.status(200).body(room);
    }

    // Get Messages of Room
    @GetMapping("/{roomId}/messages")
    public ResponseEntity<List<Message>> getRoomMessages(
            @PathVariable String roomId,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "20", required = false) int size
            ) {
        Room room = new Room();
        room = roomRepositories.findByRoomId(roomId);

        if (room == null) {
            return ResponseEntity.badRequest().build();
        }

        List<Message> messagesList = room.getMessages();

        int start = Math.max(0, messagesList.size() - (page+1) * size);
        int end = Math.min(messagesList.size(), start + size);

        List<Message> paginatedMessageList = messagesList.subList(start, end);
        return ResponseEntity.ok(paginatedMessageList);
    }

}
