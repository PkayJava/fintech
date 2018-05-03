//package com.angkorteam.fintech.pages.account;
//
//import java.util.Map;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import com.angkorteam.fintech.dto.enums.AccountType;
//import com.angkorteam.fintech.dto.enums.AccountUsage;
//import com.angkorteam.fintech.junit.JUnit;
//import com.angkorteam.fintech.junit.JUnitFormTester;
//import com.angkorteam.fintech.junit.JUnitWicketTester;
//import com.angkorteam.framework.spring.JdbcTemplate;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
//
//public class AccountCreatePageTest {
//
//    private JUnitWicketTester wicket;
//
//    @Before
//    public void before() {
//        this.wicket = JUnit.getWicket();
//    }
//
//    @Test
//    public void dataEntryAssetParent() {
//        this.wicket.login();
//        this.wicket.startPage(AccountCreatePage.class);
//
//        String prefix = "ASSET";
//        String suffix = this.wicket.getStringGenerator().generate(10);
//        String glCodeValue = prefix + "_PR_" + suffix;
//        String accountNameValue = prefix + "_PR_" + suffix;
//        String descriptionValue = prefix + "_PR_" + suffix;
//
//        String tagValue = this.wicket.getJdbcTemplate().queryForObject("select m_code_value.id from m_code_value INNER join m_code on m_code_value.code_id = m_code.id where m_code.code_name = ? LIMIT 1", String.class, AccountType.Asset.getTag());
//
//        JUnitFormTester form = this.wicket.newFormTester("form");
//
//        form.setValue("accountTypeField", AccountType.Asset);
//        Select2SingleChoice<?> accountTypeField = this.wicket.getComponentFromLastRenderedPage("form:accountTypeField", Select2SingleChoice.class);
//        this.wicket.executeBehavior(accountTypeField);
//
//        form.setValue("accountTypeField", AccountType.Asset);
//
//        form.setValue("glCodeField", glCodeValue);
//
//        form.setValue("accountNameField", accountNameValue);
//
//        form.setValue("accountUsageField", AccountUsage.Header);
//
//        form.setValue("tagField", tagValue);
//
//        form.setValue("manualAllowField", true);
//
//        form.setValue("descriptionField", descriptionValue);
//
//        form.submit("saveButton");
//
//        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
//        Map<String, Object> actual = jdbcTemplate.queryForMap("select * from acc_gl_account where gl_code = ?", glCodeValue);
//        Assert.assertNotNull("expected to have acc_gl_account gl_code = '" + glCodeValue + "'", actual);
//
//    }
//
//    @Test
//    public void dataEntryLiabilityParent() {
//        this.wicket.login();
//        this.wicket.startPage(AccountCreatePage.class);
//
//        String prefix = "LIABILITY";
//        String suffix = this.wicket.getStringGenerator().generate(10);
//        String glCodeValue = prefix + "_PR_" + suffix;
//        String accountNameValue = prefix + "_PR_" + suffix;
//        String descriptionValue = prefix + "_PR_" + suffix;
//
//        String tagValue = this.wicket.getJdbcTemplate().queryForObject("select m_code_value.id from m_code_value INNER join m_code on m_code_value.code_id = m_code.id where m_code.code_name = ? LIMIT 1", String.class, AccountType.Liability.getTag());
//
//        JUnitFormTester form = this.wicket.newFormTester("form");
//
//        form.setValue("accountTypeField", AccountType.Liability);
//        Select2SingleChoice<?> accountTypeField = this.wicket.getComponentFromLastRenderedPage("form:accountTypeField", Select2SingleChoice.class);
//        this.wicket.executeBehavior(accountTypeField);
//
//        form.setValue("accountTypeField", AccountType.Liability);
//
//        form.setValue("glCodeField", glCodeValue);
//
//        form.setValue("accountNameField", accountNameValue);
//
//        form.setValue("accountUsageField", AccountUsage.Header);
//
//        form.setValue("tagField", tagValue);
//
//        form.setValue("manualAllowField", true);
//
//        form.setValue("descriptionField", descriptionValue);
//
//        form.submit("saveButton");
//
//        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
//        Map<String, Object> actual = jdbcTemplate.queryForMap("select * from acc_gl_account where gl_code = ?", glCodeValue);
//        Assert.assertNotNull("expected to have acc_gl_account gl_code = '" + glCodeValue + "'", actual);
//
//    }
//
//    @Test
//    public void dataEntryEquityParent() {
//        this.wicket.login();
//        this.wicket.startPage(AccountCreatePage.class);
//
//        String prefix = "EQUITY";
//        String suffix = this.wicket.getStringGenerator().generate(10);
//        String glCodeValue = prefix + "_PR_" + suffix;
//        String accountNameValue = prefix + "_PR_" + suffix;
//        String descriptionValue = prefix + "_PR_" + suffix;
//
//        String tagValue = this.wicket.getJdbcTemplate().queryForObject("select m_code_value.id from m_code_value INNER join m_code on m_code_value.code_id = m_code.id where m_code.code_name = ? LIMIT 1", String.class, AccountType.Equity.getTag());
//
//        JUnitFormTester form = this.wicket.newFormTester("form");
//
//        form.setValue("accountTypeField", AccountType.Equity);
//        Select2SingleChoice<?> accountTypeField = this.wicket.getComponentFromLastRenderedPage("form:accountTypeField", Select2SingleChoice.class);
//        this.wicket.executeBehavior(accountTypeField);
//
//        form.setValue("accountTypeField", AccountType.Equity);
//
//        form.setValue("glCodeField", glCodeValue);
//
//        form.setValue("accountNameField", accountNameValue);
//
//        form.setValue("accountUsageField", AccountUsage.Header);
//
//        form.setValue("tagField", tagValue);
//
//        form.setValue("manualAllowField", true);
//
//        form.setValue("descriptionField", descriptionValue);
//
//        form.submit("saveButton");
//
//        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
//        Map<String, Object> actual = jdbcTemplate.queryForMap("select * from acc_gl_account where gl_code = ?", glCodeValue);
//        Assert.assertNotNull("expected to have acc_gl_account gl_code = '" + glCodeValue + "'", actual);
//
//    }
//
//    @Test
//    public void dataEntryIncomeParent() {
//        this.wicket.login();
//        this.wicket.startPage(AccountCreatePage.class);
//
//        String prefix = "INCOME";
//        String suffix = this.wicket.getStringGenerator().generate(10);
//        String glCodeValue = prefix + "_PR_" + suffix;
//        String accountNameValue = prefix + "_PR_" + suffix;
//        String descriptionValue = prefix + "_PR_" + suffix;
//
//        String tagValue = this.wicket.getJdbcTemplate().queryForObject("select m_code_value.id from m_code_value INNER join m_code on m_code_value.code_id = m_code.id where m_code.code_name = ? LIMIT 1", String.class, AccountType.Income.getTag());
//
//        JUnitFormTester form = this.wicket.newFormTester("form");
//
//        form.setValue("accountTypeField", AccountType.Income);
//        Select2SingleChoice<?> accountTypeField = this.wicket.getComponentFromLastRenderedPage("form:accountTypeField", Select2SingleChoice.class);
//        this.wicket.executeBehavior(accountTypeField);
//
//        form.setValue("accountTypeField", AccountType.Income);
//
//        form.setValue("glCodeField", glCodeValue);
//
//        form.setValue("accountNameField", accountNameValue);
//
//        form.setValue("accountUsageField", AccountUsage.Header);
//
//        form.setValue("tagField", tagValue);
//
//        form.setValue("manualAllowField", true);
//
//        form.setValue("descriptionField", descriptionValue);
//
//        form.submit("saveButton");
//
//        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
//        Map<String, Object> actual = jdbcTemplate.queryForMap("select * from acc_gl_account where gl_code = ?", glCodeValue);
//        Assert.assertNotNull("expected to have acc_gl_account gl_code = '" + glCodeValue + "'", actual);
//
//    }
//
//    @Test
//    public void dataEntryExpenseParent() {
//        this.wicket.login();
//        this.wicket.startPage(AccountCreatePage.class);
//
//        String prefix = "EXPENSE";
//        String suffix = this.wicket.getStringGenerator().generate(10);
//        String glCodeValue = prefix + "_PR_" + suffix;
//        String accountNameValue = prefix + "_PR_" + suffix;
//        String descriptionValue = prefix + "_PR_" + suffix;
//
//        String tagValue = this.wicket.getJdbcTemplate().queryForObject("select m_code_value.id from m_code_value INNER join m_code on m_code_value.code_id = m_code.id where m_code.code_name = ? LIMIT 1", String.class, AccountType.Expense.getTag());
//
//        JUnitFormTester form = this.wicket.newFormTester("form");
//
//        form.setValue("accountTypeField", AccountType.Expense);
//        Select2SingleChoice<?> accountTypeField = this.wicket.getComponentFromLastRenderedPage("form:accountTypeField", Select2SingleChoice.class);
//        this.wicket.executeBehavior(accountTypeField);
//
//        form.setValue("accountTypeField", AccountType.Expense);
//
//        form.setValue("glCodeField", glCodeValue);
//
//        form.setValue("accountNameField", accountNameValue);
//
//        form.setValue("accountUsageField", AccountUsage.Header);
//
//        form.setValue("tagField", tagValue);
//
//        form.setValue("manualAllowField", true);
//
//        form.setValue("descriptionField", descriptionValue);
//
//        form.submit("saveButton");
//
//        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
//        Map<String, Object> actual = jdbcTemplate.queryForMap("select * from acc_gl_account where gl_code = ?", glCodeValue);
//        Assert.assertNotNull("expected to have acc_gl_account gl_code = '" + glCodeValue + "'", actual);
//
//    }
//
//    @Test
//    public void dataEntryAsset1() {
//        this.wicket.login();
//        this.wicket.startPage(AccountCreatePage.class);
//
//        String prefix = "ASSET";
//        String suffix = this.wicket.getStringGenerator().generate(10);
//        String glCodeValue = prefix + "_" + suffix;
//        String accountNameValue = prefix + "_" + suffix;
//        String descriptionValue = prefix + "_" + suffix;
//
//        String tagValue = this.wicket.getJdbcTemplate().queryForObject("select m_code_value.id from m_code_value INNER join m_code on m_code_value.code_id = m_code.id where m_code.code_name = ? LIMIT 1", String.class, AccountType.Asset.getTag());
//
//        JUnitFormTester form = this.wicket.newFormTester("form");
//
//        form.setValue("accountTypeField", AccountType.Asset);
//        Select2SingleChoice<?> accountTypeField = this.wicket.getComponentFromLastRenderedPage("form:accountTypeField", Select2SingleChoice.class);
//        this.wicket.executeBehavior(accountTypeField);
//
//        form.setValue("accountTypeField", AccountType.Asset);
//
//        form.setValue("glCodeField", glCodeValue);
//
//        form.setValue("accountNameField", accountNameValue);
//
//        form.setValue("accountUsageField", AccountUsage.Detail);
//
//        form.setValue("tagField", tagValue);
//
//        form.setValue("manualAllowField", true);
//
//        form.setValue("descriptionField", descriptionValue);
//
//        form.submit("saveButton");
//
//        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
//        Map<String, Object> actual = jdbcTemplate.queryForMap("select * from acc_gl_account where gl_code = ?", glCodeValue);
//        Assert.assertNotNull("expected to have acc_gl_account gl_code = '" + glCodeValue + "'", actual);
//
//    }
//
//    @Test
//    public void dataEntryAsset2() {
//        this.wicket.login();
//        this.wicket.startPage(AccountCreatePage.class);
//
//        String prefix = "ASSET";
//        String suffix = this.wicket.getStringGenerator().generate(10);
//        String glCodeValue = prefix + "_" + suffix;
//        String accountNameValue = prefix + "_" + suffix;
//        String descriptionValue = prefix + "_" + suffix;
//
//        String tagValue = this.wicket.getJdbcTemplate().queryForObject("select m_code_value.id from m_code_value INNER join m_code on m_code_value.code_id = m_code.id where m_code.code_name = ? LIMIT 1", String.class, AccountType.Asset.getTag());
//        String parentValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where gl_code = ?", String.class, prefix + "_PR_JUNIT");
//
//        JUnitFormTester form = this.wicket.newFormTester("form");
//
//        form.setValue("accountTypeField", AccountType.Asset);
//        Select2SingleChoice<?> accountTypeField = this.wicket.getComponentFromLastRenderedPage("form:accountTypeField", Select2SingleChoice.class);
//        this.wicket.executeBehavior(accountTypeField);
//
//        form.setValue("accountTypeField", AccountType.Asset);
//
//        form.setValue("glCodeField", glCodeValue);
//
//        form.setValue("accountNameField", accountNameValue);
//
//        form.setValue("accountUsageField", AccountUsage.Detail);
//
//        form.setValue("tagField", tagValue);
//
//        form.setValue("parentField", parentValue);
//
//        form.setValue("manualAllowField", true);
//
//        form.setValue("descriptionField", descriptionValue);
//
//        form.submit("saveButton");
//
//        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
//        Map<String, Object> actual = jdbcTemplate.queryForMap("select * from acc_gl_account where gl_code = ?", glCodeValue);
//        Assert.assertNotNull("expected to have acc_gl_account gl_code = '" + glCodeValue + "'", actual);
//
//    }
//
//    @Test
//    public void dataEntryLiability1() {
//        this.wicket.login();
//        this.wicket.startPage(AccountCreatePage.class);
//
//        String prefix = "LIABILITY";
//        String suffix = this.wicket.getStringGenerator().generate(10);
//        String glCodeValue = prefix + "_" + suffix;
//        String accountNameValue = prefix + "_" + suffix;
//        String descriptionValue = prefix + "_" + suffix;
//
//        String tagValue = this.wicket.getJdbcTemplate().queryForObject("select m_code_value.id from m_code_value INNER join m_code on m_code_value.code_id = m_code.id where m_code.code_name = ? LIMIT 1", String.class, AccountType.Liability.getTag());
//
//        JUnitFormTester form = this.wicket.newFormTester("form");
//
//        form.setValue("accountTypeField", AccountType.Liability);
//        Select2SingleChoice<?> accountTypeField = this.wicket.getComponentFromLastRenderedPage("form:accountTypeField", Select2SingleChoice.class);
//        this.wicket.executeBehavior(accountTypeField);
//
//        form.setValue("accountTypeField", AccountType.Liability);
//
//        form.setValue("glCodeField", glCodeValue);
//
//        form.setValue("accountNameField", accountNameValue);
//
//        form.setValue("accountUsageField", AccountUsage.Detail);
//
//        form.setValue("tagField", tagValue);
//
//        form.setValue("manualAllowField", true);
//
//        form.setValue("descriptionField", descriptionValue);
//
//        form.submit("saveButton");
//
//        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
//        Map<String, Object> actual = jdbcTemplate.queryForMap("select * from acc_gl_account where gl_code = ?", glCodeValue);
//        Assert.assertNotNull("expected to have acc_gl_account gl_code = '" + glCodeValue + "'", actual);
//    }
//
//    @Test
//    public void dataEntryLiability2() {
//        this.wicket.login();
//        this.wicket.startPage(AccountCreatePage.class);
//
//        String prefix = "LIABILITY";
//        String suffix = this.wicket.getStringGenerator().generate(10);
//        String glCodeValue = prefix + "_" + suffix;
//        String accountNameValue = prefix + "_" + suffix;
//        String descriptionValue = prefix + "_" + suffix;
//
//        String tagValue = this.wicket.getJdbcTemplate().queryForObject("select m_code_value.id from m_code_value INNER join m_code on m_code_value.code_id = m_code.id where m_code.code_name = ? LIMIT 1", String.class, AccountType.Liability.getTag());
//        String parentValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where gl_code = ?", String.class, prefix + "_PR_JUNIT");
//
//        JUnitFormTester form = this.wicket.newFormTester("form");
//
//        form.setValue("accountTypeField", AccountType.Liability);
//        Select2SingleChoice<?> accountTypeField = this.wicket.getComponentFromLastRenderedPage("form:accountTypeField", Select2SingleChoice.class);
//        this.wicket.executeBehavior(accountTypeField);
//
//        form.setValue("accountTypeField", AccountType.Liability);
//
//        form.setValue("glCodeField", glCodeValue);
//
//        form.setValue("accountNameField", accountNameValue);
//
//        form.setValue("accountUsageField", AccountUsage.Detail);
//
//        form.setValue("tagField", tagValue);
//
//        form.setValue("parentField", parentValue);
//
//        form.setValue("manualAllowField", true);
//
//        form.setValue("descriptionField", descriptionValue);
//
//        form.submit("saveButton");
//
//        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
//        Map<String, Object> actual = jdbcTemplate.queryForMap("select * from acc_gl_account where gl_code = ?", glCodeValue);
//        Assert.assertNotNull("expected to have acc_gl_account gl_code = '" + glCodeValue + "'", actual);
//    }
//
//    @Test
//    public void dataEntryEquity1() {
//        this.wicket.login();
//        this.wicket.startPage(AccountCreatePage.class);
//
//        String prefix = "EQUITY";
//        String suffix = this.wicket.getStringGenerator().generate(10);
//        String glCodeValue = prefix + "_" + suffix;
//        String accountNameValue = prefix + "_" + suffix;
//        String descriptionValue = prefix + "_" + suffix;
//
//        String tagValue = this.wicket.getJdbcTemplate().queryForObject("select m_code_value.id from m_code_value INNER join m_code on m_code_value.code_id = m_code.id where m_code.code_name = ? LIMIT 1", String.class, AccountType.Equity.getTag());
//
//        JUnitFormTester form = this.wicket.newFormTester("form");
//
//        form.setValue("accountTypeField", AccountType.Equity);
//        Select2SingleChoice<?> accountTypeField = this.wicket.getComponentFromLastRenderedPage("form:accountTypeField", Select2SingleChoice.class);
//        this.wicket.executeBehavior(accountTypeField);
//
//        form.setValue("accountTypeField", AccountType.Equity);
//
//        form.setValue("glCodeField", glCodeValue);
//
//        form.setValue("accountNameField", accountNameValue);
//
//        form.setValue("accountUsageField", AccountUsage.Detail);
//
//        form.setValue("tagField", tagValue);
//
//        form.setValue("manualAllowField", true);
//
//        form.setValue("descriptionField", descriptionValue);
//
//        form.submit("saveButton");
//
//        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
//        Map<String, Object> actual = jdbcTemplate.queryForMap("select * from acc_gl_account where gl_code = ?", glCodeValue);
//        Assert.assertNotNull("expected to have acc_gl_account gl_code = '" + glCodeValue + "'", actual);
//    }
//
//    @Test
//    public void dataEntryEquity2() {
//        this.wicket.login();
//        this.wicket.startPage(AccountCreatePage.class);
//
//        String prefix = "EQUITY";
//        String suffix = this.wicket.getStringGenerator().generate(10);
//        String glCodeValue = prefix + "_" + suffix;
//        String accountNameValue = prefix + "_" + suffix;
//        String descriptionValue = prefix + "_" + suffix;
//
//        String tagValue = this.wicket.getJdbcTemplate().queryForObject("select m_code_value.id from m_code_value INNER join m_code on m_code_value.code_id = m_code.id where m_code.code_name = ? LIMIT 1", String.class, AccountType.Equity.getTag());
//        String parentValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where gl_code = ?", String.class, prefix + "_PR_JUNIT");
//
//        JUnitFormTester form = this.wicket.newFormTester("form");
//
//        form.setValue("accountTypeField", AccountType.Equity);
//        Select2SingleChoice<?> accountTypeField = this.wicket.getComponentFromLastRenderedPage("form:accountTypeField", Select2SingleChoice.class);
//        this.wicket.executeBehavior(accountTypeField);
//
//        form.setValue("accountTypeField", AccountType.Equity);
//
//        form.setValue("glCodeField", glCodeValue);
//
//        form.setValue("accountNameField", accountNameValue);
//
//        form.setValue("accountUsageField", AccountUsage.Detail);
//
//        form.setValue("tagField", tagValue);
//
//        form.setValue("parentField", parentValue);
//
//        form.setValue("manualAllowField", true);
//
//        form.setValue("descriptionField", descriptionValue);
//
//        form.submit("saveButton");
//
//        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
//        Map<String, Object> actual = jdbcTemplate.queryForMap("select * from acc_gl_account where gl_code = ?", glCodeValue);
//        Assert.assertNotNull("expected to have acc_gl_account gl_code = '" + glCodeValue + "'", actual);
//    }
//
//    @Test
//    public void dataEntryIncome1() {
//        this.wicket.login();
//        this.wicket.startPage(AccountCreatePage.class);
//
//        String prefix = "INCOME";
//        String suffix = this.wicket.getStringGenerator().generate(10);
//        String glCodeValue = prefix + "_" + suffix;
//        String accountNameValue = prefix + "_" + suffix;
//        String descriptionValue = prefix + "_" + suffix;
//
//        String tagValue = this.wicket.getJdbcTemplate().queryForObject("select m_code_value.id from m_code_value INNER join m_code on m_code_value.code_id = m_code.id where m_code.code_name = ? LIMIT 1", String.class, AccountType.Income.getTag());
//
//        JUnitFormTester form = this.wicket.newFormTester("form");
//
//        form.setValue("accountTypeField", AccountType.Income);
//        Select2SingleChoice<?> accountTypeField = this.wicket.getComponentFromLastRenderedPage("form:accountTypeField", Select2SingleChoice.class);
//        this.wicket.executeBehavior(accountTypeField);
//
//        form.setValue("accountTypeField", AccountType.Income);
//
//        form.setValue("glCodeField", glCodeValue);
//
//        form.setValue("accountNameField", accountNameValue);
//
//        form.setValue("accountUsageField", AccountUsage.Detail);
//
//        form.setValue("tagField", tagValue);
//
//        form.setValue("manualAllowField", true);
//
//        form.setValue("descriptionField", descriptionValue);
//
//        form.submit("saveButton");
//
//        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
//        Map<String, Object> actual = jdbcTemplate.queryForMap("select * from acc_gl_account where gl_code = ?", glCodeValue);
//        Assert.assertNotNull("expected to have acc_gl_account gl_code = '" + glCodeValue + "'", actual);
//    }
//
//    @Test
//    public void dataEntryIncome2() {
//        this.wicket.login();
//        this.wicket.startPage(AccountCreatePage.class);
//
//        String prefix = "INCOME";
//        String suffix = this.wicket.getStringGenerator().generate(10);
//        String glCodeValue = prefix + "_" + suffix;
//        String accountNameValue = prefix + "_" + suffix;
//        String descriptionValue = prefix + "_" + suffix;
//
//        String tagValue = this.wicket.getJdbcTemplate().queryForObject("select m_code_value.id from m_code_value INNER join m_code on m_code_value.code_id = m_code.id where m_code.code_name = ? LIMIT 1", String.class, AccountType.Income.getTag());
//        String parentValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where gl_code = ?", String.class, prefix + "_PR_JUNIT");
//
//        JUnitFormTester form = this.wicket.newFormTester("form");
//
//        form.setValue("accountTypeField", AccountType.Income);
//        Select2SingleChoice<?> accountTypeField = this.wicket.getComponentFromLastRenderedPage("form:accountTypeField", Select2SingleChoice.class);
//        this.wicket.executeBehavior(accountTypeField);
//
//        form.setValue("accountTypeField", AccountType.Income);
//
//        form.setValue("glCodeField", glCodeValue);
//
//        form.setValue("accountNameField", accountNameValue);
//
//        form.setValue("accountUsageField", AccountUsage.Detail);
//
//        form.setValue("tagField", tagValue);
//
//        form.setValue("parentField", parentValue);
//
//        form.setValue("manualAllowField", true);
//
//        form.setValue("descriptionField", descriptionValue);
//
//        form.submit("saveButton");
//
//        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
//        Map<String, Object> actual = jdbcTemplate.queryForMap("select * from acc_gl_account where gl_code = ?", glCodeValue);
//        Assert.assertNotNull("expected to have acc_gl_account gl_code = '" + glCodeValue + "'", actual);
//    }
//
//    @Test
//    public void dataEntryExpense1() {
//        this.wicket.login();
//        this.wicket.startPage(AccountCreatePage.class);
//
//        String prefix = "EXPENSE";
//        String suffix = this.wicket.getStringGenerator().generate(10);
//        String glCodeValue = prefix + "_" + suffix;
//        String accountNameValue = prefix + "_" + suffix;
//        String descriptionValue = prefix + "_" + suffix;
//
//        String tagValue = this.wicket.getJdbcTemplate().queryForObject("select m_code_value.id from m_code_value INNER join m_code on m_code_value.code_id = m_code.id where m_code.code_name = ? LIMIT 1", String.class, AccountType.Expense.getTag());
//
//        JUnitFormTester form = this.wicket.newFormTester("form");
//
//        form.setValue("accountTypeField", AccountType.Expense);
//        Select2SingleChoice<?> accountTypeField = this.wicket.getComponentFromLastRenderedPage("form:accountTypeField", Select2SingleChoice.class);
//        this.wicket.executeBehavior(accountTypeField);
//
//        form.setValue("accountTypeField", AccountType.Expense);
//
//        form.setValue("glCodeField", glCodeValue);
//
//        form.setValue("accountNameField", accountNameValue);
//
//        form.setValue("accountUsageField", AccountUsage.Detail);
//
//        form.setValue("tagField", tagValue);
//
//        form.setValue("manualAllowField", true);
//
//        form.setValue("descriptionField", descriptionValue);
//
//        form.submit("saveButton");
//
//        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
//        Map<String, Object> actual = jdbcTemplate.queryForMap("select * from acc_gl_account where gl_code = ?", glCodeValue);
//        Assert.assertNotNull("expected to have acc_gl_account gl_code = '" + glCodeValue + "'", actual);
//    }
//
//    @Test
//    public void dataEntryExpense2() {
//        this.wicket.login();
//        this.wicket.startPage(AccountCreatePage.class);
//
//        String prefix = "EXPENSE";
//        String suffix = this.wicket.getStringGenerator().generate(10);
//        String glCodeValue = prefix + "_" + suffix;
//        String accountNameValue = prefix + "_" + suffix;
//        String descriptionValue = prefix + "_" + suffix;
//
//        String tagValue = this.wicket.getJdbcTemplate().queryForObject("select m_code_value.id from m_code_value INNER join m_code on m_code_value.code_id = m_code.id where m_code.code_name = ? LIMIT 1", String.class, AccountType.Expense.getTag());
//        String parentValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where gl_code = ?", String.class, prefix + "_PR_JUNIT");
//
//        JUnitFormTester form = this.wicket.newFormTester("form");
//
//        form.setValue("accountTypeField", AccountType.Expense);
//        Select2SingleChoice<?> accountTypeField = this.wicket.getComponentFromLastRenderedPage("form:accountTypeField", Select2SingleChoice.class);
//        this.wicket.executeBehavior(accountTypeField);
//
//        form.setValue("accountTypeField", AccountType.Expense);
//
//        form.setValue("glCodeField", glCodeValue);
//
//        form.setValue("accountNameField", accountNameValue);
//
//        form.setValue("accountUsageField", AccountUsage.Detail);
//
//        form.setValue("tagField", tagValue);
//
//        form.setValue("parentField", parentValue);
//
//        form.setValue("manualAllowField", true);
//
//        form.setValue("descriptionField", descriptionValue);
//
//        form.submit("saveButton");
//
//        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
//        Map<String, Object> actual = jdbcTemplate.queryForMap("select * from acc_gl_account where gl_code = ?", glCodeValue);
//        Assert.assertNotNull("expected to have acc_gl_account gl_code = '" + glCodeValue + "'", actual);
//    }
//
//}
