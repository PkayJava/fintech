package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.SAVING_ACCOUNT_ID = getInternalColumnByName("savings_account_id");
        this.NAME = getInternalColumnByName("name");
        this.DESCRIPTION = getInternalColumnByName("description");
        this.FROM_DATE = getInternalColumnByName("from_date");
        this.END_DATE = getInternalColumnByName("end_date");
        this.PRIMARY_GROUPING_BY_AMOUNT = getInternalColumnByName("is_primary_grouping_by_amount");
    }

}
