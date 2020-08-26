package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.metamodel.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.ACC_RULE_ID = getInternalColumnByName("acc_rule_id");
        this.TAG_ID = getInternalColumnByName("tag_id");
        this.ACC_TYPE_ENUM = getInternalColumnByName("acc_type_enum");
    }

}
