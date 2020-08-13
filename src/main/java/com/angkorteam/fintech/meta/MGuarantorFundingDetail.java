package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MGuarantorFundingDetail extends AbstractTable {

    public final Column ID;

    public final Column GUARANTOR_ID;

    public final Column ACCOUNT_ASSOCIATION_ID;

    public final Column AMOUNT;

    public final Column AMOUNT_RELEASED_DERIVED;

    public final Column AMOUNT_REMAINING_DERIVED;

    public final Column AMOUNT_TRANSFERRED_DERIVED;

    public final Column STATUS_ENUM;

    public static MGuarantorFundingDetail staticInitialize(DataContext dataContext) {
        return new MGuarantorFundingDetail(dataContext);
    }

    private MGuarantorFundingDetail(DataContext dataContext) {
        super(dataContext, "m_guarantor_funding_details");
        this.ID = this.delegate.getColumnByName("id");
        this.GUARANTOR_ID = this.delegate.getColumnByName("guarantor_id");
        this.ACCOUNT_ASSOCIATION_ID = this.delegate.getColumnByName("account_associations_id");
        this.AMOUNT = this.delegate.getColumnByName("amount");
        this.AMOUNT_RELEASED_DERIVED = this.delegate.getColumnByName("amount_released_derived");
        this.AMOUNT_REMAINING_DERIVED = this.delegate.getColumnByName("amount_remaining_derived");
        this.AMOUNT_TRANSFERRED_DERIVED = this.delegate.getColumnByName("amount_transfered_derived");
        this.STATUS_ENUM = this.delegate.getColumnByName("status_enum");
    }

}
