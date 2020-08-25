package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MLoanDisbursementDetail extends AbstractTable {

    public final Column ID;

    public final Column LOAN_ID;

    public final Column EXPECTED_DISBURSE_DATE;

    public final Column DISBURSED_ON_DATE;

    public final Column PRINCIPAL;

    public static MLoanDisbursementDetail staticInitialize(DataContext dataContext) {
        return new MLoanDisbursementDetail(dataContext);
    }

    private MLoanDisbursementDetail(DataContext dataContext) {
        super(dataContext, "m_loan_disbursement_detail");
        this.ID = getInternalColumnByName("id");
        this.LOAN_ID = getInternalColumnByName("loan_id");
        this.EXPECTED_DISBURSE_DATE = getInternalColumnByName("expected_disburse_date");
        this.DISBURSED_ON_DATE = getInternalColumnByName("disbursedon_date");
        this.PRINCIPAL = getInternalColumnByName("principal");
    }

}
