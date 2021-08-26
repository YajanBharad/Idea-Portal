package com.ideaportal.dto;

import com.ideaportal.models.Ideas;
import com.ideaportal.models.User;

public class ParticipantDTO {

	private long responseId;
	private Ideas idea;
	private User user;
	
	
	public ParticipantDTO() {
	}
	
	
	public ParticipantDTO(long responseId,Ideas idea, User user) {
		this.responseId=responseId;
		this.idea = idea;
		this.user = user;
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


	public long getResponseId() {
		return responseId;
	}


	public void setResponseId(long responseId) {
		this.responseId = responseId;
	}
	
	
}
