package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MDepositAccountRecurringDetail extends AbstractTable {

    public final Column ID;

    public final Column SAVING_ACCOUNT_ID;

    public final Column MANDATORY_RECOMMENDED_DEPOSIT_AMOUNT;

    public final Column MANDATORY;

    public final Column ALLOW_WITHDRAWAL;

    public final Column ADJUST_ADVANCE_TOWARD_FUTURE_PAYMENT;

    public final Column CALENDAR_INHERITED;

    public final Column TOTAL_OVERDUE_AMOUNT;

    public final Column NO_OF_OVERDUE_INSTALLMENT;

    public static MDepositAccountRecurringDetail staticInitialize(DataContext dataContext) {
        return new MDepositAccountRecurringDetail(dataContext);
    }

    private MDepositAccountRecurringDetail(DataContext dataContext) {
        super(dataContext, "m_deposit_account_recurring_detail");
        this.ID = getInternalColumnByName("id");
        this.SAVING_ACCOUNT_ID = getInternalColumnByName("savings_account_id");
        this.MANDATORY_RECOMMENDED_DEPOSIT_AMOUNT = getInternalColumnByName("mandatory_recommended_deposit_amount");
        this.MANDATORY = getInternalColumnByName("is_mandatory");
        this.ALLOW_WITHDRAWAL = getInternalColumnByName("allow_withdrawal");
        this.ADJUST_ADVANCE_TOWARD_FUTURE_PAYMENT = getInternalColumnByName("adjust_advance_towards_future_payments");
        this.CALENDAR_INHERITED = getInternalColumnByName("is_calendar_inherited");
        this.TOTAL_OVERDUE_AMOUNT = getInternalColumnByName("total_overdue_amount");
        this.NO_OF_OVERDUE_INSTALLMENT = getInternalColumnByName("no_of_overdue_installments");
    }

}
