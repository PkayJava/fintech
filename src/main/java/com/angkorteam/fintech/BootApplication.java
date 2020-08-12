package com.angkorteam.fintech;

import com.angkorteam.fintech.boot.AppProperties;
import com.angkorteam.fintech.client.FineractClient;
import com.angkorteam.fintech.spring.NumberGenerator;
import com.angkorteam.fintech.spring.NumberGeneratorImpl;
import com.angkorteam.fintech.spring.StringGenerator;
import com.angkorteam.fintech.spring.StringGeneratorImpl;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.factory.DataContextFactoryRegistryImpl;
import org.apache.metamodel.factory.DataContextPropertiesImpl;
import org.jasypt.util.password.PasswordEncryptor;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.jasypt.util.text.AES256TextEncryptor;
import org.jasypt.util.text.TextEncryptor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.net.ssl.SSLContext;
import javax.sql.DataSource;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

@SpringBootApplication
public class BootApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(BootApplication.class, args);
    }

    @Bean("stringGenerator")
    public StringGenerator createStringGenerator() {
        return new StringGeneratorImpl();
    }

    @Bean("numberGenerator")
    public NumberGenerator createNumberGenerator() {
        return new NumberGeneratorImpl();
    }

    @Bean(name = "delegateDataSource", destroyMethod = "close")
    public DataSource createDelegateDataSource(AppProperties properties, TextEncryptor textEncryptor) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(properties.getJdbcDriverClassName());
        dataSource.setUrl(properties.getJdbcUrl());
        dataSource.setUsername(properties.getJdbcUsername());
        try {
            dataSource.setPassword(textEncryptor.decrypt(properties.getJdbcPassword()));
        } catch (Throwable e) {
            dataSource.setPassword(properties.getJdbcPassword());
        }
        return dataSource;
    }

    @Bean(name = "delegateDataContext")
    public DataContext createDelegateDataContext(AppProperties properties, TextEncryptor textEncryptor) {
        DataContextPropertiesImpl configuration = new DataContextPropertiesImpl();
        configuration.put("type", "jdbc");
        configuration.put("url", properties.getJdbcUrl());
        configuration.put("driver-class", properties.getJdbcDriverClassName());
        configuration.put("username", properties.getJdbcUsername());
        configuration.put("password", properties.getJdbcPassword());
        return DataContextFactoryRegistryImpl.getDefaultInstance().createDataContext(configuration);
    }

    @Bean("mifosDataSource")
    public MifosDataSourceManager createDataSource(@Qualifier("delegateDataContext") DataContext delegate, AppProperties properties) {
        MifosDataSourceManager mifosDataSource = new MifosDataSourceManager();
        mifosDataSource.setMifosUrl(properties.getMifosUrl());
        mifosDataSource.setDelegate(delegate);
        return mifosDataSource;
    }

    @Bean("mifosDataContext")
    public MifosDataContextManager createDataContext(@Qualifier("delegateDataContext") DataContext delegate, AppProperties properties) {
        MifosDataContextManager mifosDataSource = new MifosDataContextManager();
        mifosDataSource.setMifosUrl(properties.getMifosUrl());
        mifosDataSource.setDelegate(delegate);
        return mifosDataSource;
    }

    @Bean
    public ThreadPoolTaskExecutor createExecutor(AppProperties properties) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(1);
        executor.setQueueCapacity(500);
        executor.setDaemon(true);
        executor.setBeanName("executor");
        return executor;
    }

    @Bean
    public ThreadPoolTaskScheduler createScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setBeanName("scheduler");
        scheduler.setDaemon(true);
        scheduler.setPoolSize(1);
        return scheduler;
    }

    @Bean("passwordEncryptor")
    public PasswordEncryptor createPasswordEncryptor() {
        StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();
        return encryptor;
    }

    @Bean("textEncryptor")
    public TextEncryptor createTextEncryptor(AppProperties properties) {
        AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
        textEncryptor.setPassword(properties.getSecret());
        return textEncryptor;
    }

    @Bean
    public FineractClient createFineractClient(AppProperties properties) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        SSLContextBuilder builder = new SSLContextBuilder();
        builder.loadTrustMaterial(properties.getCacert(), "changeit".toCharArray());
        SSLContext ssl = builder.build();
        return new FineractClient(ssl, properties.getMifosUrl());
    }

}
