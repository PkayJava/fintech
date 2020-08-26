package com.angkorteam.bank.dao.base.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class AccGLAccount extends AbstractTable {

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

    public static AccGLAccount staticInitialize(DataContext dataContext) {
        return new AccGLAccount(dataContext);
    }

    private AccGLAccount(DataContext dataContext) {
        super(dataContext, "acc_gl_account");
        this.ID = getInternalColumnByName("id");
        this.NAME = getInternalColumnByName("name");
        this.PARENT_ID = getInternalColumnByName("parent_id");
        this.HIERARCHY = getInternalColumnByName("hierarchy");
        this.GL_CODE = getInternalColumnByName("gl_code");
        this.DISABLED = getInternalColumnByName("disabled");
        this.MANUAL_JOURNAL_ENTRIES_ALLOWED = getInternalColumnByName("manual_journal_entries_allowed");
        this.ACCOUNT_USAGE = getInternalColumnByName("account_usage");
        this.CLASSIFICATION_ENUM = getInternalColumnByName("classification_enum");
        this.TAG_ID = getInternalColumnByName("tag_id");
        this.DESCRIPTION = getInternalColumnByName("description");
    }

}
