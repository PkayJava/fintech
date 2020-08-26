package com.angkorteam.bank.dao.base.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MSurveyQuestion extends AbstractTable {

    public final Column ID;

    public final Column SURVEY_ID;

    public final Column A_KEY;

    public final Column DESCRIPTION;

    public final Column COMPONENT_KEY;

    public final Column A_TEXT;

    public final Column SEQUENCE_NO;

    public static MSurveyQuestion staticInitialize(DataContext dataContext) {
        return new MSurveyQuestion(dataContext);
    }

    private MSurveyQuestion(DataContext dataContext) {
        super(dataContext, "m_survey_questions");
        this.ID = getInternalColumnByName("id");
        this.SURVEY_ID = getInternalColumnByName("survey_id");
        this.A_KEY = getInternalColumnByName("a_key");
        this.DESCRIPTION = getInternalColumnByName("description");
        this.COMPONENT_KEY = getInternalColumnByName("component_key");
        this.A_TEXT = getInternalColumnByName("a_text");
        this.SEQUENCE_NO = getInternalColumnByName("sequence_no");
    }

}
