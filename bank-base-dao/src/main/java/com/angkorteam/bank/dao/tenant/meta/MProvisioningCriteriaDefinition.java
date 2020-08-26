package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.metamodel.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.CRITERIA_ID = getInternalColumnByName("criteria_id");
        this.CATEGORY_ID = getInternalColumnByName("category_id");
        this.MIN_AGE = getInternalColumnByName("min_age");
        this.MAX_AGE = getInternalColumnByName("max_age");
        this.PROVISION_PERCENTAGE = getInternalColumnByName("provision_percentage");
        this.LIABILITY_ACCOUNT = getInternalColumnByName("liability_account");
        this.EXPENSE_ACCOUNT = getInternalColumnByName("expense_account");
    }

}
