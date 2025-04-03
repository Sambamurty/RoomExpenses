package com.project.basic.openfeign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.basic.response.classes.RoomsListTemp;

@FeignClient(name = "roomexpenses-service" ,url = "http://localhost:8081")
public interface RoomExpensesResponse {
	
	@PostMapping("/createRoom")
	public ResponseEntity<String> createRoom(@RequestBody Map<String,Object> requestBody);
	
	@GetMapping("/getListOfRooms")
	public ResponseEntity<List<RoomsListTemp>> getListOfRooms();
	
	@PostMapping("/getRoomDetails")
	public ResponseEntity<Map> getRoomDetails(@RequestParam String roomName, @RequestParam(required = false) String name,
			@RequestParam(required = false) String fromDate, @RequestParam(required = false) String toDate,
			@RequestParam(required = false) Double floorPrice, @RequestParam(required = false) Double ceilPrice);
	
	@GetMapping("/getRoomDetails/addExpenses")
	public ResponseEntity<String> addExpenses(@RequestParam String personName, @RequestParam String items,
			@RequestParam Double cost, @RequestParam String roomName);

	@GetMapping("/getRoomDetails/getMonthlyToPay")
	public ResponseEntity<Map<String,Double>> getMonthlyToPay(@RequestParam String roomName);
	
	@DeleteMapping("/deleteRoom")
	public ResponseEntity<String> deleteRoom(@RequestParam String roomName);
	
	@DeleteMapping("/deleteRecord")
	public ResponseEntity<Map<String, String>> deleteRecord(@RequestParam String personName, @RequestParam String date,
			@RequestParam Integer roomId);
}
