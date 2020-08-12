package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MDepositAccountOnHoldTransaction extends AbstractTable {

    public final Column ID;

    public final Column SAVING_ACCOUNT_ID;

    public final Column AMOUNT;

    public final Column TRANSACTION_TYPE_ENUM;

    public final Column TRANSACTION_DATE;

    public final Column REVERSED;

    public final Column CREATED_DATE;

    public static MDepositAccountOnHoldTransaction staticInitialize(DataContext dataContext) {
        return new MDepositAccountOnHoldTransaction(dataContext);
    }

    private MDepositAccountOnHoldTransaction(DataContext dataContext) {
        super(dataContext, "m_deposit_account_on_hold_transaction");
        this.ID = this.delegate.getColumnByName("id");
        this.SAVING_ACCOUNT_ID = this.delegate.getColumnByName("savings_account_id");
        this.AMOUNT = this.delegate.getColumnByName("amount");
        this.TRANSACTION_TYPE_ENUM = this.delegate.getColumnByName("transaction_type_enum");
        this.TRANSACTION_DATE = this.delegate.getColumnByName("transaction_date");
        this.REVERSED = this.delegate.getColumnByName("is_reversed");
        this.CREATED_DATE = this.delegate.getColumnByName("created_date");
    }

}
