package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.jdbc.query.UpdateQuery;
import com.angkorteam.metamodel.Database;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class V43__AccountingForSavings extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V43__AccountingForSavings + getInternalChecksum("V43__accounting-for-savings-001.xml", "V43__accounting-for-savings-003.xml");
    }

    @Override
    public void migrate(Context context) throws Exception {
        try (Database database = lookupDatabase(context)) {
            JdbcDataContext dataContext = lookupDataContext(context);
            NamedParameterJdbcTemplate named = lookJdbcTemplate(context);
            UpdateQuery updateQuery = null;
            {
                updateLiquibase(database, "V43__accounting-for-savings-001.xml");
            }
            {
                Table m_savings_product = dataContext.getDefaultSchema().getTableByName("m_savings_product");
                updateQuery = new UpdateQuery(m_savings_product.getName());
                updateQuery.addValue(m_savings_product.getColumnByName("accounting_type").getName(), 1);
                named.update(updateQuery.toSQL(), updateQuery.toParam());
            }
            {
                updateLiquibase(database, "V43__accounting-for-savings-003.xml");
            }
        }
    }

}
