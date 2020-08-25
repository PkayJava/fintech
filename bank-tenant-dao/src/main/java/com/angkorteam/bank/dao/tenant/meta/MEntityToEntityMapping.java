package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.REL_ID = getInternalColumnByName("rel_id");
        this.FROM_ID = getInternalColumnByName("from_id");
        this.TO_ID = getInternalColumnByName("to_id");
        this.START_DATE = getInternalColumnByName("start_date");
        this.END_DATE = getInternalColumnByName("end_date");
    }

}
