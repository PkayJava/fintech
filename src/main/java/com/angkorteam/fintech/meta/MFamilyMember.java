package com.angkorteam.fintech.meta;

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
        this.ID = this.delegate.getColumnByName("id");
        this.CLIENT_ID = this.delegate.getColumnByName("client_id");
        this.FIRST_NAME = this.delegate.getColumnByName("firstname");
        this.MIDDLE_NAME = this.delegate.getColumnByName("middlename");
        this.LAST_NAME = this.delegate.getColumnByName("lastname");
        this.QUALIFICATION = this.delegate.getColumnByName("qualification");
        this.RELATIONSHIP_CV_ID = this.delegate.getColumnByName("relationship_cv_id");
        this.MARITAL_STATUS_CV_ID = this.delegate.getColumnByName("marital_status_cv_id");
        this.GENDER_CV_ID = this.delegate.getColumnByName("gender_cv_id");
        this.DATE_OF_BIRTH = this.delegate.getColumnByName("date_of_birth");
        this.AGE = this.delegate.getColumnByName("age");
        this.PROFESSION_CV_ID = this.delegate.getColumnByName("profession_cv_id");
        this.MOBILE_NUMBER = this.delegate.getColumnByName("mobile_number");
        this.DEPENDENT = this.delegate.getColumnByName("is_dependent");
    }

}
