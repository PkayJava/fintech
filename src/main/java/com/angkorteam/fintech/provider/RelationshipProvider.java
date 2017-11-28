package com.angkorteam.fintech.provider;

import com.angkorteam.fintech.dto.Dropdown;

public class RelationshipProvider extends SingleChoiceProvider {

    public RelationshipProvider() {
        super("m_code_value", "m_code_value.id", "m_code_value.code_description");
        applyJoin("m_code", "inner join m_code ON m_code_value.code_id = m_code.id");
        applyWhere("code_name", "m_code.code_name = '" + Dropdown.Relationship + "'");
        applyWhere("is_active", "m_code_value.is_active = 1");
    }

}
