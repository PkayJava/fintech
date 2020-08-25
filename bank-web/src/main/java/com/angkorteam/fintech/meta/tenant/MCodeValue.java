package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MCodeValue extends AbstractTable {

    public final Column ID;

    public final Column CODE_ID;

    public final Column CODE_VALUE;

    public final Column CODE_DESCRIPTION;

    public final Column ORDER_POSITION;

    public final Column CODE_SCORE;

    public final Column ACTIVE;

    public final Column MANDATORY;

    public static MCodeValue staticInitialize(DataContext dataContext) {
        return new MCodeValue(dataContext);
    }

    private MCodeValue(DataContext dataContext) {
        super(dataContext, "m_code_value");
        this.ID = getInternalColumnByName("id");
        this.CODE_ID = getInternalColumnByName("code_id");
        this.CODE_VALUE = getInternalColumnByName("code_value");
        this.CODE_DESCRIPTION = getInternalColumnByName("code_description");
        this.ORDER_POSITION = getInternalColumnByName("order_position");
        this.CODE_SCORE = getInternalColumnByName("code_score");
        this.ACTIVE = getInternalColumnByName("is_active");
        this.MANDATORY = getInternalColumnByName("is_mandatory");
    }

}
