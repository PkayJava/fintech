package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class ScheduledEmailCampaign extends AbstractTable {

    public final Column ID;

    public final Column CAMPAIGN_NAME;

    public final Column CAMPAIGN_TYPE;

    public final Column BUSINESS_RULE_ID;

    public final Column PARAM_VALUE;

    public final Column STATUS_ENUM;

    public final Column EMAIL_SUBJECT;

    public final Column EMAIL_MESSAGE;

    public final Column EMAIL_ATTACHMENT_FILE_FORMAT;

    public final Column STRETCHY_REPORT_ID;

    public final Column STRETCHY_REPORT_PARAM_MAP;

    public final Column CLOSED_ON_DATE;

    public final Column CLOSED_ON_USER_ID;

    public final Column SUBMITTED_ON_DATE;

    public final Column SUBMITTED_ON_USER_ID;

    public final Column APPROVED_ON_DATE;

    public final Column APPROVED_ON_USER_ID;

    public final Column RECURRENCE;

    public final Column NEXT_TRIGGER_DATE;

    public final Column LAST_TRIGGER_DATE;

    public final Column RECURRENCE_START_DATE;

    public final Column VISIBLE;

    public final Column PREVIOUS_RUN_ERROR_LOG;

    public final Column PREVIOUS_RUN_ERROR_MESSAGE;

    public final Column PREVIOUS_RUN_STATUS;

    public static ScheduledEmailCampaign staticInitialize(DataContext dataContext) {
        return new ScheduledEmailCampaign(dataContext);
    }

    private ScheduledEmailCampaign(DataContext dataContext) {
        super(dataContext, "scheduled_email_campaign");
        this.ID = getInternalColumnByName("id");
        this.CAMPAIGN_NAME = getInternalColumnByName("campaign_name");
        this.CAMPAIGN_TYPE = getInternalColumnByName("campaign_type");
        this.BUSINESS_RULE_ID = getInternalColumnByName("businessRule_id");
        this.PARAM_VALUE = getInternalColumnByName("param_value");
        this.STATUS_ENUM = getInternalColumnByName("status_enum");
        this.EMAIL_SUBJECT = getInternalColumnByName("email_subject");
        this.EMAIL_MESSAGE = getInternalColumnByName("email_message");
        this.EMAIL_ATTACHMENT_FILE_FORMAT = getInternalColumnByName("email_attachment_file_format");
        this.STRETCHY_REPORT_ID = getInternalColumnByName("stretchy_report_id");
        this.STRETCHY_REPORT_PARAM_MAP = getInternalColumnByName("stretchy_report_param_map");
        this.CLOSED_ON_DATE = getInternalColumnByName("closedon_date");
        this.CLOSED_ON_USER_ID = getInternalColumnByName("closedon_userid");
        this.SUBMITTED_ON_DATE = getInternalColumnByName("submittedon_date");
        this.SUBMITTED_ON_USER_ID = getInternalColumnByName("submittedon_userid");
        this.APPROVED_ON_DATE = getInternalColumnByName("approvedon_date");
        this.APPROVED_ON_USER_ID = getInternalColumnByName("approvedon_userid");
        this.RECURRENCE = getInternalColumnByName("recurrence");
        this.NEXT_TRIGGER_DATE = getInternalColumnByName("next_trigger_date");
        this.LAST_TRIGGER_DATE = getInternalColumnByName("last_trigger_date");
        this.RECURRENCE_START_DATE = getInternalColumnByName("recurrence_start_date");
        this.VISIBLE = getInternalColumnByName("is_visible");
        this.PREVIOUS_RUN_ERROR_LOG = getInternalColumnByName("previous_run_error_log");
        this.PREVIOUS_RUN_ERROR_MESSAGE = getInternalColumnByName("previous_run_error_message");
        this.PREVIOUS_RUN_STATUS = getInternalColumnByName("previous_run_status");
    }

}
