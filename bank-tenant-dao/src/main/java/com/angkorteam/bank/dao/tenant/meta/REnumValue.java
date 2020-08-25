package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class REnumValue extends AbstractTable {

    public final Column ENUM_NAME;

    public final Column ENUM_ID;

    public final Column ENUM_MESSAGE_PROPERTY;

    public final Column ENUM_VALUE;

    public final Column ENUM_TYPE;

    public static REnumValue staticInitialize(DataContext dataContext) {
        return new REnumValue(dataContext);
    }

    private REnumValue(DataContext dataContext) {
        super(dataContext, "r_enum_value");
        this.ENUM_NAME = getInternalColumnByName("enum_name");
        this.ENUM_ID = getInternalColumnByName("enum_id");
        this.ENUM_MESSAGE_PROPERTY = getInternalColumnByName("enum_message_property");
        this.ENUM_VALUE = getInternalColumnByName("enum_value");
        this.ENUM_TYPE = getInternalColumnByName("enum_type");
    }

}
