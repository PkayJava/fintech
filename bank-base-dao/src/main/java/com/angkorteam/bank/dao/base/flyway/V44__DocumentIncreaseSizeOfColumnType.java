package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class V44__DocumentIncreaseSizeOfColumnType extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V44__DocumentIncreaseSizeOfColumnType + getInternalChecksum("V44__document-increase-size-of-column-type.xml");
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        updateLiquibase("V44__document-increase-size-of-column-type.xml");
    }

}
