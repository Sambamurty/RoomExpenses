package com.room.expenses.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.room.expenses.entity.RoomList;

@Repository
public interface RoomListRepo extends JpaRepository<RoomList, Integer>{

	RoomList findByRoomName(String tableName);

	void deleteByRoomId(Integer roomId);

	RoomList findByRoomId(Integer roomId);

}
