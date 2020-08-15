package com.angkorteam.fintech.dto.builder;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.angkorteam.fintech.client.enums.Destination;
import com.angkorteam.fintech.client.enums.InstructionType;
import com.angkorteam.fintech.client.enums.Priority;
import com.angkorteam.fintech.client.enums.RecurrenceFrequency;
import com.angkorteam.fintech.client.enums.RecurrenceType;
import com.angkorteam.fintech.client.enums.StandingInstructionAccountType;
import com.angkorteam.fintech.client.enums.Status;
import com.angkorteam.fintech.client.enums.TransferType;
import io.github.openunirest.http.JsonNode;

public class StandingInstructionBuilder implements Serializable {

    private String name;
    private boolean hasName;

    public StandingInstructionBuilder withName(String name) {
        this.name = name;
        this.hasName = true;
        return this;
    }

    private TransferType transferType;
    private boolean hasTransferType;

    public StandingInstructionBuilder withTransferType(TransferType transferType) {
        this.transferType = transferType;
        this.hasTransferType = true;
        return this;
    }

    private String id;
    private boolean hasId;

    private String fromOfficeId;
    private boolean hasFromOfficeId;

    private String fromClientId;
    private boolean hasFromClientId;

    private StandingInstructionAccountType fromAccountType;
    private boolean hasFromAccountType;

    public StandingInstructionBuilder withFromAccountType(StandingInstructionAccountType fromAccountType) {
        this.fromAccountType = fromAccountType;
        this.hasFromAccountType = true;
        return this;
    }

    private Priority priority;
    private boolean hasPriority;

    public StandingInstructionBuilder withPriority(Priority priority) {
        this.priority = priority;
        this.hasPriority = true;
        return this;
    }

    private Status status;
    private boolean hasStatus;

    public StandingInstructionBuilder withStatus(Status status) {
        this.status = status;
        this.hasStatus = true;
        return this;
    }

    private String fromAccountId;
    private boolean hasFromAccountId;

    private String toOfficeId;
    private boolean hasToOfficeId;

    private String toClientId;
    private boolean hasToClientId;

    private StandingInstructionAccountType toAccountType;
    private boolean hasToAccountType;

    public StandingInstructionBuilder withToAccountType(StandingInstructionAccountType toAccountType) {
        this.toAccountType = toAccountType;
        this.hasToAccountType = true;
        return this;
    }

    private String toAccountId;
    private boolean hasToAccountId;

    private InstructionType instructionType;
    private boolean hasInstructionType;

    private double amount;
    private boolean hasAmount;

    private Date validFrom;
    private boolean hasValidFrom;

    private RecurrenceType recurrenceType;
    private boolean hasRecurrenceType;

    public StandingInstructionBuilder withRecurrenceType(RecurrenceType recurrenceType) {
        this.recurrenceType = recurrenceType;
        this.hasRecurrenceType = true;
        return this;
    }

    private String recurrenceInterval;
    private boolean hasRecurrenceInterval;

    private RecurrenceFrequency recurrenceFrequency;
    private boolean hasRecurrenceFrequency;

    public StandingInstructionBuilder withRecurrenceFrequency(RecurrenceFrequency recurrenceFrequency) {
        this.recurrenceFrequency = recurrenceFrequency;
        this.hasRecurrenceFrequency = true;
        return this;
    }

    private String locale = "en";
    private boolean hasLocale = true;

    private String dateFormat = "yyyy-MM-dd";
    private boolean hasDateFormat = true;

    private Date recurrenceOnMonthDay;
    private boolean hasRecurrenceOnMonthDay;

    private String monthDayFormat = "dd MMMM";
    private boolean hasMonthDayFormat = true;

    private Destination destination;
    private boolean hasDestination;

    public StandingInstructionBuilder withDestination(Destination destination) {
        this.destination = destination;
        this.hasDestination = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();
        if (this.hasId) {
            object.getObject().put("id", this.id);
        }
        if (this.hasDestination) {
            object.getObject().put("destination", this.destination.getLiteral());
        }
        if (this.hasFromOfficeId) {
            object.getObject().put("fromOfficeId", this.fromOfficeId);
        }
        if (this.hasFromClientId) {
            object.getObject().put("fromClientId", this.fromClientId);
        }
        if (this.hasFromAccountType) {
            object.getObject().put("fromAccountType", this.fromAccountType.getLiteral());
        }
        if (this.hasName) {
            object.getObject().put("name", this.name);
        }
        if (this.hasTransferType) {
            object.getObject().put("transferType", this.transferType.getLiteral());
        }
        if (this.hasPriority) {
            object.getObject().put("priority", this.priority.getLiteral());
        }
        if (this.hasStatus) {
            object.getObject().put("status", this.status.getLiteral());
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
            object.getObject().put("toAccountType", this.toAccountType.getLiteral());
        }
        if (this.hasToAccountId) {
            object.getObject().put("toAccountId", this.toAccountId);
        }
        if (this.hasInstructionType) {
            object.getObject().put("instructionType", this.instructionType.getLiteral());
        }
        if (this.hasAmount) {
            object.getObject().put("amount", this.amount);
        }
        if (this.hasValidFrom) {
            if (this.validFrom != null) {
                object.getObject().put("validFrom", DateFormatUtils.format(this.validFrom, this.dateFormat));
            } else {
                object.getObject().put("validFrom", (String) null);
            }
        }
        if (this.hasRecurrenceType) {
            object.getObject().put("recurrenceType", this.recurrenceType.getLiteral());
        }
        if (this.hasRecurrenceInterval) {
            object.getObject().put("recurrenceInterval", this.recurrenceInterval);
        }
        if (this.hasRecurrenceFrequency) {
            object.getObject().put("recurrenceFrequency", this.recurrenceFrequency.getLiteral());
        }
        if (this.hasLocale) {
            object.getObject().put("locale", this.locale);
        }
        if (this.hasDateFormat) {
            object.getObject().put("dateFormat", this.dateFormat);
        }
        if (this.hasRecurrenceOnMonthDay) {
            object.getObject().put("recurrenceOnMonthDay", this.recurrenceOnMonthDay);
        }
        if (this.hasMonthDayFormat) {
            object.getObject().put("monthDayFormat", this.monthDayFormat);
        }
        return object;
    }

    public StandingInstructionBuilder withId(String id) {
        this.id = id;
        this.hasId = true;
        return this;
    }

    public StandingInstructionBuilder withFromOfficeId(String fromOfficeId) {
        this.fromOfficeId = fromOfficeId;
        this.hasFromOfficeId = true;
        return this;
    }

    public StandingInstructionBuilder withFromClientId(String fromClientId) {
        this.fromClientId = fromClientId;
        this.hasFromClientId = true;
        return this;
    }

    public StandingInstructionBuilder withFromAccountId(String fromAccountId) {
        this.fromAccountId = fromAccountId;
        this.hasFromAccountId = true;
        return this;
    }

    public StandingInstructionBuilder withToOfficeId(String toOfficeId) {
        this.toOfficeId = toOfficeId;
        this.hasToOfficeId = true;
        return this;
    }

    public StandingInstructionBuilder withToClientId(String toClientId) {
        this.toClientId = toClientId;
        this.hasToClientId = true;
        return this;
    }

    public StandingInstructionBuilder withToAccountId(String toAccountId) {
        this.toAccountId = toAccountId;
        this.hasToAccountId = true;
        return this;
    }

    public StandingInstructionBuilder withInstructionType(InstructionType instructionType) {
        this.instructionType = instructionType;
        this.hasInstructionType = true;
        return this;
    }

    public StandingInstructionBuilder withAmount(double amount) {
        this.amount = amount;
        this.hasAmount = true;
        return this;
    }

    public StandingInstructionBuilder withValidFrom(Date validFrom) {
        this.validFrom = validFrom;
        this.hasValidFrom = true;
        return this;
    }

    public StandingInstructionBuilder withRecurrenceInterval(String recurrenceInterval) {
        this.recurrenceInterval = recurrenceInterval;
        this.hasRecurrenceInterval = true;
        return this;
    }

    public StandingInstructionBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    public StandingInstructionBuilder withDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.hasDateFormat = true;
        return this;
    }

    public StandingInstructionBuilder withRecurrenceOnMonthDay(Date recurrenceOnMonthDay) {
        this.recurrenceOnMonthDay = recurrenceOnMonthDay;
        this.hasRecurrenceOnMonthDay = true;
        return this;
    }

    public StandingInstructionBuilder withMonthDayFormat(String monthDayFormat) {
        this.monthDayFormat = monthDayFormat;
        this.hasMonthDayFormat = true;
        return this;
    }

}
