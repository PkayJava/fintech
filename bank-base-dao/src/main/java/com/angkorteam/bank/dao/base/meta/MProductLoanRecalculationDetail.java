package com.angkorteam.bank.dao.base.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MProductLoanRecalculationDetail extends AbstractTable {

    public final Column ID;

    public final Column PRODUCT_ID;

    public final Column COMPOUND_TYPE_ENUM;

    public final Column RESCHEDULE_STRATEGY_ENUM;

    public final Column REST_FREQUENCY_TYPE_ENUM;

    public final Column REST_FREQUENCY_INTERVAL;

    public final Column ARREARS_BASED_ON_ORIGINAL_SCHEDULE;

    public final Column PRE_CLOSE_INTEREST_CALCULATION_STRATEGY;

    public final Column COMPOUNDING_FREQUENCY_TYPE_ENUM;

    public final Column COMPOUNDING_FREQUENCY_INTERVAL;

    public final Column REST_FREQUENCY_NTH_DAY_ENUM;

    public final Column REST_FREQUENCY_ON_DAY;

    public final Column REST_FREQUENCY_WEEKDAY_ENUM;

    public final Column COMPOUNDING_FREQUENCY_NTH_DAY_ENUM;

    public final Column COMPOUNDING_FREQUENCY_ON_DAY;

    public final Column COMPOUNDING_FREQUENCY_WEEKDAY_ENUM;

    public final Column COMPOUNDING_TO_BE_POSTED_AS_TRANSACTION;

    public final Column ALLOW_COMPOUNDING_ON_EOD;

    public static MProductLoanRecalculationDetail staticInitialize(DataContext dataContext) {
        return new MProductLoanRecalculationDetail(dataContext);
    }

    private MProductLoanRecalculationDetail(DataContext dataContext) {
        super(dataContext, "m_product_loan_recalculation_details");
        this.ID = getInternalColumnByName("id");
        this.PRODUCT_ID = getInternalColumnByName("product_id");
        this.COMPOUND_TYPE_ENUM = getInternalColumnByName("compound_type_enum");
        this.RESCHEDULE_STRATEGY_ENUM = getInternalColumnByName("reschedule_strategy_enum");
        this.REST_FREQUENCY_TYPE_ENUM = getInternalColumnByName("rest_frequency_type_enum");
        this.REST_FREQUENCY_INTERVAL = getInternalColumnByName("rest_frequency_interval");
        this.ARREARS_BASED_ON_ORIGINAL_SCHEDULE = getInternalColumnByName("arrears_based_on_original_schedule");
        this.PRE_CLOSE_INTEREST_CALCULATION_STRATEGY = getInternalColumnByName("pre_close_interest_calculation_strategy");
        this.COMPOUNDING_FREQUENCY_TYPE_ENUM = getInternalColumnByName("compounding_frequency_type_enum");
        this.COMPOUNDING_FREQUENCY_INTERVAL = getInternalColumnByName("compounding_frequency_interval");
        this.REST_FREQUENCY_NTH_DAY_ENUM = getInternalColumnByName("rest_frequency_nth_day_enum");
        this.REST_FREQUENCY_ON_DAY = getInternalColumnByName("rest_frequency_on_day");
        this.REST_FREQUENCY_WEEKDAY_ENUM = getInternalColumnByName("rest_frequency_weekday_enum");
        this.COMPOUNDING_FREQUENCY_NTH_DAY_ENUM = getInternalColumnByName("compounding_frequency_nth_day_enum");
        this.COMPOUNDING_FREQUENCY_ON_DAY = getInternalColumnByName("compounding_frequency_on_day");
        this.COMPOUNDING_FREQUENCY_WEEKDAY_ENUM = getInternalColumnByName("compounding_frequency_weekday_enum");
        this.COMPOUNDING_TO_BE_POSTED_AS_TRANSACTION = getInternalColumnByName("is_compounding_to_be_posted_as_transaction");
        this.ALLOW_COMPOUNDING_ON_EOD = getInternalColumnByName("allow_compounding_on_eod");
    }

}
