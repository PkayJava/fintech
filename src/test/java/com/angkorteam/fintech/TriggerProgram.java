package com.angkorteam.fintech;

import com.angkorteam.fintech.client.FineractClient;
import com.angkorteam.fintech.client.dto.AuthenticateRequest;
import com.angkorteam.fintech.client.dto.PostAuthenticationResponse;
import com.angkorteam.fintech.installation.Function;
import org.apache.metamodel.factory.DataContextFactoryRegistryImpl;
import org.apache.metamodel.factory.DataContextPropertiesImpl;
import org.apache.metamodel.jdbc.JdbcDataContext;

import java.sql.Driver;

public class TriggerProgram {

    public static void main(String[] args) throws Exception {
        String publicIp = "vpn.i365work.com";
        String privateIp = "192.168.1.6";
        String ip = publicIp;
        int privatePort = 3306;
        int publicPort = 21631;
        int port = publicPort;

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

        JdbcDataContext appDataContext = null;
        {
            DataContextPropertiesImpl properties = new DataContextPropertiesImpl();
            properties.put("type", "jdbc");
            properties.put("url", "jdbc:mysql://" + ip + ":" + port + "/fineract_default?useSSL=true");
            properties.put("driver-class", Driver.class.getName());
            properties.put("username", "bank");
            properties.put("password", "password");
            appDataContext = (JdbcDataContext) DataContextFactoryRegistryImpl.getDefaultInstance().createDataContext(properties);
        }

        String mifosUrl = "https://bank-api.i365work.com:21632/fineract-provider/api/v1";

        boolean fileout = true;

        String tenant = "default";

        FineractClient client = new FineractClient(mifosUrl);
        PostAuthenticationResponse response = client.authentication(tenant, new AuthenticateRequest("mifos", "password"));
        String token = response.getBase64EncodedAuthenticationKey();

        Function.triggerData(fileout, tenant, token, appDataContext);
    }

}
