package com.room.expenses.entity;



import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "memberlist")
public class MemberList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "personid")
    private Integer personId;

    @Column(name = "personname")
    private String personName;
    
    @Column(name = "roomid" ,insertable = false, updatable = false)
    private Integer roomId;

    @ManyToOne
    @JoinColumn(name = "roomid", nullable = false)
    @JsonIgnore
    private RoomList roomList;

	public MemberList() {
		super();
	}

	public MemberList(Integer personId, String personName, Integer roomId, RoomList roomList) {
		super();
		this.personId = personId;
		this.personName = personName;
		this.roomId = roomId;
		this.roomList = roomList;
	}

	public Integer getPersonId() {
		return personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public RoomList getRoomList() {
		return roomList;
	}

	public void setRoomList(RoomList roomList) {
		this.roomList = roomList;
	}
    
    

}
