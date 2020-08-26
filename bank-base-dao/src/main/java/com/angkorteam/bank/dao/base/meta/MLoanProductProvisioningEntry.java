package com.angkorteam.bank.dao.base.meta;

import com.angkorteam.metamodel.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.HISTORY_ID = getInternalColumnByName("history_id");
        this.CRITERIA_ID = getInternalColumnByName("criteria_id");
        this.CURRENCY_CODE = getInternalColumnByName("currency_code");
        this.OFFICE_ID = getInternalColumnByName("office_id");
        this.PRODUCT_ID = getInternalColumnByName("product_id");
        this.CATEGORY_ID = getInternalColumnByName("category_id");
        this.OVERDUE_IN_DAY = getInternalColumnByName("overdue_in_days");
        this.RESERVE_AMOUNT = getInternalColumnByName("reseve_amount");
        this.LIABILITY_ACCOUNT = getInternalColumnByName("liability_account");
        this.EXPENSE_ACCOUNT = getInternalColumnByName("expense_account");
    }

}
