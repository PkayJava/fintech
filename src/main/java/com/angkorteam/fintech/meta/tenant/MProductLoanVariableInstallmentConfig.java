package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MProductLoanVariableInstallmentConfig extends AbstractTable {

    public final Column ID;

    public final Column LOAN_PRODUCT_ID;

    public final Column MINIMUM_GAP;

    public final Column MAXIMUM_GAP;

    public static MProductLoanVariableInstallmentConfig staticInitialize(DataContext dataContext) {
        return new MProductLoanVariableInstallmentConfig(dataContext);
    }

    private MProductLoanVariableInstallmentConfig(DataContext dataContext) {
        super(dataContext, "m_product_loan_variable_installment_config");
        this.ID = getInternalColumnByName("id");
        this.LOAN_PRODUCT_ID = getInternalColumnByName("loan_product_id");
        this.MINIMUM_GAP = getInternalColumnByName("minimum_gap");
        this.MAXIMUM_GAP = getInternalColumnByName("maximum_gap");
    }

}
