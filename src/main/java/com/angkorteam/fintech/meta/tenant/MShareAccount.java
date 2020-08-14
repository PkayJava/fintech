package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MShareAccount extends AbstractTable {

    public final Column ID;

    public final Column ACCOUNT_NO;

    public final Column PRODUCT_ID;

    public final Column CLIENT_ID;

    public final Column EXTERNAL_ID;

    public final Column STATUS_ENUM;

    public final Column TOTAL_APPROVED_SHARE;

    public final Column TOTAL_PENDING_SHARE;

    public final Column SUBMITTED_DATE;

    public final Column SUBMITTED_USER_ID;

    public final Column APPROVED_DATE;

    public final Column APPROVED_USER_ID;

    public final Column REJECTED_DATE;

    public final Column REJECTED_USER_ID;

    public final Column ACTIVATED_DATE;

    public final Column ACTIVATED_USER_ID;

    public final Column CLOSED_DATE;

    public final Column CLOSED_USER_ID;

    public final Column CURRENCY_CODE;

    public final Column CURRENCY_DIGIT;

    public final Column CURRENCY_MULTIPLE_OF;

    public final Column SAVING_ACCOUNT_ID;

    public final Column MINIMUM_ACTIVE_PERIOD_FREQUENCY;

    public final Column MINIMUM_ACTIVE_PERIOD_FREQUENCY_ENUM;

    public final Column LOCKIN_PERIOD_FREQUENCY;

    public final Column LOCKIN_PERIOD_FREQUENCY_ENUM;

    public final Column ALLOW_DIVIDENDS_INACTIVE_CLIENT;

    public final Column CREATED_DATE;

    public final Column LAST_MODIFIED_BY_ID;

    public final Column LAST_MODIFIED_DATE;

    public static MShareAccount staticInitialize(DataContext dataContext) {
        return new MShareAccount(dataContext);
    }

    private MShareAccount(DataContext dataContext) {
        super(dataContext, "m_share_account");
        this.ID = getInternalColumnByName("id");
        this.ACCOUNT_NO = getInternalColumnByName("account_no");
        this.PRODUCT_ID = getInternalColumnByName("product_id");
        this.CLIENT_ID = getInternalColumnByName("client_id");
        this.EXTERNAL_ID = getInternalColumnByName("external_id");
        this.STATUS_ENUM = getInternalColumnByName("status_enum");
        this.TOTAL_APPROVED_SHARE = getInternalColumnByName("total_approved_shares");
        this.TOTAL_PENDING_SHARE = getInternalColumnByName("total_pending_shares");
        this.SUBMITTED_DATE = getInternalColumnByName("submitted_date");
        this.SUBMITTED_USER_ID = getInternalColumnByName("submitted_userid");
        this.APPROVED_DATE = getInternalColumnByName("approved_date");
        this.APPROVED_USER_ID = getInternalColumnByName("approved_userid");
        this.REJECTED_DATE = getInternalColumnByName("rejected_date");
        this.REJECTED_USER_ID = getInternalColumnByName("rejected_userid");
        this.ACTIVATED_DATE = getInternalColumnByName("activated_date");
        this.ACTIVATED_USER_ID = getInternalColumnByName("activated_userid");
        this.CLOSED_DATE = getInternalColumnByName("closed_date");
        this.CLOSED_USER_ID = getInternalColumnByName("closed_userid");
        this.CURRENCY_CODE = getInternalColumnByName("currency_code");
        this.CURRENCY_DIGIT = getInternalColumnByName("currency_digits");
        this.CURRENCY_MULTIPLE_OF = getInternalColumnByName("currency_multiplesof");
        this.SAVING_ACCOUNT_ID = getInternalColumnByName("savings_account_id");
        this.MINIMUM_ACTIVE_PERIOD_FREQUENCY = getInternalColumnByName("minimum_active_period_frequency");
        this.MINIMUM_ACTIVE_PERIOD_FREQUENCY_ENUM = getInternalColumnByName("minimum_active_period_frequency_enum");
        this.LOCKIN_PERIOD_FREQUENCY = getInternalColumnByName("lockin_period_frequency");
        this.LOCKIN_PERIOD_FREQUENCY_ENUM = getInternalColumnByName("lockin_period_frequency_enum");
        this.ALLOW_DIVIDENDS_INACTIVE_CLIENT = getInternalColumnByName("allow_dividends_inactive_clients");
        this.CREATED_DATE = getInternalColumnByName("created_date");
        this.LAST_MODIFIED_BY_ID = getInternalColumnByName("lastmodifiedby_id");
        this.LAST_MODIFIED_DATE = getInternalColumnByName("lastmodified_date");
    }

}
