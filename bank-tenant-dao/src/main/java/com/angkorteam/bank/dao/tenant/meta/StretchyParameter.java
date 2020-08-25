package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class StretchyParameter extends AbstractTable {

    public final Column ID;

    public final Column PARAMETER_NAME;

    public final Column PARAMETER_VARIABLE;

    public final Column PARAMETER_LABEL;

    public final Column PARAMETER_DISPLAY_TYPE;

    public final Column PARAMETER_FORMAT_TYPE;

    public final Column PARAMETER_DEFAULT;

    public final Column SPECIAL;

    public final Column SELECT_ONE;

    public final Column SELECT_ALL;

    public final Column PARAMETER_SQL;

    public final Column PARENT_ID;

    public static StretchyParameter staticInitialize(DataContext dataContext) {
        return new StretchyParameter(dataContext);
    }

    private StretchyParameter(DataContext dataContext) {
        super(dataContext, "stretchy_parameter");
        this.ID = getInternalColumnByName("id");
        this.PARAMETER_NAME = getInternalColumnByName("parameter_name");
        this.PARAMETER_VARIABLE = getInternalColumnByName("parameter_variable");
        this.PARAMETER_LABEL = getInternalColumnByName("parameter_label");
        this.PARAMETER_DISPLAY_TYPE = getInternalColumnByName("parameter_displayType");
        this.PARAMETER_FORMAT_TYPE = getInternalColumnByName("parameter_FormatType");
        this.PARAMETER_DEFAULT = getInternalColumnByName("parameter_default");
        this.SPECIAL = getInternalColumnByName("special");
        this.SELECT_ONE = getInternalColumnByName("selectOne");
        this.SELECT_ALL = getInternalColumnByName("selectAll");
        this.PARAMETER_SQL = getInternalColumnByName("parameter_sql");
        this.PARENT_ID = getInternalColumnByName("parent_id");
    }

}
