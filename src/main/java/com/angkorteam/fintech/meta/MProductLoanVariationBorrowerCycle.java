package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MProductLoanVariationBorrowerCycle extends AbstractTable {

    public final Column ID;

    public final Column LOAN_PRODUCT_ID;

    public final Column BORROWER_CYCLE_NUMBER;

    public final Column VALUE_CONDITION;

    public final Column PARAM_TYPE;

    public final Column DEFAULT_VALUE;

    public final Column MAX_VALUE;

    public final Column MIN_VALUE;

    public static MProductLoanVariationBorrowerCycle staticInitialize(DataContext dataContext) {
        return new MProductLoanVariationBorrowerCycle(dataContext);
    }

    private MProductLoanVariationBorrowerCycle(DataContext dataContext) {
        super(dataContext, "m_product_loan_variations_borrower_cycle");
        this.ID = this.delegate.getColumnByName("id");
        this.LOAN_PRODUCT_ID = this.delegate.getColumnByName("loan_product_id");
        this.BORROWER_CYCLE_NUMBER = this.delegate.getColumnByName("borrower_cycle_number");
        this.VALUE_CONDITION = this.delegate.getColumnByName("value_condition");
        this.PARAM_TYPE = this.delegate.getColumnByName("param_type");
        this.DEFAULT_VALUE = this.delegate.getColumnByName("default_value");
        this.MAX_VALUE = this.delegate.getColumnByName("max_value");
        this.MIN_VALUE = this.delegate.getColumnByName("min_value");
    }

}
