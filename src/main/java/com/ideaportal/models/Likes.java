package com.ideaportal.models;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Likes {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="like_id")
	private long likeId;
	
	@Column(name="like_value",nullable = false, columnDefinition = "TINYINT(1)")
	private boolean likeValue;
	@OneToOne
	@JoinColumn(name="idea_id")
	private Ideas idea;
	@OneToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "like_date", nullable = false)
	private Date likedDate;
	
	
	public boolean getLikeValue() {
		return likeValue;
	}


	public Likes() {

	}


	public Likes(long likeId, Boolean likeValue, Ideas idea, User user, Date likedDate) {
		this.likeId = likeId;
		this.likeValue = likeValue;
		this.idea = idea;
		this.user = user;
		this.likedDate = likedDate;
	}


	public long getLikeId() {
		return likeId;
	}


	public void setLikeId(long likeId) {
		this.likeId = likeId;
	}


	public Boolean   isLikeValue() {
		return likeValue;
	}


	public void setLikeValue(Boolean likeValue) {
		this.likeValue = likeValue;
	}
	public Boolean getLikeValue(Boolean likeValue) {
		return likeValue;
	}


	public Ideas getIdea() {
		return idea;
	}


	public void setIdea(Ideas idea) {
		this.idea = idea;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Date getLikedDate() {
		return likedDate;
	}


	public void setLikedDate(Date likedDate) {
		this.likedDate = likedDate;
	}


	@Override
	public String toString() {
		return "Likes [likeId=" + likeId + ", likeValue=" + likeValue + ", idea=" + idea + ", user=" + user
				+ ", likedDate=" + likedDate + "]";
	}
	
	
}