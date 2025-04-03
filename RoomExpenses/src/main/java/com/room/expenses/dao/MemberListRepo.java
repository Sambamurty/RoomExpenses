package com.room.expenses.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.room.expenses.entity.MemberList;

import feign.Param;

@Repository
public interface MemberListRepo extends JpaRepository<MemberList, Integer>{

	List<MemberList> findByRoomId(Integer roomId);

	MemberList findByPersonNameAndRoomId(String name, Integer roomId);

	@Query("SELECT e.personId FROM MemberList e WHERE roomId = :roomId")
	List<Integer> getPersonIdsByRoomId(@Param("roomId")Integer roomId);

	@Query("SELECT e.personName FROM MemberList e WHERE personId = :personId")
	String findPersonNameByPersonId(@Param("personId") Integer personId);

	void deleteByRoomId(Integer roomId);

}
