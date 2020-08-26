package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class RefLoanTransactionProcessingStrategy extends AbstractTable {

    public final Column ID;

    public final Column CODE;

    public final Column NAME;

    public final Column SORT_ORDER;

    public static RefLoanTransactionProcessingStrategy staticInitialize(DataContext dataContext) {
        return new RefLoanTransactionProcessingStrategy(dataContext);
    }

    private RefLoanTransactionProcessingStrategy(DataContext dataContext) {
        super(dataContext, "ref_loan_transaction_processing_strategy");
        this.ID = getInternalColumnByName("id");
        this.CODE = getInternalColumnByName("code");
        this.NAME = getInternalColumnByName("name");
        this.SORT_ORDER = getInternalColumnByName("sort_order");
    }

}
