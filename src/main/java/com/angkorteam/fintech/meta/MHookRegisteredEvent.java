package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MHookRegisteredEvent extends AbstractTable {

    public final Column ID;

    public final Column HOOK_ID;

    public final Column ENTITY_NAME;

    public final Column ACTION_NAME;

    public static MHookRegisteredEvent staticInitialize(DataContext dataContext) {
        return new MHookRegisteredEvent(dataContext);
    }

    private MHookRegisteredEvent(DataContext dataContext) {
        super(dataContext, "m_hook_registered_events");
        this.ID = this.delegate.getColumnByName("id");
        this.HOOK_ID = this.delegate.getColumnByName("hook_id");
        this.ENTITY_NAME = this.delegate.getColumnByName("entity_name");
        this.ACTION_NAME = this.delegate.getColumnByName("action_name");
    }

}
