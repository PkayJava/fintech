package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.JOB_ID = getInternalColumnByName("job_id");
        this.VERSION = getInternalColumnByName("version");
        this.START_TIME = getInternalColumnByName("start_time");
        this.END_TIME = getInternalColumnByName("end_time");
        this.STATUS = getInternalColumnByName("status");
        this.ERROR_MESSAGE = getInternalColumnByName("error_message");
        this.TRIGGER_TYPE = getInternalColumnByName("trigger_type");
        this.ERROR_LOG = getInternalColumnByName("error_log");
    }

}
