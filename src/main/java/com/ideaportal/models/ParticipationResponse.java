package com.ideaportal.models;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="participationresponse")
public class ParticipationResponse {

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		@Column(name="response_id",nullable=false)
		private long responseId;
		
		
		@ManyToOne
		@JoinColumn(name="user_id")
		private User user;
		
		@ManyToOne
		@JoinColumn(name="idea_id")
		private Ideas idea;
		
		@Basic
		@Temporal(TemporalType.TIMESTAMP)
		@Column(nullable = false)
		private Date participationDate;

		
		
		public ParticipationResponse() {
		}

		public ParticipationResponse(long responseId, User user, Ideas idea, Date participationDate) {
			this.responseId = responseId;
			this.user = user;
			this.idea = idea;
			this.participationDate = participationDate;
		}

		public long getResponseId() {
			return responseId;
		}

		public void setResponseId(long responseId) {
			this.responseId = responseId;
		}


		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public Ideas getIdea() {
			return idea;
		}

		public void setIdea(Ideas idea) {
			this.idea = idea;
		}

		public Date getParticipationDate() {
			return participationDate;
		}

		public void setParticipationDate(Date participationDate) {
			this.participationDate = participationDate;
		}

		@Override
		public String toString() {
			return "ParticipationResponse [responseId=" + responseId + ", user=" + user + ", idea="
					+ idea + ", participationDate=" + participationDate + "]";
		}
		
		
		
}