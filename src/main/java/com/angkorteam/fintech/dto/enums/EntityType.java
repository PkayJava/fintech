package com.angkorteam.fintech.dto.enums;

import com.angkorteam.framework.wicket.markup.html.form.select2.Option;

/**
 * Created by socheatkhauv on 7/15/17.
 */
public enum EntityType {

    Client("m_client", "Client", EntityStatus.Create, EntityStatus.Approve, EntityStatus.Close), 
    Group("m_group", "Group", EntityStatus.Create, EntityStatus.Approve, EntityStatus.Close), 
    Loan("m_loan", "Loan", EntityStatus.Create, EntityStatus.Approve, EntityStatus.Disburse, EntityStatus.Withdrawn, EntityStatus.Rejected, EntityStatus.WriteOff), 
    SavingsAccount("m_savings_account", "Savings Account", EntityStatus.Create, EntityStatus.Approve, EntityStatus.Activate, EntityStatus.Withdrawn, EntityStatus.Rejected, EntityStatus.Close);

    private String literal;

    private String description;

    private EntityStatus[] status;

    EntityType(String literal, String description, EntityStatus... status) {
        this.literal = literal;
        this.description = description;
        this.status = status;
    }

    public String getLiteral() {
        return literal;
    }

    public String getDescription() {
        return description;
    }

    public Option toOption() {
        return new Option(this.name(), this.description);
    }

    public EntityStatus[] getStatus() {
        return status;
    }

    public static EntityType parseLiteral(String literal) {
        for (EntityType value : EntityType.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        EntityType value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return value.toOption();
    }

}
