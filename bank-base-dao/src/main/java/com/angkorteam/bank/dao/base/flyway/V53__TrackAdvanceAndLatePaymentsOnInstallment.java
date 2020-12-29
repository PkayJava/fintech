package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class V53__TrackAdvanceAndLatePaymentsOnInstallment extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V53__TrackAdvanceAndLatePaymentsOnInstallment + getInternalChecksum("V53__track-advance-and-late-payments-on-installment.xml");
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        updateLiquibase("V53__track-advance-and-late-payments-on-installment.xml");
    }

}
