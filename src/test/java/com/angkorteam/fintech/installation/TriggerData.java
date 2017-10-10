package com.angkorteam.fintech.installation;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.angkorteam.fintech.Constants;
import com.angkorteam.fintech.MifosDataSourceManager;

public class TriggerData {

    @Test
    public void initTriggerData() throws Exception {
        File fintechFile = new File(FileUtils.getUserDirectory(), ".xml/fintech.properties.xml");

        Properties properties = new Properties();
        try (FileInputStream inputStream = FileUtils.openInputStream(fintechFile)) {
            properties.loadFromXML(inputStream);
        }
        BasicDataSource platformDataSource = new BasicDataSource();
        platformDataSource.setUsername(properties.getProperty("app.jdbc.username"));
        platformDataSource.setPassword(properties.getProperty("app.jdbc.password"));
        platformDataSource.setUrl(properties.getProperty("app.jdbc.url"));
        platformDataSource.setDriverClassName(properties.getProperty("app.jdbc.driver"));

        String mifosUrl = properties.getProperty("mifos.url");

        MifosDataSourceManager dataSourceManager = new MifosDataSourceManager();
        dataSourceManager.setDelegate(platformDataSource);
        dataSourceManager.setMifosUrl(mifosUrl);
        dataSourceManager.afterPropertiesSet();

        boolean fileout = false;

        DataSource dataSource = dataSourceManager.getDataSource(Constants.AID);

        Function.triggerData(fileout, dataSource);

    }

}
