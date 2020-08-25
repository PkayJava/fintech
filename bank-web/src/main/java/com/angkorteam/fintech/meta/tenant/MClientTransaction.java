package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.CLIENT_ID = getInternalColumnByName("client_id");
        this.OFFICE_ID = getInternalColumnByName("office_id");
        this.CURRENCY_CODE = getInternalColumnByName("currency_code");
        this.PAYMENT_DETAIL_ID = getInternalColumnByName("payment_detail_id");
        this.REVERSED = getInternalColumnByName("is_reversed");
        this.EXTERNAL_ID = getInternalColumnByName("external_id");
        this.TRANSACTION_DATE = getInternalColumnByName("transaction_date");
        this.TRANSACTION_TYPE_ENUM = getInternalColumnByName("transaction_type_enum");
        this.AMOUNT = getInternalColumnByName("amount");
        this.CREATED_DATE = getInternalColumnByName("created_date");
        this.APP_USER_ID = getInternalColumnByName("appuser_id");
    }

}
