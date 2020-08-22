package com.angkorteam.fintech.data;

import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Option;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OptionUtilities implements RowMapper<Option>, IChoiceRenderer<Option> {

    @Override
    public Option mapRow(ResultSet result, int i) throws SQLException {
        return new Option(result.getString("id"), result.getString("text"));
    }

    @Override
    public Object getDisplayValue(Option object) {
        return object.getText();
    }

    @Override
    public String getIdValue(Option object, int index) {
        return object.getId();
    }

    @Override
    public Option getObject(String id, IModel<? extends List<? extends Option>> choices) {
        for (Option choice : choices.getObject()) {
            if (choice.getId().equals(id)) {
                return choice;
            }
        }
        return null;
    }

}
