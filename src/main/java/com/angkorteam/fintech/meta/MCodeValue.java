package com.angkorteam.fintech.meta;

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
        this.ID = this.delegate.getColumnByName("id");
        this.CODE_ID = this.delegate.getColumnByName("code_id");
        this.CODE_VALUE = this.delegate.getColumnByName("code_value");
        this.CODE_DESCRIPTION = this.delegate.getColumnByName("code_description");
        this.ORDER_POSITION = this.delegate.getColumnByName("order_position");
        this.CODE_SCORE = this.delegate.getColumnByName("code_score");
        this.ACTIVE = this.delegate.getColumnByName("is_active");
        this.MANDATORY = this.delegate.getColumnByName("is_mandatory");
    }

}
