package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MSavingAccountTransactionTaxDetail extends AbstractTable {

    public final Column ID;

    public final Column SAVING_TRANSACTION_ID;

    public final Column TAX_COMPONENT_ID;

    public final Column AMOUNT;

    public static MSavingAccountTransactionTaxDetail staticInitialize(DataContext dataContext) {
        return new MSavingAccountTransactionTaxDetail(dataContext);
    }

    private MSavingAccountTransactionTaxDetail(DataContext dataContext) {
        super(dataContext, "m_savings_account_transaction_tax_details");
        this.ID = this.delegate.getColumnByName("id");
        this.SAVING_TRANSACTION_ID = this.delegate.getColumnByName("savings_transaction_id");
        this.TAX_COMPONENT_ID = this.delegate.getColumnByName("tax_component_id");
        this.AMOUNT = this.delegate.getColumnByName("amount");
    }

}
