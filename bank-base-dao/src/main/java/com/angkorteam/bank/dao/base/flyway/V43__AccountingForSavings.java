package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import liquibase.database.Database;
import org.apache.metamodel.insert.RowInsertable;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;

public class V43__AccountingForSavings extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V43__AccountingForSavings + getInternalChecksum("V43__accounting-for-savings-001.xml", "V43__accounting-for-savings-003.xml");
    }

    @Override
    public void migrate(Context context) throws Exception {
        Database database = lookupDatabase(context);
        JdbcDataContext dataContext = lookupDataContext(context);
        {
            updateLiquibase(database, "V43__accounting-for-savings-001.xml");
        }
        {
            Table m_savings_product = dataContext.getDefaultSchema().getTableByName("m_savings_product");
            dataContext.executeUpdate(callback -> {
                callback.update(m_savings_product)
                        .value(m_savings_product.getColumnByName("accounting_type"), 1)
                        .execute();
            });
        }
        {
            updateLiquibase(database, "V43__accounting-for-savings-003.xml");
        }
    }

    protected void insert_m_permission(Table table, RowInsertable callback, String grouping, String code, String entity_name, String action_name, long can_maker_checker) {
        callback.insertInto(table)
                .value(table.getColumnByName("grouping"), grouping)
                .value(table.getColumnByName("code"), code)
                .value(table.getColumnByName("entity_name"), entity_name)
                .value(table.getColumnByName("action_name"), action_name)
                .value(table.getColumnByName("can_maker_checker"), can_maker_checker)
                .execute();
    }

    protected void insert_stretchy_report(Table table, RowInsertable callback, String report_name, String report_type, long core_report, long use_report, String description, String report_sql) {
        callback.insertInto(table)
                .value(table.getColumnByName("report_name"), report_name)
                .value(table.getColumnByName("report_type"), report_type)
                .value(table.getColumnByName("core_report"), core_report)
                .value(table.getColumnByName("use_report"), use_report)
                .value(table.getColumnByName("description"), description)
                .value(table.getColumnByName("report_sql"), report_sql)
                .execute();
    }

}
