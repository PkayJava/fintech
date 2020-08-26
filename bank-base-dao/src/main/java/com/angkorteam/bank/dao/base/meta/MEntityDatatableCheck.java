package com.angkorteam.bank.dao.base.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MEntityDatatableCheck extends AbstractTable {

    public final Column ID;

    public final Column APPLICATION_TABLE_NAME;

    public final Column X_REGISTERED_TABLE_NAME;

    public final Column STATUS_ENUM;

    public final Column SYSTEM_DEFINED;

    public final Column PRODUCT_ID;

    public static MEntityDatatableCheck staticInitialize(DataContext dataContext) {
        return new MEntityDatatableCheck(dataContext);
    }

    private MEntityDatatableCheck(DataContext dataContext) {
        super(dataContext, "m_entity_datatable_check");
        this.ID = getInternalColumnByName("id");
        this.APPLICATION_TABLE_NAME = getInternalColumnByName("application_table_name");
        this.X_REGISTERED_TABLE_NAME = getInternalColumnByName("x_registered_table_name");
        this.STATUS_ENUM = getInternalColumnByName("status_enum");
        this.SYSTEM_DEFINED = getInternalColumnByName("system_defined");
        this.PRODUCT_ID = getInternalColumnByName("product_id");
    }

}
