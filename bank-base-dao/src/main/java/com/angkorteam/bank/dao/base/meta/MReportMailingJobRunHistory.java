package com.angkorteam.bank.dao.base.meta;

import com.angkorteam.metamodel.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.JOB_ID = getInternalColumnByName("job_id");
        this.START_DATETIME = getInternalColumnByName("start_datetime");
        this.END_DATETIME = getInternalColumnByName("end_datetime");
        this.STATUS = getInternalColumnByName("status");
        this.ERROR_MESSAGE = getInternalColumnByName("error_message");
        this.ERROR_LOG = getInternalColumnByName("error_log");
    }

}
