package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.metamodel.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.LOAN_ID = getInternalColumnByName("loan_id");
        this.CLIENT_RELN_CV_ID = getInternalColumnByName("client_reln_cv_id");
        this.TYPE_ENUM = getInternalColumnByName("type_enum");
        this.ENTITY_ID = getInternalColumnByName("entity_id");
        this.FIRST_NAME = getInternalColumnByName("firstname");
        this.LAST_NAME = getInternalColumnByName("lastname");
        this.DOB = getInternalColumnByName("dob");
        this.ADDRESS_LINE_1 = getInternalColumnByName("address_line_1");
        this.ADDRESS_LINE_2 = getInternalColumnByName("address_line_2");
        this.CITY = getInternalColumnByName("city");
        this.STATE = getInternalColumnByName("state");
        this.COUNTRY = getInternalColumnByName("country");
        this.ZIP = getInternalColumnByName("zip");
        this.HOUSE_PHONE_NUMBER = getInternalColumnByName("house_phone_number");
        this.MOBILE_NUMBER = getInternalColumnByName("mobile_number");
        this.COMMENT = getInternalColumnByName("comment");
        this.ACTIVE = getInternalColumnByName("is_active");
    }

}
