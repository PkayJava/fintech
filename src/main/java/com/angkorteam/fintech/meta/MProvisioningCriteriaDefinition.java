package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MProvisioningCriteriaDefinition extends AbstractTable {

    public final Column ID;

    public final Column CRITERIA_ID;

    public final Column CATEGORY_ID;

    public final Column MIN_AGE;

    public final Column MAX_AGE;

    public final Column PROVISION_PERCENTAGE;

    public final Column LIABILITY_ACCOUNT;

    public final Column EXPENSE_ACCOUNT;

    public static MProvisioningCriteriaDefinition staticInitialize(DataContext dataContext) {
        return new MProvisioningCriteriaDefinition(dataContext);
    }

    private MProvisioningCriteriaDefinition(DataContext dataContext) {
        super(dataContext, "m_provisioning_criteria_definition");
        this.ID = this.delegate.getColumnByName("id");
        this.CRITERIA_ID = this.delegate.getColumnByName("criteria_id");
        this.CATEGORY_ID = this.delegate.getColumnByName("category_id");
        this.MIN_AGE = this.delegate.getColumnByName("min_age");
        this.MAX_AGE = this.delegate.getColumnByName("max_age");
        this.PROVISION_PERCENTAGE = this.delegate.getColumnByName("provision_percentage");
        this.LIABILITY_ACCOUNT = this.delegate.getColumnByName("liability_account");
        this.EXPENSE_ACCOUNT = this.delegate.getColumnByName("expense_account");
    }

}
