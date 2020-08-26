package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MAccountTransferTransaction extends AbstractTable {

    public final Column ID;

    public final Column ACCOUNT_TRANSFER_DETAIL_ID;

    public final Column FROM_SAVING_TRANSACTION_ID;

    public final Column FROM_LOAN_TRANSACTION_ID;

    public final Column TO_SAVING_TRANSACTION_ID;

    public final Column TO_LOAN_TRANSACTION_ID;

    public final Column REVERSED;

    public final Column TRANSACTION_DATE;

    public final Column CURRENCY_CODE;

    public final Column CURRENCY_DIGIT;

    public final Column CURRENCY_MULTIPLE_OF;

    public final Column AMOUNT;

    public final Column DESCRIPTION;

    public static MAccountTransferTransaction staticInitialize(DataContext dataContext) {
        return new MAccountTransferTransaction(dataContext);
    }

    private MAccountTransferTransaction(DataContext dataContext) {
        super(dataContext, "m_account_transfer_transaction");
        this.ID = getInternalColumnByName("id");
        this.ACCOUNT_TRANSFER_DETAIL_ID = getInternalColumnByName("account_transfer_details_id");
        this.FROM_SAVING_TRANSACTION_ID = getInternalColumnByName("from_savings_transaction_id");
        this.FROM_LOAN_TRANSACTION_ID = getInternalColumnByName("from_loan_transaction_id");
        this.TO_SAVING_TRANSACTION_ID = getInternalColumnByName("to_savings_transaction_id");
        this.TO_LOAN_TRANSACTION_ID = getInternalColumnByName("to_loan_transaction_id");
        this.REVERSED = getInternalColumnByName("is_reversed");
        this.TRANSACTION_DATE = getInternalColumnByName("transaction_date");
        this.CURRENCY_CODE = getInternalColumnByName("currency_code");
        this.CURRENCY_DIGIT = getInternalColumnByName("currency_digits");
        this.CURRENCY_MULTIPLE_OF = getInternalColumnByName("currency_multiplesof");
        this.AMOUNT = getInternalColumnByName("amount");
        this.DESCRIPTION = getInternalColumnByName("description");
    }

}
