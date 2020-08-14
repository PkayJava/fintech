package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class NotificationMapper extends AbstractTable {

    public final Column ID;

    public final Column NOTIFICATION_ID;

    public final Column USER_ID;

    public final Column READ;

    public final Column CREATED_AT;

    public static NotificationMapper staticInitialize(DataContext dataContext) {
        return new NotificationMapper(dataContext);
    }

    private NotificationMapper(DataContext dataContext) {
        super(dataContext, "notification_mapper");
        this.ID = getInternalColumnByName("id");
        this.NOTIFICATION_ID = getInternalColumnByName("notification_id");
        this.USER_ID = getInternalColumnByName("user_id");
        this.READ = getInternalColumnByName("is_read");
        this.CREATED_AT = getInternalColumnByName("created_at");
    }

}
