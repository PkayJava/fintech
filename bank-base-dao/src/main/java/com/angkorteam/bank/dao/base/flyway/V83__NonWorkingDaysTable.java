package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.jdbc.query.InsertQuery;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class V83__NonWorkingDaysTable extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return getInternalChecksum("V83__non-working-days-table.xml");
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        updateLiquibase("V83__non-working-days-table.xml");

        dataContext.refreshSchemas();
        Table m_working_days = dataContext.getDefaultSchema().getTableByName("m_working_days");
        insert_m_working_days(named, m_working_days, "FREQ=WEEKLY;INTERVAL=1;BYDAY=MO,TU,WE,TH,FR,SA", 2);
    }

    protected void insert_m_working_days(NamedParameterJdbcTemplate named, Table table, String recurrence, long repayment_rescheduling_enum) {
        InsertQuery insertQuery = new InsertQuery(table.getName());
        insertQuery.addValue(table.getColumnByName("recurrence").getName(), recurrence);
        insertQuery.addValue(table.getColumnByName("repayment_rescheduling_enum").getName(), repayment_rescheduling_enum);
        named.update(insertQuery.toSQL(), insertQuery.toParam());
    }

}