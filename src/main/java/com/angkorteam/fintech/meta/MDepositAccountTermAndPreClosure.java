package com.angkorteam.fintech.meta;

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
        this.ID = this.delegate.getColumnByName("id");
        this.SAVING_ACCOUNT_ID = this.delegate.getColumnByName("savings_account_id");
        this.MIN_DEPOSIT_TERM = this.delegate.getColumnByName("min_deposit_term");
        this.MAX_DEPOSIT_TERM = this.delegate.getColumnByName("max_deposit_term");
        this.MIN_DEPOSIT_TERM_TYPE_ENUM = this.delegate.getColumnByName("min_deposit_term_type_enum");
        this.MAX_DEPOSIT_TERM_TYPE_ENUM = this.delegate.getColumnByName("max_deposit_term_type_enum");
        this.IN_MULTIPLE_OF_DEPOSIT_TERM = this.delegate.getColumnByName("in_multiples_of_deposit_term");
        this.IN_MULTIPLE_OF_DEPOSIT_TERM_TYPE_ENUM = this.delegate.getColumnByName("in_multiples_of_deposit_term_type_enum");
        this.PRE_CLOSURE_PENAL_APPLICABLE = this.delegate.getColumnByName("pre_closure_penal_applicable");
        this.PRE_CLOSURE_PENAL_INTEREST = this.delegate.getColumnByName("pre_closure_penal_interest");
        this.PRE_CLOSURE_PENAL_INTEREST_ON_ENUM = this.delegate.getColumnByName("pre_closure_penal_interest_on_enum");
        this.DEPOSIT_PERIOD = this.delegate.getColumnByName("deposit_period");
        this.DEPOSIT_PERIOD_FREQUENCY_ENUM = this.delegate.getColumnByName("deposit_period_frequency_enum");
        this.DEPOSIT_AMOUNT = this.delegate.getColumnByName("deposit_amount");
        this.MATURITY_AMOUNT = this.delegate.getColumnByName("maturity_amount");
        this.MATURITY_DATE = this.delegate.getColumnByName("maturity_date");
        this.ON_ACCOUNT_CLOSURE_ENUM = this.delegate.getColumnByName("on_account_closure_enum");
        this.EXPECTED_FIRST_DEPOSITON_DATE = this.delegate.getColumnByName("expected_firstdepositon_date");
        this.TRANSFER_INTEREST_TO_LINKED_ACCOUNT = this.delegate.getColumnByName("transfer_interest_to_linked_account");
        this.TRANSFER_TO_SAVING_ACCOUNT_ID = this.delegate.getColumnByName("transfer_to_savings_account_id");
    }

}
