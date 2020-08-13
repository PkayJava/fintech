package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MSavingAccountInterestRateSlab extends AbstractTable {

    public final Column ID;

    public final Column DESCRIPTION;

    public final Column SAVING_ACCOUNT_INTEREST_RATE_CHART_ID;

    public final Column PERIOD_TYPE_ENUM;

    public final Column FROM_PERIOD;

    public final Column TO_PERIOD;

    public final Column AMOUNT_RANGE_FROM;

    public final Column AMOUNT_RANGE_TO;

    public final Column ANNUAL_INTEREST_RATE;

    public final Column CURRENCY_CODE;

    public static MSavingAccountInterestRateSlab staticInitialize(DataContext dataContext) {
        return new MSavingAccountInterestRateSlab(dataContext);
    }

    private MSavingAccountInterestRateSlab(DataContext dataContext) {
        super(dataContext, "m_savings_account_interest_rate_slab");
        this.ID = this.delegate.getColumnByName("id");
        this.DESCRIPTION = this.delegate.getColumnByName("description");
        this.SAVING_ACCOUNT_INTEREST_RATE_CHART_ID = this.delegate.getColumnByName("savings_account_interest_rate_chart_id");
        this.PERIOD_TYPE_ENUM = this.delegate.getColumnByName("period_type_enum");
        this.FROM_PERIOD = this.delegate.getColumnByName("from_period");
        this.TO_PERIOD = this.delegate.getColumnByName("to_period");
        this.AMOUNT_RANGE_FROM = this.delegate.getColumnByName("amount_range_from");
        this.AMOUNT_RANGE_TO = this.delegate.getColumnByName("amount_range_to");
        this.ANNUAL_INTEREST_RATE = this.delegate.getColumnByName("annual_interest_rate");
        this.CURRENCY_CODE = this.delegate.getColumnByName("currency_code");
    }

}
