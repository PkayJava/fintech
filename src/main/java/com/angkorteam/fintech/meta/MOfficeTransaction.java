package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MOfficeTransaction extends AbstractTable {

    public final Column ID;

    public final Column FROM_OFFICE_ID;

    public final Column TO_OFFICE_ID;

    public final Column CURRENCY_CODE;

    public final Column CURRENCY_DIGIT;

    public final Column TRANSACTION_AMOUNT;

    public final Column TRANSACTION_DATE;

    public final Column DESCRIPTION;

    public static MOfficeTransaction staticInitialize(DataContext dataContext) {
        return new MOfficeTransaction(dataContext);
    }

    private MOfficeTransaction(DataContext dataContext) {
        super(dataContext, "m_office_transaction");
        this.ID = this.delegate.getColumnByName("id");
        this.FROM_OFFICE_ID = this.delegate.getColumnByName("from_office_id");
        this.TO_OFFICE_ID = this.delegate.getColumnByName("to_office_id");
        this.CURRENCY_CODE = this.delegate.getColumnByName("currency_code");
        this.CURRENCY_DIGIT = this.delegate.getColumnByName("currency_digits");
        this.TRANSACTION_AMOUNT = this.delegate.getColumnByName("transaction_amount");
        this.TRANSACTION_DATE = this.delegate.getColumnByName("transaction_date");
        this.DESCRIPTION = this.delegate.getColumnByName("description");
    }

}
