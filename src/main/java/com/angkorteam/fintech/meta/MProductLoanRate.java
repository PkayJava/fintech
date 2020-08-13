package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MProductLoanRate extends AbstractTable {

    public final Column PRODUCT_LOAN_ID;

    public final Column RATE_ID;

    public static MProductLoanRate staticInitialize(DataContext dataContext) {
        return new MProductLoanRate(dataContext);
    }

    private MProductLoanRate(DataContext dataContext) {
        super(dataContext, "m_product_loan_rate");
        this.PRODUCT_LOAN_ID = this.delegate.getColumnByName("product_loan_id");
        this.RATE_ID = this.delegate.getColumnByName("rate_id");
    }

}
