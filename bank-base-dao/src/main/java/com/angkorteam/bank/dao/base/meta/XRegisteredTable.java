package com.angkorteam.bank.dao.base.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class XRegisteredTable extends AbstractTable {

    public final Column REGISTERED_TABLE_NAME;

    public final Column APPLICATION_TABLE_NAME;

    public final Column CATEGORY;

    public static XRegisteredTable staticInitialize(DataContext dataContext) {
        return new XRegisteredTable(dataContext);
    }

    private XRegisteredTable(DataContext dataContext) {
        super(dataContext, "x_registered_table");
        this.REGISTERED_TABLE_NAME = getInternalColumnByName("registered_table_name");
        this.APPLICATION_TABLE_NAME = getInternalColumnByName("application_table_name");
        this.CATEGORY = getInternalColumnByName("category");
    }

}
