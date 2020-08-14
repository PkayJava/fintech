package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.SAVING_PRODUCT_ID = getInternalColumnByName("savings_product_id");
        this.MANDATORY = getInternalColumnByName("is_mandatory");
        this.ALLOW_WITHDRAWAL = getInternalColumnByName("allow_withdrawal");
        this.ADJUST_ADVANCE_TOWARD_FUTURE_PAYMENT = getInternalColumnByName("adjust_advance_towards_future_payments");
    }

}
