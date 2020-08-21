package com.angkorteam.fintech.client.enums;

import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Option;

/**
 * Created by socheatkhauv on 7/15/17.
 */
public enum EntityTypeEnum {

    Client("m_client", "Client", EntityStatusEnum.Create, EntityStatusEnum.Approve, EntityStatusEnum.Close),
    Group("m_group", "Group", EntityStatusEnum.Create, EntityStatusEnum.Approve, EntityStatusEnum.Close),
    Loan("m_loan", "Loan", EntityStatusEnum.Create, EntityStatusEnum.Approve, EntityStatusEnum.Disburse, EntityStatusEnum.Withdrawn, EntityStatusEnum.Rejected, EntityStatusEnum.WriteOff),
    SavingAccount("m_savings_account", "Saving Account", EntityStatusEnum.Create, EntityStatusEnum.Approve, EntityStatusEnum.Activate, EntityStatusEnum.Withdrawn, EntityStatusEnum.Rejected, EntityStatusEnum.Close);

    private String literal;

    private String description;

    private EntityStatusEnum[] status;

    EntityTypeEnum(String literal, String description, EntityStatusEnum... status) {
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

    public EntityStatusEnum[] getStatus() {
        return status;
    }

    public static EntityTypeEnum parseLiteral(String literal) {
        for (EntityTypeEnum value : EntityTypeEnum.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        EntityTypeEnum value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return value.toOption();
    }

}
