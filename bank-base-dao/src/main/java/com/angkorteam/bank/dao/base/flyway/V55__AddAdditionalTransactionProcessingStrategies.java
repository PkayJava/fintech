package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.jdbc.query.InsertQuery;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class V55__AddAdditionalTransactionProcessingStrategies extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V55__AddAdditionalTransactionProcessingStrategies + getInternalChecksum("V55__add-additional-transaction-processing-strategies.xml");
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        updateLiquibase("V55__add-additional-transaction-processing-strategies.xml");

        dataContext.refreshSchemas();
        Table ref_loan_transaction_processing_strategy = dataContext.getDefaultSchema().getTableByName("ref_loan_transaction_processing_strategy");
        insert_ref_loan_transaction_processing_strategy(named, ref_loan_transaction_processing_strategy, 5, "principal-interest-penalties-fees-order-strategy", "Principal Interest Penalties Fees Order");
        insert_ref_loan_transaction_processing_strategy(named, ref_loan_transaction_processing_strategy, 6, "interest-principal-penalties-fees-order-strategy", "Interest Principal Penalties Fees Order");
    }

    protected void insert_ref_loan_transaction_processing_strategy(NamedParameterJdbcTemplate named, Table table, long id, String code, String name) {
        InsertQuery insertQuery = new InsertQuery(table.getName());
        insertQuery.addValue(table.getColumnByName("id").getName(), id);
        insertQuery.addValue(table.getColumnByName("code").getName(), code);
        insertQuery.addValue(table.getColumnByName("name").getName(), name);
        named.update(insertQuery.toSQL(), insertQuery.toParam());
    }

}
