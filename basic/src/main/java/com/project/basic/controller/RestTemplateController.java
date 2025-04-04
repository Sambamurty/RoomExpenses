package com.project.basic.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.project.basic.openfeign.RoomExpensesResponse;
import com.project.basic.response.classes.RoomsListTemp;

@Controller
@RequestMapping("/home")
public class RestTemplateController {
	
	@Autowired
	private RoomExpensesResponse roomExpensesResponse;

//	private RestTemplate restTemplate = new RestTemplate();
//	String baseUrl = "http://localhost:8000/";

	@GetMapping("/createRoom")
	public String createRoom() {
		return "createRoom";
	}

	@PostMapping("/createRoom")
	public String createRoomPost(@RequestParam("tableName") String roomName, @RequestParam List<String> names,
			Model model) {
		try {

			Map<String, Object> requestBody = new HashMap<>();
			requestBody.put("tableName", roomName);
			requestBody.put("names", names);

//			String url = baseUrl + "/createRoom";
//			ResponseEntity<String> response = restTemplate.postForEntity(url, requestBody, String.class);

			ResponseEntity<String> response = roomExpensesResponse.createRoom(requestBody);
			// Check the response status and redirect accordingly
			if (response.getStatusCode() == HttpStatus.OK) {
				return "redirect:/home/getListOfRooms";
			} else {
				model.addAttribute("error", "Failed to create the room. Try again!");
				return "redirect:/home/createRoom";
			}
		} catch (RestClientException e) {
			// Handle exceptions like connection issues
			model.addAttribute("error", "Error: " + e.getMessage());
			return "redirect:/home/createRoom";
		}
	}

	@GetMapping("/getListOfRooms")
	public String getListOfRooms(Model model) {
		try {
			// Define the REST API endpoint
//			String url = baseUrl + "/getListOfRooms"; // Update to match the REST service URL

			// Send a GET request to the REST API and retrieve the response
//			ResponseEntity<List<RoomsListTemp>> response = restTemplate.exchange(url, HttpMethod.GET, null,
//					new ParameterizedTypeReference<List<RoomsListTemp>>() {
//					});
			
			ResponseEntity<List<RoomsListTemp>> response = roomExpensesResponse.getListOfRooms();

			// Check if the response is successful
			if (response.getStatusCode() == HttpStatus.OK) {
				List<RoomsListTemp> listOfRooms = response.getBody();
				model.addAttribute("listOfRooms", listOfRooms); // Add the data to the Model
			} else {
				model.addAttribute("error", "Failed to retrieve the list of rooms.");
			}

		} catch (RestClientException e) {
			model.addAttribute("error", "Error: " + e.getMessage());
		}

		return "displayListOfRooms"; // Return the view name
	}

	@GetMapping("/getRoomDetails")
	public String getRoomDetails(@RequestParam String roomName, @RequestParam(required = false) String name,
			@RequestParam(required = false) String fromDate, @RequestParam(required = false) String toDate,
			@RequestParam(required = false) Double floorPrice, @RequestParam(required = false) Double ceilPrice,
			Model model) {
		try {
			// Build the API URL dynamically
//			StringBuilder urlBuilder = new StringBuilder(baseUrl + "/getListOfRooms/roomDetails");
//			urlBuilder.append("?roomName=").append(roomName);
//			if (name != null && !name.isEmpty()) {
//				urlBuilder.append("&name=").append(name);
//			}
//			if (fromDate != null && toDate != null) {
//				urlBuilder.append("&fromDate=").append(fromDate);
//				urlBuilder.append("&toDate=").append(toDate);
//			}
//			if (floorPrice != null) {
//				urlBuilder.append("&floorPrice=").append(floorPrice);
//			}
//			if (ceilPrice != null) {
//				urlBuilder.append("&ceilPrice=").append(ceilPrice);
//			}

			// Convert the URL to a string
//			String url = urlBuilder.toString();

			// Use RestTemplate to make the GET request
//			HttpHeaders headers = new HttpHeaders();
//			headers.setContentType(MediaType.APPLICATION_JSON);
//
//			HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
//			ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);
			// Check if the response is valid
			ResponseEntity<Map> response = roomExpensesResponse.getRoomDetails(roomName, name, fromDate, toDate, floorPrice, ceilPrice);
			
			if (response.getStatusCode().is2xxSuccessful()) {
				Map<String, Object> responseBody = response.getBody();
				// Populate the model object for JSP
				model.addAttribute("namesList", responseBody.get("namesList"));
				model.addAttribute("roomName", responseBody.get("roomName"));
				model.addAttribute("expensesByRoom", responseBody.get("expensesByRoom"));
				model.addAttribute("namesByIds", responseBody.get("namesByIds"));
				return "roomDetails"; // JSP name to render the data
			} else {
				model.addAttribute("error", "Error: Unable to fetch room details.");
				return "errorPage"; // Render error JSP in case of non-200 response
			}

		} catch (Exception e) {
			model.addAttribute("error", "Exception: " + e.getMessage());
			return "errorPage"; // Render error JSP for exceptions
		}
	}

	@PostMapping("/getRoomDetails/addExpenses")
	public String addExpenses(@RequestParam("username") String personName, @RequestParam("itemName") String items,
			@RequestParam Double cost, @RequestParam String roomName) {
		try {
			// Build URL for the REST API
//			String url = baseUrl + "/getListOfRooms/roomDetails/addExpenses";
//			String url = baseUrl + "/getListOfRooms/roomDetails/addExpenses?personName=" + personName + "&items="
//					+ items + "&cost=" + cost + "&roomName=" + roomName;
//	        Map<String, Object> requestBody = new HashMap<>();
//	        requestBody.put("personName", personName);
//	        requestBody.put("items", items);
//	        requestBody.put("cost", cost);
//	        requestBody.put("roomName", roomName);

			// Make the POST request
//			ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

			ResponseEntity<String> response = roomExpensesResponse.addExpenses(personName, items, cost, roomName);
//			HttpHeaders headers = new HttpHeaders();
//			headers.setContentType(MediaType.APPLICATION_JSON);
//
//			HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
//			ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
			// Redirect to the expense details page with success message
			response.getBody();
			return "redirect:/home/getRoomDetails?roomName=" + roomName + "&success=true";

		} catch (Exception e) {
			// Log the error and return the error page
			e.printStackTrace();
			return "errorPage";
		}
	}

	@GetMapping("/getRoomDetails/getMonthlyToPay")
	public String getMonthlyToPay(@RequestParam String roomName, Model model) {
//		String url = baseUrl + "/getListOfRooms/roomDetails/getMonthlyToPay?roomName=" + roomName;
//		ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
		ResponseEntity<Map<String, Double>> response = roomExpensesResponse.getMonthlyToPay(roomName);
		if (response.getStatusCode().is2xxSuccessful()) {
			Map<String, Double> responseBody = response.getBody();
			model.addAttribute("paymentsList", responseBody);
		}
		return "getMonthlyToPay";
	}

	@PostMapping("/deleteRoom")
	public String deleteRoom(@RequestParam("name") String roomName) {

//		String url = baseUrl + "/deleteRoom?name=" + roomName;
//
//		restTemplate.postForEntity(url, null, String.class);
		
		roomExpensesResponse.deleteRoom(roomName);

		return "redirect:/home/getListOfRooms";
	}

	@PostMapping("/deleteRecord")
	public String deleteRecord(@RequestParam String personName, @RequestParam String date,
			@RequestParam Integer roomId) {
//		String url = baseUrl + "/deleteRecord?personName=" + personName
//												+"&date=" + date
//												+"&roomId=" + roomId;
//		System.out.println(personName+"==="+date+"---"+roomId);
//		ResponseEntity<Map> response = restTemplate.postForEntity(url,null , Map.class);
		ResponseEntity<Map<String, String>> response = roomExpensesResponse.deleteRecord(personName, date, roomId);
		String roomName = null;
		if(response.getStatusCode().is2xxSuccessful())
		{
			Map<String, String> responseBody = response.getBody();
			roomName = (String) responseBody.get("roomName");
		}
		return "redirect:/home/getRoomDetails?roomName="+roomName;
	}
	
	
	@GetMapping("/getRoomDetails/filter/getByPerson")
	public String getByPerson(@RequestParam String name, @RequestParam String roomName)
	{
		return "redirect:/home/getRoomDetails?roomName="
				+roomName+"&name="+name;
	}
	
	
	@GetMapping("/getRoomDetails/filter/getBetweenDates")
	public String getBetweenDates(@RequestParam String fromDate,@RequestParam String toDate,@RequestParam String roomName)
	{
		LocalDate fDate = LocalDate.parse(fromDate);
		LocalDate tDate = LocalDate.parse(toDate);
		LocalDateTime fromDateTime = fDate.atStartOfDay();
		LocalDateTime toDateTime = tDate.atStartOfDay();
		return "redirect:/home/getRoomDetails?roomName="+
				roomName+"&fromDate="+fromDateTime+"&toDate="+toDateTime;
	}
	
	@GetMapping("/getRoomDetails/filter/getBetweenPrice")
	public String getBetweenPrice(@RequestParam Double floorPrice,@RequestParam Double ceilPrice,@RequestParam String roomName)
	{
		return "redirect:/home/getRoomDetails?roomName="+
				roomName+"&floorPrice="+floorPrice+"&ceilPrice="+ceilPrice;
	}
}
