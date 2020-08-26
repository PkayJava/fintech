package com.angkorteam.bank.dao.base.meta;

import com.angkorteam.metamodel.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.JOB_ID = getInternalColumnByName("job_id");
        this.PARAMETER_NAME = getInternalColumnByName("parameter_name");
        this.PARAMETER_VALUE = getInternalColumnByName("parameter_value");
    }

}
