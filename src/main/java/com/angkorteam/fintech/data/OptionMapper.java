package com.angkorteam.fintech.data;

import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Option;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OptionMapper implements RowMapper<Option> {

    @Override
    public Option mapRow(ResultSet result, int i) throws SQLException {
        return new Option(result.getString("id"), result.getString("text"));
    }

}
