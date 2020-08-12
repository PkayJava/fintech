package com.angkorteam.fintech.meta;

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
        this.ID = this.delegate.getColumnByName("id");
        this.SAVING_ACCOUNT_ID = this.delegate.getColumnByName("savings_account_id");
        this.MANDATORY_RECOMMENDED_DEPOSIT_AMOUNT = this.delegate.getColumnByName("mandatory_recommended_deposit_amount");
        this.MANDATORY = this.delegate.getColumnByName("is_mandatory");
        this.ALLOW_WITHDRAWAL = this.delegate.getColumnByName("allow_withdrawal");
        this.ADJUST_ADVANCE_TOWARD_FUTURE_PAYMENT = this.delegate.getColumnByName("adjust_advance_towards_future_payments");
        this.CALENDAR_INHERITED = this.delegate.getColumnByName("is_calendar_inherited");
        this.TOTAL_OVERDUE_AMOUNT = this.delegate.getColumnByName("total_overdue_amount");
        this.NO_OF_OVERDUE_INSTALLMENT = this.delegate.getColumnByName("no_of_overdue_installments");
    }

}
