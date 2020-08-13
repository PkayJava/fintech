package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MSavingInterestIncentive extends AbstractTable {

    public final Column ID;

    public final Column DEPOSIT_ACCOUNT_INTEREST_RATE_SLAB_ID;

    public final Column ENTRY_TYPE;

    public final Column ATTRIBUTE_NAME;

    public final Column CONDITION_TYPE;

    public final Column ATTRIBUTE_VALUE;

    public final Column INCENTIVE_TYPE;

    public final Column AMOUNT;

    public static MSavingInterestIncentive staticInitialize(DataContext dataContext) {
        return new MSavingInterestIncentive(dataContext);
    }

    private MSavingInterestIncentive(DataContext dataContext) {
        super(dataContext, "m_savings_interest_incentives");
        this.ID = this.delegate.getColumnByName("id");
        this.DEPOSIT_ACCOUNT_INTEREST_RATE_SLAB_ID = this.delegate.getColumnByName("deposit_account_interest_rate_slab_id");
        this.ENTRY_TYPE = this.delegate.getColumnByName("entiry_type");
        this.ATTRIBUTE_NAME = this.delegate.getColumnByName("attribute_name");
        this.CONDITION_TYPE = this.delegate.getColumnByName("condition_type");
        this.ATTRIBUTE_VALUE = this.delegate.getColumnByName("attribute_value");
        this.INCENTIVE_TYPE = this.delegate.getColumnByName("incentive_type");
        this.AMOUNT = this.delegate.getColumnByName("amount");
    }

}
