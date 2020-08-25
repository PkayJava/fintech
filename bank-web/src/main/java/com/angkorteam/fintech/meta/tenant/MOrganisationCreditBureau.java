package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MOrganisationCreditBureau extends AbstractTable {

    public final Column ID;

    public final Column ALIAS;

    public final Column CREDIT_BUREAU_ID;

    public final Column ACTIVE;

    public static MOrganisationCreditBureau staticInitialize(DataContext dataContext) {
        return new MOrganisationCreditBureau(dataContext);
    }

    private MOrganisationCreditBureau(DataContext dataContext) {
        super(dataContext, "m_organisation_creditbureau");
        this.ID = getInternalColumnByName("id");
        this.ALIAS = getInternalColumnByName("alias");
        this.CREDIT_BUREAU_ID = getInternalColumnByName("creditbureau_id");
        this.ACTIVE = getInternalColumnByName("is_active");
    }

}
