package com.angkorteam.bank.dao.base.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MCode extends AbstractTable {

    public final Column ID;

    public final Column CODE_NAME;

    public final Column SYSTEM_DEFINED;

    public static MCode staticInitialize(DataContext dataContext) {
        return new MCode(dataContext);
    }

    private MCode(DataContext dataContext) {
        super(dataContext, "m_code");
        this.ID = getInternalColumnByName("id");
        this.CODE_NAME = getInternalColumnByName("code_name");
        this.SYSTEM_DEFINED = getInternalColumnByName("is_system_defined");
    }

}
