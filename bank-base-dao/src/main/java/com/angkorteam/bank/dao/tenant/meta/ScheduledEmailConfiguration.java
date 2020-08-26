package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class ScheduledEmailConfiguration extends AbstractTable {

    public final Column ID;

    public final Column NAME;

    public final Column VALUE;

    public static ScheduledEmailConfiguration staticInitialize(DataContext dataContext) {
        return new ScheduledEmailConfiguration(dataContext);
    }

    private ScheduledEmailConfiguration(DataContext dataContext) {
        super(dataContext, "scheduled_email_configuration");
        this.ID = getInternalColumnByName("id");
        this.NAME = getInternalColumnByName("name");
        this.VALUE = getInternalColumnByName("value");
    }

}
