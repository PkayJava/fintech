package com.angkorteam.bank.dao.base.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MFamilyMember extends AbstractTable {

    public final Column ID;

    public final Column CLIENT_ID;

    public final Column FIRST_NAME;

    public final Column MIDDLE_NAME;

    public final Column LAST_NAME;

    public final Column QUALIFICATION;

    public final Column RELATIONSHIP_CV_ID;

    public final Column MARITAL_STATUS_CV_ID;

    public final Column GENDER_CV_ID;

    public final Column DATE_OF_BIRTH;

    public final Column AGE;

    public final Column PROFESSION_CV_ID;

    public final Column MOBILE_NUMBER;

    public final Column DEPENDENT;

    public static MFamilyMember staticInitialize(DataContext dataContext) {
        return new MFamilyMember(dataContext);
    }

    private MFamilyMember(DataContext dataContext) {
        super(dataContext, "m_family_members");
        this.ID = getInternalColumnByName("id");
        this.CLIENT_ID = getInternalColumnByName("client_id");
        this.FIRST_NAME = getInternalColumnByName("firstname");
        this.MIDDLE_NAME = getInternalColumnByName("middlename");
        this.LAST_NAME = getInternalColumnByName("lastname");
        this.QUALIFICATION = getInternalColumnByName("qualification");
        this.RELATIONSHIP_CV_ID = getInternalColumnByName("relationship_cv_id");
        this.MARITAL_STATUS_CV_ID = getInternalColumnByName("marital_status_cv_id");
        this.GENDER_CV_ID = getInternalColumnByName("gender_cv_id");
        this.DATE_OF_BIRTH = getInternalColumnByName("date_of_birth");
        this.AGE = getInternalColumnByName("age");
        this.PROFESSION_CV_ID = getInternalColumnByName("profession_cv_id");
        this.MOBILE_NUMBER = getInternalColumnByName("mobile_number");
        this.DEPENDENT = getInternalColumnByName("is_dependent");
    }

}
