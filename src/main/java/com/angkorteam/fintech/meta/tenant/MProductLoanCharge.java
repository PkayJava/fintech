package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
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
        this.PRODUCT_LOAN_ID = getInternalColumnByName("product_loan_id");
        this.CHARGE_ID = getInternalColumnByName("charge_id");
    }

}
