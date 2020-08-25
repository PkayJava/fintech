package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.LOAN_ID = getInternalColumnByName("loan_id");
        this.TERM_TYPE = getInternalColumnByName("term_type");
        this.APPLICABLE_DATE = getInternalColumnByName("applicable_date");
        this.DECIMAL_VALUE = getInternalColumnByName("decimal_value");
        this.DATE_VALUE = getInternalColumnByName("date_value");
        this.SPECIFIC_TO_INSTALLMENT = getInternalColumnByName("is_specific_to_installment");
        this.APPLIED_ON_LOAN_STATUS = getInternalColumnByName("applied_on_loan_status");
        this.ACTIVE = getInternalColumnByName("is_active");
        this.PARENT_ID = getInternalColumnByName("parent_id");
    }

}
