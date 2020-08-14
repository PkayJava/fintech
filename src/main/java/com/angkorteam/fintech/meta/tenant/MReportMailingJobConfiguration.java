package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MReportMailingJobConfiguration extends AbstractTable {

    public final Column ID;

    public final Column NAME;

    public final Column VALUE;

    public static MReportMailingJobConfiguration staticInitialize(DataContext dataContext) {
        return new MReportMailingJobConfiguration(dataContext);
    }

    private MReportMailingJobConfiguration(DataContext dataContext) {
        super(dataContext, "m_report_mailing_job_configuration");
        this.ID = getInternalColumnByName("id");
        this.NAME = getInternalColumnByName("name");
        this.VALUE = getInternalColumnByName("value");
    }

}
