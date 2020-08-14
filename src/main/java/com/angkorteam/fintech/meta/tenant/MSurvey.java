package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MSurvey extends AbstractTable {

    public final Column ID;

    public final Column A_KEY;

    public final Column A_NAME;

    public final Column DESCRIPTION;

    public final Column COUNTRY_CODE;

    public final Column VALID_FROM;

    public final Column VALID_TO;

    public static MSurvey staticInitialize(DataContext dataContext) {
        return new MSurvey(dataContext);
    }

    private MSurvey(DataContext dataContext) {
        super(dataContext, "m_surveys");
        this.ID = getInternalColumnByName("id");
        this.A_KEY = getInternalColumnByName("a_key");
        this.A_NAME = getInternalColumnByName("a_name");
        this.DESCRIPTION = getInternalColumnByName("description");
        this.COUNTRY_CODE = getInternalColumnByName("country_code");
        this.VALID_FROM = getInternalColumnByName("valid_from");
        this.VALID_TO = getInternalColumnByName("valid_to");
    }

}
