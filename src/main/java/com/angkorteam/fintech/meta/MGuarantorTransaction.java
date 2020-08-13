package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MGuarantorTransaction extends AbstractTable {

    public final Column ID;

    public final Column GUARANTOR_FUND_DETAIL_ID;

    public final Column LOAN_TRANSACTION_ID;

    public final Column DEPOSIT_ON_HOLD_TRANSACTION_ID;

    public final Column REVERSED;

    public static MGuarantorTransaction staticInitialize(DataContext dataContext) {
        return new MGuarantorTransaction(dataContext);
    }

    private MGuarantorTransaction(DataContext dataContext) {
        super(dataContext, "m_guarantor_transaction");
        this.ID = this.delegate.getColumnByName("id");
        this.GUARANTOR_FUND_DETAIL_ID = this.delegate.getColumnByName("guarantor_fund_detail_id");
        this.LOAN_TRANSACTION_ID = this.delegate.getColumnByName("loan_transaction_id");
        this.DEPOSIT_ON_HOLD_TRANSACTION_ID = this.delegate.getColumnByName("deposit_on_hold_transaction_id");
        this.REVERSED = this.delegate.getColumnByName("is_reversed");
    }

}
