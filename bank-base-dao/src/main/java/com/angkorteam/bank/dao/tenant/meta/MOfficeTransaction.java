package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.metamodel.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.FROM_OFFICE_ID = getInternalColumnByName("from_office_id");
        this.TO_OFFICE_ID = getInternalColumnByName("to_office_id");
        this.CURRENCY_CODE = getInternalColumnByName("currency_code");
        this.CURRENCY_DIGIT = getInternalColumnByName("currency_digits");
        this.TRANSACTION_AMOUNT = getInternalColumnByName("transaction_amount");
        this.TRANSACTION_DATE = getInternalColumnByName("transaction_date");
        this.DESCRIPTION = getInternalColumnByName("description");
    }

}
