package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MDepositProductRecurringDetail extends AbstractTable {

    public final Column ID;

    public final Column SAVING_PRODUCT_ID;

    public final Column MANDATORY;

    public final Column ALLOW_WITHDRAWAL;

    public final Column ADJUST_ADVANCE_TOWARD_FUTURE_PAYMENT;

    public static MDepositProductRecurringDetail staticInitialize(DataContext dataContext) {
        return new MDepositProductRecurringDetail(dataContext);
    }

    private MDepositProductRecurringDetail(DataContext dataContext) {
        super(dataContext, "m_deposit_product_recurring_detail");
        this.ID = this.delegate.getColumnByName("id");
        this.SAVING_PRODUCT_ID = this.delegate.getColumnByName("savings_product_id");
        this.MANDATORY = this.delegate.getColumnByName("is_mandatory");
        this.ALLOW_WITHDRAWAL = this.delegate.getColumnByName("allow_withdrawal");
        this.ADJUST_ADVANCE_TOWARD_FUTURE_PAYMENT = this.delegate.getColumnByName("adjust_advance_towards_future_payments");
    }

}
