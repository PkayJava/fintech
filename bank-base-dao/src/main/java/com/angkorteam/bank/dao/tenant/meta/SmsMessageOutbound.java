package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class SmsMessageOutbound extends AbstractTable {

    public final Column ID;

    public final Column GROUP_ID;

    public final Column CLIENT_ID;

    public final Column STAFF_ID;

    public final Column STATUS_ENUM;

    public final Column MOBILE_NO;

    public final Column MESSAGE;

    public final Column CAMPAIGN_ID;

    public final Column EXTERNAL_ID;

    public final Column SUBMITTED_ON_DATE;

    public final Column DELIVERED_ON_DATE;

    public final Column NOTIFICATION;

    public static SmsMessageOutbound staticInitialize(DataContext dataContext) {
        return new SmsMessageOutbound(dataContext);
    }

    private SmsMessageOutbound(DataContext dataContext) {
        super(dataContext, "sms_messages_outbound");
        this.ID = getInternalColumnByName("id");
        this.GROUP_ID = getInternalColumnByName("group_id");
        this.CLIENT_ID = getInternalColumnByName("client_id");
        this.STAFF_ID = getInternalColumnByName("staff_id");
        this.STATUS_ENUM = getInternalColumnByName("status_enum");
        this.MOBILE_NO = getInternalColumnByName("mobile_no");
        this.MESSAGE = getInternalColumnByName("message");
        this.CAMPAIGN_ID = getInternalColumnByName("campaign_id");
        this.EXTERNAL_ID = getInternalColumnByName("external_id");
        this.SUBMITTED_ON_DATE = getInternalColumnByName("submittedon_date");
        this.DELIVERED_ON_DATE = getInternalColumnByName("delivered_on_date");
        this.NOTIFICATION = getInternalColumnByName("is_notification");
    }

}
