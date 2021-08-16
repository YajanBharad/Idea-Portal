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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Comments {

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		@Column(name="comment_id")
		private long commentId;
		
		@Column(name="comment_value",columnDefinition = "TEXT",nullable = false)
		private String commentValue;
		
		@ManyToOne
		@JoinColumn(name="idea_id")
		private Ideas idea;
		
		@ManyToOne
		@JoinColumn(name="user_id")
		private User user;
		
		@Basic
		@Temporal(TemporalType.TIMESTAMP)
		@Column(name="comment_date",nullable=false)
		private Date commentDate;
		
		public Comments() {

		}
		
		
		public Comments(long commentId, String commentValue, Ideas idea, User user, Date commentDate) {
			this.commentId = commentId;
			this.commentValue = commentValue;
			this.idea = idea;
			this.user = user;
			this.commentDate = commentDate;
		}


		public long getCommentId() {
			return commentId;
		}

		public void setCommentId(long commentId) {
			this.commentId = commentId;
		}

		public String getCommentValue() {
			return commentValue;
		}

		public void setCommentValue(String commentValue) {
			this.commentValue = commentValue;
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

		public Date getCommentDate() {
			return commentDate;
		}

		public void setCommentDate(Date commentDate) {
			this.commentDate = commentDate;
		}


		@Override
		public String toString() {
			return "Comments [commentId=" + commentId + ", commentValue=" + commentValue + ", idea=" + idea + ", user="
					+ user + ", commentDate=" + commentDate + "]";
		}
		
		
}