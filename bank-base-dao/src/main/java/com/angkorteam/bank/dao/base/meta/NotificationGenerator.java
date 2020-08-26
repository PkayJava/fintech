package com.angkorteam.bank.dao.base.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class NotificationGenerator extends AbstractTable {

    public final Column ID;

    public final Column OBJECT_TYPE;

    public final Column OBJECT_IDENTIFIER;

    public final Column ACTION;

    public final Column ACTOR;

    public final Column SYSTEM_GENERATED;

    public final Column NOTIFICATION_CONTENT;

    public final Column CREATED_AT;

    public static NotificationGenerator staticInitialize(DataContext dataContext) {
        return new NotificationGenerator(dataContext);
    }

    private NotificationGenerator(DataContext dataContext) {
        super(dataContext, "notification_generator");
        this.ID = getInternalColumnByName("id");
        this.OBJECT_TYPE = getInternalColumnByName("object_type");
        this.OBJECT_IDENTIFIER = getInternalColumnByName("object_identifier");
        this.ACTION = getInternalColumnByName("action");
        this.ACTOR = getInternalColumnByName("actor");
        this.SYSTEM_GENERATED = getInternalColumnByName("is_system_generated");
        this.NOTIFICATION_CONTENT = getInternalColumnByName("notification_content");
        this.CREATED_AT = getInternalColumnByName("created_at");
    }

}
