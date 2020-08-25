package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.*;

public class MClient extends AbstractTable {

    public final Column ID;

    public final Column ACCOUNT_NO;

    public final Column EXTERNAL_ID;

    public final Column STATUS_ENUM;

    public final Column SUB_STATUS;

    public final Column ACTIVATION_DATE;

    public final Column OFFICE_JOINING_DATE;

    public final Column OFFICE_ID;

    public final Column TRANSFER_TO_OFFICE_ID;

    public final Column STAFF_ID;

    public final Column FIRST_NAME;

    public final Column MIDDLE_NAME;

    public final Column LAST_NAME;

    public final Column FULL_NAME;

    public final Column DISPLAY_NAME;

    public final Column MOBILE_NO;

    public final Column STAFF;

    public final Column GENDER_CV_ID;

    public final Column DATE_OF_BIRTH;

    public final Column IMAGE_ID;

    public final Column CLOSURE_REASON_CV_ID;

    public final Column CLOSED_ON_DATE;

    public final Column UPDATED_BY;

    public final Column UPDATED_ON;

    public final Column SUBMITTED_ON_DATE;

    public final Column SUBMITTED_ON_USER_ID;

    public final Column ACTIVATED_ON_USER_ID;

    public final Column CLOSED_ON_USER_ID;

    public final Column DEFAULT_SAVING_PRODUCT;

    public final Column DEFAULT_SAVING_ACCOUNT;

    public final Column CLIENT_TYPE_CV_ID;

    public final Column CLIENT_CLASSIFICATION_CV_ID;

    public final Column REJECT_REASON_CV_ID;

    public final Column REJECTED_ON_DATE;

    public final Column REJECTED_ON_USER_ID;

    public final Column WITHDRAW_REASON_CV_ID;

    public final Column WITHDRAWN_ON_DATE;

    public final Column WITHDRAW_ON_USER_ID;

    public final Column RE_ACTIVATED_ON_DATE;

    public final Column RE_ACTIVATED_ON_USER_ID;

    public final Column legal_form_enum;

    public final Column RE_OPENED_ON_DATE;

    public final Column RE_OPENED_BY_USER_ID;

    public final Column EMAIL_ADDRESS;

    public final Column PROPOSED_TRANSFER_DATE;

    public static MClient staticInitialize(DataContext dataContext) {
        return new MClient(dataContext);
    }

    private MClient(DataContext dataContext) {
        super(dataContext, "m_client");
        this.ID = getInternalColumnByName("id");
        this.ACCOUNT_NO = getInternalColumnByName("account_no");
        this.EXTERNAL_ID = getInternalColumnByName("external_id");
        this.STATUS_ENUM = getInternalColumnByName("status_enum");
        this.SUB_STATUS = getInternalColumnByName("sub_status");
        this.ACTIVATION_DATE = getInternalColumnByName("activation_date");
        this.OFFICE_JOINING_DATE = getInternalColumnByName("office_joining_date");
        this.OFFICE_ID = getInternalColumnByName("office_id");
        this.TRANSFER_TO_OFFICE_ID = getInternalColumnByName("transfer_to_office_id");
        this.STAFF_ID = getInternalColumnByName("staff_id");
        this.FIRST_NAME = getInternalColumnByName("firstname");
        this.MIDDLE_NAME = getInternalColumnByName("middlename");
        this.LAST_NAME = getInternalColumnByName("lastname");
        this.FULL_NAME = getInternalColumnByName("fullname");
        this.DISPLAY_NAME = getInternalColumnByName("display_name");
        this.MOBILE_NO = getInternalColumnByName("mobile_no");
        this.STAFF = getInternalColumnByName("is_staff");
        this.GENDER_CV_ID = getInternalColumnByName("gender_cv_id");
        this.DATE_OF_BIRTH = getInternalColumnByName("date_of_birth");
        this.IMAGE_ID = getInternalColumnByName("image_id");
        this.CLOSURE_REASON_CV_ID = getInternalColumnByName("closure_reason_cv_id");
        this.CLOSED_ON_DATE = getInternalColumnByName("closedon_date");
        this.UPDATED_BY = getInternalColumnByName("updated_by");
        this.UPDATED_ON = getInternalColumnByName("updated_on");
        this.SUBMITTED_ON_DATE = getInternalColumnByName("submittedon_date");
        this.SUBMITTED_ON_USER_ID = getInternalColumnByName("submittedon_userid");
        this.ACTIVATED_ON_USER_ID = getInternalColumnByName("activatedon_userid");
        this.CLOSED_ON_USER_ID = getInternalColumnByName("closedon_userid");
        this.DEFAULT_SAVING_PRODUCT = getInternalColumnByName("default_savings_product");
        this.DEFAULT_SAVING_ACCOUNT = getInternalColumnByName("default_savings_account");
        this.CLIENT_TYPE_CV_ID = getInternalColumnByName("client_type_cv_id");
        this.CLIENT_CLASSIFICATION_CV_ID = getInternalColumnByName("client_classification_cv_id");
        this.REJECT_REASON_CV_ID = getInternalColumnByName("reject_reason_cv_id");
        this.REJECTED_ON_DATE = getInternalColumnByName("rejectedon_date");
        this.REJECTED_ON_USER_ID = getInternalColumnByName("rejectedon_userid");
        this.WITHDRAW_REASON_CV_ID = getInternalColumnByName("withdraw_reason_cv_id");
        this.WITHDRAWN_ON_DATE = getInternalColumnByName("withdrawn_on_date");
        this.WITHDRAW_ON_USER_ID = getInternalColumnByName("withdraw_on_userid");
        this.RE_ACTIVATED_ON_DATE = getInternalColumnByName("reactivated_on_date");
        this.RE_ACTIVATED_ON_USER_ID = getInternalColumnByName("reactivated_on_userid");
        this.legal_form_enum = getInternalColumnByName("legal_form_enum");
        this.RE_OPENED_ON_DATE = getInternalColumnByName("reopened_on_date");
        this.RE_OPENED_BY_USER_ID = getInternalColumnByName("reopened_by_userid");
        this.EMAIL_ADDRESS = getInternalColumnByName("email_address");
        this.PROPOSED_TRANSFER_DATE = getInternalColumnByName("proposed_transfer_date");
    }

}
