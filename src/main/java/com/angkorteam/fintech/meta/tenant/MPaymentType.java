package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.VALUE = getInternalColumnByName("value");
        this.DESCRIPTION = getInternalColumnByName("description");
        this.CASH_PAYMENT = getInternalColumnByName("is_cash_payment");
        this.ORDER_POSITION = getInternalColumnByName("order_position");
    }

}
