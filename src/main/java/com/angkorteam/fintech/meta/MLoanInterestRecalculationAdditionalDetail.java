package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MLoanInterestRecalculationAdditionalDetail extends AbstractTable {

    public final Column ID;

    public final Column LOAN_REPAYMENT_SCHEDULE_ID;

    public final Column EFFECTIVE_DATE;

    public final Column AMOUNT;

    public static MLoanInterestRecalculationAdditionalDetail staticInitialize(DataContext dataContext) {
        return new MLoanInterestRecalculationAdditionalDetail(dataContext);
    }

    private MLoanInterestRecalculationAdditionalDetail(DataContext dataContext) {
        super(dataContext, "m_loan_interest_recalculation_additional_details");
        this.ID = this.delegate.getColumnByName("id");
        this.AMOUNT = this.delegate.getColumnByName("amount");
        this.EFFECTIVE_DATE = this.delegate.getColumnByName("effective_date");
        this.LOAN_REPAYMENT_SCHEDULE_ID = this.delegate.getColumnByName("loan_repayment_schedule_id");
    }

}
