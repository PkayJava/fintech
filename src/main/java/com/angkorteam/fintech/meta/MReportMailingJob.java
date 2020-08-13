package com.angkorteam.fintech.meta;

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
        this.ID = this.delegate.getColumnByName("id");
        this.NAME = this.delegate.getColumnByName("name");
        this.DESCRIPTION = this.delegate.getColumnByName("description");
        this.START_DATETIME = this.delegate.getColumnByName("start_datetime");
        this.RECURRENCE = this.delegate.getColumnByName("recurrence");
        this.CREATED_DATE = this.delegate.getColumnByName("created_date");
        this.CREATED_BY_ID = this.delegate.getColumnByName("createdby_id");
        this.LAST_MODIFIED_DATE = this.delegate.getColumnByName("lastmodified_date");
        this.LAST_MODIFIED_BY_ID = this.delegate.getColumnByName("lastmodifiedby_id");
        this.EMAIL_RECIPIENT = this.delegate.getColumnByName("email_recipients");
        this.EMAIL_SUBJECT = this.delegate.getColumnByName("email_subject");
        this.EMAIL_MESSAGE = this.delegate.getColumnByName("email_message");
        this.EMAIL_ATTACHMENT_FILE_FORMAT = this.delegate.getColumnByName("email_attachment_file_format");
        this.STRETCHY_REPORT_ID = this.delegate.getColumnByName("stretchy_report_id");
        this.STRETCHY_REPORT_PARAM_MAP = this.delegate.getColumnByName("stretchy_report_param_map");
        this.PREVIOUS_RUN_DATETIME = this.delegate.getColumnByName("previous_run_datetime");
        this.NEXT_RUN_DATETIME = this.delegate.getColumnByName("next_run_datetime");
        this.PREVIOUS_RUN_STATUS = this.delegate.getColumnByName("previous_run_status");
        this.PREVIOUS_RUN_ERROR_LOG = this.delegate.getColumnByName("previous_run_error_log");
        this.PREVIOUS_RUN_ERROR_MESSAGE = this.delegate.getColumnByName("previous_run_error_message");
        this.NUMBER_OF_RUN = this.delegate.getColumnByName("number_of_runs");
        this.ACTIVE = this.delegate.getColumnByName("is_active");
        this.DELETED = this.delegate.getColumnByName("is_deleted");
        this.RUN_AS_USER_ID = this.delegate.getColumnByName("run_as_userid");
    }

}
