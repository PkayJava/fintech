package com.angkorteam.bank.dao.tenant.flyway;

import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Schema;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;

public class V9__AddMinMaxConstraintColumnToLoanLoanProduct extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return getInternalChecksum("V9__add_min_max_constraint_column_to_loan_loanproduct.xml");
    }

    @Override
    public void migrate(Context context) throws Exception {
        JdbcDataContext dataContext = lookupDataContext(context);
        dataContext.refreshSchemas();
        Schema schema = dataContext.getDefaultSchema();
        {
            Table m_permission = schema.getTableByName("m_permission");
            dataContext.executeUpdate(callback -> {
                callback.deleteFrom(m_permission)
                        .where(m_permission.getColumnByName("grouping")).eq("transaction_deposit")
                        .execute();
            });
        }
    }

}

