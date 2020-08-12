package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class AccGlAccount extends AbstractTable {

    public final Column ID;

    public final Column NAME;

    public final Column PARENT_ID;

    public final Column HIERARCHY;

    public final Column GL_CODE;

    public final Column DISABLED;

    public final Column MANUAL_JOURNAL_ENTRIES_ALLOWED;

    public final Column ACCOUNT_USAGE;

    public final Column CLASSIFICATION_ENUM;

    public final Column TAG_ID;

    public final Column DESCRIPTION;

    public static AccGlAccount staticInitialize(DataContext dataContext) {
        return new AccGlAccount(dataContext);
    }

    private AccGlAccount(DataContext dataContext) {
        super(dataContext, "acc_gl_account");
        this.ID = this.delegate.getColumnByName("id");
        this.NAME = this.delegate.getColumnByName("name");
        this.PARENT_ID = this.delegate.getColumnByName("parent_id");
        this.HIERARCHY = this.delegate.getColumnByName("hierarchy");
        this.GL_CODE = this.delegate.getColumnByName("gl_code");
        this.DISABLED = this.delegate.getColumnByName("disabled");
        this.MANUAL_JOURNAL_ENTRIES_ALLOWED = this.delegate.getColumnByName("manual_journal_entries_allowed");
        this.ACCOUNT_USAGE = this.delegate.getColumnByName("account_usage");
        this.CLASSIFICATION_ENUM = this.delegate.getColumnByName("classification_enum");
        this.TAG_ID = this.delegate.getColumnByName("tag_id");
        this.DESCRIPTION = this.delegate.getColumnByName("description");
    }

}
