package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MDepositProductTermAndPreClosure extends AbstractTable {

    public final Column ID;

    public final Column SAVING_PRODUCT_ID;

    public final Column MIN_DEPOSIT_TERM;

    public final Column MAX_DEPOSIT_TERM;

    public final Column MIN_DEPOSIT_TERM_TYPE_ENUM;

    public final Column MAX_DEPOSIT_TERM_TYPE_ENUM;

    public final Column IN_MULTIPLE_OF_DEPOSIT_TERM;

    public final Column IN_MULTIPLE_OF_DEPOSIT_TERM_TYPE_ENUM;

    public final Column PRE_CLOSURE_PENAL_APPLICABLE;

    public final Column PRE_CLOSURE_PENAL_INTEREST;

    public final Column PRE_CLOSURE_PENAL_INTEREST_ON_ENUM;

    public final Column MIN_DEPOSIT_AMOUNT;

    public final Column MAX_DEPOSIT_AMOUNT;

    public final Column DEPOSIT_AMOUNT;

    public static MDepositProductTermAndPreClosure staticInitialize(DataContext dataContext) {
        return new MDepositProductTermAndPreClosure(dataContext);
    }

    private MDepositProductTermAndPreClosure(DataContext dataContext) {
        super(dataContext, "m_deposit_product_term_and_preclosure");
        this.ID = this.delegate.getColumnByName("id");
        this.SAVING_PRODUCT_ID = this.delegate.getColumnByName("savings_product_id");
        this.MIN_DEPOSIT_TERM = this.delegate.getColumnByName("min_deposit_term");
        this.MAX_DEPOSIT_TERM = this.delegate.getColumnByName("max_deposit_term");
        this.MIN_DEPOSIT_TERM_TYPE_ENUM = this.delegate.getColumnByName("min_deposit_term_type_enum");
        this.MAX_DEPOSIT_TERM_TYPE_ENUM = this.delegate.getColumnByName("max_deposit_term_type_enum");
        this.IN_MULTIPLE_OF_DEPOSIT_TERM = this.delegate.getColumnByName("in_multiples_of_deposit_term");
        this.IN_MULTIPLE_OF_DEPOSIT_TERM_TYPE_ENUM = this.delegate.getColumnByName("in_multiples_of_deposit_term_type_enum");
        this.PRE_CLOSURE_PENAL_APPLICABLE = this.delegate.getColumnByName("pre_closure_penal_applicable");
        this.PRE_CLOSURE_PENAL_INTEREST = this.delegate.getColumnByName("pre_closure_penal_interest");
        this.PRE_CLOSURE_PENAL_INTEREST_ON_ENUM = this.delegate.getColumnByName("pre_closure_penal_interest_on_enum");
        this.MIN_DEPOSIT_AMOUNT = this.delegate.getColumnByName("min_deposit_amount");
        this.MAX_DEPOSIT_AMOUNT = this.delegate.getColumnByName("max_deposit_amount");
        this.DEPOSIT_AMOUNT = this.delegate.getColumnByName("deposit_amount");
    }

}
