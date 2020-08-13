package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MLoanProductProvisioningMapping extends AbstractTable {

    public final Column ID;

    public final Column PRODUCT_ID;

    public final Column CRITERIA_ID;

    public static MLoanProductProvisioningMapping staticInitialize(DataContext dataContext) {
        return new MLoanProductProvisioningMapping(dataContext);
    }

    private MLoanProductProvisioningMapping(DataContext dataContext) {
        super(dataContext, "m_loanproduct_provisioning_mapping");
        this.ID = this.delegate.getColumnByName("id");
        this.PRODUCT_ID = this.delegate.getColumnByName("product_id");
        this.CRITERIA_ID = this.delegate.getColumnByName("criteria_id");
    }

}
