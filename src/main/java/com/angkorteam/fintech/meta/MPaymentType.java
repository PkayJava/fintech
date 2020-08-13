package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MPaymentType extends AbstractTable {

    public final Column ID;

    public final Column VALUE;

    public final Column DESCRIPTION;

    public final Column CASH_PAYMENT;

    public final Column ORDER_POSITION;

    public static MPaymentType staticInitialize(DataContext dataContext) {
        return new MPaymentType(dataContext);
    }

    private MPaymentType(DataContext dataContext) {
        super(dataContext, "m_payment_type");
        this.ID = this.delegate.getColumnByName("id");
        this.VALUE = this.delegate.getColumnByName("value");
        this.DESCRIPTION = this.delegate.getColumnByName("description");
        this.CASH_PAYMENT = this.delegate.getColumnByName("is_cash_payment");
        this.ORDER_POSITION = this.delegate.getColumnByName("order_position");
    }

}
