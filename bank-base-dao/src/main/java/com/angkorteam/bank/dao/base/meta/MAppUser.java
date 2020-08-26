package com.angkorteam.bank.dao.base.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MAppUser extends AbstractTable {

    public final Column ID;

    public final Column DELETED;

    public final Column OFFICE_ID;

    public final Column STAFF_ID;

    public final Column USERNAME;

    public final Column FIRST_NAME;

    public final Column LAST_NAME;

    public final Column PASSWORD;

    public final Column EMAIL;

    public final Column FIRST_TIME_LOGIN_REMAINING;

    public final Column NON_EXPIRED;

    public final Column NON_LOCKED;

    public final Column NON_EXPIRED_CREDENTIAL;

    public final Column ENABLED;

    public final Column LAST_TIME_PASSWORD_UPDATED;

    public final Column PASSWORD_NEVER_EXPIRED;

    public final Column SELF_SERVICE_USER;

    public static MAppUser staticInitialize(DataContext dataContext) {
        return new MAppUser(dataContext);
    }

    private MAppUser(DataContext dataContext) {
        super(dataContext, "m_appuser");
        this.ID = getInternalColumnByName("id");
        this.DELETED = getInternalColumnByName("is_deleted");
        this.OFFICE_ID = getInternalColumnByName("office_id");
        this.STAFF_ID = getInternalColumnByName("staff_id");
        this.USERNAME = getInternalColumnByName("username");
        this.FIRST_NAME = getInternalColumnByName("firstname");
        this.LAST_NAME = getInternalColumnByName("lastname");
        this.PASSWORD = getInternalColumnByName("password");
        this.EMAIL = getInternalColumnByName("email");
        this.FIRST_TIME_LOGIN_REMAINING = getInternalColumnByName("firsttime_login_remaining");
        this.NON_EXPIRED = getInternalColumnByName("nonexpired");
        this.NON_LOCKED = getInternalColumnByName("nonlocked");
        this.NON_EXPIRED_CREDENTIAL = getInternalColumnByName("nonexpired_credentials");
        this.ENABLED = getInternalColumnByName("enabled");
        this.LAST_TIME_PASSWORD_UPDATED = getInternalColumnByName("last_time_password_updated");
        this.PASSWORD_NEVER_EXPIRED = getInternalColumnByName("password_never_expires");
        this.SELF_SERVICE_USER = getInternalColumnByName("is_self_service_user");
    }

}
