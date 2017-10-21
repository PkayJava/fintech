package com.angkorteam.fintech.dto.builder.client.client;

import java.io.Serializable;

import com.mashape.unirest.http.JsonNode;

public class ClientTransferBuilder implements Serializable {

    private String id;
    private boolean hasId;

    public ClientTransferBuilder withId(String id) {
        this.id = id;
        this.hasId = true;
        return this;
    }

    private String dateFormat = "yyyy-MM-DD";
    private boolean hasDateFormat = true;

    public ClientTransferBuilder withDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.hasDateFormat = true;
        return this;
    }

    private String locale = "en";
    private boolean hasLocale = true;

    public ClientTransferBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    private String destinationOfficeId;
    private boolean hasDestinationOfficeId;

    public ClientTransferBuilder withDestinationOfficeId(String destinationOfficeId) {
        this.destinationOfficeId = destinationOfficeId;
        this.hasDestinationOfficeId = true;
        return this;
    }

    private String note;
    private boolean hasNote;

    public ClientTransferBuilder withNote(String note) {
        this.note = note;
        this.hasNote = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();

        if (this.hasDestinationOfficeId) {
            object.getObject().put("destinationOfficeId", this.destinationOfficeId);
        }

        if (this.hasId) {
            object.getObject().put("id", this.id);
        }

        if (this.hasNote) {
            object.getObject().put("note", this.note);
        }

        if (this.hasLocale) {
            object.getObject().put("locale", this.locale);
        }

        if (this.hasDateFormat) {
            object.getObject().put("dateFormat", this.dateFormat);
        }

        return object;
    }

}
