package com.angkorteam.fintech.dto.request;

import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;

import java.io.Serializable;
import java.util.List;

public class AppUserBuilder implements Serializable {

    private boolean sendPasswordToEmail;
    private boolean hasSendPasswordToEmail;

    private List<String> roles = Lists.newArrayList();
    private boolean hasRoles;

    private boolean passwordNeverExpires;
    private boolean hasPasswordNeverExpires;

    private String officeId;
    private boolean hasOfficeId;

    private String staffId;
    private boolean hasStaffId;

    private String username;
    private boolean hasUsername;

    private String firstname;
    private boolean hasFirstname;

    private String lastname;
    private boolean hasLastname;

    private String email;
    private boolean hasEmail;

    private String password;
    private boolean hasPassword;

    private String repeatPassword;
    private boolean hasRepeatPassword;

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();

        object.getObject().put("sendPasswordToEmail", this.sendPasswordToEmail);

        if (!this.sendPasswordToEmail) {
            object.getObject().put("password", this.password);
            object.getObject().put("repeatPassword", this.repeatPassword);
        }

        if (this.hasRoles) {
            object.getObject().put("roles", this.roles);
        }
        if (this.hasUsername) {
            object.getObject().put("username", this.username);
        }
        if (this.hasFirstname) {
            object.getObject().put("firstname", this.firstname);
        }
        if (this.hasLastname) {
            object.getObject().put("lastname", this.lastname);
        }
        if (this.hasEmail) {
            object.getObject().put("email", this.email);
        }
        if (this.hasPasswordNeverExpires) {
            object.getObject().put("passwordNeverExpires", this.passwordNeverExpires);
        }
        if (this.hasOfficeId) {
            object.getObject().put("officeId", this.officeId);
        }
        if (this.hasStaffId) {
            object.getObject().put("staffId", this.staffId);
        }
        return object;
    }

    public AppUserBuilder withRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
        this.hasRepeatPassword = true;
        return this;
    }

    public AppUserBuilder withPassword(String password) {
        this.password = password;
        this.hasPassword = true;
        return this;
    }

    public AppUserBuilder withEmail(String email) {
        this.email = email;
        this.hasEmail = true;
        return this;
    }

    public AppUserBuilder withLastname(String lastname) {
        this.lastname = lastname;
        this.hasLastname = true;
        return this;
    }

    public AppUserBuilder withFirstname(String firstname) {
        this.firstname = firstname;
        this.hasFirstname = true;
        return this;
    }

    public AppUserBuilder withUsername(String username) {
        this.username = username;
        this.hasUsername = true;
        return this;
    }

    public AppUserBuilder withStaffId(String staffId) {
        this.staffId = staffId;
        this.hasStaffId = true;
        return this;
    }

    public AppUserBuilder withOfficeId(String officeId) {
        this.officeId = officeId;
        this.hasOfficeId = true;
        return this;
    }

    public AppUserBuilder withPasswordNeverExpires(boolean passwordNeverExpires) {
        this.passwordNeverExpires = passwordNeverExpires;
        this.hasPasswordNeverExpires = true;
        return this;
    }

    public AppUserBuilder withRole(String role) {
        this.roles.add(role);
        this.hasRoles = true;
        return this;
    }

    public AppUserBuilder withSendPasswordToEmail(boolean sendPasswordToEmail) {
        this.sendPasswordToEmail = sendPasswordToEmail;
        this.hasSendPasswordToEmail = true;
        return this;
    }
}
