package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class StretchyReportParameter extends AbstractTable {

    public final Column ID;

    public final Column REPORT_ID;

    public final Column PARAMETER_ID;

    public final Column REPORT_PARAMETER_NAME;

    public static StretchyReportParameter staticInitialize(DataContext dataContext) {
        return new StretchyReportParameter(dataContext);
    }

    private StretchyReportParameter(DataContext dataContext) {
        super(dataContext, "stretchy_report_parameter");
        this.ID = getInternalColumnByName("id");
        this.REPORT_ID = getInternalColumnByName("report_id");
        this.PARAMETER_ID = getInternalColumnByName("parameter_id");
        this.REPORT_PARAMETER_NAME = getInternalColumnByName("report_parameter_name");
    }

}
