package com.angkorteam.bank.dao.base.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MLoanTrancheDisbursementCharge extends AbstractTable {

    public final Column ID;

    public final Column LOAN_CHARGE_ID;

    public final Column DISBURSEMENT_DETAIL_ID;

    public static MLoanTrancheDisbursementCharge staticInitialize(DataContext dataContext) {
        return new MLoanTrancheDisbursementCharge(dataContext);
    }

    private MLoanTrancheDisbursementCharge(DataContext dataContext) {
        super(dataContext, "m_loan_tranche_disbursement_charge");
        this.ID = getInternalColumnByName("id");
        this.LOAN_CHARGE_ID = getInternalColumnByName("loan_charge_id");
        this.DISBURSEMENT_DETAIL_ID = getInternalColumnByName("disbursement_detail_id");
    }

}
