package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class AccGLClosure extends AbstractTable {

    public final Column ID;

    public final Column OFFICE_ID;

    public final Column CLOSING_DATE;

    public final Column DELETED;

    public final Column CREATED_BY_ID;

    public final Column LAST_MODIFIED_BY_ID;

    public final Column CREATED_DATE;

    public final Column LAST_MODIFIED_DATE;

    public final Column COMMENT;

    public static AccGLClosure staticInitialize(DataContext dataContext) {
        return new AccGLClosure(dataContext);
    }

    private AccGLClosure(DataContext dataContext) {
        super(dataContext, "acc_gl_closure");
        this.ID = getInternalColumnByName("id");
        this.OFFICE_ID = getInternalColumnByName("office_id");
        this.CLOSING_DATE = getInternalColumnByName("closing_date");
        this.DELETED = getInternalColumnByName("is_deleted");
        this.CREATED_BY_ID = getInternalColumnByName("createdby_id");
        this.LAST_MODIFIED_BY_ID = getInternalColumnByName("lastmodifiedby_id");
        this.CREATED_DATE = getInternalColumnByName("created_date");
        this.LAST_MODIFIED_DATE = getInternalColumnByName("lastmodified_date");
        this.COMMENT = getInternalColumnByName("comments");
    }

}
