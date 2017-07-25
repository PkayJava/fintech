package com.angkorteam.fintech.dto.request;

import com.google.common.collect.Lists;

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
}
