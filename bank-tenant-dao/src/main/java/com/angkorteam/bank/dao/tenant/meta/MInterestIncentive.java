package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MInterestIncentive extends AbstractTable {

    public final Column ID;

    public final Column INTEREST_RATE_SLAB_ID;

    public final Column ENTRY_TYPE;

    public final Column ATTRIBUTE_NAME;

    public final Column CONDITION_TYPE;

    public final Column ATTRIBUTE_VALUE;

    public final Column INCENTIVE_TYPE;

    public final Column AMOUNT;

    public static MInterestIncentive staticInitialize(DataContext dataContext) {
        return new MInterestIncentive(dataContext);
    }

    private MInterestIncentive(DataContext dataContext) {
        super(dataContext, "m_interest_incentives");
        this.ID = getInternalColumnByName("id");
        this.INTEREST_RATE_SLAB_ID = getInternalColumnByName("interest_rate_slab_id");
        this.ENTRY_TYPE = getInternalColumnByName("entiry_type");
        this.ATTRIBUTE_NAME = getInternalColumnByName("attribute_name");
        this.CONDITION_TYPE = getInternalColumnByName("condition_type");
        this.ATTRIBUTE_VALUE = getInternalColumnByName("attribute_value");
        this.INCENTIVE_TYPE = getInternalColumnByName("incentive_type");
        this.AMOUNT = getInternalColumnByName("amount");
    }

}
