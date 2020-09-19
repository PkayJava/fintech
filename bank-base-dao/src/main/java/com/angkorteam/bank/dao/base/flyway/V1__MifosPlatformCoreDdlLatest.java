package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class V1__MifosPlatformCoreDdlLatest extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V1__MifosPlatformCoreDdlLatest + getInternalChecksum("V1__mifosplatform-core-ddl-latest-002.xml");
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        // sub change 001
        List<String> tables = new ArrayList<>();
        tables.addAll(dataContext.getDefaultSchema().getTableNames());
        for (String table : tables) {
            if (!"DATABASECHANGELOG".equals(table) && !"DATABASECHANGELOGLOCK".equals(table) && !"flyway_schema_history".equals(table)) {
                dataContext.executeUpdate(callback -> {
                    callback.dropTable(table);
                });
            }
        }
        
        // sub change 002
        updateLiquibase("V1__mifosplatform-core-ddl-latest-002.xml");
    }

}