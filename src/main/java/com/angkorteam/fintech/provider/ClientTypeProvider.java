package com.angkorteam.fintech.provider;

import com.angkorteam.fintech.dto.Dropdown;

public class ClientTypeProvider extends SingleChoiceProvider {

    public ClientTypeProvider() {
        super("m_code_value", "m_code_value.id", "m_code_value.code_value");
        applyJoin("m_code", "inner join m_code ON m_code_value.code_id = m_code.id");
        applyWhere("code_name", "m_code.code_name = '" + Dropdown.ClientType + "'");
        applyWhere("is_active", "m_code_value.is_active = 1");
    }

}
