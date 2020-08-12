package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class AccRuleTag extends AbstractTable {

    public final Column ID;

    public final Column ACC_RULE_ID;

    public final Column TAG_ID;

    public final Column ACC_TYPE_ENUM;

    public static AccRuleTag staticInitialize(DataContext dataContext) {
        return new AccRuleTag(dataContext);
    }

    private AccRuleTag(DataContext dataContext) {
        super(dataContext, "acc_rule_tags");
        this.ID = this.delegate.getColumnByName("id");
        this.ACC_RULE_ID = this.delegate.getColumnByName("acc_rule_id");
        this.TAG_ID = this.delegate.getColumnByName("tag_id");
        this.ACC_TYPE_ENUM = this.delegate.getColumnByName("acc_type_enum");
    }

}
