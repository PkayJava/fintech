package com.angkorteam.fintech;

import com.angkorteam.fintech.client.FineractClient;
import com.angkorteam.fintech.client.dto.AuthenticateRequest;
import com.angkorteam.fintech.client.dto.PostAuthenticationResponse;
import org.apache.metamodel.factory.DataContextFactoryRegistryImpl;
import org.apache.metamodel.factory.DataContextPropertiesImpl;
import org.apache.metamodel.jdbc.JdbcDataContext;

import java.sql.Driver;
import java.text.ParseException;

public class TestProgram {


    public static void main(String[] args) throws ParseException {

        String publicIp = "vpn.i365work.com";
        String privateIp = "192.168.1.6";
        String ip = privateIp;
        int privatePort = 3306;
        int publicPort = 21631;
        int port = privatePort;

        DataContextPropertiesImpl properties = new DataContextPropertiesImpl();
        properties.put("type", "jdbc");
        properties.put("url", "jdbc:mysql://" + ip + ":" + port + "/fineract_default?useSSL=true");
        properties.put("driver-class", Driver.class.getName());
        properties.put("username", "bank");
        properties.put("password", "password");
        JdbcDataContext appDataContext = (JdbcDataContext) DataContextFactoryRegistryImpl.getDefaultInstance().createDataContext(properties);

        String mifosUrl = "https://bank-api.i365work.com:21632/fineract-provider/api/v1";

        String tenant = "default";
        String token = null;

        FineractClient client = new FineractClient(mifosUrl);

        {
            PostAuthenticationResponse response = client.authentication(tenant, new AuthenticateRequest("mifos", "password"));
            token = response.getBase64EncodedAuthenticationKey();
        }

//        FunctionProgram.createDataTable(client, tenant, token);
//        client.datatableDelete(tenant,token,"group_grFDfxwGmr");

        FunctionProgram.createGroup(client, tenant, token);
//        FunctionProgram.createCenter(client, tenant, token);

    }

}
