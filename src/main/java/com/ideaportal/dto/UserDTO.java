package com.ideaportal.dto;
import com.ideaportal.models.Roles;

public class UserDTO {

    private long userID;

    private String userPassword;

    private String userName;

    private String userEmail;

    private String companyName;

    private Roles role;

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getcompanyName() {
        return companyName;
    }

    public void setcompanyName(String userCompany) {
        this.companyName=userCompany;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }
}

