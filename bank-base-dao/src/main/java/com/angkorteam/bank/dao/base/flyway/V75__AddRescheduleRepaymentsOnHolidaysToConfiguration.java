package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.jdbc.query.InsertQuery;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class V75__AddRescheduleRepaymentsOnHolidaysToConfiguration extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V75__AddRescheduleRepaymentsOnHolidaysToConfiguration;
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        Table c_configuration = dataContext.getDefaultSchema().getTableByName("c_configuration");
        insert_c_configuration(named, c_configuration, "reschedule-repayments-on-holidays");
    }

    protected void insert_c_configuration(NamedParameterJdbcTemplate named, Table table, String name) {
        InsertQuery insertQuery = new InsertQuery(table.getName());
        insertQuery.addValue(table.getColumnByName("name").getName(), name);
        named.update(insertQuery.toSQL(), insertQuery.toParam());
    }

}
