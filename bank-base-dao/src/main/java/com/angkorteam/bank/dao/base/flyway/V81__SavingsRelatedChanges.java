package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.jdbc.query.DeleteQuery;
import com.angkorteam.jdbc.query.InsertQuery;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collections;

public class V81__SavingsRelatedChanges extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V81__SavingsRelatedChanges + getInternalChecksum("V81__savings_related_changes.xml");
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        updateLiquibase("V81__savings_related_changes.xml");

        dataContext.refreshSchemas();
        Table m_savings_account = dataContext.getDefaultSchema().getTableByName("m_savings_account");
        named.update("UPDATE " + m_savings_account.getName() + " SET " + m_savings_account.getColumnByName("submittedon_date").getName() + " = " + m_savings_account.getColumnByName("activatedon_date").getName() + ", " + m_savings_account.getColumnByName("approvedon_date").getName() + " = " + m_savings_account.getColumnByName("activatedon_date").getName(), Collections.emptyMap());

        Table m_permission = dataContext.getDefaultSchema().getTableByName("m_permission");
        insert_m_permission(named, m_permission, "transaction_savings", "APPROVE_SAVINGSACCOUNT", "SAVINGSACCOUNT", "APPROVE", 1);
        insert_m_permission(named, m_permission, "transaction_savings", "REJECT_SAVINGSACCOUNT", "SAVINGSACCOUNT", "REJECT", 1);
        insert_m_permission(named, m_permission, "transaction_savings", "WITHDRAW_SAVINGSACCOUNT", "SAVINGSACCOUNT", "WITHDRAW", 1);
        insert_m_permission(named, m_permission, "transaction_savings", "APPROVALUNDO_SAVINGSACCOUNT", "SAVINGSACCOUNT", "APPROVALUNDO", 1);
        insert_m_permission(named, m_permission, "transaction_savings", "CLOSE_SAVINGSACCOUNT", "SAVINGSACCOUNT", "CLOSE", 1);

        insert_m_permission(named, m_permission, "transaction_savings", "APPROVE_SAVINGSACCOUNT_CHECKER", "SAVINGSACCOUNT", "APPROVE", 0);
        insert_m_permission(named, m_permission, "transaction_savings", "REJECT_SAVINGSACCOUNT_CHECKER", "SAVINGSACCOUNT", "REJECT", 0);
        insert_m_permission(named, m_permission, "transaction_savings", "WITHDRAW_SAVINGSACCOUNT_CHECKER", "SAVINGSACCOUNT", "WITHDRAW", 0);
        insert_m_permission(named, m_permission, "transaction_savings", "APPROVALUNDO_SAVINGSACCOUNT_CHECKER", "SAVINGSACCOUNT", "APPROVALUNDO", 0);
        insert_m_permission(named, m_permission, "transaction_savings", "CLOSE_SAVINGSACCOUNT_CHECKER", "SAVINGSACCOUNT", "CLOSE", 0);

        DeleteQuery deleteQuery = new DeleteQuery(m_permission.getName());
        deleteQuery.addWhere(m_permission.getColumnByName("id").getName() + " IN (:id)", "id", String.class, Arrays.asList("210", "212", "214", "217", "220", "233", "235", "237", "240", "243"));
        named.update(deleteQuery.toSQL(), deleteQuery.toParam());
    }

    protected void insert_m_permission(NamedParameterJdbcTemplate named, Table table, String grouping, String code, String entity_name, String action_name, long can_maker_checker) {
        InsertQuery insertQuery = new InsertQuery(table.getName());
        insertQuery.addValue(table.getColumnByName("grouping").getQualifiedLabel(), grouping);
        insertQuery.addValue(table.getColumnByName("code").getName(), code);
        insertQuery.addValue(table.getColumnByName("entity_name").getName(), entity_name);
        insertQuery.addValue(table.getColumnByName("action_name").getName(), action_name);
        insertQuery.addValue(table.getColumnByName("can_maker_checker").getName(), can_maker_checker);
        named.update(insertQuery.toSQL(), insertQuery.toParam());
    }

}