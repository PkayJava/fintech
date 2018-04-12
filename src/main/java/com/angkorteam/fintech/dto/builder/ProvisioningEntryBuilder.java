package com.angkorteam.fintech.dto.builder;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import io.github.openunirest.http.JsonNode;

public class ProvisioningEntryBuilder implements Serializable {

    private Date date;
    private boolean hasDate;

    private String locale = "en";
    private boolean hasLocale = true;

    private String dateFormat = "yyyy-MM-dd";
    private boolean hasDateFormat = true;

    private boolean createJournalEntries;
    private boolean hasCreateJournalEntries;

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();
        if (this.hasCreateJournalEntries) {
            object.getObject().put("createjournalentries", this.createJournalEntries);
        }
        if (this.hasDate) {
            if (this.date != null) {
                object.getObject().put("date", DateFormatUtils.format(this.date, this.dateFormat));
            } else {
                object.getObject().put("date", (String) null);
            }
        }
        if (this.hasLocale) {
            object.getObject().put("locale", this.locale);
        }
        if (this.hasDateFormat) {
            object.getObject().put("dateFormat", this.dateFormat);
        }
        return object;
    }

    public ProvisioningEntryBuilder withDate(Date date) {
        this.date = date;
        this.hasDate = true;
        return this;
    }

    public ProvisioningEntryBuilder withDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.hasDateFormat = true;
        return this;
    }

    public ProvisioningEntryBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    public ProvisioningEntryBuilder withCreateJournalEntries(boolean createJournalEntries) {
        this.createJournalEntries = createJournalEntries;
        this.hasCreateJournalEntries = true;
        return this;
    }

}
