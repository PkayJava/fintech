package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MLoanProductProvisioningEntry extends AbstractTable {

    public final Column ID;

    public final Column HISTORY_ID;

    public final Column CRITERIA_ID;

    public final Column CURRENCY_CODE;

    public final Column OFFICE_ID;

    public final Column PRODUCT_ID;

    public final Column CATEGORY_ID;

    public final Column OVERDUE_IN_DAY;

    public final Column RESERVE_AMOUNT;

    public final Column LIABILITY_ACCOUNT;

    public final Column EXPENSE_ACCOUNT;

    public static MLoanProductProvisioningEntry staticInitialize(DataContext dataContext) {
        return new MLoanProductProvisioningEntry(dataContext);
    }

    private MLoanProductProvisioningEntry(DataContext dataContext) {
        super(dataContext, "m_loanproduct_provisioning_entry");
        this.ID = this.delegate.getColumnByName("id");
        this.HISTORY_ID = this.delegate.getColumnByName("history_id");
        this.CRITERIA_ID = this.delegate.getColumnByName("criteria_id");
        this.CURRENCY_CODE = this.delegate.getColumnByName("currency_code");
        this.OFFICE_ID = this.delegate.getColumnByName("office_id");
        this.PRODUCT_ID = this.delegate.getColumnByName("product_id");
        this.CATEGORY_ID = this.delegate.getColumnByName("category_id");
        this.OVERDUE_IN_DAY = this.delegate.getColumnByName("overdue_in_days");
        this.RESERVE_AMOUNT = this.delegate.getColumnByName("reseve_amount");
        this.LIABILITY_ACCOUNT = this.delegate.getColumnByName("liability_account");
        this.EXPENSE_ACCOUNT = this.delegate.getColumnByName("expense_account");
    }

}
