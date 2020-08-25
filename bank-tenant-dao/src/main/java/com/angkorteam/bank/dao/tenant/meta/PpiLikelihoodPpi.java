package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class PpiLikelihoodPpi extends AbstractTable {

    public final Column ID;

    public final Column likelihood_id;

    public final Column ppi_name;

    public final Column enabled;

    public static PpiLikelihoodPpi staticInitialize(DataContext dataContext) {
        return new PpiLikelihoodPpi(dataContext);
    }

    private PpiLikelihoodPpi(DataContext dataContext) {
        super(dataContext, "ppi_likelihoods_ppi");
        this.ID = getInternalColumnByName("id");
        this.likelihood_id = getInternalColumnByName("likelihood_id");
        this.ppi_name = getInternalColumnByName("ppi_name");
        this.enabled = getInternalColumnByName("enabled");
    }

}
