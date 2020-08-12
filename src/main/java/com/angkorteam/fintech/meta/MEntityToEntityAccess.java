package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MEntityToEntityAccess extends AbstractTable {

    public final Column ID;

    public final Column ENTITY_TYPE;

    public final Column ENTITY_ID;

    public final Column ACCESS_TYPE_CODE_VALUE_ID;

    public final Column SECOND_ENTITY_TYPE;

    public final Column SECOND_ENTITY_ID;

    public static MEntityToEntityAccess staticInitialize(DataContext dataContext) {
        return new MEntityToEntityAccess(dataContext);
    }

    private MEntityToEntityAccess(DataContext dataContext) {
        super(dataContext, "m_entity_to_entity_access");
        this.ID = this.delegate.getColumnByName("id");
        this.ENTITY_TYPE = this.delegate.getColumnByName("entity_type");
        this.ENTITY_ID = this.delegate.getColumnByName("entity_id");
        this.ACCESS_TYPE_CODE_VALUE_ID = this.delegate.getColumnByName("access_type_code_value_id");
        this.SECOND_ENTITY_TYPE = this.delegate.getColumnByName("second_entity_type");
        this.SECOND_ENTITY_ID = this.delegate.getColumnByName("second_entity_id");
    }

}
