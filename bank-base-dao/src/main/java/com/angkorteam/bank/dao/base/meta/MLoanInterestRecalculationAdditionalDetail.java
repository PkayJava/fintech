package com.angkorteam.bank.dao.base.meta;

import com.angkorteam.metamodel.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.AMOUNT = getInternalColumnByName("amount");
        this.EFFECTIVE_DATE = getInternalColumnByName("effective_date");
        this.LOAN_REPAYMENT_SCHEDULE_ID = getInternalColumnByName("loan_repayment_schedule_id");
    }

}
