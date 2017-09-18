package com.angkorteam.fintech.pages.tax;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;

public class TaxComponentModifyPageTest {
    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void visitPage() {
        this.wicket.login();
        String taxId = this.wicket.getJdbcTemplate().queryForObject("select id from m_tax_component limit 1", String.class);
        PageParameters parameters = new PageParameters();
        parameters.add("taxId", taxId);
        this.wicket.startPage(TaxComponentModifyPage.class, parameters);
        this.wicket.assertRenderedPage(TaxComponentModifyPage.class);
    }
}
