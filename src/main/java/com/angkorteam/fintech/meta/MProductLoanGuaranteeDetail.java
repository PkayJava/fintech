package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MProductLoanGuaranteeDetail extends AbstractTable {

    public final Column ID;

    public final Column LOAN_PRODUCT_ID;

    public final Column MANDATORY_GUARANTEE;

    public final Column MINIMUM_GUARANTEE_FROM_OWN_FUND;

    public final Column MINIMUM_GUARANTEE_FROM_GUARANTOR_FUND;

    public static MProductLoanGuaranteeDetail staticInitialize(DataContext dataContext) {
        return new MProductLoanGuaranteeDetail(dataContext);
    }

    private MProductLoanGuaranteeDetail(DataContext dataContext) {
        super(dataContext, "m_product_loan_guarantee_details");
        this.ID = this.delegate.getColumnByName("id");
        this.LOAN_PRODUCT_ID = this.delegate.getColumnByName("loan_product_id");
        this.MANDATORY_GUARANTEE = this.delegate.getColumnByName("mandatory_guarantee");
        this.MINIMUM_GUARANTEE_FROM_OWN_FUND = this.delegate.getColumnByName("minimum_guarantee_from_own_funds");
        this.MINIMUM_GUARANTEE_FROM_GUARANTOR_FUND = this.delegate.getColumnByName("minimum_guarantee_from_guarantor_funds");
    }

}
