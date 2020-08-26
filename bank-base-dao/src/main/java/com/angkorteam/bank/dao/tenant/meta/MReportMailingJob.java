package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MReportMailingJob extends AbstractTable {

    public final Column ID;

    public final Column NAME;

    public final Column DESCRIPTION;

    public final Column START_DATETIME;

    public final Column RECURRENCE;

    public final Column CREATED_DATE;

    public final Column CREATED_BY_ID;

    public final Column LAST_MODIFIED_DATE;

    public final Column LAST_MODIFIED_BY_ID;

    public final Column EMAIL_RECIPIENT;

    public final Column EMAIL_SUBJECT;

    public final Column EMAIL_MESSAGE;

    public final Column EMAIL_ATTACHMENT_FILE_FORMAT;

    public final Column STRETCHY_REPORT_ID;

    public final Column STRETCHY_REPORT_PARAM_MAP;

    public final Column PREVIOUS_RUN_DATETIME;

    public final Column NEXT_RUN_DATETIME;

    public final Column PREVIOUS_RUN_STATUS;

    public final Column PREVIOUS_RUN_ERROR_LOG;

    public final Column PREVIOUS_RUN_ERROR_MESSAGE;

    public final Column NUMBER_OF_RUN;

    public final Column ACTIVE;

    public final Column DELETED;

    public final Column RUN_AS_USER_ID;

    public static MReportMailingJob staticInitialize(DataContext dataContext) {
        return new MReportMailingJob(dataContext);
    }

    private MReportMailingJob(DataContext dataContext) {
        super(dataContext, "m_report_mailing_job");
        this.ID = getInternalColumnByName("id");
        this.NAME = getInternalColumnByName("name");
        this.DESCRIPTION = getInternalColumnByName("description");
        this.START_DATETIME = getInternalColumnByName("start_datetime");
        this.RECURRENCE = getInternalColumnByName("recurrence");
        this.CREATED_DATE = getInternalColumnByName("created_date");
        this.CREATED_BY_ID = getInternalColumnByName("createdby_id");
        this.LAST_MODIFIED_DATE = getInternalColumnByName("lastmodified_date");
        this.LAST_MODIFIED_BY_ID = getInternalColumnByName("lastmodifiedby_id");
        this.EMAIL_RECIPIENT = getInternalColumnByName("email_recipients");
        this.EMAIL_SUBJECT = getInternalColumnByName("email_subject");
        this.EMAIL_MESSAGE = getInternalColumnByName("email_message");
        this.EMAIL_ATTACHMENT_FILE_FORMAT = getInternalColumnByName("email_attachment_file_format");
        this.STRETCHY_REPORT_ID = getInternalColumnByName("stretchy_report_id");
        this.STRETCHY_REPORT_PARAM_MAP = getInternalColumnByName("stretchy_report_param_map");
        this.PREVIOUS_RUN_DATETIME = getInternalColumnByName("previous_run_datetime");
        this.NEXT_RUN_DATETIME = getInternalColumnByName("next_run_datetime");
        this.PREVIOUS_RUN_STATUS = getInternalColumnByName("previous_run_status");
        this.PREVIOUS_RUN_ERROR_LOG = getInternalColumnByName("previous_run_error_log");
        this.PREVIOUS_RUN_ERROR_MESSAGE = getInternalColumnByName("previous_run_error_message");
        this.NUMBER_OF_RUN = getInternalColumnByName("number_of_runs");
        this.ACTIVE = getInternalColumnByName("is_active");
        this.DELETED = getInternalColumnByName("is_deleted");
        this.RUN_AS_USER_ID = getInternalColumnByName("run_as_userid");
    }

}
