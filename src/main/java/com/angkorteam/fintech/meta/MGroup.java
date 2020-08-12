package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MGroup extends AbstractTable {

    public final Column ID;

    public final Column EXTERNAL_ID;

    public final Column STATUS_ENUM;

    public final Column ACTIVATION_DATE;

    public final Column OFFICE_ID;

    public final Column STAFF_ID;

    public final Column PARENT_ID;

    public final Column LEVEL_ID;

    public final Column DISPLAY_NAME;

    public final Column HIERARCHY;

    public final Column CLOSURE_REASON_CV_ID;

    public final Column CLOSED_ON_DATE;

    public final Column ACTIVATED_ON_USER_ID;

    public final Column SUBMITTED_ON_DATE;

    public final Column SUBMITTED_ON_USER_ID;

    public final Column CLOSED_ON_USER_ID;

    public final Column ACCOUNT_NO;

    public static MGroup staticInitialize(DataContext dataContext) {
        return new MGroup(dataContext);
    }

    private MGroup(DataContext dataContext) {
        super(dataContext, "m_group");
        this.ID = this.delegate.getColumnByName("id");
        this.EXTERNAL_ID = this.delegate.getColumnByName("external_id");
        this.STATUS_ENUM = this.delegate.getColumnByName("status_enum");
        this.ACTIVATION_DATE = this.delegate.getColumnByName("activation_date");
        this.OFFICE_ID = this.delegate.getColumnByName("office_id");
        this.STAFF_ID = this.delegate.getColumnByName("staff_id");
        this.PARENT_ID = this.delegate.getColumnByName("parent_id");
        this.LEVEL_ID = this.delegate.getColumnByName("level_id");
        this.DISPLAY_NAME = this.delegate.getColumnByName("display_name");
        this.HIERARCHY = this.delegate.getColumnByName("hierarchy");
        this.CLOSURE_REASON_CV_ID = this.delegate.getColumnByName("closure_reason_cv_id");
        this.CLOSED_ON_DATE = this.delegate.getColumnByName("closedon_date");
        this.ACTIVATED_ON_USER_ID = this.delegate.getColumnByName("activatedon_userid");
        this.SUBMITTED_ON_DATE = this.delegate.getColumnByName("submittedon_date");
        this.SUBMITTED_ON_USER_ID = this.delegate.getColumnByName("submittedon_userid");
        this.CLOSED_ON_USER_ID = this.delegate.getColumnByName("closedon_userid");
        this.ACCOUNT_NO = this.delegate.getColumnByName("account_no");
    }

}
