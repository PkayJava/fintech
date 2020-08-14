package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MixTaxonomyMapping extends AbstractTable {

    public final Column ID;

    public final Column IDENTIFIER;

    public final Column CONFIG;

    public final Column LAST_UPDATE_DATE;

    public final Column CURRENCY;

    public static MixTaxonomyMapping staticInitialize(DataContext dataContext) {
        return new MixTaxonomyMapping(dataContext);
    }

    private MixTaxonomyMapping(DataContext dataContext) {
        super(dataContext, "mix_taxonomy_mapping");
        this.ID = getInternalColumnByName("id");
        this.IDENTIFIER = getInternalColumnByName("identifier");
        this.CONFIG = getInternalColumnByName("config");
        this.LAST_UPDATE_DATE = getInternalColumnByName("last_update_date");
        this.CURRENCY = getInternalColumnByName("currency");
    }

}
