package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.PRODUCT_ID = getInternalColumnByName("product_id");
        this.CRITERIA_ID = getInternalColumnByName("criteria_id");
    }

}
