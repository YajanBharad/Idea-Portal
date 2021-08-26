package com.ideaportal.dto;

import java.sql.Date;

import com.ideaportal.models.Ideas;

import com.ideaportal.models.User;

public class LikesDTO {
    private long likeID;

    private boolean likeValue;

    private Date likeDate;

    private Ideas idea;

    private User user;

    public long getLikeID() {
        return likeID;
    }

    public void setLikeID(long likeID) {
        this.likeID = likeID;
    }

    public boolean getLikeValue() {
        return likeValue;
    }

    public void setLikeValue(Boolean  likeValue) {
        this.likeValue = likeValue;
    }

    public Date getLikeDate() {
        return likeDate;
    }

    public void setLikeDate(Date likeDate) {
        this.likeDate = likeDate;
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
