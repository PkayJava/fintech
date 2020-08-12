package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MCashierTransaction extends AbstractTable {

    public final Column ID;

    public final Column CASHIER_ID;

    public final Column TXN_TYPE;

    public final Column TXN_AMOUNT;

    public final Column TXN_DATE;

    public final Column CREATED_DATE;

    public final Column ENTITY_TYPE;

    public final Column TXN_NOTE;

    public final Column CURRENCY_CODE;

    public final Column ENTITY_ID;

    public static MCashierTransaction staticInitialize(DataContext dataContext) {
        return new MCashierTransaction(dataContext);
    }

    private MCashierTransaction(DataContext dataContext) {
        super(dataContext, "m_cashier_transactions");
        this.ID = this.delegate.getColumnByName("id");
        this.ENTITY_ID = this.delegate.getColumnByName("entity_id");
        this.CASHIER_ID = this.delegate.getColumnByName("cashier_id");
        this.TXN_TYPE = this.delegate.getColumnByName("txn_type");
        this.TXN_AMOUNT = this.delegate.getColumnByName("txn_amount");
        this.TXN_DATE = this.delegate.getColumnByName("txn_date");
        this.CREATED_DATE = this.delegate.getColumnByName("created_date");
        this.ENTITY_TYPE = this.delegate.getColumnByName("entity_type");
        this.TXN_NOTE = this.delegate.getColumnByName("txn_note");
        this.CURRENCY_CODE = this.delegate.getColumnByName("currency_code");
    }

}
