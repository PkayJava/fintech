package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.ACTION_NAME = getInternalColumnByName("action_name");
        this.ENTITY_NAME = getInternalColumnByName("entity_name");
        this.OFFICE_ID = getInternalColumnByName("office_id");
        this.GROUP_ID = getInternalColumnByName("group_id");
        this.CLIENT_ID = getInternalColumnByName("client_id");
        this.LOAN_ID = getInternalColumnByName("loan_id");
        this.SAVING_ACCOUNT_ID = getInternalColumnByName("savings_account_id");
        this.API_GET_URL = getInternalColumnByName("api_get_url");
        this.RESOURCE_ID = getInternalColumnByName("resource_id");
        this.SUB_RESOURCE_ID = getInternalColumnByName("subresource_id");
        this.COMMAND_AS_JSON = getInternalColumnByName("command_as_json");
        this.MAKER_ID = getInternalColumnByName("maker_id");
        this.MADE_ON_DATE = getInternalColumnByName("made_on_date");
        this.CHECKER_ID = getInternalColumnByName("checker_id");
        this.CHECKED_ON_DATE = getInternalColumnByName("checked_on_date");
        this.PROCESSING_RESULT_ENUM = getInternalColumnByName("processing_result_enum");
        this.PRODUCT_ID = getInternalColumnByName("product_id");
        this.TRANSACTION_ID = getInternalColumnByName("transaction_id");
        this.CREDIT_BUREAU_ID = getInternalColumnByName("creditbureau_id");
        this.ORGANISATION_CREDIT_BUREAU_ID = getInternalColumnByName("organisation_creditbureau_id");
    }

}
