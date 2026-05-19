package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tasks")
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "category_id")
	private Integer categoryId;

	@Column(name = "userId")
	private Integer userId;

	private String title;

	private LocalDate deadline;

	private Integer importance;

	private Integer routine;

	private String memo;

	@Column(name = "is_today")
	private Boolean isToday;

	public Task() {

	}

	//	public Task(Integer categoryId, String title, LocalDate deadline, Integer importance,
	//			Integer routine, String memo) {
	//
	//		this.categoryId = categoryId;
	//		this.title = title;
	//		this.deadline = deadline;
	//		this.importance = importance;
	//		this.routine = routine;
	//		this.memo = memo;
	//	}

	public Task(Integer categoryId, String title, LocalDate deadline, Integer importance,
			Integer routine, String memo, Boolean isToday) {

		this.categoryId = categoryId;
		this.title = title;
		this.deadline = deadline;
		this.importance = importance;
		this.routine = routine;
		this.memo = memo;
		this.isToday = isToday;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}

	public Integer getImportance() {
		return importance;
	}

	public void setImportance(Integer importance) {
		this.importance = importance;
	}

	public Integer getRoutine() {
		return routine;
	}

	public void setRoutine(Integer routine) {
		this.routine = routine;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Boolean getIsToday() {
		return isToday;
	}

	public void setIsToday(Boolean isToday) {
		this.isToday = isToday;
	}

}