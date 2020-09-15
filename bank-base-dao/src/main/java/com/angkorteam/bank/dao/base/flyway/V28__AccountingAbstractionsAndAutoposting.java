package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class V28__AccountingAbstractionsAndAutoposting extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return getInternalChecksum("V28__accounting-abstractions-and-autoposting.xml");
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        updateLiquibase("V28__accounting-abstractions-and-autoposting.xml");
    }

}
