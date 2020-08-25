package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MLoanChargePaidBy extends AbstractTable {

    public final Column ID;

    public final Column LOAN_TRANSACTION_ID;

    public final Column LOAN_CHARGE_ID;

    public final Column AMOUNT;

    public final Column INSTALLMENT_NUMBER;

    public static MLoanChargePaidBy staticInitialize(DataContext dataContext) {
        return new MLoanChargePaidBy(dataContext);
    }

    private MLoanChargePaidBy(DataContext dataContext) {
        super(dataContext, "m_loan_charge_paid_by");
        this.ID = getInternalColumnByName("id");
        this.LOAN_TRANSACTION_ID = getInternalColumnByName("loan_transaction_id");
        this.LOAN_CHARGE_ID = getInternalColumnByName("loan_charge_id");
        this.AMOUNT = getInternalColumnByName("amount");
        this.INSTALLMENT_NUMBER = getInternalColumnByName("installment_number");
    }

}
