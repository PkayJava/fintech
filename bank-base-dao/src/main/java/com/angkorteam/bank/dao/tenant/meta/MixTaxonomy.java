package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MixTaxonomy extends AbstractTable {

    public final Column ID;

    public final Column NAME;

    public final Column NAMESPACE_ID;

    public final Column DIMENSION;

    public final Column TYPE;

    public final Column DESCRIPTION;

    public final Column NEED_MAPPING;

    public static MixTaxonomy staticInitialize(DataContext dataContext) {
        return new MixTaxonomy(dataContext);
    }

    private MixTaxonomy(DataContext dataContext) {
        super(dataContext, "mix_taxonomy");
        this.ID = getInternalColumnByName("id");
        this.NAME = getInternalColumnByName("name");
        this.NAMESPACE_ID = getInternalColumnByName("namespace_id");
        this.DIMENSION = getInternalColumnByName("dimension");
        this.TYPE = getInternalColumnByName("type");
        this.DESCRIPTION = getInternalColumnByName("description");
        this.NEED_MAPPING = getInternalColumnByName("need_mapping");
    }

}
