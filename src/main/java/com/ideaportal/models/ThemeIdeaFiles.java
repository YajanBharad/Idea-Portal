package com.ideaportal.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name ="theme_idea_files")
public class ThemeIdeaFiles {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="file_id")
	private long fileId;
	@Column(name="file_name",columnDefinition = "TEXT",nullable = false)
	private String fileName;
	
	@Column(name="file_type",columnDefinition = "TEXT",nullable = false)
	private String fileType;
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="theme_id")
	private Themes theme;
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="idea_id")
	private Ideas idea;
	  @ManyToOne
	    @JoinColumn(name = "user_id")
	    User user;
	  @Column(columnDefinition = "LONGTEXT", name = "themeidea_url")
	  private String ThemeideaUrl;
	
	

	public String getThemeideaUrl() {
		return ThemeideaUrl;
	}

	public void setThemeideaUrl(String themeideaUrl) {
		ThemeideaUrl = themeideaUrl;
	}

	public ThemeIdeaFiles() {
		// TODO Auto-generated constructor stub
	}	
	
	public ThemeIdeaFiles(String fileName, String fileType, Themes theme, Ideas idea) {
		this.fileName = fileName;
		this.fileType = fileType;
		this.theme = theme;
		this.idea = idea;
	}

	public long getFileId() {
		return fileId;
	}
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setFileId(long fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public  Themes getThemeId() {
		return this.theme;
	}

	public void setThemeId(Themes themeId) {
		this.theme = themeId;
	}

	public Ideas getIdeaId() {
		return this.idea;
	}

	public void setIdeaId(Ideas ideaId) {
		this.idea = ideaId;
	}



	@Override
	public String toString() {
		return "ThemeIdeaFiles [fileId=" + fileId + ", fileName=" + fileName + ", fileType=" + fileType + ", themeId="
				+ theme + ", ideaId=" + idea + "]";
	}
	
	
}