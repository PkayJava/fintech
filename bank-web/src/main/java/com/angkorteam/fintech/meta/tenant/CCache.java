package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.CACHE_TYPE_ENUM = getInternalColumnByName("cache_type_enum");
    }

}
