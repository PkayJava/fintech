package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class Topic extends AbstractTable {

    public final Column ID;

    public final Column TITLE;

    public final Column ENABLED;

    public final Column ENTITY_ID;

    public final Column ENTITY_TYPE;

    public final Column MEMBER_TYPE;

    public static Topic staticInitialize(DataContext dataContext) {
        return new Topic(dataContext);
    }

    private Topic(DataContext dataContext) {
        super(dataContext, "topic");
        this.ID = getInternalColumnByName("id");
        this.TITLE = getInternalColumnByName("title");
        this.ENABLED = getInternalColumnByName("enabled");
        this.ENTITY_ID = getInternalColumnByName("entity_id");
        this.ENTITY_TYPE = getInternalColumnByName("entity_type");
        this.MEMBER_TYPE = getInternalColumnByName("member_type");
    }

}
