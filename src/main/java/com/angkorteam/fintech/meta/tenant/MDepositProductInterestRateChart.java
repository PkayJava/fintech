package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MDepositProductInterestRateChart extends AbstractTable {

    public final Column DEPOSIT_PRODUCT_ID;

    public final Column INTEREST_RATE_CHART_ID;

    public static MDepositProductInterestRateChart staticInitialize(DataContext dataContext) {
        return new MDepositProductInterestRateChart(dataContext);
    }

    private MDepositProductInterestRateChart(DataContext dataContext) {
        super(dataContext, "m_deposit_product_interest_rate_chart");
        this.DEPOSIT_PRODUCT_ID = getInternalColumnByName("deposit_product_id");
        this.INTEREST_RATE_CHART_ID = getInternalColumnByName("interest_rate_chart_id");
    }

}
