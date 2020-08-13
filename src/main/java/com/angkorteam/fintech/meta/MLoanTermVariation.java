package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MLoanTermVariation extends AbstractTable {

    public final Column ID;

    public final Column LOAN_ID;

    public final Column TERM_TYPE;

    public final Column APPLICABLE_DATE;

    public final Column DECIMAL_VALUE;

    public final Column DATE_VALUE;

    public final Column SPECIFIC_TO_INSTALLMENT;

    public final Column APPLIED_ON_LOAN_STATUS;

    public final Column ACTIVE;

    public final Column PARENT_ID;

    public static MLoanTermVariation staticInitialize(DataContext dataContext) {
        return new MLoanTermVariation(dataContext);
    }

    private MLoanTermVariation(DataContext dataContext) {
        super(dataContext, "m_loan_term_variations");
        this.ID = this.delegate.getColumnByName("id");
        this.LOAN_ID = this.delegate.getColumnByName("loan_id");
        this.TERM_TYPE = this.delegate.getColumnByName("term_type");
        this.APPLICABLE_DATE = this.delegate.getColumnByName("applicable_date");
        this.DECIMAL_VALUE = this.delegate.getColumnByName("decimal_value");
        this.DATE_VALUE = this.delegate.getColumnByName("date_value");
        this.SPECIFIC_TO_INSTALLMENT = this.delegate.getColumnByName("is_specific_to_installment");
        this.APPLIED_ON_LOAN_STATUS = this.delegate.getColumnByName("applied_on_loan_status");
        this.ACTIVE = this.delegate.getColumnByName("is_active");
        this.PARENT_ID = this.delegate.getColumnByName("parent_id");
    }

}
