package com.angkorteam.fintech.meta;

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
        this.ID = this.delegate.getColumnByName("id");
        this.NAME = this.delegate.getColumnByName("name");
        this.VALUE = this.delegate.getColumnByName("value");
    }

}
