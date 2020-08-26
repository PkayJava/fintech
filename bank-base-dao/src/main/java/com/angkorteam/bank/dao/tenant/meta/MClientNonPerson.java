package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MClientNonPerson extends AbstractTable {

    public final Column ID;

    public final Column CLIENT_ID;

    public final Column CONSTITUTION_CV_ID;

    public final Column IN_CORP_NO;

    public final Column IN_CORP_VALIDITY_TILL;

    public final Column MAIN_BUSINESS_LINE_CV_ID;

    public final Column REMARK;

    public static MClientNonPerson staticInitialize(DataContext dataContext) {
        return new MClientNonPerson(dataContext);
    }

    private MClientNonPerson(DataContext dataContext) {
        super(dataContext, "m_client_non_person");
        this.ID = getInternalColumnByName("id");
        this.CLIENT_ID = getInternalColumnByName("client_id");
        this.CONSTITUTION_CV_ID = getInternalColumnByName("constitution_cv_id");
        this.IN_CORP_NO = getInternalColumnByName("incorp_no");
        this.IN_CORP_VALIDITY_TILL = getInternalColumnByName("incorp_validity_till");
        this.MAIN_BUSINESS_LINE_CV_ID = getInternalColumnByName("main_business_line_cv_id");
        this.REMARK = getInternalColumnByName("remarks");
    }

}
