package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MSurveyScoreCard extends AbstractTable {

    public final Column ID;

    public final Column SURVEY_ID;

    public final Column QUESTION_ID;

    public final Column RESPONSE_ID;

    public final Column USER_ID;

    public final Column CLIENT_ID;

    public final Column CREATED_ON;

    public final Column A_VALUE;

    public static MSurveyScoreCard staticInitialize(DataContext dataContext) {
        return new MSurveyScoreCard(dataContext);
    }

    private MSurveyScoreCard(DataContext dataContext) {
        super(dataContext, "m_survey_scorecards");
        this.ID = getInternalColumnByName("id");
        this.SURVEY_ID = getInternalColumnByName("survey_id");
        this.QUESTION_ID = getInternalColumnByName("question_id");
        this.RESPONSE_ID = getInternalColumnByName("response_id");
        this.USER_ID = getInternalColumnByName("user_id");
        this.CLIENT_ID = getInternalColumnByName("client_id");
        this.CREATED_ON = getInternalColumnByName("created_on");
        this.A_VALUE = getInternalColumnByName("a_value");
    }

}
