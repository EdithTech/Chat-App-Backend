package com.yash.chat_app.repositories;

import com.yash.chat_app.entities.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomRepositories extends MongoRepository<Room, String> {
    Room findByRoomId(String roomId);
}
