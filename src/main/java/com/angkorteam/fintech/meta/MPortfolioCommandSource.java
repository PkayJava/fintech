package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MPortfolioCommandSource extends AbstractTable {

    public final Column ID;

    public final Column ACTION_NAME;

    public final Column ENTITY_NAME;

    public final Column OFFICE_ID;

    public final Column GROUP_ID;

    public final Column CLIENT_ID;

    public final Column LOAN_ID;

    public final Column SAVING_ACCOUNT_ID;

    public final Column API_GET_URL;

    public final Column RESOURCE_ID;

    public final Column SUB_RESOURCE_ID;

    public final Column COMMAND_AS_JSON;

    public final Column MAKER_ID;

    public final Column MADE_ON_DATE;

    public final Column CHECKER_ID;

    public final Column CHECKED_ON_DATE;

    public final Column PROCESSING_RESULT_ENUM;

    public final Column PRODUCT_ID;

    public final Column TRANSACTION_ID;

    public final Column CREDIT_BUREAU_ID;

    public final Column ORGANISATION_CREDIT_BUREAU_ID;

    public static MPortfolioCommandSource staticInitialize(DataContext dataContext) {
        return new MPortfolioCommandSource(dataContext);
    }

    private MPortfolioCommandSource(DataContext dataContext) {
        super(dataContext, "m_portfolio_command_source");
        this.ID = this.delegate.getColumnByName("id");
        this.ACTION_NAME = this.delegate.getColumnByName("action_name");
        this.ENTITY_NAME = this.delegate.getColumnByName("entity_name");
        this.OFFICE_ID = this.delegate.getColumnByName("office_id");
        this.GROUP_ID = this.delegate.getColumnByName("group_id");
        this.CLIENT_ID = this.delegate.getColumnByName("client_id");
        this.LOAN_ID = this.delegate.getColumnByName("loan_id");
        this.SAVING_ACCOUNT_ID = this.delegate.getColumnByName("savings_account_id");
        this.API_GET_URL = this.delegate.getColumnByName("api_get_url");
        this.RESOURCE_ID = this.delegate.getColumnByName("resource_id");
        this.SUB_RESOURCE_ID = this.delegate.getColumnByName("subresource_id");
        this.COMMAND_AS_JSON = this.delegate.getColumnByName("command_as_json");
        this.MAKER_ID = this.delegate.getColumnByName("maker_id");
        this.MADE_ON_DATE = this.delegate.getColumnByName("made_on_date");
        this.CHECKER_ID = this.delegate.getColumnByName("checker_id");
        this.CHECKED_ON_DATE = this.delegate.getColumnByName("checked_on_date");
        this.PROCESSING_RESULT_ENUM = this.delegate.getColumnByName("processing_result_enum");
        this.PRODUCT_ID = this.delegate.getColumnByName("product_id");
        this.TRANSACTION_ID = this.delegate.getColumnByName("transaction_id");
        this.CREDIT_BUREAU_ID = this.delegate.getColumnByName("creditbureau_id");
        this.ORGANISATION_CREDIT_BUREAU_ID = this.delegate.getColumnByName("organisation_creditbureau_id");
    }

}
