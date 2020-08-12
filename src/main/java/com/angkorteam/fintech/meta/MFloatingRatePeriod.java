package com.angkorteam.fintech.meta;

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
        this.ID = this.delegate.getColumnByName("id");
        this.FLOATING_RATE_ID = this.delegate.getColumnByName("floating_rates_id");
        this.FROM_DATE = this.delegate.getColumnByName("from_date");
        this.INTEREST_RATE = this.delegate.getColumnByName("interest_rate");
        this.DIFFERENTIAL_TO_BASE_LENDING_RATE = this.delegate.getColumnByName("is_differential_to_base_lending_rate");
        this.ACTIVE = this.delegate.getColumnByName("is_active");
        this.CREATED_BY_ID = this.delegate.getColumnByName("createdby_id");
        this.CREATED_DATE = this.delegate.getColumnByName("created_date");
        this.LAST_MODIFIED_BY_ID = this.delegate.getColumnByName("lastmodifiedby_id");
        this.LAST_MODIFIED_DATE = this.delegate.getColumnByName("lastmodified_date");
    }

}
