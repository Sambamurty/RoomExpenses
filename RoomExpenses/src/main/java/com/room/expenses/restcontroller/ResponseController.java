package com.room.expenses.restcontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.room.expenses.entity.Expenses;
import com.room.expenses.entity.RoomList;
import com.room.expenses.service.ResponseService;

@RestController
public class ResponseController {
	
	@Autowired
	private ResponseService responseService;

	@PostMapping("/createRoom")
	public ResponseEntity<String> createRoom(@RequestBody Map<String,Object> requestBody)
	{
		return responseService.createRoom(requestBody);
	}
	
	@GetMapping("/getListOfRooms")
	public ResponseEntity<List<RoomList>> getListOfRooms()
	{
		return responseService.getListOfRooms();
	}
	
	@PostMapping("/getRoomDetails")
	public ResponseEntity<Map> getRoomDetails(@RequestParam String roomName, @RequestParam(required = false) String name,
			@RequestParam(required = false) String fromDate, @RequestParam(required = false) String toDate,
			@RequestParam(required = false) Double floorPrice, @RequestParam(required = false) Double ceilPrice)
	{
		Map<String, Object> result = new HashMap<>();
		List<String> namesList = responseService.getNamesByRoom(roomName);
		List<Expenses> expenses = new ArrayList<>();
		Map<String, String> namesByIds = responseService.getNamesByIds(roomName);
		
		if(name != null){
			expenses = responseService.getExpensesByRoomAndPerson(roomName, name);
		}else if(fromDate != null && toDate != null){
			expenses = responseService.getExpensesByRoomAndBetweenDate(roomName, fromDate, toDate);
		}else if(floorPrice != null && ceilPrice != null) {
			expenses = responseService.getExpensesByRoomAndPrice(roomName, floorPrice, ceilPrice);
		}else {
			expenses = responseService.getExpensesByRoom(roomName);
		}
		
		result.put("namesList", namesList);
		result.put("roomName", roomName);
		result.put("expensesByRoom", expenses);
		result.put("namesByIds", namesByIds);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/getRoomDetails/addExpenses")
	public ResponseEntity<String> addExpenses(@RequestParam String personName, @RequestParam String items,
			@RequestParam Double cost, @RequestParam String roomName)
	{
		return responseService.addExpenses(personName,items,cost,roomName);
	}
	
	@GetMapping("/getRoomDetails/getMonthlyToPay")
	public ResponseEntity<Map<String,Double>> getMonthlyToPay(@RequestParam String roomName) {
		
		return responseService.getMonthlyToPay(roomName);
	}
	
	@DeleteMapping("/deleteRoom")
	public ResponseEntity<String> deleteRoom(@RequestParam String roomName)
	{
		return responseService.deleteRoom(roomName);
	}
	
	@DeleteMapping("/deleteRecord")
	public ResponseEntity<Map<String, String>> deleteRecord(@RequestParam String personName, @RequestParam String date,
			@RequestParam Integer roomId){
		
		return responseService.deleteRecord(personName,date,roomId);
	}
}
