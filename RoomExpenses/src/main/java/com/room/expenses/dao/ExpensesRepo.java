package com.room.expenses.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.room.expenses.entity.Expenses;

import feign.Param;

@Repository
public interface ExpensesRepo extends JpaRepository<Expenses, Integer>{

	List<Expenses> findByRoomId(Integer roomId);

	List<Expenses> findByRoomIdAndPersonId(Integer roomId, Integer personId);

	@Query("SELECT e FROM Expenses e WHERE e.roomId = :roomId AND e.cost BETWEEN :floorPrice AND :ceilPrice")
	List<Expenses> findByRoomIdAndPrice(@Param("roomId") Integer roomId,
			@Param("floorPrice") Double floorPrice,@Param("ceilPrice") Double ceilPrice);

	@Query("SELECT e FROM Expenses e WHERE e.roomId = :roomId AND e.date BETWEEN :from AND :to")
	List<Expenses> findByRoomIdAndBetweenDates(@Param("roomId") Integer roomId,
			@Param("from") LocalDateTime from,@Param("to") LocalDateTime to);

	@Query("SELECT SUM(e.cost) FROM Expenses e WHERE e.personId = :byId")
	Double getTotalCostByPersonId(@Param("byId") Integer byId);

	void deleteByRoomId(Integer roomId);

	void deleteByPersonIdAndDateAndRoomId(Integer personId, LocalDateTime atDate, Integer roomId);

}
