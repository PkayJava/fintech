package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class ScheduledEmailMessageOutbound extends AbstractTable {

    public final Column ID;

    public final Column GROUP_ID;

    public final Column CLIENT_ID;

    public final Column STAFF_ID;

    public final Column EMAIL_CAMPAIGN_ID;

    public final Column STATUS_ENUM;

    public final Column EMAIL_ADDRESS;

    public final Column EMAIL_SUBJECT;

    public final Column MESSAGE;

    public final Column CAMPAIGN_NAME;

    public final Column SUBMITTED_ON_DATE;

    public final Column ERROR_MESSAGE;

    public static ScheduledEmailMessageOutbound staticInitialize(DataContext dataContext) {
        return new ScheduledEmailMessageOutbound(dataContext);
    }

    private ScheduledEmailMessageOutbound(DataContext dataContext) {
        super(dataContext, "scheduled_email_messages_outbound");
        this.ID = getInternalColumnByName("id");
        this.GROUP_ID = getInternalColumnByName("group_id");
        this.CLIENT_ID = getInternalColumnByName("client_id");
        this.STAFF_ID = getInternalColumnByName("staff_id");
        this.EMAIL_CAMPAIGN_ID = getInternalColumnByName("email_campaign_id");
        this.STATUS_ENUM = getInternalColumnByName("status_enum");
        this.EMAIL_ADDRESS = getInternalColumnByName("email_address");
        this.EMAIL_SUBJECT = getInternalColumnByName("email_subject");
        this.MESSAGE = getInternalColumnByName("message");
        this.CAMPAIGN_NAME = getInternalColumnByName("campaign_name");
        this.SUBMITTED_ON_DATE = getInternalColumnByName("submittedon_date");
        this.ERROR_MESSAGE = getInternalColumnByName("error_message");
    }

}
