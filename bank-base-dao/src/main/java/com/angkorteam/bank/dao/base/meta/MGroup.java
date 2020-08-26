package com.angkorteam.bank.dao.base.meta;

import com.angkorteam.metamodel.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.EXTERNAL_ID = getInternalColumnByName("external_id");
        this.STATUS_ENUM = getInternalColumnByName("status_enum");
        this.ACTIVATION_DATE = getInternalColumnByName("activation_date");
        this.OFFICE_ID = getInternalColumnByName("office_id");
        this.STAFF_ID = getInternalColumnByName("staff_id");
        this.PARENT_ID = getInternalColumnByName("parent_id");
        this.LEVEL_ID = getInternalColumnByName("level_id");
        this.DISPLAY_NAME = getInternalColumnByName("display_name");
        this.HIERARCHY = getInternalColumnByName("hierarchy");
        this.CLOSURE_REASON_CV_ID = getInternalColumnByName("closure_reason_cv_id");
        this.CLOSED_ON_DATE = getInternalColumnByName("closedon_date");
        this.ACTIVATED_ON_USER_ID = getInternalColumnByName("activatedon_userid");
        this.SUBMITTED_ON_DATE = getInternalColumnByName("submittedon_date");
        this.SUBMITTED_ON_USER_ID = getInternalColumnByName("submittedon_userid");
        this.CLOSED_ON_USER_ID = getInternalColumnByName("closedon_userid");
        this.ACCOUNT_NO = getInternalColumnByName("account_no");
    }

}
