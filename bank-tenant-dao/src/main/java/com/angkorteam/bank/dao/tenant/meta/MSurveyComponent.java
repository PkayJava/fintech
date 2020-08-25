package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MSurveyComponent extends AbstractTable {

    public final Column ID;

    public final Column SURVEY_ID;

    public final Column A_KEY;

    public final Column A_TEXT;

    public final Column DESCRIPTION;

    public final Column SEQUENCE_NO;

    public static MSurveyComponent staticInitialize(DataContext dataContext) {
        return new MSurveyComponent(dataContext);
    }

    private MSurveyComponent(DataContext dataContext) {
        super(dataContext, "m_survey_components");
        this.ID = getInternalColumnByName("id");
        this.SURVEY_ID = getInternalColumnByName("survey_id");
        this.A_KEY = getInternalColumnByName("a_key");
        this.A_TEXT = getInternalColumnByName("a_text");
        this.DESCRIPTION = getInternalColumnByName("description");
        this.SEQUENCE_NO = getInternalColumnByName("sequence_no");
    }

}
