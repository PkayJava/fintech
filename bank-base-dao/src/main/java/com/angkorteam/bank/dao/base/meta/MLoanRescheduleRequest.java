package com.angkorteam.bank.dao.base.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MLoanRescheduleRequest extends AbstractTable {

    public final Column ID;

    public final Column LOAN_ID;

    public final Column STATUS_ENUM;

    public final Column RESCHEDULE_FROM_INSTALLMENT;

    public final Column RESCHEDULE_FROM_DATE;

    public final Column RECALCULATE_INTEREST;

    public final Column RESCHEDULE_REASON_CV_ID;

    public final Column RESCHEDULE_REASON_COMMENT;

    public final Column SUBMITTED_ON_DATE;

    public final Column SUBMITTED_BY_USER_ID;

    public final Column APPROVED_ON_DATE;

    public final Column APPROVED_BY_USER_ID;

    public final Column REJECTED_ON_DATE;

    public final Column REJECTED_BY_USER_ID;

    public static MLoanRescheduleRequest staticInitialize(DataContext dataContext) {
        return new MLoanRescheduleRequest(dataContext);
    }

    private MLoanRescheduleRequest(DataContext dataContext) {
        super(dataContext, "m_loan_reschedule_request");
        this.ID = getInternalColumnByName("id");
        this.LOAN_ID = getInternalColumnByName("loan_id");
        this.STATUS_ENUM = getInternalColumnByName("status_enum");
        this.RESCHEDULE_FROM_INSTALLMENT = getInternalColumnByName("reschedule_from_installment");
        this.RESCHEDULE_FROM_DATE = getInternalColumnByName("reschedule_from_date");
        this.RECALCULATE_INTEREST = getInternalColumnByName("recalculate_interest");
        this.RESCHEDULE_REASON_CV_ID = getInternalColumnByName("reschedule_reason_cv_id");
        this.RESCHEDULE_REASON_COMMENT = getInternalColumnByName("reschedule_reason_comment");
        this.SUBMITTED_ON_DATE = getInternalColumnByName("submitted_on_date");
        this.SUBMITTED_BY_USER_ID = getInternalColumnByName("submitted_by_user_id");
        this.APPROVED_ON_DATE = getInternalColumnByName("approved_on_date");
        this.APPROVED_BY_USER_ID = getInternalColumnByName("approved_by_user_id");
        this.REJECTED_ON_DATE = getInternalColumnByName("rejected_on_date");
        this.REJECTED_BY_USER_ID = getInternalColumnByName("rejected_by_user_id");
    }

}
