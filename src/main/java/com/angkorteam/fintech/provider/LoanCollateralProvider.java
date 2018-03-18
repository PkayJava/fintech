package com.angkorteam.fintech.provider;

import com.angkorteam.fintech.ddl.MCode;
import com.angkorteam.fintech.ddl.MCodeValue;
import com.angkorteam.fintech.dto.Dropdown;

public class LoanCollateralProvider extends SingleChoiceProvider {

    public LoanCollateralProvider() {
        super(MCodeValue.NAME, MCodeValue.NAME + "." + MCodeValue.Field.ID, MCodeValue.NAME + "." + MCodeValue.Field.CODE_VALUE, MCodeValue.NAME + "." + MCodeValue.Field.ORDER_POSITION + " asc");
        applyJoin("m_code", "INNER JOIN " + MCode.NAME + " ON " + MCodeValue.NAME + "." + MCodeValue.Field.CODE_ID + " = " + MCode.NAME + "." + MCode.Field.ID);
        applyWhere("code_name", MCode.NAME + "." + MCode.Field.CODE_NAME + " = '" + Dropdown.LoanCollateral + "'");
        applyWhere("is_active", MCodeValue.NAME + "." + MCodeValue.Field.IS_ACTIVE + " = 1");
    }

}
