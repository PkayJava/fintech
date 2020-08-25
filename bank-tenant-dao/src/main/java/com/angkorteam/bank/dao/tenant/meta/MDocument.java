package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.PARENT_ENTITY_TYPE = getInternalColumnByName("parent_entity_type");
        this.PARENT_ENTITY_ID = getInternalColumnByName("parent_entity_id");
        this.NAME = getInternalColumnByName("name");
        this.FILE_NAME = getInternalColumnByName("file_name");
        this.SIZE = getInternalColumnByName("size");
        this.TYPE = getInternalColumnByName("type");
        this.DESCRIPTION = getInternalColumnByName("description");
        this.LOCATION = getInternalColumnByName("location");
        this.STORAGE_TYPE_ENUM = getInternalColumnByName("storage_type_enum");
    }

}
