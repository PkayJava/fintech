package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MLoanRepaymentScheduleHistory extends AbstractTable {

    public final Column ID;

    public final Column LOAN_ID;

    public final Column FROM_DATE;

    public final Column DUE_DATE;

    public final Column INSTALLMENT;

    public final Column PRINCIPAL_AMOUNT;

    public final Column INTEREST_AMOUNT;

    public final Column FEE_CHARGE_AMOUNT;

    public final Column PENALTY_CHARGE_AMOUNT;

    public final Column CREATED_BY_ID;

    public final Column CREATED_DATE;

    public final Column LAST_MODIFIED_DATE;

    public final Column LAST_MODIFIED_BY_ID;

    public final Column VERSION;

    public static MLoanRepaymentScheduleHistory staticInitialize(DataContext dataContext) {
        return new MLoanRepaymentScheduleHistory(dataContext);
    }

    private MLoanRepaymentScheduleHistory(DataContext dataContext) {
        super(dataContext, "m_loan_repayment_schedule_history");
        this.ID = getInternalColumnByName("id");
        this.LOAN_ID = getInternalColumnByName("loan_id");
        this.FROM_DATE = getInternalColumnByName("fromdate");
        this.DUE_DATE = getInternalColumnByName("duedate");
        this.INSTALLMENT = getInternalColumnByName("installment");
        this.PRINCIPAL_AMOUNT = getInternalColumnByName("principal_amount");
        this.INTEREST_AMOUNT = getInternalColumnByName("interest_amount");
        this.FEE_CHARGE_AMOUNT = getInternalColumnByName("fee_charges_amount");
        this.PENALTY_CHARGE_AMOUNT = getInternalColumnByName("penalty_charges_amount");
        this.CREATED_BY_ID = getInternalColumnByName("createdby_id");
        this.CREATED_DATE = getInternalColumnByName("created_date");
        this.LAST_MODIFIED_DATE = getInternalColumnByName("lastmodified_date");
        this.LAST_MODIFIED_BY_ID = getInternalColumnByName("lastmodifiedby_id");
        this.VERSION = getInternalColumnByName("version");
    }

}
