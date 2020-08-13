package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MProductLoanCharge extends AbstractTable {

    public final Column PRODUCT_LOAN_ID;

    public final Column CHARGE_ID;

    public static MProductLoanCharge staticInitialize(DataContext dataContext) {
        return new MProductLoanCharge(dataContext);
    }

    private MProductLoanCharge(DataContext dataContext) {
        super(dataContext, "m_product_loan_charge");
        this.PRODUCT_LOAN_ID = this.delegate.getColumnByName("product_loan_id");
        this.CHARGE_ID = this.delegate.getColumnByName("charge_id");
    }

}