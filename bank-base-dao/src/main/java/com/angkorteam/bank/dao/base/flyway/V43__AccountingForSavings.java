package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.jdbc.query.UpdateQuery;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class V43__AccountingForSavings extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V43__AccountingForSavings + getInternalChecksum("V43__accounting-for-savings-001.xml", "V43__accounting-for-savings-003.xml");
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        UpdateQuery updateQuery = null;
        {
            updateLiquibase("V43__accounting-for-savings-001.xml");
        }
        {
            Table m_savings_product = dataContext.getDefaultSchema().getTableByName("m_savings_product");
            updateQuery = new UpdateQuery(m_savings_product.getName());
            updateQuery.addValue(m_savings_product.getColumnByName("accounting_type").getName(), 1);
            named.update(updateQuery.toSQL(), updateQuery.toParam());
        }
        {
            updateLiquibase("V43__accounting-for-savings-003.xml");
        }
    }

}
