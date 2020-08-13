package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MGuarantor extends AbstractTable {

    public final Column ID;

    public final Column LOAN_ID;

    public final Column CLIENT_RELN_CV_ID;

    public final Column TYPE_ENUM;

    public final Column ENTITY_ID;

    public final Column FIRST_NAME;

    public final Column LAST_NAME;

    public final Column DOB;

    public final Column ADDRESS_LINE_1;

    public final Column ADDRESS_LINE_2;

    public final Column CITY;

    public final Column STATE;

    public final Column COUNTRY;

    public final Column ZIP;

    public final Column HOUSE_PHONE_NUMBER;

    public final Column MOBILE_NUMBER;

    public final Column COMMENT;

    public final Column ACTIVE;

    public static MGuarantor staticInitialize(DataContext dataContext) {
        return new MGuarantor(dataContext);
    }

    private MGuarantor(DataContext dataContext) {
        super(dataContext, "m_guarantor");
        this.ID = this.delegate.getColumnByName("id");
        this.LOAN_ID = this.delegate.getColumnByName("loan_id");
        this.CLIENT_RELN_CV_ID = this.delegate.getColumnByName("client_reln_cv_id");
        this.TYPE_ENUM = this.delegate.getColumnByName("type_enum");
        this.ENTITY_ID = this.delegate.getColumnByName("entity_id");
        this.FIRST_NAME = this.delegate.getColumnByName("firstname");
        this.LAST_NAME = this.delegate.getColumnByName("lastname");
        this.DOB = this.delegate.getColumnByName("dob");
        this.ADDRESS_LINE_1 = this.delegate.getColumnByName("address_line_1");
        this.ADDRESS_LINE_2 = this.delegate.getColumnByName("address_line_2");
        this.CITY = this.delegate.getColumnByName("city");
        this.STATE = this.delegate.getColumnByName("state");
        this.COUNTRY = this.delegate.getColumnByName("country");
        this.ZIP = this.delegate.getColumnByName("zip");
        this.HOUSE_PHONE_NUMBER = this.delegate.getColumnByName("house_phone_number");
        this.MOBILE_NUMBER = this.delegate.getColumnByName("mobile_number");
        this.COMMENT = this.delegate.getColumnByName("comment");
        this.ACTIVE = this.delegate.getColumnByName("is_active");
    }

}
