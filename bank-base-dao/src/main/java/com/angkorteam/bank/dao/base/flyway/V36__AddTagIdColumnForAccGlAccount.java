package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.jdbc.query.InsertQuery;
import com.angkorteam.metamodel.Database;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class V36__AddTagIdColumnForAccGlAccount extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V36__AddTagIdColumnForAccGlAccount + getInternalChecksum("V36__add_tag_id_column_for_acc_gl_account-001.xml");
    }

    @Override
    public void migrate(Context context) throws Exception {
        try (Database database = lookupDatabase(context)) {
            JdbcDataContext dataContext = lookupDataContext(context);
            NamedParameterJdbcTemplate named = lookJdbcTemplate(context);
            {
                updateLiquibase(database, "V36__add_tag_id_column_for_acc_gl_account-001.xml");
            }
            {
                dataContext.refreshSchemas();
                Table m_code = dataContext.getDefaultSchema().getTableByName("m_code");
                insert_m_code(named, m_code, "AssetAccountTags", 1);
                insert_m_code(named, m_code, "LiabilityAccountTags", 1);
                insert_m_code(named, m_code, "EquityAccountTags", 1);
                insert_m_code(named, m_code, "IncomeAccountTags", 1);
                insert_m_code(named, m_code, "ExpenseAccountTags", 1);
            }
        }
    }

    protected void insert_m_code(NamedParameterJdbcTemplate named, Table table, String code_name, long is_system_defined) {
        InsertQuery insertQuery = new InsertQuery(table.getName());
        insertQuery.addValue(table.getColumnByName("code_name").getName(), code_name);
        insertQuery.addValue(table.getColumnByName("is_system_defined").getName(), is_system_defined);
        named.update(insertQuery.toSQL(), insertQuery.toParam());
    }

}
