package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class JobParameter extends AbstractTable {

    public final Column ID;

    public final Column JOB_ID;

    public final Column PARAMETER_NAME;

    public final Column PARAMETER_VALUE;

    public static JobParameter staticInitialize(DataContext dataContext) {
        return new JobParameter(dataContext);
    }

    private JobParameter(DataContext dataContext) {
        super(dataContext, "job_parameters");
        this.ID = this.delegate.getColumnByName("id");
        this.JOB_ID = this.delegate.getColumnByName("job_id");
        this.PARAMETER_NAME = this.delegate.getColumnByName("parameter_name");
        this.PARAMETER_VALUE = this.delegate.getColumnByName("parameter_value");
    }

}
