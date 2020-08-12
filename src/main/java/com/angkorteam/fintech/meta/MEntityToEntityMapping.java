package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MEntityToEntityMapping extends AbstractTable {

    public final Column ID;

    public final Column REL_ID;

    public final Column FROM_ID;

    public final Column TO_ID;

    public final Column START_DATE;

    public final Column END_DATE;

    public static MEntityToEntityMapping staticInitialize(DataContext dataContext) {
        return new MEntityToEntityMapping(dataContext);
    }

    private MEntityToEntityMapping(DataContext dataContext) {
        super(dataContext, "m_entity_to_entity_mapping");
        this.ID = this.delegate.getColumnByName("id");
        this.REL_ID = this.delegate.getColumnByName("rel_id");
        this.FROM_ID = this.delegate.getColumnByName("from_id");
        this.TO_ID = this.delegate.getColumnByName("to_id");
        this.START_DATE = this.delegate.getColumnByName("start_date");
        this.END_DATE = this.delegate.getColumnByName("end_date");
    }

}
