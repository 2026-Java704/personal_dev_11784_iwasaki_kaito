package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "medicine")
public class Medicine {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Integer id;

	private String name;

	private String note;

	private Integer count;

	@Column(name = "m_time")
	private String mtime;

	@Column(name = "m_check")
	private Boolean check;

	@Column(name = "drink_time")
	private String dtime;

	private String around;

	public String getDtime() {
		return dtime;
	}

	public void setDtime(String dtime) {
		this.dtime = dtime;
	}

	public String getAround() {
		return around;
	}

	public void setAround(String around) {
		this.around = around;
	}

	@ManyToOne
	@JoinColumn(name = "users_id")
	private User user;

	public Medicine() {

	}

	public Medicine(String name, Integer count, String note, Boolean mCheck, User id, String dtime, String around) {
		this.name = name;
		this.count = count;
		this.note = note;
		this.check = false;
		this.user = id;
		this.dtime = dtime;
		this.around = around;
	}

	public User getUser() {
		return user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Boolean getCheck() {
		return check;
	}

	public void setCheck(Boolean check) {
		if (check == false) {
			this.check = false;
		} else {
			this.check = true;
		}
	}

	public String getMtime() {
		return mtime;
	}

	public void setMtime(String mtime) {
		this.mtime = mtime;
	}
}
