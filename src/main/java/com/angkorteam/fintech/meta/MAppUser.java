package com.angkorteam.fintech.meta;

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
        this.ID = this.delegate.getColumnByName("id");
        this.DELETED = this.delegate.getColumnByName("is_deleted");
        this.OFFICE_ID = this.delegate.getColumnByName("office_id");
        this.STAFF_ID = this.delegate.getColumnByName("staff_id");
        this.USERNAME = this.delegate.getColumnByName("username");
        this.FIRST_NAME = this.delegate.getColumnByName("firstname");
        this.LAST_NAME = this.delegate.getColumnByName("lastname");
        this.PASSWORD = this.delegate.getColumnByName("password");
        this.EMAIL = this.delegate.getColumnByName("email");
        this.FIRST_TIME_LOGIN_REMAINING = this.delegate.getColumnByName("firsttime_login_remaining");
        this.NON_EXPIRED = this.delegate.getColumnByName("nonexpired");
        this.NON_LOCKED = this.delegate.getColumnByName("nonlocked");
        this.NON_EXPIRED_CREDENTIAL = this.delegate.getColumnByName("nonexpired_credentials");
        this.ENABLED = this.delegate.getColumnByName("enabled");
        this.LAST_TIME_PASSWORD_UPDATED = this.delegate.getColumnByName("last_time_password_updated");
        this.PASSWORD_NEVER_EXPIRED = this.delegate.getColumnByName("password_never_expires");
        this.SELF_SERVICE_USER = this.delegate.getColumnByName("is_self_service_user");
    }

}
