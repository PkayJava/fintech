package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.ENTITY_ID = getInternalColumnByName("entity_id");
        this.CASHIER_ID = getInternalColumnByName("cashier_id");
        this.TXN_TYPE = getInternalColumnByName("txn_type");
        this.TXN_AMOUNT = getInternalColumnByName("txn_amount");
        this.TXN_DATE = getInternalColumnByName("txn_date");
        this.CREATED_DATE = getInternalColumnByName("created_date");
        this.ENTITY_TYPE = getInternalColumnByName("entity_type");
        this.TXN_NOTE = getInternalColumnByName("txn_note");
        this.CURRENCY_CODE = getInternalColumnByName("currency_code");
    }

}
