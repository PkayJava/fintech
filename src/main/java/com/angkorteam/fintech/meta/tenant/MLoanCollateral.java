package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.LOAN_ID = getInternalColumnByName("loan_id");
        this.TYPE_CV_ID = getInternalColumnByName("type_cv_id");
        this.VALUE = getInternalColumnByName("value");
        this.DESCRIPTION = getInternalColumnByName("description");
    }

}
