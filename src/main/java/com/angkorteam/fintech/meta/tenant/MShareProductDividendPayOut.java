package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MShareProductDividendPayOut extends AbstractTable {

    public final Column ID;

    public final Column PRODUCT_ID;

    public final Column AMOUNT;

    public final Column DIVIDEND_PERIOD_START_DATE;

    public final Column DIVIDEND_PERIOD_END_DATE;

    public final Column STATUS;

    public final Column CREATED_BY_ID;

    public final Column CREATED_DATE;

    public final Column LAST_MODIFIED_BY_ID;

    public final Column LAST_MODIFIED_DATE;

    public static MShareProductDividendPayOut staticInitialize(DataContext dataContext) {
        return new MShareProductDividendPayOut(dataContext);
    }

    private MShareProductDividendPayOut(DataContext dataContext) {
        super(dataContext, "m_share_product_dividend_pay_out");
        this.ID = getInternalColumnByName("id");
        this.PRODUCT_ID = getInternalColumnByName("product_id");
        this.AMOUNT = getInternalColumnByName("amount");
        this.DIVIDEND_PERIOD_START_DATE = getInternalColumnByName("dividend_period_start_date");
        this.DIVIDEND_PERIOD_END_DATE = getInternalColumnByName("dividend_period_end_date");
        this.STATUS = getInternalColumnByName("status");
        this.CREATED_BY_ID = getInternalColumnByName("createdby_id");
        this.CREATED_DATE = getInternalColumnByName("created_date");
        this.LAST_MODIFIED_BY_ID = getInternalColumnByName("lastmodifiedby_id");
        this.LAST_MODIFIED_DATE = getInternalColumnByName("lastmodified_date");
    }

}
