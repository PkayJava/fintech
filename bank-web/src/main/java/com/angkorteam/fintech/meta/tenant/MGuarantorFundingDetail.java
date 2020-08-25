package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.GUARANTOR_ID = getInternalColumnByName("guarantor_id");
        this.ACCOUNT_ASSOCIATION_ID = getInternalColumnByName("account_associations_id");
        this.AMOUNT = getInternalColumnByName("amount");
        this.AMOUNT_RELEASED_DERIVED = getInternalColumnByName("amount_released_derived");
        this.AMOUNT_REMAINING_DERIVED = getInternalColumnByName("amount_remaining_derived");
        this.AMOUNT_TRANSFERRED_DERIVED = getInternalColumnByName("amount_transfered_derived");
        this.STATUS_ENUM = getInternalColumnByName("status_enum");
    }

}
