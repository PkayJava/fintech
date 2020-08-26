package com.angkorteam.bank.dao.base.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MShareAccountDividendDetail extends AbstractTable {

    public final Column ID;

    public final Column DIVIDEND_PAY_OUT_ID;

    public final Column ACCOUNT_ID;

    public final Column AMOUNT;

    public final Column STATUS;

    public final Column SAVING_TRANSACTION_ID;

    public static MShareAccountDividendDetail staticInitialize(DataContext dataContext) {
        return new MShareAccountDividendDetail(dataContext);
    }

    private MShareAccountDividendDetail(DataContext dataContext) {
        super(dataContext, "m_share_account_dividend_details");
        this.ID = getInternalColumnByName("id");
        this.AMOUNT = getInternalColumnByName("amount");
        this.DIVIDEND_PAY_OUT_ID = getInternalColumnByName("dividend_pay_out_id");
        this.ACCOUNT_ID = getInternalColumnByName("account_id");
        this.STATUS = getInternalColumnByName("status");
        this.SAVING_TRANSACTION_ID = getInternalColumnByName("savings_transaction_id");
    }

}
