package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MProductLoanFloatingRate extends AbstractTable {

    public final Column ID;

    public final Column LOAN_PRODUCT_ID;

    public final Column FLOATING_RATE_ID;

    public final Column INTEREST_RATE_DIFFERENTIAL;

    public final Column MIN_DIFFERENTIAL_LENDING_RATE;

    public final Column DEFAULT_DIFFERENTIAL_LENDING_RATE;

    public final Column MAX_DIFFERENTIAL_LENDING_RATE;

    public final Column FLOATING_INTEREST_RATE_CALCULATION_ALLOWED;

    public static MProductLoanFloatingRate staticInitialize(DataContext dataContext) {
        return new MProductLoanFloatingRate(dataContext);
    }

    private MProductLoanFloatingRate(DataContext dataContext) {
        super(dataContext, "m_product_loan_floating_rates");
        this.ID = getInternalColumnByName("id");
        this.LOAN_PRODUCT_ID = getInternalColumnByName("loan_product_id");
        this.FLOATING_RATE_ID = getInternalColumnByName("floating_rates_id");
        this.INTEREST_RATE_DIFFERENTIAL = getInternalColumnByName("interest_rate_differential");
        this.MIN_DIFFERENTIAL_LENDING_RATE = getInternalColumnByName("min_differential_lending_rate");
        this.DEFAULT_DIFFERENTIAL_LENDING_RATE = getInternalColumnByName("default_differential_lending_rate");
        this.MAX_DIFFERENTIAL_LENDING_RATE = getInternalColumnByName("max_differential_lending_rate");
        this.FLOATING_INTEREST_RATE_CALCULATION_ALLOWED = getInternalColumnByName("is_floating_interest_rate_calculation_allowed");
    }

}
