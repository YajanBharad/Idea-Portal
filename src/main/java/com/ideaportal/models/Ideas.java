package com.ideaportal.models;


import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Ideas {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="idea_id")
	private long ideaId;
	
	@Column(name="idea_description",columnDefinition = "TEXT",nullable = false)
	private String ideaDescription;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "theme")
	private List<ThemeIdeaFiles> ideaFiles;
	
	@ManyToOne
	@JoinColumn(name="theme_id")
	private Themes theme;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="creation_date",nullable=false)
	private Date ideaDate;

	public Ideas() {
	}

	public Ideas(long ideaId, String ideaDescription, List<ThemeIdeaFiles> ideaFiles, Themes theme, User user,
			Date ideaDate) {
		this.ideaId = ideaId;
		this.ideaDescription = ideaDescription;
		this.ideaFiles = ideaFiles;
		this.theme = theme;
		this.user = user;
		this.ideaDate = ideaDate;
	}
	
	public long getIdeaId() {
		return ideaId;
	}

	public void setIdeaId(long ideaId) {
		this.ideaId = ideaId;
	}

	public String getIdeaDescription() {
		return ideaDescription;
	}

	public void setIdeaDescription(String ideaDescription) {
		this.ideaDescription = ideaDescription;
	}
	@JsonManagedReference("idea")
	public List<ThemeIdeaFiles> getIdeaFiles() {
		return ideaFiles;
	}

	public void setIdeaFiles(List<ThemeIdeaFiles> ideaFiles) {
		this.ideaFiles = ideaFiles;
	}

	public Themes getTheme() {
		return theme;
	}

	public void setTheme(Themes theme) {
		this.theme = theme;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getIdeaDate() {
		return ideaDate;
	}

	public void setIdeaDate(Date ideaDate) {
		this.ideaDate = ideaDate;
	}

	@Override
	public String toString() {
		return "Ideas [ideaId=" + ideaId + ", ideaDescription=" + ideaDescription + ", ideaFiles=" + ideaFiles
				+ ", theme=" + theme + ", user=" + user + ", ideaDate=" + ideaDate + "]";
	}
	
	
	
}