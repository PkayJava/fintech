package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MReportMailingJobRunHistory extends AbstractTable {

    public final Column ID;

    public final Column JOB_ID;

    public final Column START_DATETIME;

    public final Column END_DATETIME;

    public final Column STATUS;

    public final Column ERROR_MESSAGE;

    public final Column ERROR_LOG;

    public static MReportMailingJobRunHistory staticInitialize(DataContext dataContext) {
        return new MReportMailingJobRunHistory(dataContext);
    }

    private MReportMailingJobRunHistory(DataContext dataContext) {
        super(dataContext, "m_report_mailing_job_run_history");
        this.ID = this.delegate.getColumnByName("id");
        this.JOB_ID = this.delegate.getColumnByName("job_id");
        this.START_DATETIME = this.delegate.getColumnByName("start_datetime");
        this.END_DATETIME = this.delegate.getColumnByName("end_datetime");
        this.STATUS = this.delegate.getColumnByName("status");
        this.ERROR_MESSAGE = this.delegate.getColumnByName("error_message");
        this.ERROR_LOG = this.delegate.getColumnByName("error_log");
    }

}
