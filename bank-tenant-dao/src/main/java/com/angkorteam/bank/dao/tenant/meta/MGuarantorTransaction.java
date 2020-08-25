package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.GUARANTOR_FUND_DETAIL_ID = getInternalColumnByName("guarantor_fund_detail_id");
        this.LOAN_TRANSACTION_ID = getInternalColumnByName("loan_transaction_id");
        this.DEPOSIT_ON_HOLD_TRANSACTION_ID = getInternalColumnByName("deposit_on_hold_transaction_id");
        this.REVERSED = getInternalColumnByName("is_reversed");
    }

}
