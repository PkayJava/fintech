package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MLoanRate extends AbstractTable {

    public final Column LOAN_ID;

    public final Column RATE_ID;

    public static MLoanRate staticInitialize(DataContext dataContext) {
        return new MLoanRate(dataContext);
    }

    private MLoanRate(DataContext dataContext) {
        super(dataContext, "m_loan_rate");
        this.LOAN_ID = this.delegate.getColumnByName("loan_id");
        this.RATE_ID = this.delegate.getColumnByName("rate_id");
    }

}
