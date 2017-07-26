package com.angkorteam.fintech.dto.request;

import com.mashape.unirest.http.JsonNode;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.Serializable;
import java.util.Date;

public class AccountTransferBuilder implements Serializable {

    private String fromOfficeId;
    private boolean hasFromOfficeId;

    private String fromClientId;
    private boolean hasFromClientId;

    private String fromAccountType;
    private boolean hasFromAccountType;

    private String fromAccountId;
    private boolean hasFromAccountId;

    private String toOfficeId;
    private boolean hasToOfficeId;

    private String toClientId;
    private boolean hasToClientId;

    private String toAccountType;
    private boolean hasToAccountType;

    private String toAccountId;
    private boolean hasToAccountId;

    private String dateFormat = "yyyy-MM-dd";
    private boolean hasDateFormat = true;

    private String locale = "en";
    private boolean hasLocale = true;

    private Date transferDate;
    private boolean hasTransferDate;

    private double transferAmount;
    private boolean hasTransferAmount;

    private String transferDescription;
    private boolean hasTransferDescription;

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();
        if (this.hasFromOfficeId) {
            object.getObject().put("fromOfficeId", this.fromOfficeId);
        }

        if (this.hasFromClientId) {
            object.getObject().put("fromClientId", this.fromClientId);
        }

        if (this.hasFromAccountType) {
            object.getObject().put("fromAccountType", this.fromAccountType);
        }

        if (this.hasFromAccountId) {
            object.getObject().put("fromAccountId", this.fromAccountId);
        }

        if (this.hasToOfficeId) {
            object.getObject().put("toOfficeId", this.toOfficeId);
        }

        if (this.hasToClientId) {
            object.getObject().put("toClientId", this.toClientId);
        }

        if (this.hasToAccountType) {
            object.getObject().put("toAccountType", this.toAccountType);
        }

        if (this.hasToAccountId) {
            object.getObject().put("toAccountId", this.toAccountId);
        }

        if (this.hasDateFormat = true) {
            object.getObject().put("dateFormat", this.dateFormat);
        }

        if (this.hasLocale = true) {
            object.getObject().put("locale", this.locale);
        }

        if (this.hasTransferDate) {
            if (this.transferDate != null) {
                object.getObject().put("transferDate", DateFormatUtils.format(this.transferDate, this.dateFormat));
            } else {
                object.getObject().put("transferDate", (String) null);
            }
        }

        if (this.hasTransferAmount) {
            object.getObject().put("transferAmount", this.transferAmount);
        }

        if (this.hasTransferDescription) {
            object.getObject().put("transferDescription", this.transferDescription);
        }
        return object;
    }

    public AccountTransferBuilder withFromOfficeId(String fromOfficeId) {
        this.fromOfficeId = fromOfficeId;
        this.hasFromOfficeId = true;
        return this;
    }

    public AccountTransferBuilder withFromClientId(String fromClientId) {
        this.fromClientId = fromClientId;
        this.hasFromClientId = true;
        return this;
    }

    public AccountTransferBuilder withFromAccountType(String fromAccountType) {
        this.fromAccountType = fromAccountType;
        this.hasFromAccountType = true;
        return this;
    }

    public AccountTransferBuilder withFromAccountId(String fromAccountId) {
        this.fromAccountId = fromAccountId;
        this.hasFromAccountId = true;
        return this;
    }

    public AccountTransferBuilder withToOfficeId(String toOfficeId) {
        this.toOfficeId = toOfficeId;
        this.hasToOfficeId = true;
        return this;
    }

    public AccountTransferBuilder withToClientId(String toClientId) {
        this.toClientId = toClientId;
        this.hasToClientId = true;
        return this;
    }

    public AccountTransferBuilder withToAccountType(String toAccountType) {
        this.toAccountType = toAccountType;
        this.hasToAccountType = true;
        return this;
    }

    public AccountTransferBuilder withToAccountId(String toAccountId) {
        this.toAccountId = toAccountId;
        this.hasToAccountId = true;
        return this;
    }

    public AccountTransferBuilder withDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.hasDateFormat = true;
        return this;
    }

    public AccountTransferBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    public AccountTransferBuilder withTransferDate(Date transferDate) {
        this.transferDate = transferDate;
        this.hasTransferDate = true;
        return this;
    }

    public AccountTransferBuilder withTransferAmount(double transferAmount) {
        this.transferAmount = transferAmount;
        this.hasTransferAmount = true;
        return this;
    }

    public AccountTransferBuilder withTransferDescription(String transferDescription) {
        this.transferDescription = transferDescription;
        this.hasTransferDescription = true;
        return this;
    }

}
