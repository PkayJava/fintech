package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MDocument extends AbstractTable {

    public final Column ID;

    public final Column PARENT_ENTITY_TYPE;

    public final Column PARENT_ENTITY_ID;

    public final Column NAME;

    public final Column FILE_NAME;

    public final Column SIZE;

    public final Column TYPE;

    public final Column DESCRIPTION;

    public final Column LOCATION;

    public final Column STORAGE_TYPE_ENUM;

    public static MDocument staticInitialize(DataContext dataContext) {
        return new MDocument(dataContext);
    }

    private MDocument(DataContext dataContext) {
        super(dataContext, "m_document");
        this.ID = this.delegate.getColumnByName("id");
        this.PARENT_ENTITY_TYPE = this.delegate.getColumnByName("parent_entity_type");
        this.PARENT_ENTITY_ID = this.delegate.getColumnByName("parent_entity_id");
        this.NAME = this.delegate.getColumnByName("name");
        this.FILE_NAME = this.delegate.getColumnByName("file_name");
        this.SIZE = this.delegate.getColumnByName("size");
        this.TYPE = this.delegate.getColumnByName("type");
        this.DESCRIPTION = this.delegate.getColumnByName("description");
        this.LOCATION = this.delegate.getColumnByName("location");
        this.STORAGE_TYPE_ENUM = this.delegate.getColumnByName("storage_type_enum");
    }

}
