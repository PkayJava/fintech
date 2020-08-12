package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MEntityRelation extends AbstractTable {

    public final Column ID;

    public final Column FROM_ENTITY_TYPE;

    public final Column TO_ENTITY_TYPE;

    public final Column CODE_NAME;

    public static MEntityRelation staticInitialize(DataContext dataContext) {
        return new MEntityRelation(dataContext);
    }

    private MEntityRelation(DataContext dataContext) {
        super(dataContext, "m_entity_relation");
        this.ID = this.delegate.getColumnByName("id");
        this.FROM_ENTITY_TYPE = this.delegate.getColumnByName("from_entity_type");
        this.TO_ENTITY_TYPE = this.delegate.getColumnByName("to_entity_type");
        this.CODE_NAME = this.delegate.getColumnByName("code_name");
    }

}
