package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MInterestRateChart extends AbstractTable {

    public final Column ID;

    public final Column NAME;

    public final Column DESCRIPTION;

    public final Column FROM_DATE;

    public final Column END_DATE;

    public final Column PRIMARY_GROUPING_BY_AMOUNT;

    public static MInterestRateChart staticInitialize(DataContext dataContext) {
        return new MInterestRateChart(dataContext);
    }

    private MInterestRateChart(DataContext dataContext) {
        super(dataContext, "m_interest_rate_chart");
        this.ID = this.delegate.getColumnByName("id");
        this.NAME = this.delegate.getColumnByName("name");
        this.DESCRIPTION = this.delegate.getColumnByName("description");
        this.FROM_DATE = this.delegate.getColumnByName("from_date");
        this.END_DATE = this.delegate.getColumnByName("end_date");
        this.PRIMARY_GROUPING_BY_AMOUNT = this.delegate.getColumnByName("is_primary_grouping_by_amount");
    }

}
