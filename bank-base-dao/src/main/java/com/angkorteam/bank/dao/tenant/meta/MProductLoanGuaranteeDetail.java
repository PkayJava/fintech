package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.metamodel.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.LOAN_PRODUCT_ID = getInternalColumnByName("loan_product_id");
        this.MANDATORY_GUARANTEE = getInternalColumnByName("mandatory_guarantee");
        this.MINIMUM_GUARANTEE_FROM_OWN_FUND = getInternalColumnByName("minimum_guarantee_from_own_funds");
        this.MINIMUM_GUARANTEE_FROM_GUARANTOR_FUND = getInternalColumnByName("minimum_guarantee_from_guarantor_funds");
    }

}
