package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class JobRunHistory extends AbstractTable {

    public final Column ID;

    public final Column JOB_ID;

    public final Column VERSION;

    public final Column START_TIME;

    public final Column END_TIME;

    public final Column STATUS;

    public final Column ERROR_MESSAGE;

    public final Column TRIGGER_TYPE;

    public final Column ERROR_LOG;

    public static JobRunHistory staticInitialize(DataContext dataContext) {
        return new JobRunHistory(dataContext);
    }

    private JobRunHistory(DataContext dataContext) {
        super(dataContext, "job_run_history");
        this.ID = this.delegate.getColumnByName("id");
        this.JOB_ID = this.delegate.getColumnByName("job_id");
        this.VERSION = this.delegate.getColumnByName("version");
        this.START_TIME = this.delegate.getColumnByName("start_time");
        this.END_TIME = this.delegate.getColumnByName("end_time");
        this.STATUS = this.delegate.getColumnByName("status");
        this.ERROR_MESSAGE = this.delegate.getColumnByName("error_message");
        this.TRIGGER_TYPE = this.delegate.getColumnByName("trigger_type");
        this.ERROR_LOG = this.delegate.getColumnByName("error_log");
    }

}
