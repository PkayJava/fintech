package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MTemplateMTemplateMapper extends AbstractTable {

    public final Column TEMPLATE_ID;

    public final Column MAPPER_ID;

    public static MTemplateMTemplateMapper staticInitialize(DataContext dataContext) {
        return new MTemplateMTemplateMapper(dataContext);
    }

    private MTemplateMTemplateMapper(DataContext dataContext) {
        super(dataContext, "m_template_m_templatemappers");
        this.TEMPLATE_ID = getInternalColumnByName("m_template_id");
        this.MAPPER_ID = getInternalColumnByName("mappers_id");
    }

}
