package com.ideaportal.dto;

import com.ideaportal.models.Ideas;
import com.ideaportal.models.User;

import java.sql.Date;

public class CommentsDTO {

    private long commentID;

    private String commentValue;

    private Date commentDate;

    private Ideas idea;

    private User user;

    public long getCommentID() {
        return commentID;
    }

    public void setCommentID(long commentID) {
        this.commentID = commentID;
    }

    public String getCommentValue() {
        return commentValue;
    }

    public void setCommentValue(String commentValue) {
        this.commentValue = commentValue;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
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
}
