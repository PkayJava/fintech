package com.angkorteam.fintech.meta;

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
        this.ID = this.delegate.getColumnByName("id");
        this.CODE_NAME = this.delegate.getColumnByName("code_name");
        this.SYSTEM_DEFINED = this.delegate.getColumnByName("is_system_defined");
    }

}
