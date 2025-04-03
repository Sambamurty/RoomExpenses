package com.room.expenses.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "roomlist")
public class RoomList {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roomid")
    private Integer roomId;

    @Column(name = "countofmembers")
    private Integer countOfMembers;
    
    @Column(name = "roomname")
    private String roomName;

    @OneToMany(mappedBy = "roomList", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<MemberList> members;
    
    
    public RoomList() {
		super();
	}

	public RoomList(Integer roomId, Integer countOfMembers, String roomName, List<MemberList> members) {
		super();
		this.roomId = roomId;
		this.countOfMembers = countOfMembers;
		this.roomName = roomName;
		this.members = members;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public Integer getCountOfMembers() {
		return countOfMembers;
	}

	public void setCountOfMembers(Integer countOfMembers) {
		this.countOfMembers = countOfMembers;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public List<MemberList> getMembers() {
		return members;
	}

	public void setMembers(List<MemberList> members) {
		this.members = members;
	}

	

}

