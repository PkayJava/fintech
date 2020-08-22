package com.angkorteam.fintech;

import com.angkorteam.fintech.client.FineractClient;
import com.angkorteam.fintech.client.dto.AuthenticateRequest;
import com.angkorteam.fintech.client.dto.PostAuthenticationResponse;
import com.angkorteam.fintech.client.dto.PostScoreRequest;
import org.apache.metamodel.factory.DataContextFactoryRegistryImpl;
import org.apache.metamodel.factory.DataContextPropertiesImpl;
import org.apache.metamodel.jdbc.JdbcDataContext;

import java.sql.Driver;
import java.text.ParseException;
import java.util.Date;

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

//        SurveyRequest request = new SurveyRequest();

//        request.setCountryCode("KH");
//        request.setDescription("Test");
//        request.setKey("KH");
//        request.setName("Auto");
//
//        SurveyRequest.ResponseData responseData = new SurveyRequest.ResponseData();
//        responseData.setSequenceNo(1);
//        responseData.setText("abc");
//
//        SurveyRequest.QuestionData questionData = new SurveyRequest.QuestionData();
//        questionData.setSequenceNo(1);
//        questionData.setKey("key1");
//        questionData.setText("text1");
//        questionData.getResponseDatas().add(responseData);
//
//        request.getQuestionDatas().add(questionData);

//        client.surveyCreate(tenant, token, request);


//        PostLookupTableRequest request = new PostLookupTableRequest();
//        request.setKey("K");
//        request.setDescription(1);
//        request.getEntries().add(new PostLookupTableRequest.LookupTableEntry(1, 10, 5));
//
//        client.surveyLookupTableCreate(tenant, token, 1, request);

//        PostScoreRequest request = new PostScoreRequest();
//        request.setClientId(0);
//        request.setId(1);
//        request.setSurveyId(1);
//        request.setSurveyName("test");
//        request.setUserId(1);
//        request.setUsername("test");
//        request.getScorecardValues().add(PostScoreRequest.ScorecardValue.instance(1, 1, 1, new Date()));
//        client.surveyScoreCreate(tenant, token, 1, request);

        client.clientCreate();

    }
}
