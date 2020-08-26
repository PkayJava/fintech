package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class PpiLikelihood extends AbstractTable {

    public final Column ID;

    public final Column CODE;

    public final Column NAME;

    public static PpiLikelihood staticInitialize(DataContext dataContext) {
        return new PpiLikelihood(dataContext);
    }

    private PpiLikelihood(DataContext dataContext) {
        super(dataContext, "ppi_likelihoods");
        this.ID = getInternalColumnByName("id");
        this.CODE = getInternalColumnByName("code");
        this.NAME = getInternalColumnByName("name");
    }

}
