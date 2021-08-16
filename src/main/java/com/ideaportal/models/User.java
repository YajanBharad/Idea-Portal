package com.ideaportal.models;



import javax.persistence.Column;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="User")
public class User {

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		@Column(name="user_id")
		private long userId;
		@Column(nullable=false,name="user_name",columnDefinition = "TEXT",unique = true)
		private String userName;
		@Column(nullable=false,name="user_email_id",columnDefinition = "TEXT",unique = true)
		private String userEmailId;
		@Column(nullable=false,name="user_password",columnDefinition = "TEXT")
		private String userPassword;
		@Column(nullable=false,name="company_name",columnDefinition = "TEXT")
		private String companyName;
		@OneToOne
		@JoinColumn(name = "role_id")
		private Roles role;
		
		
		public User() {
		}


		public User(String userName,String userEmailId,String userPassword,String companyName,
				Roles role) {
			
			this.userName = userName;
			this.userEmailId = userEmailId;
			this.userPassword = userPassword;
			this.companyName = companyName;
			this.role = role;
		}

		public long getUserId() {
			return this.userId;
		}


		public void setUserId(long userId) {
			this.userId = userId;
		}


		public String getUserName() {
			return this.userName;
		}


		public void setUserName(String userName) {
			this.userName = userName;
		}


		public String getUserEmailId() {
			return this.userEmailId;
		}


		public void setUserEmail(String userEmailId) {
			this.userEmailId = userEmailId;
		}


		public String getUserPassword() {
			return this.userPassword;
		}


		public void setUserPassword(String userPassword) {
			this.userPassword = userPassword;
		}


		public String getCompanyName() {
			return this.companyName;
		}


		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}


		public Roles getRoles() {
			return this.role;
		}

		public void setRoles(Roles role) {
			this.role=role;
		}


		@Override
		public String toString() {
			return "User [userId=" + userId + ", userName=" + userName + ", userEmail=" + userEmailId + ", userPassword="
					+ userPassword + ", companyName=" + companyName + ", role=" + role + "]";
		}
		
		
}