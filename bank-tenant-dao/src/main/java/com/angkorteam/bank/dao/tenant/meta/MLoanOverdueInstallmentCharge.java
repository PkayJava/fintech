package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MLoanOverdueInstallmentCharge extends AbstractTable {

    public final Column ID;

    public final Column LOAN_CHARGE_ID;

    public final Column LOAN_SCHEDULE_ID;

    public final Column FREQUENCY_NUMBER;

    public static MLoanOverdueInstallmentCharge staticInitialize(DataContext dataContext) {
        return new MLoanOverdueInstallmentCharge(dataContext);
    }

    private MLoanOverdueInstallmentCharge(DataContext dataContext) {
        super(dataContext, "m_loan_overdue_installment_charge");
        this.ID = getInternalColumnByName("id");
        this.LOAN_CHARGE_ID = getInternalColumnByName("loan_charge_id");
        this.LOAN_SCHEDULE_ID = getInternalColumnByName("loan_schedule_id");
        this.FREQUENCY_NUMBER = getInternalColumnByName("frequency_number");
    }

}
