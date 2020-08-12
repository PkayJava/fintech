package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.*;

import java.util.Collection;
import java.util.List;

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
        this.ID = this.delegate.getColumnByName("id");
        this.ACCOUNT_NO = this.delegate.getColumnByName("account_no");
        this.EXTERNAL_ID = this.delegate.getColumnByName("external_id");
        this.STATUS_ENUM = this.delegate.getColumnByName("status_enum");
        this.SUB_STATUS = this.delegate.getColumnByName("sub_status");
        this.ACTIVATION_DATE = this.delegate.getColumnByName("activation_date");
        this.OFFICE_JOINING_DATE = this.delegate.getColumnByName("office_joining_date");
        this.OFFICE_ID = this.delegate.getColumnByName("office_id");
        this.TRANSFER_TO_OFFICE_ID = this.delegate.getColumnByName("transfer_to_office_id");
        this.STAFF_ID = this.delegate.getColumnByName("staff_id");
        this.FIRST_NAME = this.delegate.getColumnByName("firstname");
        this.MIDDLE_NAME = this.delegate.getColumnByName("middlename");
        this.LAST_NAME = this.delegate.getColumnByName("lastname");
        this.FULL_NAME = this.delegate.getColumnByName("fullname");
        this.DISPLAY_NAME = this.delegate.getColumnByName("display_name");
        this.MOBILE_NO = this.delegate.getColumnByName("mobile_no");
        this.STAFF = this.delegate.getColumnByName("is_staff");
        this.GENDER_CV_ID = this.delegate.getColumnByName("gender_cv_id");
        this.DATE_OF_BIRTH = this.delegate.getColumnByName("date_of_birth");
        this.IMAGE_ID = this.delegate.getColumnByName("image_id");
        this.CLOSURE_REASON_CV_ID = this.delegate.getColumnByName("closure_reason_cv_id");
        this.CLOSED_ON_DATE = this.delegate.getColumnByName("closedon_date");
        this.UPDATED_BY = this.delegate.getColumnByName("updated_by");
        this.UPDATED_ON = this.delegate.getColumnByName("updated_on");
        this.SUBMITTED_ON_DATE = this.delegate.getColumnByName("submittedon_date");
        this.SUBMITTED_ON_USER_ID = this.delegate.getColumnByName("submittedon_userid");
        this.ACTIVATED_ON_USER_ID = this.delegate.getColumnByName("activatedon_userid");
        this.CLOSED_ON_USER_ID = this.delegate.getColumnByName("closedon_userid");
        this.DEFAULT_SAVING_PRODUCT = this.delegate.getColumnByName("default_savings_product");
        this.DEFAULT_SAVING_ACCOUNT = this.delegate.getColumnByName("default_savings_account");
        this.CLIENT_TYPE_CV_ID = this.delegate.getColumnByName("client_type_cv_id");
        this.CLIENT_CLASSIFICATION_CV_ID = this.delegate.getColumnByName("client_classification_cv_id");
        this.REJECT_REASON_CV_ID = this.delegate.getColumnByName("reject_reason_cv_id");
        this.REJECTED_ON_DATE = this.delegate.getColumnByName("rejectedon_date");
        this.REJECTED_ON_USER_ID = this.delegate.getColumnByName("rejectedon_userid");
        this.WITHDRAW_REASON_CV_ID = this.delegate.getColumnByName("withdraw_reason_cv_id");
        this.WITHDRAWN_ON_DATE = this.delegate.getColumnByName("withdrawn_on_date");
        this.WITHDRAW_ON_USER_ID = this.delegate.getColumnByName("withdraw_on_userid");
        this.RE_ACTIVATED_ON_DATE = this.delegate.getColumnByName("reactivated_on_date");
        this.RE_ACTIVATED_ON_USER_ID = this.delegate.getColumnByName("reactivated_on_userid");
        this.legal_form_enum = this.delegate.getColumnByName("legal_form_enum");
        this.RE_OPENED_ON_DATE = this.delegate.getColumnByName("reopened_on_date");
        this.RE_OPENED_BY_USER_ID = this.delegate.getColumnByName("reopened_by_userid");
        this.EMAIL_ADDRESS = this.delegate.getColumnByName("email_address");
        this.PROPOSED_TRANSFER_DATE = this.delegate.getColumnByName("proposed_transfer_date");
    }

}
