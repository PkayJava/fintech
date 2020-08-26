package com.angkorteam.bank.dao.base.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MShareProduct extends AbstractTable {

    public final Column ID;

    public final Column NAME;

    public final Column SHORT_NAME;

    public final Column EXTERNAL_ID;

    public final Column DESCRIPTION;

    public final Column START_DATE;

    public final Column END_DATE;

    public final Column CURRENCY_CODE;

    public final Column CURRENCY_DIGIT;

    public final Column CURRENCY_MULTIPLE_OF;

    public final Column TOTAL_SHARE;

    public final Column ISSUED_SHARE;

    public final Column TOTAL_SUBSCRIBED_SHARE;

    public final Column UNIT_PRICE;

    public final Column CAPITAL_AMOUNT;

    public final Column MINIMUM_CLIENT_SHARE;

    public final Column NOMINAL_CLIENT_SHARE;

    public final Column MAXIMUM_CLIENT_SHARE;

    public final Column MINIMUM_ACTIVE_PERIOD_FREQUENCY;

    public final Column MINIMUM_ACTIVE_PERIOD_FREQUENCY_ENUM;

    public final Column LOCKIN_PERIOD_FREQUENCY;

    public final Column LOCKIN_PERIOD_FREQUENCY_ENUM;

    public final Column ALLOW_DIVIDEND_INACTIVE_CLIENT;

    public final Column CREATED_BY_ID;

    public final Column CREATED_DATE;

    public final Column LAST_MODIFIED_BY_ID;

    public final Column LAST_MODIFIED_DATE;

    public final Column ACCOUNTING_TYPE;

    public static MShareProduct staticInitialize(DataContext dataContext) {
        return new MShareProduct(dataContext);
    }

    private MShareProduct(DataContext dataContext) {
        super(dataContext, "m_share_product");
        this.ID = getInternalColumnByName("id");
        this.NAME = getInternalColumnByName("name");
        this.SHORT_NAME = getInternalColumnByName("short_name");
        this.EXTERNAL_ID = getInternalColumnByName("external_id");
        this.DESCRIPTION = getInternalColumnByName("description");
        this.START_DATE = getInternalColumnByName("start_date");
        this.END_DATE = getInternalColumnByName("end_date");
        this.CURRENCY_CODE = getInternalColumnByName("currency_code");
        this.CURRENCY_DIGIT = getInternalColumnByName("currency_digits");
        this.CURRENCY_MULTIPLE_OF = getInternalColumnByName("currency_multiplesof");
        this.TOTAL_SHARE = getInternalColumnByName("total_shares");
        this.ISSUED_SHARE = getInternalColumnByName("issued_shares");
        this.TOTAL_SUBSCRIBED_SHARE = getInternalColumnByName("totalsubscribed_shares");
        this.UNIT_PRICE = getInternalColumnByName("unit_price");
        this.CAPITAL_AMOUNT = getInternalColumnByName("capital_amount");
        this.MINIMUM_CLIENT_SHARE = getInternalColumnByName("minimum_client_shares");
        this.NOMINAL_CLIENT_SHARE = getInternalColumnByName("nominal_client_shares");
        this.MAXIMUM_CLIENT_SHARE = getInternalColumnByName("maximum_client_shares");
        this.MINIMUM_ACTIVE_PERIOD_FREQUENCY = getInternalColumnByName("minimum_active_period_frequency");
        this.MINIMUM_ACTIVE_PERIOD_FREQUENCY_ENUM = getInternalColumnByName("minimum_active_period_frequency_enum");
        this.LOCKIN_PERIOD_FREQUENCY = getInternalColumnByName("lockin_period_frequency");
        this.LOCKIN_PERIOD_FREQUENCY_ENUM = getInternalColumnByName("lockin_period_frequency_enum");
        this.ALLOW_DIVIDEND_INACTIVE_CLIENT = getInternalColumnByName("allow_dividends_inactive_clients");
        this.CREATED_BY_ID = getInternalColumnByName("createdby_id");
        this.CREATED_DATE = getInternalColumnByName("created_date");
        this.LAST_MODIFIED_BY_ID = getInternalColumnByName("lastmodifiedby_id");
        this.LAST_MODIFIED_DATE = getInternalColumnByName("lastmodified_date");
        this.ACCOUNTING_TYPE = getInternalColumnByName("accounting_type");
    }

}
