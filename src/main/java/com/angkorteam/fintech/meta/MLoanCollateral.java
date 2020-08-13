package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MLoanCollateral extends AbstractTable {

    public final Column ID;

    public final Column LOAN_ID;

    public final Column TYPE_CV_ID;

    public final Column VALUE;

    public final Column DESCRIPTION;

    public static MLoanCollateral staticInitialize(DataContext dataContext) {
        return new MLoanCollateral(dataContext);
    }

    private MLoanCollateral(DataContext dataContext) {
        super(dataContext, "m_loan_collateral");
        this.ID = this.delegate.getColumnByName("id");
        this.LOAN_ID = this.delegate.getColumnByName("loan_id");
        this.TYPE_CV_ID = this.delegate.getColumnByName("type_cv_id");
        this.VALUE = this.delegate.getColumnByName("value");
        this.DESCRIPTION = this.delegate.getColumnByName("description");
    }

}
