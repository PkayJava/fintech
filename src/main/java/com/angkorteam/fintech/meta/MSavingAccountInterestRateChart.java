package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MSavingAccountInterestRateChart extends AbstractTable {

    public final Column ID;

    public final Column SAVING_ACCOUNT_ID;

    public final Column NAME;

    public final Column DESCRIPTION;

    public final Column FROM_DATE;

    public final Column END_DATE;

    public final Column PRIMARY_GROUPING_BY_AMOUNT;

    public static MSavingAccountInterestRateChart staticInitialize(DataContext dataContext) {
        return new MSavingAccountInterestRateChart(dataContext);
    }

    private MSavingAccountInterestRateChart(DataContext dataContext) {
        super(dataContext, "m_savings_account_interest_rate_chart");
        this.ID = this.delegate.getColumnByName("id");
        this.SAVING_ACCOUNT_ID = this.delegate.getColumnByName("savings_account_id");
        this.NAME = this.delegate.getColumnByName("name");
        this.DESCRIPTION = this.delegate.getColumnByName("description");
        this.FROM_DATE = this.delegate.getColumnByName("from_date");
        this.END_DATE = this.delegate.getColumnByName("end_date");
        this.PRIMARY_GROUPING_BY_AMOUNT = this.delegate.getColumnByName("is_primary_grouping_by_amount");
    }

}
