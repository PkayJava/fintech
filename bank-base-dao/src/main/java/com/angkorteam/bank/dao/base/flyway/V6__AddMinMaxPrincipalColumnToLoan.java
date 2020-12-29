package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class V6__AddMinMaxPrincipalColumnToLoan extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V6__AddMinMaxPrincipalColumnToLoan + getInternalChecksum("V6__add_min_max_principal_column_to_loan.xml");
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        // sub change 001
        updateLiquibase("V6__add_min_max_principal_column_to_loan.xml");
    }

}

