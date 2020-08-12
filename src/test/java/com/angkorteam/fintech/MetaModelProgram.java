package com.angkorteam.fintech;

import com.angkorteam.fintech.meta.Tenant;
import org.apache.metamodel.data.DataSet;
import org.apache.metamodel.factory.DataContextFactoryRegistryImpl;
import org.apache.metamodel.factory.DataContextPropertiesImpl;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.query.FunctionType;

import java.sql.Driver;

public class MetaModelProgram {

    public static void main(String[] args) {
        DataContextPropertiesImpl properties = new DataContextPropertiesImpl();
        properties.put("type", "jdbc");
        properties.put("url", "jdbc:mysql://192.168.1.6:3306/fineract_tenants?useSSL=true");
        properties.put("driver-class", Driver.class.getName());
        properties.put("username", "bank");
        properties.put("password", "password");
        JdbcDataContext dataContext = (JdbcDataContext) DataContextFactoryRegistryImpl.getDefaultInstance().createDataContext(properties);

        Tenant tenants = Tenant.staticInitialize(dataContext);
        try (DataSet rows = dataContext.query().from(tenants).select(FunctionType.COUNT, tenants.ID).where().where(tenants.IDENTIFIER).eq("default").execute()) {
            rows.next();
            System.out.println(rows.getRow().getValue(0));
        }

//        List<Schema> schemas = dataContext.getSchemas();
//        Schema skhauv = null;
//        for (Schema schema : schemas) {
//            if ("skhauv".equalsIgnoreCase(schema.getName())) {
//                skhauv = schema;
//                break;
//            }
//        }
//        if (skhauv == null) {
//            return;
//        }
//        Table table = skhauv.getTableByName("TBL_COUNTRY");
//        Column name = table.getColumnByName("NAME");
//        Column phone = table.getColumnByName("PHONE_CODE");
//
//
//        Tenants tenants = Tenants.INSTANCE;
//        Query query = dataContext.query().from(tenants).select(FunctionType.MAX, tenants.NAME).groupBy(tenants.NAME).firstRow(1).maxRows(10).toQuery();
//        System.out.println(query.toSql());

//        Query q = new Query();
//        q.from(table);
//        q.select(name);
//
//        // Execute the Query
//        DataSet ds = dataContext.executeQuery(query);
//        while (ds.next()) {
//            Row row = ds.getRow();
//            System.out.println(row.getValue(0));
//        }
//
////        Schema finalSkhauv = skhauv;
////        dataContext.executeUpdate(callback -> {
////            // CREATE a table
////            String tableName = "TEST" + RandomStringUtils.randomAlphabetic(5);
////            Table pp = callback.createTable(finalSkhauv, tableName)
////                    .withColumn("id").ofType(ColumnType.INTEGER)
////                    .withColumn("ts").ofType(ColumnType.TIMESTAMP)
////                    .withColumn("d").ofType(ColumnType.ROWID.DATE)
//////                    .withColumn("t").ofType(ColumnType.TIME)
////                    .withColumn("name").ofType(ColumnType.VARCHAR).ofSize(100).execute();
//////            callback.dropTable(pp).execute();
////
////        });
    }

}
