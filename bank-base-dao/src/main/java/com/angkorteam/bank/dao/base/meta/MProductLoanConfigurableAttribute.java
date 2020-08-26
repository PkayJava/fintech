package com.angkorteam.bank.dao.base.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MProductLoanConfigurableAttribute extends AbstractTable {

    public final Column ID;

    public final Column LOAN_PRODUCT_ID;

    public final Column AMORTIZATION_METHOD_ENUM;

    public final Column INTEREST_METHOD_ENUM;

    public final Column LOAN_TRANSACTION_STRATEGY_ID;

    public final Column INTEREST_CALCULATED_IN_PERIOD_ENUM;

    public final Column ARREARS_TOLERANCE_AMOUNT;

    public final Column REPAY_EVERY;

    public final Column MORATORIUM;

    public final Column GRACE_ON_ARREARS_AGEING;

    public static MProductLoanConfigurableAttribute staticInitialize(DataContext dataContext) {
        return new MProductLoanConfigurableAttribute(dataContext);
    }

    private MProductLoanConfigurableAttribute(DataContext dataContext) {
        super(dataContext, "m_product_loan_configurable_attributes");
        this.ID = getInternalColumnByName("id");
        this.LOAN_PRODUCT_ID = getInternalColumnByName("loan_product_id");
        this.AMORTIZATION_METHOD_ENUM = getInternalColumnByName("amortization_method_enum");
        this.INTEREST_METHOD_ENUM = getInternalColumnByName("interest_method_enum");
        this.LOAN_TRANSACTION_STRATEGY_ID = getInternalColumnByName("loan_transaction_strategy_id");
        this.INTEREST_CALCULATED_IN_PERIOD_ENUM = getInternalColumnByName("interest_calculated_in_period_enum");
        this.ARREARS_TOLERANCE_AMOUNT = getInternalColumnByName("arrearstolerance_amount");
        this.REPAY_EVERY = getInternalColumnByName("repay_every");
        this.MORATORIUM = getInternalColumnByName("moratorium");
        this.GRACE_ON_ARREARS_AGEING = getInternalColumnByName("grace_on_arrears_ageing");
    }

}
