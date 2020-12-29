package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class V51__TrackAdditionalDetailsRelatedToInstallmentPerformance extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V51__TrackAdditionalDetailsRelatedToInstallmentPerformance + getInternalChecksum("V51__track-additional-details-related-to-installment-performance.xml");
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        updateLiquibase("V51__track-additional-details-related-to-installment-performance.xml");
    }

}
