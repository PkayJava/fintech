package com.angkorteam.fintech.pages.code;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitFormTester;
import com.angkorteam.fintech.junit.JUnitWicketTester;

public class ValueBrowsePageTest {

    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void dataEntry() {
        this.wicket.login();

        String nameValue = "TEST_JUNIT_" + this.wicket.getStringGenerator().generate(10);
        String codeId = this.wicket.getJdbcTemplate().queryForObject("select id from m_code where is_system_defined = 0 and code_name like ? limit 1", String.class, "TEST_%");
        PageParameters parameters = new PageParameters();
        parameters.add("codeId", codeId);

        this.wicket.startPage(ValueBrowsePage.class, parameters);

        JUnitFormTester form = this.wicket.newFormTester("form");

        form.setValue("nameField", nameValue);
        form.setValue("descriptionField", this.wicket.getStringGenerator().generate(10));
        form.setValue("positionField", "1");
        form.setValue("activeField", true);

        form.submit("addButton");

        this.wicket.assertRenderedPage(ValueBrowsePage.class);
    }

}
