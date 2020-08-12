package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MClientTransaction extends AbstractTable {

    public final Column ID;

    public final Column CLIENT_ID;

    public final Column OFFICE_ID;

    public final Column CURRENCY_CODE;

    public final Column PAYMENT_DETAIL_ID;

    public final Column REVERSED;

    public final Column EXTERNAL_ID;

    public final Column TRANSACTION_DATE;

    public final Column TRANSACTION_TYPE_ENUM;

    public final Column AMOUNT;

    public final Column CREATED_DATE;

    public final Column APP_USER_ID;

    public static MClientTransaction staticInitialize(DataContext dataContext) {
        return new MClientTransaction(dataContext);
    }

    private MClientTransaction(DataContext dataContext) {
        super(dataContext, "m_client_transaction");
        this.ID = this.delegate.getColumnByName("id");
        this.CLIENT_ID = this.delegate.getColumnByName("client_id");
        this.OFFICE_ID = this.delegate.getColumnByName("office_id");
        this.CURRENCY_CODE = this.delegate.getColumnByName("currency_code");
        this.PAYMENT_DETAIL_ID = this.delegate.getColumnByName("payment_detail_id");
        this.REVERSED = this.delegate.getColumnByName("is_reversed");
        this.EXTERNAL_ID = this.delegate.getColumnByName("external_id");
        this.TRANSACTION_DATE = this.delegate.getColumnByName("transaction_date");
        this.TRANSACTION_TYPE_ENUM = this.delegate.getColumnByName("transaction_type_enum");
        this.AMOUNT = this.delegate.getColumnByName("amount");
        this.CREATED_DATE = this.delegate.getColumnByName("created_date");
        this.APP_USER_ID = this.delegate.getColumnByName("appuser_id");
    }

}
