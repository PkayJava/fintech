package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MPortfolioAccountAssociation extends AbstractTable {

    public final Column ID;

    public final Column LOAN_ACCOUNT_ID;

    public final Column SAVING_ACCOUNT_ID;

    public final Column LINKED_LOAN_ACCOUNT_ID;

    public final Column LINKED_SAVING_ACCOUNT_ID;

    public final Column ASSOCIATION_TYPE_ENUM;

    public final Column ACTIVE;

    public static MPortfolioAccountAssociation staticInitialize(DataContext dataContext) {
        return new MPortfolioAccountAssociation(dataContext);
    }

    private MPortfolioAccountAssociation(DataContext dataContext) {
        super(dataContext, "m_portfolio_account_associations");
        this.ID = getInternalColumnByName("id");
        this.LOAN_ACCOUNT_ID = getInternalColumnByName("loan_account_id");
        this.SAVING_ACCOUNT_ID = getInternalColumnByName("savings_account_id");
        this.LINKED_LOAN_ACCOUNT_ID = getInternalColumnByName("linked_loan_account_id");
        this.LINKED_SAVING_ACCOUNT_ID = getInternalColumnByName("linked_savings_account_id");
        this.ASSOCIATION_TYPE_ENUM = getInternalColumnByName("association_type_enum");
        this.ACTIVE = getInternalColumnByName("is_active");
    }

}
