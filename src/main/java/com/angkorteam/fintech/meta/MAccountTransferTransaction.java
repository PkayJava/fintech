package com.angkorteam.fintech.meta;

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
        this.ID = this.delegate.getColumnByName("id");
        this.ACCOUNT_TRANSFER_DETAIL_ID = this.delegate.getColumnByName("account_transfer_details_id");
        this.FROM_SAVING_TRANSACTION_ID = this.delegate.getColumnByName("from_savings_transaction_id");
        this.FROM_LOAN_TRANSACTION_ID = this.delegate.getColumnByName("from_loan_transaction_id");
        this.TO_SAVING_TRANSACTION_ID = this.delegate.getColumnByName("to_savings_transaction_id");
        this.TO_LOAN_TRANSACTION_ID = this.delegate.getColumnByName("to_loan_transaction_id");
        this.REVERSED = this.delegate.getColumnByName("is_reversed");
        this.TRANSACTION_DATE = this.delegate.getColumnByName("transaction_date");
        this.CURRENCY_CODE = this.delegate.getColumnByName("currency_code");
        this.CURRENCY_DIGIT = this.delegate.getColumnByName("currency_digits");
        this.CURRENCY_MULTIPLE_OF = this.delegate.getColumnByName("currency_multiplesof");
        this.AMOUNT = this.delegate.getColumnByName("amount");
        this.DESCRIPTION = this.delegate.getColumnByName("description");
    }

}
