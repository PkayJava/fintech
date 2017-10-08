package com.angkorteam.fintech.dto.builder;

import com.mashape.unirest.http.JsonNode;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by socheatkhauv on 6/26/17.
 */
public class StaffBuilder implements Serializable {

    private String id;
    private boolean hasId;

    private String officeId;
    private boolean hasOfficeId;

    private String firstName;
    private boolean hasFirstName;

    private String lastName;
    private boolean hasLastName;

    private boolean loanOfficer;
    private boolean hasLoanOfficer;

    private String locale = "en";
    private boolean hasLocale = true;

    private String dateFormat = "yyyy-MM-dd";
    private boolean hasDateFormat = true;

    private Date joiningDate;
    private boolean hasJoiningDate;

    private String externalId;
    private boolean hasExternalId;

    private String mobileNo;
    private boolean hasMobileNo;

    private String staffId;
    private boolean hasStaffId;

    private String note;
    private boolean hasNote;

    public StaffBuilder withExternalId(String externalId) {
        this.externalId = externalId;
        this.hasExternalId = true;
        return this;
    }

    public StaffBuilder withNote(String note) {
        this.note = note;
        this.hasNote = true;
        return this;
    }

    public StaffBuilder withStaffId(String staffId) {
        this.staffId = staffId;
        this.hasStaffId = true;
        return this;
    }

    public StaffBuilder withMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
        this.hasMobileNo = true;
        return this;
    }

    public StaffBuilder withDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.hasDateFormat = true;
        return this;
    }

    public StaffBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    public StaffBuilder withJoiningDate(Date joiningDate) {
        this.joiningDate = joiningDate;
        this.hasJoiningDate = true;
        return this;
    }

    public StaffBuilder withLoanOfficer(boolean loanOfficer) {
        this.loanOfficer = loanOfficer;
        this.hasLoanOfficer = true;
        return this;
    }

    public StaffBuilder withLastName(String lastName) {
        this.lastName = lastName;
        this.hasLastName = true;
        return this;
    }

    public StaffBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        this.hasFirstName = true;
        return this;
    }

    public StaffBuilder withOfficeId(String officeId) {
        this.officeId = officeId;
        this.hasOfficeId = true;
        return this;
    }

    public StaffBuilder withId(String id) {
        this.id = id;
        this.hasId = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode(new JSONObject());
        if (this.hasId) {
            object.getObject().put("id", this.id);
        }
        if (this.hasMobileNo) {
            object.getObject().put("mobileNo", this.mobileNo);
        }
        if (this.hasOfficeId) {
            object.getObject().put("officeId", this.officeId);
        }
        if (this.hasDateFormat) {
            object.getObject().put("dateFormat", this.dateFormat);
        }
        if (this.hasLocale) {
            object.getObject().put("locale", this.locale);
        }
        if (this.hasLoanOfficer) {
            object.getObject().put("isLoanOfficer", this.loanOfficer);
        }
        if (this.hasFirstName) {
            object.getObject().put("firstname", this.firstName);
        }
        if (this.hasLastName) {
            object.getObject().put("lastname", this.lastName);
        }
        if (this.hasJoiningDate) {
            if (this.joiningDate != null) {
                object.getObject().put("joiningDate", DateFormatUtils.format(this.joiningDate, this.dateFormat));
            } else {
                object.getObject().put("joiningDate", (String) null);
            }
        }
        if (this.hasStaffId) {
            object.getObject().put("staffId", this.staffId);
        }
        if (this.hasNote) {
            object.getObject().put("note", this.note);
        }
        if (this.hasExternalId) {
            object.getObject().put("externalId", this.externalId);
        }
        return object;
    }
}
