package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class V27__AddLoanTypeColumnToLoanTable extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V27__AddLoanTypeColumnToLoanTable + getInternalChecksum("V27__add-loan-type-column-to-loan-table.xml");
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        updateLiquibase("V27__add-loan-type-column-to-loan-table.xml");
    }

}
