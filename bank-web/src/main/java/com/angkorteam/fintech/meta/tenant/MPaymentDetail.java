package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.PAYMENT_TYPE_ID = getInternalColumnByName("payment_type_id");
        this.ACCOUNT_NUMBER = getInternalColumnByName("account_number");
        this.CHECK_NUMBER = getInternalColumnByName("check_number");
        this.RECEIPT_NUMBER = getInternalColumnByName("receipt_number");
        this.BANK_NUMBER = getInternalColumnByName("bank_number");
        this.ROUTING_CODE = getInternalColumnByName("routing_code");
    }

}
