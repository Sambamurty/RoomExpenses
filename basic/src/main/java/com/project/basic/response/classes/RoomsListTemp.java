package com.project.basic.response.classes;

public class RoomsListTemp {
	private Integer roomId;
	private String roomName;
	private Integer countOfMembers;
	
	public RoomsListTemp(Integer roomId, String roomName, Integer countOfMembers) {
		super();
		this.roomId = roomId;
		this.roomName = roomName;
		this.countOfMembers = countOfMembers;
	}
	@Override
	public String toString() {
		return "RoomsListTemp [roomId=" + roomId + ", roomName=" + roomName + ", countOfMembers=" + countOfMembers
				+ "]";
	}
	public Integer getRoomId() {
		return roomId;
	}
	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public Integer getCountOfMembers() {
		return countOfMembers;
	}
	public void setCountOfMembers(Integer countOfMembers) {
		this.countOfMembers = countOfMembers;
	}
	
}
