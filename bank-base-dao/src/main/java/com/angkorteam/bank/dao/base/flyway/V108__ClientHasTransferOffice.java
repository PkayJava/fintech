package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.Collections;

public class V108__ClientHasTransferOffice extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V108__ClientHasTransferOffice + getInternalChecksum("V108__client_has_transfer_office.xml");
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcDataContext dataContext) throws Exception {
        updateLiquibase("V108__client_has_transfer_office.xml");

        dataContext.refreshSchemas();
        Table m_client = dataContext.getDefaultSchema().getTableByName("m_client");
        namedParameterJdbcTemplate.update("UPDATE " + m_client.getName() + " SET " + m_client.getColumnByName("office_joining_date").getName() + " = " + m_client.getColumnByName("activation_date").getName(), Collections.emptyMap());
    }
}
