package com.angkorteam.fintech;

import com.angkorteam.fintech.meta.SchemaVersion;
import com.angkorteam.fintech.meta.infrastructure.Tenant;
import com.angkorteam.fintech.meta.infrastructure.TenantServerConnection;
import com.angkorteam.fintech.meta.infrastructure.TimeZone;
import com.google.gson.GsonBuilder;
import org.apache.metamodel.data.DataSet;
import org.apache.metamodel.factory.DataContextFactoryRegistryImpl;
import org.apache.metamodel.factory.DataContextPropertiesImpl;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Column;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InsertProgram {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {

        String publicIp = "vpn.i365work.com";
        String privateIp = "192.168.1.6";
        String ip = privateIp;
        int privatePort = 3306;
        int publicPort = 21631;
        int port = privatePort;

        JdbcDataContext infrastructureDataContext = null;
        {
            DataContextPropertiesImpl properties = new DataContextPropertiesImpl();
            properties.put("type", "jdbc");
            properties.put("url", "jdbc:mysql://" + ip + ":" + port + "/fineract_tenants?useSSL=true");
            properties.put("driver-class", Driver.class.getName());
            properties.put("username", "bank");
            properties.put("password", "password");
            infrastructureDataContext = (JdbcDataContext) DataContextFactoryRegistryImpl.getDefaultInstance().createDataContext(properties);
        }

        Tenant tenant = Tenant.staticInitialize(infrastructureDataContext);
        TenantServerConnection tenantServerConnection = TenantServerConnection.staticInitialize(infrastructureDataContext);
        TimeZone timeZone = TimeZone.staticInitialize(infrastructureDataContext);
        SchemaVersion schemaVersion = SchemaVersion.staticInitialize(infrastructureDataContext);

        try (DataSet rows = infrastructureDataContext.query().from(tenantServerConnection).selectAll().execute()) {
            while (rows.next()) {
                for (Column column : tenantServerConnection.getColumns()) {
                    System.out.println(column.getName() + " - " + rows.getRow().getValue(column));
                }
            }
        }

        List<Map<String, Object>> datas = new ArrayList<>();
        try (DataSet rows = infrastructureDataContext.query().from(timeZone).selectAll().execute()) {
            while (rows.next()) {
                Map<String, Object> data = new HashMap<>();
                for (Column column : timeZone.getColumns()) {
                    data.put(column.getName(), rows.getRow().getValue(column));
                }
                datas.add(data);
            }
        }
        for (Map<String, Object> data : datas) {
            System.out.println("insertTimeZone(timeZone, callback, " + data.get("id") + ", \"" + data.get("country_code") + "\", \"" + data.get("timezonename") + "\");");
        }

    }
}
