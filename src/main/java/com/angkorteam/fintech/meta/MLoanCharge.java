package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MLoanCharge extends AbstractTable {

    public final Column ID;

    public final Column LOAN_ID;

    public final Column CHARGE_ID;

    public final Column PENALTY;

    public final Column CHARGE_TIME_ENUM;

    public final Column DUE_FOR_COLLECTION_AS_OF_DATE;

    public final Column CHARGE_CALCULATION_ENUM;

    public final Column CHARGE_PAYMENT_MODE_ENUM;

    public final Column CALCULATION_PERCENTAGE;

    public final Column CALCULATION_ON_AMOUNT;

    public final Column CHARGE_AMOUNT_OR_PERCENTAGE;

    public final Column AMOUNT;

    public final Column AMOUNT_PAID_DERIVED;

    public final Column AMOUNT_WAIVED_DERIVED;

    public final Column AMOUNT_WRITTEN_OFF_DERIVED;

    public final Column AMOUNT_OUTSTANDING_DERIVED;

    public final Column PAID_DERIVED;

    public final Column WAIVED;

    public final Column MIN_CAP;

    public final Column MAX_CAP;

    public final Column ACTIVE;

    public static MLoanCharge staticInitialize(DataContext dataContext) {
        return new MLoanCharge(dataContext);
    }

    private MLoanCharge(DataContext dataContext) {
        super(dataContext, "m_loan_charge");
        this.ID = this.delegate.getColumnByName("id");
        this.LOAN_ID = this.delegate.getColumnByName("loan_id");
        this.CHARGE_ID = this.delegate.getColumnByName("charge_id");
        this.PENALTY = this.delegate.getColumnByName("is_penalty");
        this.CHARGE_TIME_ENUM = this.delegate.getColumnByName("charge_time_enum");
        this.DUE_FOR_COLLECTION_AS_OF_DATE = this.delegate.getColumnByName("due_for_collection_as_of_date");
        this.CHARGE_CALCULATION_ENUM = this.delegate.getColumnByName("charge_calculation_enum");
        this.CHARGE_PAYMENT_MODE_ENUM = this.delegate.getColumnByName("charge_payment_mode_enum");
        this.CALCULATION_PERCENTAGE = this.delegate.getColumnByName("calculation_percentage");
        this.CALCULATION_ON_AMOUNT = this.delegate.getColumnByName("calculation_on_amount");
        this.CHARGE_AMOUNT_OR_PERCENTAGE = this.delegate.getColumnByName("charge_amount_or_percentage");
        this.AMOUNT = this.delegate.getColumnByName("amount");
        this.AMOUNT_PAID_DERIVED = this.delegate.getColumnByName("amount_paid_derived");
        this.AMOUNT_WAIVED_DERIVED = this.delegate.getColumnByName("amount_waived_derived");
        this.AMOUNT_WRITTEN_OFF_DERIVED = this.delegate.getColumnByName("amount_writtenoff_derived");
        this.AMOUNT_OUTSTANDING_DERIVED = this.delegate.getColumnByName("amount_outstanding_derived");
        this.PAID_DERIVED = this.delegate.getColumnByName("is_paid_derived");
        this.WAIVED = this.delegate.getColumnByName("waived");
        this.MIN_CAP = this.delegate.getColumnByName("min_cap");
        this.MAX_CAP = this.delegate.getColumnByName("max_cap");
        this.ACTIVE = this.delegate.getColumnByName("is_active");
    }

}
