package com.angkorteam.bank.dao.flyway;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.apache.commons.io.IOUtils;
import org.apache.metamodel.factory.DataContextFactoryRegistryImpl;
import org.apache.metamodel.factory.DataContextPropertiesImpl;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.jdbc.PublicSqlKeywords;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.flywaydb.core.internal.jdbc.DriverDataSource;

import java.io.IOException;
import java.io.InputStream;

public abstract class LiquibaseJavaMigration extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws Exception {
        PublicSqlKeywords publicSqlKeywords = new PublicSqlKeywords();
        Database database = lookupDatabase(context);
        Liquibase liquibase = new Liquibase(this.getClass().getSimpleName() + ".xml", new ClassLoaderResourceAccessor(), database);
        liquibase.update(new Contexts(), new LabelExpression());
    }

    @Override
    public Integer getChecksum() {
        return getInternalChecksum(this.getClass().getSimpleName() + ".xml");
    }

    protected Integer getInternalChecksum(String... names) {
        int checksum = 0;
        for (String name : names) {
            checksum = checksum + getInternalChecksum(name);
        }
        return checksum;
    }

    protected Integer getInternalChecksum(String name) {
        try (InputStream stream = getClass().getResourceAsStream("/" + name)) {
            byte[] temp = IOUtils.toByteArray(stream);
            HashCode hashCode = Hashing.sha256().hashBytes(temp);
            return hashCode.asInt();
        } catch (NullPointerException | IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    protected Database lookupDatabase(Context context) throws DatabaseException {
        PublicSqlKeywords publicSqlKeywords = new PublicSqlKeywords();
        return DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(context.getConnection()));
    }

    protected JdbcDataContext lookupDataContext(Context context) {
        PublicSqlKeywords publicSqlKeywords = new PublicSqlKeywords();
        DriverDataSource driver = (DriverDataSource) context.getConfiguration().getDataSource();
        DataContextPropertiesImpl properties = new DataContextPropertiesImpl();
        properties.put("type", "jdbc");
        properties.put("url", driver.getUrl());
        properties.put("driver-class", driver.getDriver().getClass().getName());
        properties.put("username", driver.getUser());
        properties.put("password", driver.getPassword());
        return (JdbcDataContext) DataContextFactoryRegistryImpl.getDefaultInstance().createDataContext(properties);
    }

    protected void updateLiquibase(Database database, String changeset) throws LiquibaseException {
        Liquibase liquibase = new Liquibase(changeset, new ClassLoaderResourceAccessor(), database);
        try {
            liquibase.update(new Contexts(), new LabelExpression());
        } catch (Exception e) {
            Throwable throwable = e;
            while (throwable != null) {
                if (throwable instanceof DatabaseException) {
                    break;
                } else {
                    throwable = throwable.getCause();
                }
            }
            if (throwable != null) {
                System.out.println(throwable.getMessage());
            }
            throw e;
        }
    }

}
