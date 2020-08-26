package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MLoanRescheduleRequestTermVariationMapping extends AbstractTable {

    public final Column ID;

    public final Column LOAN_RESCHEDULE_REQUEST_ID;

    public final Column LOAN_TERM_VARIATIONS_ID;

    public static MLoanRescheduleRequestTermVariationMapping staticInitialize(DataContext dataContext) {
        return new MLoanRescheduleRequestTermVariationMapping(dataContext);
    }

    private MLoanRescheduleRequestTermVariationMapping(DataContext dataContext) {
        super(dataContext, "m_loan_reschedule_request_term_variations_mapping");
        this.ID = getInternalColumnByName("id");
        this.LOAN_RESCHEDULE_REQUEST_ID = getInternalColumnByName("loan_reschedule_request_id");
        this.LOAN_TERM_VARIATIONS_ID = getInternalColumnByName("loan_term_variations_id");
    }

}
