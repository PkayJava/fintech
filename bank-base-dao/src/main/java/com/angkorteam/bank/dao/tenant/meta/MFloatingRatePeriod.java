package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MFloatingRatePeriod extends AbstractTable {

    public final Column ID;

    public final Column FLOATING_RATE_ID;

    public final Column FROM_DATE;

    public final Column INTEREST_RATE;

    public final Column DIFFERENTIAL_TO_BASE_LENDING_RATE;

    public final Column ACTIVE;

    public final Column CREATED_BY_ID;

    public final Column CREATED_DATE;

    public final Column LAST_MODIFIED_BY_ID;

    public final Column LAST_MODIFIED_DATE;

    public static MFloatingRatePeriod staticInitialize(DataContext dataContext) {
        return new MFloatingRatePeriod(dataContext);
    }

    private MFloatingRatePeriod(DataContext dataContext) {
        super(dataContext, "m_floating_rates_periods");
        this.ID = getInternalColumnByName("id");
        this.FLOATING_RATE_ID = getInternalColumnByName("floating_rates_id");
        this.FROM_DATE = getInternalColumnByName("from_date");
        this.INTEREST_RATE = getInternalColumnByName("interest_rate");
        this.DIFFERENTIAL_TO_BASE_LENDING_RATE = getInternalColumnByName("is_differential_to_base_lending_rate");
        this.ACTIVE = getInternalColumnByName("is_active");
        this.CREATED_BY_ID = getInternalColumnByName("createdby_id");
        this.CREATED_DATE = getInternalColumnByName("created_date");
        this.LAST_MODIFIED_BY_ID = getInternalColumnByName("lastmodifiedby_id");
        this.LAST_MODIFIED_DATE = getInternalColumnByName("lastmodified_date");
    }

}
