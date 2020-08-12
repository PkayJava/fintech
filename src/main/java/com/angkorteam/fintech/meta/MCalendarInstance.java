package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MCalendarInstance extends AbstractTable {

    public final Column ID;

    public final Column CALENDAR_ID;

    public final Column ENTITY_ID;

    public final Column ENTITY_TYPE_ENUM;

    public static MCalendarInstance staticInitialize(DataContext dataContext) {
        return new MCalendarInstance(dataContext);
    }

    private MCalendarInstance(DataContext dataContext) {
        super(dataContext, "m_calendar_instance");
        this.ID = this.delegate.getColumnByName("id");
        this.CALENDAR_ID = this.delegate.getColumnByName("calendar_id");
        this.ENTITY_ID = this.delegate.getColumnByName("entity_id");
        this.ENTITY_TYPE_ENUM = this.delegate.getColumnByName("entity_type_enum");
    }

}
