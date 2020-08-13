package com.angkorteam.fintech.meta;

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
        this.ID = this.delegate.getColumnByName("id");
        this.LOAN_ID = this.delegate.getColumnByName("loan_id");
        this.EXPECTED_DISBURSE_DATE = this.delegate.getColumnByName("expected_disburse_date");
        this.DISBURSED_ON_DATE = this.delegate.getColumnByName("disbursedon_date");
        this.PRINCIPAL = this.delegate.getColumnByName("principal");
    }

}
