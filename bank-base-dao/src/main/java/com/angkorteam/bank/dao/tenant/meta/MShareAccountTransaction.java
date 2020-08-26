package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MShareAccountTransaction extends AbstractTable {

    public final Column ID;

    public final Column ACCOUNT_ID;

    public final Column TRANSACTION_DATE;

    public final Column TOTAL_SHARE;

    public final Column UNIT_PRICE;

    public final Column AMOUNT;

    public final Column CHARGE_AMOUNT;

    public final Column AMOUNT_PAID;

    public final Column STATUS_ENUM;

    public final Column TYPE_ENUM;

    public final Column ACTIVE;

    public static MShareAccountTransaction staticInitialize(DataContext dataContext) {
        return new MShareAccountTransaction(dataContext);
    }

    private MShareAccountTransaction(DataContext dataContext) {
        super(dataContext, "m_share_account_transactions");
        this.ID = getInternalColumnByName("id");
        this.ACCOUNT_ID = getInternalColumnByName("account_id");
        this.TRANSACTION_DATE = getInternalColumnByName("transaction_date");
        this.TOTAL_SHARE = getInternalColumnByName("total_shares");
        this.UNIT_PRICE = getInternalColumnByName("unit_price");
        this.AMOUNT = getInternalColumnByName("amount");
        this.CHARGE_AMOUNT = getInternalColumnByName("charge_amount");
        this.AMOUNT_PAID = getInternalColumnByName("amount_paid");
        this.STATUS_ENUM = getInternalColumnByName("status_enum");
        this.TYPE_ENUM = getInternalColumnByName("type_enum");
        this.ACTIVE = getInternalColumnByName("is_active");
    }

}
