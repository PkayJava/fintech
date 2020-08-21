package com.angkorteam.fintech.client.enums;

import java.util.ArrayList;
import java.util.List;

public class PutUserRequest {

    private long officeId;

    private List<String> roles = new ArrayList<>();

    private long staffId;

    private boolean selfServiceUser;

    private List<Long> clients = new ArrayList<>();

    private boolean sendPasswordToEmail;

    private String username;

    private String password;

    private boolean passwordNeverExpires;

    private String email;

    private String firstName;

    private String lastName;

    public long getOfficeId() {
        return officeId;
    }

    public void setOfficeId(long officeId) {
        this.officeId = officeId;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public long getStaffId() {
        return staffId;
    }

    public void setStaffId(long staffId) {
        this.staffId = staffId;
    }

    public boolean isSelfServiceUser() {
        return selfServiceUser;
    }

    public void setSelfServiceUser(boolean selfServiceUser) {
        this.selfServiceUser = selfServiceUser;
    }

    public List<Long> getClients() {
        return clients;
    }

    public void setClients(List<Long> clients) {
        this.clients = clients;
    }

    public boolean isSendPasswordToEmail() {
        return sendPasswordToEmail;
    }

    public void setSendPasswordToEmail(boolean sendPasswordToEmail) {
        this.sendPasswordToEmail = sendPasswordToEmail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPasswordNeverExpires() {
        return passwordNeverExpires;
    }

    public void setPasswordNeverExpires(boolean passwordNeverExpires) {
        this.passwordNeverExpires = passwordNeverExpires;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
