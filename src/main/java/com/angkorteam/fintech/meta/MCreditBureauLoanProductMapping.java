package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MCreditBureauLoanProductMapping extends AbstractTable {

    public final Column ID;

    public final Column ORGANISATION_CREDIT_BUREAU_ID;

    public final Column LOAN_PRODUCT_ID;

    public final Column CREDIT_CHECK_MANDATORY;

    public final Column SKIP_CREDIT_CHECK_IN_FAILURE;

    public final Column STALE_PERIOD;

    public final Column ACTIVE;

    public static MCreditBureauLoanProductMapping staticInitialize(DataContext dataContext) {
        return new MCreditBureauLoanProductMapping(dataContext);
    }

    private MCreditBureauLoanProductMapping(DataContext dataContext) {
        super(dataContext, "m_creditbureau_loanproduct_mapping");
        this.ID = this.delegate.getColumnByName("id");
        this.ORGANISATION_CREDIT_BUREAU_ID = this.delegate.getColumnByName("organisation_creditbureau_id");
        this.LOAN_PRODUCT_ID = this.delegate.getColumnByName("loan_product_id");
        this.CREDIT_CHECK_MANDATORY = this.delegate.getColumnByName("is_creditcheck_mandatory");
        this.SKIP_CREDIT_CHECK_IN_FAILURE = this.delegate.getColumnByName("skip_creditcheck_in_failure");
        this.STALE_PERIOD = this.delegate.getColumnByName("stale_period");
        this.ACTIVE = this.delegate.getColumnByName("is_active");
    }

}
