package com.angkorteam.fintech.provider;

import com.angkorteam.fintech.dto.Dropdown;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.wicket.markup.html.form.select2.OptionSingleChoiceProvider;

public class GenderProvider extends OptionSingleChoiceProvider {

    public GenderProvider() {
        super("m_code_value", "m_code_value.id", "m_code_value.code_value");
        addJoin("inner join m_code ON m_code_value.code_id = m_code.id");
        applyWhere("code_name", "m_code.code_name = '" + Dropdown.Gender + "'");
    }

    public GenderProvider(String table, String idField, String queryField, String labelField) {
        super(table, idField, queryField, labelField);
    }

    @Override
    protected JdbcNamed getNamed() {
        return SpringBean.getBean(JdbcNamed.class);
    }

}
