package com.angkorteam.fintech.installation;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.mashape.unirest.http.exceptions.UnirestException;

public class TriggerData {

    private JUnitWicketTester wicket;

    @Before
    public void before() throws UnirestException {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void initTriggerData() throws ParseException, UnirestException, SQLException, IOException {
        this.wicket.login();
        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();

        boolean fileout = false;

        Function.triggerData(fileout, jdbcTemplate.getDataSource());

    }

}
