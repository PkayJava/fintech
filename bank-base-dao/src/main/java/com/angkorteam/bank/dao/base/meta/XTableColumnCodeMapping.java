package com.angkorteam.bank.dao.base.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class XTableColumnCodeMapping extends AbstractTable {

    public final Column COLUMN_ALIAS_NAME;

    public final Column CODE_ID;

    public static XTableColumnCodeMapping staticInitialize(DataContext dataContext) {
        return new XTableColumnCodeMapping(dataContext);
    }

    private XTableColumnCodeMapping(DataContext dataContext) {
        super(dataContext, "x_table_column_code_mappings");
        this.COLUMN_ALIAS_NAME = getInternalColumnByName("column_alias_name");
        this.CODE_ID = getInternalColumnByName("code_id");
    }

}
