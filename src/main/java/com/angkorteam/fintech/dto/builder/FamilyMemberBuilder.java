package com.angkorteam.fintech.dto.builder;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.json.JSONObject;

import com.mashape.unirest.http.JsonNode;

public class FamilyMemberBuilder implements Serializable {

    private String relationshipId;
    private boolean hasRelationshipId;

    public FamilyMemberBuilder withRelationshipId(String relationshipId) {
        this.relationshipId = relationshipId;
        this.hasRelationshipId = true;
        return this;
    }

    private String firstName;
    private boolean hasFirstName;

    public FamilyMemberBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        this.hasFirstName = true;
        return this;
    }

    private String middleName;
    private boolean hasMiddleName;

    public FamilyMemberBuilder withMiddleName(String middleName) {
        this.middleName = middleName;
        this.hasMiddleName = true;
        return this;
    }

    private String lastName;
    private boolean hasLastName;

    public FamilyMemberBuilder withLastName(String lastName) {
        this.lastName = lastName;
        this.hasLastName = true;
        return this;
    }

    private String qualification;
    private boolean hasQualification;

    public FamilyMemberBuilder withQualification(String qualification) {
        this.qualification = qualification;
        this.hasQualification = true;
        return this;
    }

    private String mobileNumber;
    private boolean hasMobileNumber;

    public FamilyMemberBuilder withMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
        this.hasMobileNumber = true;
        return this;
    }

    private int age;
    private boolean hasAge;

    public FamilyMemberBuilder withAge(int age) {
        this.age = age;
        this.hasAge = true;
        return this;
    }

    private boolean dependent;
    private boolean hasDependent;

    public FamilyMemberBuilder withDependent(boolean dependent) {
        this.dependent = dependent;
        this.hasDependent = true;
        return this;
    }

    private String genderId;
    private boolean hasGenderId;

    public FamilyMemberBuilder withGenderId(String genderId) {
        this.genderId = genderId;
        this.hasGenderId = true;
        return this;
    }

    private String professionId;
    private boolean hasProfessionId;

    public FamilyMemberBuilder withProfessionId(String professionId) {
        this.professionId = professionId;
        this.hasProfessionId = true;
        return this;
    }

    private String maritalStatusId;
    private boolean hasMaritalStatusId;

    public FamilyMemberBuilder withMaritalStatusId(String maritalStatusId) {
        this.maritalStatusId = maritalStatusId;
        this.hasMaritalStatusId = true;
        return this;
    }

    private String locale = "en";
    private boolean hasLocale = true;

    public FamilyMemberBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    private String dateFormat = "yyyy-MM-dd";
    private boolean hasDateFormat = true;

    public FamilyMemberBuilder withDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.hasDateFormat = true;
        return this;
    }

    private Date dateOfBirth;
    private boolean hasDateOfBirth;

    public FamilyMemberBuilder withDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        this.hasDateOfBirth = true;
        return this;
    }

    public JSONObject build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();

        if (this.hasDateOfBirth) {
            if (this.dateOfBirth != null) {
                object.getObject().put("dateOfBirth", DateFormatUtils.format(this.dateOfBirth, this.dateFormat));
            } else {
                object.getObject().put("dateOfBirth", (String) null);
            }
        }

        if (this.hasDateFormat) {
            object.getObject().put("dateFormat", this.dateFormat);
        }

        if (this.hasLocale) {
            object.getObject().put("locale", this.locale);
        }

        if (this.hasMaritalStatusId) {
            object.getObject().put("maritalStatusId", this.maritalStatusId);
        }

        if (this.hasProfessionId) {
            object.getObject().put("professionId", this.professionId);
        }

        if (this.hasGenderId) {
            object.getObject().put("genderId", this.genderId);
        }

        if (this.hasDependent) {
            object.getObject().put("isDependent", this.dependent);
        }

        if (this.hasAge) {
            object.getObject().put("age", this.age);
        }

        if (this.hasMobileNumber) {
            object.getObject().put("mobileNumber", this.mobileNumber);
        }

        if (this.hasQualification) {
            object.getObject().put("qualification", this.qualification);
        }

        if (this.hasLastName) {
            object.getObject().put("lastName", this.lastName);
        }

        if (this.hasMiddleName) {
            object.getObject().put("middleName", this.middleName);
        }

        if (this.hasFirstName) {
            object.getObject().put("firstName", this.firstName);
        }

        if (this.hasRelationshipId) {
            object.getObject().put("relationshipId", this.relationshipId);
        }

        return object.getObject();
    }

}
