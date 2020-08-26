package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.metamodel.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.LOAN_PRODUCT_ID = getInternalColumnByName("loan_product_id");
        this.BORROWER_CYCLE_NUMBER = getInternalColumnByName("borrower_cycle_number");
        this.VALUE_CONDITION = getInternalColumnByName("value_condition");
        this.PARAM_TYPE = getInternalColumnByName("param_type");
        this.DEFAULT_VALUE = getInternalColumnByName("default_value");
        this.MAX_VALUE = getInternalColumnByName("max_value");
        this.MIN_VALUE = getInternalColumnByName("min_value");
    }

}
