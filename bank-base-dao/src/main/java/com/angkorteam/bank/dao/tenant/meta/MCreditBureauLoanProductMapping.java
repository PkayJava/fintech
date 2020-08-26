package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.metamodel.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.ORGANISATION_CREDIT_BUREAU_ID = getInternalColumnByName("organisation_creditbureau_id");
        this.LOAN_PRODUCT_ID = getInternalColumnByName("loan_product_id");
        this.CREDIT_CHECK_MANDATORY = getInternalColumnByName("is_creditcheck_mandatory");
        this.SKIP_CREDIT_CHECK_IN_FAILURE = getInternalColumnByName("skip_creditcheck_in_failure");
        this.STALE_PERIOD = getInternalColumnByName("stale_period");
        this.ACTIVE = getInternalColumnByName("is_active");
    }

}
