package com.angkorteam.fintech.pages.group;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;

public class GroupModifyPageTest {
    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void visitPage() {
        this.wicket.login();

        String groupId = this.wicket.getJdbcTemplate().queryForObject("SELECT id FROM m_group LIMIT 1", String.class);

        PageParameters parameters = new PageParameters();
        parameters.add("groupId", groupId);

        this.wicket.startPage(GroupModifyPage.class, parameters);

        this.wicket.assertRenderedPage(GroupModifyPage.class);
    }
}
