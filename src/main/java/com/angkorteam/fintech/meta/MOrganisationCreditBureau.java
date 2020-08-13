package com.angkorteam.fintech.meta;

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
        this.ID = this.delegate.getColumnByName("id");
        this.ALIAS = this.delegate.getColumnByName("alias");
        this.CREDIT_BUREAU_ID = this.delegate.getColumnByName("creditbureau_id");
        this.ACTIVE = this.delegate.getColumnByName("is_active");
    }

}
