package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.SAVING_ACCOUNT_ID = getInternalColumnByName("savings_account_id");
        this.AMOUNT = getInternalColumnByName("amount");
        this.TRANSACTION_TYPE_ENUM = getInternalColumnByName("transaction_type_enum");
        this.TRANSACTION_DATE = getInternalColumnByName("transaction_date");
        this.REVERSED = getInternalColumnByName("is_reversed");
        this.CREATED_DATE = getInternalColumnByName("created_date");
    }

}
