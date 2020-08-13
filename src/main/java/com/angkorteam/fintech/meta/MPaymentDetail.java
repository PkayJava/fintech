package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MPaymentDetail extends AbstractTable {

    public final Column ID;

    public final Column PAYMENT_TYPE_ID;

    public final Column ACCOUNT_NUMBER;

    public final Column CHECK_NUMBER;

    public final Column RECEIPT_NUMBER;

    public final Column BANK_NUMBER;

    public final Column ROUTING_CODE;

    public static MPaymentDetail staticInitialize(DataContext dataContext) {
        return new MPaymentDetail(dataContext);
    }

    private MPaymentDetail(DataContext dataContext) {
        super(dataContext, "m_payment_detail");
        this.ID = this.delegate.getColumnByName("id");
        this.PAYMENT_TYPE_ID = this.delegate.getColumnByName("payment_type_id");
        this.ACCOUNT_NUMBER = this.delegate.getColumnByName("account_number");
        this.CHECK_NUMBER = this.delegate.getColumnByName("check_number");
        this.RECEIPT_NUMBER = this.delegate.getColumnByName("receipt_number");
        this.BANK_NUMBER = this.delegate.getColumnByName("bank_number");
        this.ROUTING_CODE = this.delegate.getColumnByName("routing_code");
    }

}
