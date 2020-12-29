package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class V109__AccountTransferWithdrawalFeeConfiguration extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V109__AccountTransferWithdrawalFeeConfiguration + getInternalChecksum("V109__account_transfer_withdrawal_fee_configuration.xml");
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcDataContext jdbcDataContext) throws Exception {
        updateLiquibase("V109__account_transfer_withdrawal_fee_configuration.xml");
    }
}
