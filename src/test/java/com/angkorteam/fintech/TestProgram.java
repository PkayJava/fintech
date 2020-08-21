package com.angkorteam.fintech;

import com.angkorteam.fintech.client.FineractClient;
import com.angkorteam.fintech.client.dto.AuthenticateRequest;
import com.angkorteam.fintech.client.dto.PostAuthenticationResponse;
import com.angkorteam.fintech.client.dto.PostDataTableRequest;
import com.angkorteam.fintech.client.enums.TableTypeEnum;
import com.angkorteam.fintech.meta.tenant.MPermission;
import org.apache.commons.lang3.StringUtils;
import org.apache.metamodel.data.DataSet;
import org.apache.metamodel.factory.DataContextFactoryRegistryImpl;
import org.apache.metamodel.factory.DataContextPropertiesImpl;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.query.FunctionType;

import java.sql.Driver;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            PostAuthenticationResponse response = client.authentication(tenant, new AuthenticateRequest("skhauv", "password"));
            token = response.getBase64EncodedAuthenticationKey();
        }

        List<String> pp = new ArrayList<>();
        MPermission mPermission = MPermission.staticInitialize(appDataContext);
        try (DataSet rows = appDataContext.query().from(mPermission).select(FunctionType.MAX, mPermission.GROUPING).groupBy(mPermission.GROUPING).execute()) {
            while (rows.next()) {
                pp.add((String) rows.getRow().getValue(0));
            }
        }
        System.out.println(StringUtils.join(pp, ", "));

//        long userId = 0;
//        {
//            PostUserRequest request = new PostUserRequest();
//            request.setEmail("pkayjava@gmail.com");
//            request.setUsername("skhauv");
//            request.setPasswordNeverExpires(true);
//            request.setPassword("password");
//            request.setLastName("Socheat");
//            request.setFirstName("KHAUV");
//            request.setSendPasswordToEmail(false);
//            request.setOfficeId(1);
//            request.getRoles().add(4L);
//            FineractResponse response = client.userCreate(tenant, token, request);
//            userId = response.getResourceId();
//            System.out.println("user id " + userId);
//        }

//        PostDataTableRequest request = new PostDataTableRequest();
//        request.setMultiRow(true);
//        request.setApptableName(TableTypeEnum.Office);
//        request.setDatatableName("sssssssssssss");
//        request.getColumns().add(PostDataTableRequest.Column.string("aaaaaaaaa", true, 100));
//        client.datatableCreate(tenant, token, request);

        Map<String, Object> request2 = new HashMap<>();
        request2.put("aaaaaaaaa", "Hello");

//        client.datatableDelete(tenant, token, "sssssssssssss");

         client.datatableEntryCreate(tenant, token, "sssssssssssss", 1L, request2);

//

//        client.datatableDelete(tenant, token, "ppppppppppppppppp");

    }
}
