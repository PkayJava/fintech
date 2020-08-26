package com.angkorteam.bank.dao.base.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class SmsCampaign extends AbstractTable {

    public final Column ID;

    public final Column CAMPAIGN_NAME;

    public final Column CAMPAIGN_TYPE;

    public final Column CAMPAIGN_TRIGGER_TYPE;

    public final Column REPORT_ID;

    public final Column PROVIDER_ID;

    public final Column PARAM_VALUE;

    public final Column STATUS_ENUM;

    public final Column MESSAGE;

    public final Column SUBMITTED_ON_DATE;

    public final Column SUBMITTED_ON_USER_ID;

    public final Column APPROVED_ON_DATE;

    public final Column APPROVED_ON_USER_ID;

    public final Column CLOSED_ON_DATE;

    public final Column CLOSED_ON_USER_ID;

    public final Column RECURRENCE;

    public final Column NEXT_TRIGGER_DATE;

    public final Column LAST_TRIGGER_DATE;

    public final Column RECURRENCE_START_DATE;

    public final Column VISIBLE;

    public final Column NOTIFICATION;

    public static SmsCampaign staticInitialize(DataContext dataContext) {
        return new SmsCampaign(dataContext);
    }

    private SmsCampaign(DataContext dataContext) {
        super(dataContext, "sms_campaign");
        this.ID = getInternalColumnByName("id");
        this.CAMPAIGN_NAME = getInternalColumnByName("campaign_name");
        this.CAMPAIGN_TYPE = getInternalColumnByName("campaign_type");
        this.CAMPAIGN_TRIGGER_TYPE = getInternalColumnByName("campaign_trigger_type");
        this.REPORT_ID = getInternalColumnByName("report_id");
        this.PROVIDER_ID = getInternalColumnByName("provider_id");
        this.PARAM_VALUE = getInternalColumnByName("param_value");
        this.STATUS_ENUM = getInternalColumnByName("status_enum");
        this.MESSAGE = getInternalColumnByName("message");
        this.SUBMITTED_ON_DATE = getInternalColumnByName("submittedon_date");
        this.SUBMITTED_ON_USER_ID = getInternalColumnByName("submittedon_userid");
        this.APPROVED_ON_DATE = getInternalColumnByName("approvedon_date");
        this.APPROVED_ON_USER_ID = getInternalColumnByName("approvedon_userid");
        this.CLOSED_ON_DATE = getInternalColumnByName("closedon_date");
        this.CLOSED_ON_USER_ID = getInternalColumnByName("closedon_userid");
        this.RECURRENCE = getInternalColumnByName("recurrence");
        this.NEXT_TRIGGER_DATE = getInternalColumnByName("next_trigger_date");
        this.LAST_TRIGGER_DATE = getInternalColumnByName("last_trigger_date");
        this.RECURRENCE_START_DATE = getInternalColumnByName("recurrence_start_date");
        this.VISIBLE = getInternalColumnByName("is_visible");
        this.NOTIFICATION = getInternalColumnByName("is_notification");
    }

}
