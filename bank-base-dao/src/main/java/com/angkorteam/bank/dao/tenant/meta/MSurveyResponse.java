package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MSurveyResponse extends AbstractTable {

    public final Column ID;

    public final Column QUESTION_ID;

    public final Column A_VALUE;

    public final Column A_TEXT;

    public final Column SEQUENCE_NO;

    public static MSurveyResponse staticInitialize(DataContext dataContext) {
        return new MSurveyResponse(dataContext);
    }

    private MSurveyResponse(DataContext dataContext) {
        super(dataContext, "m_survey_responses");
        this.ID = getInternalColumnByName("id");
        this.A_TEXT = getInternalColumnByName("a_text");
        this.SEQUENCE_NO = getInternalColumnByName("sequence_no");
        this.QUESTION_ID = getInternalColumnByName("question_id");
        this.A_VALUE = getInternalColumnByName("a_value");
    }

}
