package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MSelfServiceBeneficiaryTpt extends AbstractTable {

    public final Column ID;

    public final Column APP_USER_ID;

    public final Column NAME;

    public final Column OFFICE_ID;

    public final Column CLIENT_ID;

    public final Column ACCOUNT_ID;

    public final Column ACCOUNT_TYPE;

    public final Column TRANSFER_LIMIT;

    public final Column ACTIVE;

    public static MSelfServiceBeneficiaryTpt staticInitialize(DataContext dataContext) {
        return new MSelfServiceBeneficiaryTpt(dataContext);
    }

    private MSelfServiceBeneficiaryTpt(DataContext dataContext) {
        super(dataContext, "m_selfservice_beneficiaries_tpt");
        this.ID = getInternalColumnByName("id");
        this.APP_USER_ID = getInternalColumnByName("app_user_id");
        this.NAME = getInternalColumnByName("name");
        this.OFFICE_ID = getInternalColumnByName("office_id");
        this.CLIENT_ID = getInternalColumnByName("client_id");
        this.ACCOUNT_ID = getInternalColumnByName("account_id");
        this.ACCOUNT_TYPE = getInternalColumnByName("account_type");
        this.TRANSFER_LIMIT = getInternalColumnByName("transfer_limit");
        this.ACTIVE = getInternalColumnByName("is_active");
    }

}
