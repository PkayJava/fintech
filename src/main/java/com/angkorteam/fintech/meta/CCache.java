package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class CCache extends AbstractTable {

    public final Column ID;

    public final Column CACHE_TYPE_ENUM;

    public static CCache staticInitialize(DataContext dataContext) {
        return new CCache(dataContext);
    }

    private CCache(DataContext dataContext) {
        super(dataContext, "c_cache");
        this.ID = this.delegate.getColumnByName("id");
        this.CACHE_TYPE_ENUM = this.delegate.getColumnByName("cache_type_enum");
    }

}
