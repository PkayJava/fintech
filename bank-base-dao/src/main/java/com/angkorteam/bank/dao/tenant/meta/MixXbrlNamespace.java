package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MixXbrlNamespace extends AbstractTable {

    public final Column ID;

    public final Column PREFIX;

    public final Column URL;

    public static MixXbrlNamespace staticInitialize(DataContext dataContext) {
        return new MixXbrlNamespace(dataContext);
    }

    private MixXbrlNamespace(DataContext dataContext) {
        super(dataContext, "mix_xbrl_namespace");
        this.ID = getInternalColumnByName("id");
        this.PREFIX = getInternalColumnByName("prefix");
        this.URL = getInternalColumnByName("url");
    }

}
