package com.room.expenses.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.room.expenses.RoomExpensesApplication;
import com.room.expenses.dao.ExpensesRepo;
import com.room.expenses.dao.MemberListRepo;
import com.room.expenses.dao.RoomListRepo;
import com.room.expenses.entity.Expenses;
import com.room.expenses.entity.MemberList;
import com.room.expenses.entity.RoomList;

import jakarta.transaction.Transactional;

@Service
public class ResponseService {

	
	@Autowired
	private RoomListRepo roomListRepo;
	
	@Autowired
	private MemberListRepo memberListRepo;
	
	@Autowired
	private ExpensesRepo expensesRepo;
	
	private Integer getRoomIdByRoomName(String roomName)
	{
		return roomListRepo.findByRoomName(roomName).getRoomId();
	}
	
	private Integer getPersonIdByNameAndRoomId(String personName,Integer roomId)
	{
		return memberListRepo.findByPersonNameAndRoomId(personName,roomId).getPersonId();
	}

	public ResponseEntity<String> createRoom(Map<String, Object> response) {
		String tableName = (String) response.get("tableName");
		List<String> names = (List<String>) response.get("names");
		RoomList isExistingOrNot = roomListRepo.findByRoomName(tableName);
		if(isExistingOrNot == null) {
			//creating room--
			RoomList roomList = new RoomList();
			roomList.setRoomName(tableName);
			roomList.setCountOfMembers(names.size());
			
			//Adding persons to MemberLsit
			List<MemberList> members = names.stream().map(name -> {
				MemberList memberList = new MemberList();
				memberList.setPersonName(name);
				memberList.setRoomList(roomList);
				return memberList;
			}).toList();
			
			roomList.setMembers(members);
			roomListRepo.save(roomList);
			
			return ResponseEntity.ok("Success");
		}
		
		
		return ResponseEntity.badRequest().body("Room Already Exist with RoomName");
	
	}

	public ResponseEntity<List<RoomList>> getListOfRooms() {
		List<RoomList> all = roomListRepo.findAll();
		return ResponseEntity.ok(all);
	}

	public List<String> getNamesByRoom(String roomName) {
		Integer roomId = getRoomIdByRoomName(roomName);
		List<MemberList> memberList = memberListRepo.findByRoomId(roomId);
		List<String> nameList = memberList.stream().map(ele->ele.getPersonName()).toList();
		return nameList;
	}

	public List<Expenses> getExpensesByRoom(String roomName) {
		Integer roomId = getRoomIdByRoomName(roomName);
		List<Expenses> byRoomId = expensesRepo.findByRoomId(roomId);
		return byRoomId;
	}

	public Map<String, String> getNamesByIds(String roomName) {
		Integer roomId = getRoomIdByRoomName(roomName);
		List<MemberList> roomListById = memberListRepo.findByRoomId(roomId);
		Map<String, String> res = new HashMap<>();
		roomListById.stream().forEach(e->{
			res.put(e.getPersonId().toString(), e.getPersonName());
		});
		
		return res;
	}

	//Filters
	public List<Expenses> getExpensesByRoomAndPerson(String roomName, String name) {
		Integer roomId = getRoomIdByRoomName(roomName);
		Integer personId = getPersonIdByNameAndRoomId(name, roomId);
		List<Expenses> byRoomIdAndPersonId = expensesRepo.findByRoomIdAndPersonId(roomId,personId);
		return byRoomIdAndPersonId;
	}

	public List<Expenses> getExpensesByRoomAndPrice(String roomName, Double floorPrice, Double ceilPrice) {
		Integer roomId = getRoomIdByRoomName(roomName);
		List<Expenses> byRoomIdAndPrice = expensesRepo.findByRoomIdAndPrice(roomId,floorPrice,ceilPrice);
		return byRoomIdAndPrice;
	}

	public List<Expenses> getExpensesByRoomAndBetweenDate(String roomName, String fromDate, String toDate) {
		LocalDateTime from = LocalDateTime.parse(fromDate);
		LocalDateTime to = LocalDateTime.parse(toDate);
		Integer roomId = getRoomIdByRoomName(roomName);
		return expensesRepo.findByRoomIdAndBetweenDates(roomId,from,to);
	}

	public ResponseEntity<String> addExpenses(String personName, String items, Double cost, String roomName) {
	    // Get RoomList object by roomName
	    RoomList roomList = roomListRepo.findByRoomName(roomName);

	    // Get MemberList object by personName and roomId
	    MemberList member = memberListRepo.findByPersonNameAndRoomId(personName, roomList.getRoomId());
	    // Create Expenses object
	    Expenses expenses = new Expenses();
	    expenses.setCost(cost);
	    expenses.setDate(LocalDateTime.now());
	    expenses.setItems(items);
	    expenses.setMember(member); // Set MemberList object
	    expenses.setRoomList(roomList); // Set RoomList object

	    // Save Expenses object
	    expensesRepo.save(expenses);

	    return ResponseEntity.ok("Expenses added successfully");
	}

	public ResponseEntity<Map<String,Double>> getMonthlyToPay(String roomName) {
		Integer roomId = getRoomIdByRoomName(roomName);
		List<Integer> personIdsList = memberListRepo.getPersonIdsByRoomId(roomId);
		List<String> personNamesList = new LinkedList<>();
		personIdsList.stream().forEach(personId->{
			personNamesList.add(memberListRepo.findPersonNameByPersonId(personId));
		});
		
		List<Double> monthAmountAvg = new ArrayList<>();
		for(Integer byId:personIdsList)
		{
			monthAmountAvg.add(expensesRepo.getTotalCostByPersonId(byId));
		}
		
		
		return ResponseEntity.ok(paymentCalculator(personNamesList,monthAmountAvg));
	}

	private Map<String,Double> paymentCalculator(List<String> personNamesList,List<Double> monthAmountAvg)
	{
		Map<String, Double> res = new HashMap<>();
		int size = monthAmountAvg.size();
		for(int i=0;i<size-1;i++)
		{
			for(int j=i+1;j<size;j++)
			{
				Double iAvg = monthAmountAvg.get(i)/size;
				Double jAvg = monthAmountAvg.get(j)/size;
				String whomToWhom = null;
				if(iAvg > jAvg)
				{
					whomToWhom = personNamesList.get(j)+" should pay to "+personNamesList.get(i);
					res.put(whomToWhom, iAvg-jAvg);
				}
				else if(jAvg > iAvg)
				{
					whomToWhom = personNamesList.get(i)+" should pay to "+personNamesList.get(j);
					res.put(whomToWhom, jAvg-iAvg);
				}
			}
		}
		return res;
	}

	@Transactional
	public ResponseEntity<String> deleteRoom(String roomName) {
		Integer roomId = getRoomIdByRoomName(roomName);
		expensesRepo.deleteByRoomId(roomId);
		memberListRepo.deleteByRoomId(roomId);
		roomListRepo.deleteByRoomId(roomId);
		return ResponseEntity.ok("Successfully deleted.");
	}

	@Transactional
	public ResponseEntity<Map<String, String>> deleteRecord(String personName, String date, Integer roomId) {
		String roomName = roomListRepo.findByRoomId(roomId).getRoomName();
		Integer personId = memberListRepo.findByPersonNameAndRoomId(personName, roomId).getPersonId();
		Map<String,String> response = new HashMap<>();
		LocalDateTime atDate = LocalDateTime.parse(date);
		response.put("roomName", roomName);
		expensesRepo.deleteByPersonIdAndDateAndRoomId(personId,atDate,roomId);
		return ResponseEntity.ok(response);
	}
	
	
}
