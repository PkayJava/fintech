package com.angkorteam.fintech.dto.request;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;

public class ClientBuilder implements Serializable {

    private String id;
    private boolean hasId;

    private String officeId;
    private boolean hasOfficeId;

    private String firstName;
    private boolean hasFirstName;

    private String lastName;
    private boolean hasLastName;

    private String externalId;
    private boolean hasExternalId;

    private String dateFormat = "yyyy-MM-DD";
    private boolean hasDateFormat = true;

    private String locale = "en";
    private boolean hasLocale = true;

    private boolean active;
    private boolean hasActive;

    private Date activationDate;
    private boolean hasActivationDate;

    private Date submittedOnDate;
    private boolean hasSubmittedOnDate;

    private String savingsProductId;
    private boolean hasSavingsProductId;

    private List<Map<String, Object>> datatables = Lists.newArrayList();
    private boolean hasDatatables;

    private List<Object> address = Lists.newArrayList();
    private boolean hasAddress;

    public ClientBuilder withAddress(Object address) {
        this.address.add(address);
        this.hasAddress = true;
        return this;
    }

    private String legalFormId;
    private boolean hasLegalFormId;

    public ClientBuilder withLegalFormId(String legalFormId) {
        this.legalFormId = legalFormId;
        this.hasLegalFormId = true;
        return this;
    }

    private String staffId;
    private boolean hasStaffId;

    public ClientBuilder withStaffId(String staffId) {
        this.staffId = staffId;
        this.hasStaffId = true;
        return this;
    }

    private boolean staff;
    private boolean hasStaff;

    public ClientBuilder withStaff(boolean staff) {
        this.staff = staff;
        this.hasStaff = true;
        return this;
    }

    private String middleName;
    private boolean hasMiddleName;

    public ClientBuilder withMiddlename(String middleName) {
        this.middleName = middleName;
        this.hasMiddleName = true;
        return this;
    }

    private String mobileNo;
    private boolean hasMobileNo;

    public ClientBuilder withMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
        this.hasMobileNo = true;
        return this;
    }

    private String clientTypeId;
    private boolean hasClientTypeId;

    public ClientBuilder withClientTypeId(String clientTypeId) {
        this.clientTypeId = clientTypeId;
        this.hasClientTypeId = true;
        return this;
    }

    private String genderId;
    private boolean hasGenderId;

    public ClientBuilder withGenderId(String genderId) {
        this.genderId = genderId;
        this.hasGenderId = true;
        return this;
    }

    private String clientClassificationId;
    private boolean hasClientClassificationId;

    public ClientBuilder withClientClassificationId(String clientClassificationId) {
        this.clientClassificationId = clientClassificationId;
        this.hasClientClassificationId = true;
        return this;
    }

    private Date dateOfBirth;
    private boolean hasdateOfBirth;

    public ClientBuilder withdateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        this.hasdateOfBirth = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();

        if (this.hasdateOfBirth) {
            if (this.dateOfBirth != null) {
                object.getObject().put("dateOfBirth", DateFormatUtils.format(this.dateOfBirth, this.dateFormat));
            } else {
                object.getObject().put("dateOfBirth", (String) null);
            }
        }

        if (this.hasClientClassificationId) {
            object.getObject().put("clientClassificationId", this.clientClassificationId);
        }

        if (this.hasGenderId) {
            object.getObject().put("genderId", this.genderId);
        }

        if (this.hasClientTypeId) {
            object.getObject().put("clientTypeId", this.clientTypeId);
        }

        if (this.hasMobileNo) {
            object.getObject().put("mobileNo", this.mobileNo);
        }

        if (this.hasMiddleName) {
            object.getObject().put("middlename", this.middleName);
        }

        if (this.hasStaff) {
            object.getObject().put("isStaff", this.staff);
        }

        if (this.hasStaffId) {
            object.getObject().put("staffId", this.staffId);
        }

        if (this.hasLegalFormId) {
            object.getObject().put("legalFormId", this.legalFormId);
        }

        if (this.hasAddress) {
            object.getObject().put("address", this.address);
        }
        if (this.hasDatatables) {
            object.getObject().put("datatables", this.datatables);
        }
        if (this.hasSavingsProductId) {
            object.getObject().put("savingsProductId", this.savingsProductId);
        }
        if (this.hasSubmittedOnDate) {
            if (this.submittedOnDate != null) {
                object.getObject().put("submittedOnDate", DateFormatUtils.format(this.submittedOnDate, this.dateFormat));
            } else {
                object.getObject().put("submittedOnDate", (String) null);
            }
        }
        if (this.hasActivationDate) {
            if (this.activationDate != null) {
                object.getObject().put("activationDate", DateFormatUtils.format(this.activationDate, this.dateFormat));
            } else {
                object.getObject().put("activationDate", (String) null);
            }
        }
        if (this.hasActive) {
            object.getObject().put("active", this.active);
        }
        if (this.hasLocale) {
            object.getObject().put("locale", this.locale);
        }
        if (this.hasDateFormat) {
            object.getObject().put("dateFormat", this.dateFormat);
        }
        if (this.hasExternalId) {
            object.getObject().put("externalId", this.externalId);
        }
        if (this.hasLastName) {
            object.getObject().put("lastname", this.lastName);
        }
        if (this.hasFirstName) {
            object.getObject().put("firstname", this.firstName);
        }
        if (this.hasOfficeId) {
            object.getObject().put("officeId", this.officeId);
        }
        if (this.hasId) {
            object.getObject().put("id", this.id);
        }
        return object;
    }

    public ClientBuilder withSavingsProductId(String savingsProductId) {
        this.savingsProductId = savingsProductId;
        this.hasSavingsProductId = true;
        return this;
    }

    public ClientBuilder withSubmittedOnDate(Date submittedOnDate) {
        this.submittedOnDate = submittedOnDate;
        this.hasSubmittedOnDate = true;
        return this;
    }

    public ClientBuilder withActivationDate(Date activationDate) {
        this.activationDate = activationDate;
        this.hasActivationDate = true;
        return this;
    }

    public ClientBuilder withActive(boolean active) {
        this.active = active;
        this.hasActive = true;
        return this;
    }

    public ClientBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    public ClientBuilder withDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.hasDateFormat = true;
        return this;
    }

    public ClientBuilder withExternalId(String externalId) {
        this.externalId = externalId;
        this.hasExternalId = true;
        return this;
    }

    public ClientBuilder withLastName(String lastName) {
        this.lastName = lastName;
        this.hasLastName = true;
        return this;
    }

    public ClientBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        this.hasFirstName = true;
        return this;
    }

    public ClientBuilder withId(String id) {
        this.id = id;
        this.hasId = true;
        return this;
    }

    public ClientBuilder withOfficeId(String officeId) {
        this.officeId = officeId;
        this.hasOfficeId = true;
        return this;
    }

    public ClientBuilder withDatatable(String registeredTableName, Map<String, Object> data) {
        Map<String, Object> datatable = new HashMap<>();
        datatable.put("registeredTableName", registeredTableName);
        datatable.put("data", datatable);
        this.datatables.add(datatable);
        this.hasDatatables = true;
        return this;
    }

}
