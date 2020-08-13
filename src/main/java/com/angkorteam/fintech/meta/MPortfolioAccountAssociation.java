package com.angkorteam.fintech.meta;

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
        this.ID = this.delegate.getColumnByName("id");
        this.LOAN_ACCOUNT_ID = this.delegate.getColumnByName("loan_account_id");
        this.SAVING_ACCOUNT_ID = this.delegate.getColumnByName("savings_account_id");
        this.LINKED_LOAN_ACCOUNT_ID = this.delegate.getColumnByName("linked_loan_account_id");
        this.LINKED_SAVING_ACCOUNT_ID = this.delegate.getColumnByName("linked_savings_account_id");
        this.ASSOCIATION_TYPE_ENUM = this.delegate.getColumnByName("association_type_enum");
        this.ACTIVE = this.delegate.getColumnByName("is_active");
    }

}
