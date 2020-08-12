package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class CAccountNumberFormat extends AbstractTable {

    public final Column ID;

    public final Column ACCOUNT_TYPE_ENUM;

    public final Column PREFIX_TYPE_ENUM;

    public static CAccountNumberFormat staticInitialize(DataContext dataContext) {
        return new CAccountNumberFormat(dataContext);
    }

    private CAccountNumberFormat(DataContext dataContext) {
        super(dataContext, "c_account_number_format");
        this.ID = this.delegate.getColumnByName("id");
        this.ACCOUNT_TYPE_ENUM = this.delegate.getColumnByName("account_type_enum");
        this.PREFIX_TYPE_ENUM = this.delegate.getColumnByName("prefix_type_enum");
    }

}
