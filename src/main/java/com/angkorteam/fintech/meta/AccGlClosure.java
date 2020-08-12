package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class AccGlClosure extends AbstractTable {

    public final Column ID;

    public final Column OFFICE_ID;

    public final Column CLOSING_DATE;

    public final Column DELETED;

    public final Column CREATED_BY_ID;

    public final Column LAST_MODIFIED_BY_ID;

    public final Column CREATED_DATE;

    public final Column LAST_MODIFIED_DATE;

    public final Column COMMENT;

    public static AccGlClosure staticInitialize(DataContext dataContext) {
        return new AccGlClosure(dataContext);
    }

    private AccGlClosure(DataContext dataContext) {
        super(dataContext, "acc_gl_closure");
        this.ID = this.delegate.getColumnByName("id");
        this.OFFICE_ID = this.delegate.getColumnByName("office_id");
        this.CLOSING_DATE = this.delegate.getColumnByName("closing_date");
        this.DELETED = this.delegate.getColumnByName("is_deleted");
        this.CREATED_BY_ID = this.delegate.getColumnByName("createdby_id");
        this.LAST_MODIFIED_BY_ID = this.delegate.getColumnByName("lastmodifiedby_id");
        this.CREATED_DATE = this.delegate.getColumnByName("created_date");
        this.LAST_MODIFIED_DATE = this.delegate.getColumnByName("lastmodified_date");
        this.COMMENT = this.delegate.getColumnByName("comments");
    }

}
