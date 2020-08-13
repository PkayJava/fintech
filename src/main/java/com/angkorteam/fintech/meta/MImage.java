package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MImage extends AbstractTable {

    public final Column ID;

    public final Column LOCATION;

    public final Column STORAGE_TYPE_ENUM;

    public static MImage staticInitialize(DataContext dataContext) {
        return new MImage(dataContext);
    }

    private MImage(DataContext dataContext) {
        super(dataContext, "m_image");
        this.ID = this.delegate.getColumnByName("id");
        this.LOCATION = this.delegate.getColumnByName("location");
        this.STORAGE_TYPE_ENUM = this.delegate.getColumnByName("storage_type_enum");
    }

}
