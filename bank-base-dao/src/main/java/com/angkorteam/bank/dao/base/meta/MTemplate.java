package com.angkorteam.bank.dao.base.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MTemplate extends AbstractTable {

    public final Column ID;

    public final Column NAME;

    public final Column TEXT;

    public final Column ENTITY;

    public final Column TYPE;

    public static MTemplate staticInitialize(DataContext dataContext) {
        return new MTemplate(dataContext);
    }

    private MTemplate(DataContext dataContext) {
        super(dataContext, "m_template");
        this.ID = getInternalColumnByName("id");
        this.NAME = getInternalColumnByName("name");
        this.TEXT = getInternalColumnByName("text");
        this.ENTITY = getInternalColumnByName("entity");
        this.TYPE = getInternalColumnByName("type");
    }

}
