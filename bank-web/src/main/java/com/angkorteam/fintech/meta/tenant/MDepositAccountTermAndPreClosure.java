package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MDepositAccountTermAndPreClosure extends AbstractTable {

    public final Column ID;

    public final Column SAVING_ACCOUNT_ID;

    public final Column MIN_DEPOSIT_TERM;

    public final Column MAX_DEPOSIT_TERM;

    public final Column MIN_DEPOSIT_TERM_TYPE_ENUM;

    public final Column MAX_DEPOSIT_TERM_TYPE_ENUM;

    public final Column IN_MULTIPLE_OF_DEPOSIT_TERM;

    public final Column IN_MULTIPLE_OF_DEPOSIT_TERM_TYPE_ENUM;

    public final Column PRE_CLOSURE_PENAL_APPLICABLE;

    public final Column PRE_CLOSURE_PENAL_INTEREST;

    public final Column PRE_CLOSURE_PENAL_INTEREST_ON_ENUM;

    public final Column DEPOSIT_PERIOD;

    public final Column DEPOSIT_PERIOD_FREQUENCY_ENUM;

    public final Column DEPOSIT_AMOUNT;

    public final Column MATURITY_AMOUNT;

    public final Column MATURITY_DATE;

    public final Column ON_ACCOUNT_CLOSURE_ENUM;

    public final Column EXPECTED_FIRST_DEPOSITON_DATE;

    public final Column TRANSFER_INTEREST_TO_LINKED_ACCOUNT;

    public final Column TRANSFER_TO_SAVING_ACCOUNT_ID;

    public static MDepositAccountTermAndPreClosure staticInitialize(DataContext dataContext) {
        return new MDepositAccountTermAndPreClosure(dataContext);
    }

    private MDepositAccountTermAndPreClosure(DataContext dataContext) {
        super(dataContext, "m_deposit_account_term_and_preclosure");
        this.ID = getInternalColumnByName("id");
        this.SAVING_ACCOUNT_ID = getInternalColumnByName("savings_account_id");
        this.MIN_DEPOSIT_TERM = getInternalColumnByName("min_deposit_term");
        this.MAX_DEPOSIT_TERM = getInternalColumnByName("max_deposit_term");
        this.MIN_DEPOSIT_TERM_TYPE_ENUM = getInternalColumnByName("min_deposit_term_type_enum");
        this.MAX_DEPOSIT_TERM_TYPE_ENUM = getInternalColumnByName("max_deposit_term_type_enum");
        this.IN_MULTIPLE_OF_DEPOSIT_TERM = getInternalColumnByName("in_multiples_of_deposit_term");
        this.IN_MULTIPLE_OF_DEPOSIT_TERM_TYPE_ENUM = getInternalColumnByName("in_multiples_of_deposit_term_type_enum");
        this.PRE_CLOSURE_PENAL_APPLICABLE = getInternalColumnByName("pre_closure_penal_applicable");
        this.PRE_CLOSURE_PENAL_INTEREST = getInternalColumnByName("pre_closure_penal_interest");
        this.PRE_CLOSURE_PENAL_INTEREST_ON_ENUM = getInternalColumnByName("pre_closure_penal_interest_on_enum");
        this.DEPOSIT_PERIOD = getInternalColumnByName("deposit_period");
        this.DEPOSIT_PERIOD_FREQUENCY_ENUM = getInternalColumnByName("deposit_period_frequency_enum");
        this.DEPOSIT_AMOUNT = getInternalColumnByName("deposit_amount");
        this.MATURITY_AMOUNT = getInternalColumnByName("maturity_amount");
        this.MATURITY_DATE = getInternalColumnByName("maturity_date");
        this.ON_ACCOUNT_CLOSURE_ENUM = getInternalColumnByName("on_account_closure_enum");
        this.EXPECTED_FIRST_DEPOSITON_DATE = getInternalColumnByName("expected_firstdepositon_date");
        this.TRANSFER_INTEREST_TO_LINKED_ACCOUNT = getInternalColumnByName("transfer_interest_to_linked_account");
        this.TRANSFER_TO_SAVING_ACCOUNT_ID = getInternalColumnByName("transfer_to_savings_account_id");
    }

}
