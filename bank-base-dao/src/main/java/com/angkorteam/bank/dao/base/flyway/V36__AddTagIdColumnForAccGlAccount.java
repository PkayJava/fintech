package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import liquibase.database.Database;
import org.apache.metamodel.insert.RowInsertable;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;

public class V36__AddTagIdColumnForAccGlAccount extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V36__AddTagIdColumnForAccGlAccount + getInternalChecksum("V36__add_tag_id_column_for_acc_gl_account-001.xml");
    }

    @Override
    public void migrate(Context context) throws Exception {
        Database database = lookupDatabase(context);
        JdbcDataContext dataContext = lookupDataContext(context);
        {
            updateLiquibase(database, "V36__add_tag_id_column_for_acc_gl_account-001.xml");
        }
        {
            dataContext.refreshSchemas();
            Table m_code = dataContext.getDefaultSchema().getTableByName("m_code");
            dataContext.executeUpdate(callback -> {
                insert_m_code(m_code, callback, "AssetAccountTags", 1);
                insert_m_code(m_code, callback, "LiabilityAccountTags", 1);
                insert_m_code(m_code, callback, "EquityAccountTags", 1);
                insert_m_code(m_code, callback, "IncomeAccountTags", 1);
                insert_m_code(m_code, callback, "ExpenseAccountTags", 1);
            });
        }
    }

    protected void insert_m_code(Table table, RowInsertable callback, String code_name, long is_system_defined) {
        callback.insertInto(table)
                .value(table.getColumnByName("code_name"), code_name)
                .value(table.getColumnByName("is_system_defined"), is_system_defined)
                .execute();
    }

}
