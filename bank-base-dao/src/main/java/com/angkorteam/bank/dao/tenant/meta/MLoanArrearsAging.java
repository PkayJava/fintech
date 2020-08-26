package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MLoanArrearsAging extends AbstractTable {

    public final Column LOAN_ID;

    public final Column PRINCIPAL_OVERDUE_DERIVED;

    public final Column INTEREST_OVERDUE_DERIVED;

    public final Column FEE_CHARGE_OVERDUE_DERIVED;

    public final Column PENALTY_CHARGE_OVERDUE_DERIVED;

    public final Column TOTAL_OVERDUE_DERIVED;

    public final Column OVERDUE_SINCE_DATE_DERIVED;

    public static MLoanArrearsAging staticInitialize(DataContext dataContext) {
        return new MLoanArrearsAging(dataContext);
    }

    private MLoanArrearsAging(DataContext dataContext) {
        super(dataContext, "m_loan_arrears_aging");
        this.LOAN_ID = getInternalColumnByName("loan_id");
        this.PRINCIPAL_OVERDUE_DERIVED = getInternalColumnByName("principal_overdue_derived");
        this.INTEREST_OVERDUE_DERIVED = getInternalColumnByName("interest_overdue_derived");
        this.FEE_CHARGE_OVERDUE_DERIVED = getInternalColumnByName("fee_charges_overdue_derived");
        this.PENALTY_CHARGE_OVERDUE_DERIVED = getInternalColumnByName("penalty_charges_overdue_derived");
        this.TOTAL_OVERDUE_DERIVED = getInternalColumnByName("total_overdue_derived");
        this.OVERDUE_SINCE_DATE_DERIVED = getInternalColumnByName("overdue_since_date_derived");
    }

}
