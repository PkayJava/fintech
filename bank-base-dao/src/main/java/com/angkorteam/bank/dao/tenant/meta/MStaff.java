package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MStaff extends AbstractTable {

    public final Column ID;

    public final Column LOAN_OFFICER;

    public final Column OFFICE_ID;

    public final Column FIRST_NAME;

    public final Column LAST_NAME;

    public final Column DISPLAY_NAME;

    public final Column MOBILE_NO;

    public final Column EXTERNAL_ID;

    public final Column ORGANISATIONAL_ROLE_ENUM;

    public final Column ORGANISATIONAL_ROLE_PARENT_STAFF_ID;

    public final Column ACTIVE;

    public final Column JOINING_DATE;

    public final Column IMAGE_ID;

    public final Column EMAIL_ADDRESS;

    public static MStaff staticInitialize(DataContext dataContext) {
        return new MStaff(dataContext);
    }

    private MStaff(DataContext dataContext) {
        super(dataContext, "m_staff");
        this.ID = getInternalColumnByName("id");
        this.LOAN_OFFICER = getInternalColumnByName("is_loan_officer");
        this.OFFICE_ID = getInternalColumnByName("office_id");
        this.FIRST_NAME = getInternalColumnByName("firstname");
        this.LAST_NAME = getInternalColumnByName("lastname");
        this.DISPLAY_NAME = getInternalColumnByName("display_name");
        this.MOBILE_NO = getInternalColumnByName("mobile_no");
        this.EXTERNAL_ID = getInternalColumnByName("external_id");
        this.ORGANISATIONAL_ROLE_ENUM = getInternalColumnByName("organisational_role_enum");
        this.ORGANISATIONAL_ROLE_PARENT_STAFF_ID = getInternalColumnByName("organisational_role_parent_staff_id");
        this.ACTIVE = getInternalColumnByName("is_active");
        this.JOINING_DATE = getInternalColumnByName("joining_date");
        this.IMAGE_ID = getInternalColumnByName("image_id");
        this.EMAIL_ADDRESS = getInternalColumnByName("email_address");
    }

}
