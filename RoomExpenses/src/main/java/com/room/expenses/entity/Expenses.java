package com.room.expenses.entity;


import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "expenses")
public class Expenses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer entryId;

    private Double cost;
    
    private LocalDateTime date;
    
    private String items;
    
    @Column(name = "personid" ,insertable = false, updatable = false)
    private Integer personId;
    
    @Column(name = "roomid" ,insertable = false, updatable = false)
    private Integer roomId;

    @ManyToOne
    @JoinColumn(name = "personid", nullable = false)
    @JsonIgnore
    private MemberList member;

    @ManyToOne
    @JoinColumn(name = "roomid", nullable = false)
    @JsonIgnore
    private RoomList roomList;

    
	public Expenses() {
		super();
	}

	public Expenses(Integer entryId, Double cost, LocalDateTime date, String items, Integer personId, Integer roomId) {
		super();
		this.entryId = entryId;
		this.cost = cost;
		this.date = date;
		this.items = items;
		this.personId = personId;
		this.roomId = roomId;
	}

	public Integer getEntryId() {
		return entryId;
	}

	public void setEntryId(Integer entryId) {
		this.entryId = entryId;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public Integer getPersonId() {
		return personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public MemberList getMember() {
		return member;
	}

	public void setMember(MemberList member) {
		this.member = member;
	}

	public RoomList getRoomList() {
		return roomList;
	}

	public void setRoomList(RoomList roomList) {
		this.roomList = roomList;
	}

    
}

