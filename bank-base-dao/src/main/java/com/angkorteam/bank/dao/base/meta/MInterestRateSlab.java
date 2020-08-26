package com.angkorteam.bank.dao.base.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MInterestRateSlab extends AbstractTable {

    public final Column ID;

    public final Column INTEREST_RATE_CHART_ID;

    public final Column DESCRIPTION;

    public final Column PERIOD_TYPE_ENUM;

    public final Column FROM_PERIOD;

    public final Column TO_PERIOD;

    public final Column AMOUNT_RANGE_FROM;

    public final Column AMOUNT_RANGE_TO;

    public final Column ANNUAL_INTEREST_RATE;

    public final Column CURRENCY_CODE;

    public static MInterestRateSlab staticInitialize(DataContext dataContext) {
        return new MInterestRateSlab(dataContext);
    }

    private MInterestRateSlab(DataContext dataContext) {
        super(dataContext, "m_interest_rate_slab");
        this.ID = getInternalColumnByName("id");
        this.INTEREST_RATE_CHART_ID = getInternalColumnByName("interest_rate_chart_id");
        this.DESCRIPTION = getInternalColumnByName("description");
        this.PERIOD_TYPE_ENUM = getInternalColumnByName("period_type_enum");
        this.FROM_PERIOD = getInternalColumnByName("from_period");
        this.TO_PERIOD = getInternalColumnByName("to_period");
        this.AMOUNT_RANGE_FROM = getInternalColumnByName("amount_range_from");
        this.AMOUNT_RANGE_TO = getInternalColumnByName("amount_range_to");
        this.ANNUAL_INTEREST_RATE = getInternalColumnByName("annual_interest_rate");
        this.CURRENCY_CODE = getInternalColumnByName("currency_code");
    }

}
