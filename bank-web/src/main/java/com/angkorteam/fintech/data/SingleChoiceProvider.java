package com.angkorteam.fintech.data;

import com.angkorteam.fintech.MifosDataSourceManager;
import com.angkorteam.fintech.factory.WebSession;
import com.angkorteam.webui.frmk.common.WicketFactory;
import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Option;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SingleChoiceProvider extends com.angkorteam.webui.frmk.wicket.markup.html.form.select2.SingleChoiceProvider {

    public SingleChoiceProvider(String table, String idField) {
        super(table, idField);
    }

    public SingleChoiceProvider(String table, String idField, String queryField) {
        super(table, idField, queryField);
    }

    public SingleChoiceProvider(String table, String idField, String queryField, String orderBy) {
        super(table, idField, queryField, orderBy);
    }

    public SingleChoiceProvider(String table, String idField, String queryField, String orderBy, String labelField) {
        super(table, idField, queryField, orderBy, labelField);
    }

    @Override
    protected Option queryOption(String query, Map<String, Object> params) {
        MifosDataSourceManager mifos = WicketFactory.getApplicationContext().getBean(MifosDataSourceManager.class);
        WebSession session = (WebSession) WebSession.get();
        DataSource dataSource = mifos.getDataSource(session.getIdentifier());
        NamedParameterJdbcOperations named = new NamedParameterJdbcTemplate(dataSource);
        Map<String, Object> record = null;
        try {
            record = named.queryForMap(query, params);
        } catch (DataAccessException e) {
        }
        if (record == null) {
            return null;
        } else {
            return new Option(String.valueOf(record.get("id")), (String) record.get("text"));
        }
    }

    @Override
    protected List<Option> queryOptions(String query, Map<String, Object> params) {
        MifosDataSourceManager mifos = WicketFactory.getApplicationContext().getBean(MifosDataSourceManager.class);
        WebSession session = (WebSession) WebSession.get();
        DataSource dataSource = mifos.getDataSource(session.getIdentifier());
        NamedParameterJdbcOperations named = new NamedParameterJdbcTemplate(dataSource);
        List<Map<String, Object>> records = named.queryForList(query, params);
        List<Option> options = new ArrayList<>(records.size());
        for (Map<String, Object> record : records) {
            options.add(new Option(String.valueOf(record.get("id")), (String) record.get("text")));
        }
        return options;
    }

}
