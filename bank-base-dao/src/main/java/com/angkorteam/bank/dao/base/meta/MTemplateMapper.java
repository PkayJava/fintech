package com.angkorteam.bank.dao.base.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MTemplateMapper extends AbstractTable {

    public final Column ID;

    public final Column MAPPER_KEY;

    public final Column MAPPER_ORDER;

    public final Column MAPPER_VALUE;

    public static MTemplateMapper staticInitialize(DataContext dataContext) {
        return new MTemplateMapper(dataContext);
    }

    private MTemplateMapper(DataContext dataContext) {
        super(dataContext, "m_templatemappers");
        this.ID = getInternalColumnByName("id");
        this.MAPPER_KEY = getInternalColumnByName("mapperkey");
        this.MAPPER_ORDER = getInternalColumnByName("mapperorder");
        this.MAPPER_VALUE = getInternalColumnByName("mappervalue");
    }

}