package com.angkorteam.fintech.pages.code;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitFormTester;
import com.angkorteam.fintech.junit.JUnitWicketTester;
import com.angkorteam.framework.spring.JdbcTemplate;

public class CodeBrowsePageTest {

    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void dataEntry() {
        this.wicket.login();

        this.wicket.startPage(CodeBrowsePage.class);

        this.wicket.assertRenderedPage(CodeBrowsePage.class);

        JUnitFormTester form = this.wicket.newFormTester("form");

        String nameValue = "TEST_" + this.wicket.getStringGenerator().generate(10);

        form.setValue("nameField", nameValue);

        form.submit("addButton");

        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
        Map<String, Object> actual = jdbcTemplate.queryForMap("select * from m_code where code_name = ?", nameValue);
        Assert.assertNotNull("expected to have m_code code_name = '" + nameValue + "'", actual);

    }

}
