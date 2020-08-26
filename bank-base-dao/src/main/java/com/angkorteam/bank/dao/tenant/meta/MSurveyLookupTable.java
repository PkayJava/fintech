package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MSurveyLookupTable extends AbstractTable {

    public final Column ID;

    public final Column SURVEY_ID;

    public final Column A_KEY;

    public final Column VALUE_FROM;

    public final Column DESCRIPTION;

    public final Column VALUE_TO;

    public final Column SCORE;

    public static MSurveyLookupTable staticInitialize(DataContext dataContext) {
        return new MSurveyLookupTable(dataContext);
    }

    private MSurveyLookupTable(DataContext dataContext) {
        super(dataContext, "m_survey_lookup_tables");
        this.ID = getInternalColumnByName("id");
        this.SURVEY_ID = getInternalColumnByName("survey_id");
        this.A_KEY = getInternalColumnByName("a_key");
        this.DESCRIPTION = getInternalColumnByName("description");
        this.VALUE_FROM = getInternalColumnByName("value_from");
        this.VALUE_TO = getInternalColumnByName("value_to");
        this.SCORE = getInternalColumnByName("score");
    }

}
