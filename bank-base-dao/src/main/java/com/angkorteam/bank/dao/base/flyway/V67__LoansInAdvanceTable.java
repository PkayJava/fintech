package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class V67__LoansInAdvanceTable extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V67__LoansInAdvanceTable + getInternalChecksum("V67__loans_in_advance_table.xml");
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        updateLiquibase("V67__loans_in_advance_table.xml");
    }

}
