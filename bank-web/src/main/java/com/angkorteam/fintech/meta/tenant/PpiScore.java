package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class PpiScore extends AbstractTable {

    public final Column ID;

    public final Column SCORE_FROM;

    public final Column SCORE_TO;

    public static PpiScore staticInitialize(DataContext dataContext) {
        return new PpiScore(dataContext);
    }

    private PpiScore(DataContext dataContext) {
        super(dataContext, "ppi_scores");
        this.ID = getInternalColumnByName("id");
        this.SCORE_FROM = getInternalColumnByName("score_from");
        this.SCORE_TO = getInternalColumnByName("score_to");
    }

}
