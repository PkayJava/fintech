package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import liquibase.database.Database;
import org.flywaydb.core.api.migration.Context;

public class V55__AddAdditionalTransactionProcessingStrategies extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V55__AddAdditionalTransactionProcessingStrategies + getInternalChecksum("V55__add-additional-transaction-processing-strategies.xml");
    }

    @Override
    public void migrate(Context context) throws Exception {
        Database database = lookupDatabase(context);
        {
            updateLiquibase(database, "V55__add-additional-transaction-processing-strategies.xml");
        }
        {
            INSERT INTO `ref_loan_transaction_processing_strategy` (`id`, `code`, `name`) VALUES
                (5,'principal-interest-penalties-fees-order-strategy', 'Principal Interest Penalties Fees Order');

            INSERT INTO `ref_loan_transaction_processing_strategy` (`id`,`code`, `name`)
            VALUES (6,'interest-principal-penalties-fees-order-strategy', 'Interest Principal Penalties Fees Order');
        }
    }

}
