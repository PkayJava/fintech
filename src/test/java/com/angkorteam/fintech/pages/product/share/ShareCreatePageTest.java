package com.angkorteam.fintech.pages.product.share;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.dto.enums.AccountType;
import com.angkorteam.fintech.dto.enums.AccountUsage;
import com.angkorteam.fintech.dto.enums.ChargeType;
import com.angkorteam.fintech.dto.enums.LockInType;
import com.angkorteam.fintech.dto.enums.MinimumActivePeriod;
import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitFormTester;
import com.angkorteam.fintech.junit.JUnitWicketTester;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Maps;

public class ShareCreatePageTest {
    private JUnitWicketTester wicket;

    @Before
    public void before() {
        this.wicket = JUnit.getWicket();
    }

    @Test
    public void dataEntryMinimum() {
        this.wicket.login();

        String detailProductNameValue = "SHARED_PRODUCT_" + this.wicket.getStringGenerator().generate(10);
        String detailShortNameValue = this.wicket.getStringGenerator().generate(4);
        String detailDescriptionValue = detailProductNameValue;

        String currencyCodeValue = "USD";
        int currencyDecimalPlaceValue = 2;

        int termTotalNumberOfShareValue = 1;
        int termShareToBeIssuedValue = 1;
        double termNominalPriceValue = 10.99;

        int settingSharePerClientDefaultValue = 2;

        int accountingValue = 0;

        this.wicket.startPage(ShareCreatePage.class);

        JUnitFormTester form = null;

        form = this.wicket.newFormTester("form");

        form.setValue("detailProductNameBlock:detailProductNameContainer:detailProductNameField", detailProductNameValue);
        form.setValue("detailShortNameBlock:detailShortNameContainer:detailShortNameField", detailShortNameValue);
        form.setValue("detailDescriptionBlock:detailDescriptionContainer:detailDescriptionField", detailDescriptionValue);

        form.setValue("currencyCodeBlock:currencyCodeContainer:currencyCodeField", currencyCodeValue);
        form.setValue("currencyDecimalPlaceBlock:currencyDecimalPlaceContainer:currencyDecimalPlaceField", currencyDecimalPlaceValue);

        form.setValue("termTotalNumberOfShareBlock:termTotalNumberOfShareContainer:termTotalNumberOfShareField", termTotalNumberOfShareValue);
        form.setValue("termShareToBeIssuedBlock:termShareToBeIssuedContainer:termShareToBeIssuedField", termShareToBeIssuedValue);
        form.setValue("termNominalPriceBlock:termNominalPriceContainer:termNominalPriceField", termNominalPriceValue);
        form.setValue("settingSharePerClientDefaultBlock:settingSharePerClientDefaultContainer:settingSharePerClientDefaultField", settingSharePerClientDefaultValue);

        // Accounting

        form.select("accountingField", accountingValue);

        form.submit("saveButton");

        this.wicket.assertNoErrorMessage();

        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
        Map<String, Object> actual = jdbcTemplate.queryForMap("select * from m_share_product where name = ?", detailProductNameValue);
        Assert.assertNotNull("expected to have m_share_product name = '" + detailProductNameValue + "'", actual);
    }

    @Test
    public void dataEntryMaximum() {
        this.wicket.login();

        String detailProductNameValue = "SHARED_PRODUCT_" + this.wicket.getStringGenerator().generate(10);
        String detailShortNameValue = this.wicket.getStringGenerator().generate(4);
        String detailDescriptionValue = detailProductNameValue;

        String currencyCodeValue = "USD";
        int currencyDecimalPlaceValue = 2;
        int currencyMultipleOfValue = 1;

        int termTotalNumberOfShareValue = 1;
        int termShareToBeIssuedValue = 1;
        double termNominalPriceValue = 10.99;
        double termCapitalValue = 10.99;

        int settingSharePerClientMinimumValue = 1;
        int settingSharePerClientDefaultValue = 2;
        int settingSharePerClientMaximumValue = 3;
        int settingMinimumActivePeriodValue = 1;
        MinimumActivePeriod settingMinimumActiveTypeValue = MinimumActivePeriod.Day;
        int settingLockInPeriodValue = 1;
        LockInType settingLockInTypeValue = LockInType.Day;
        boolean settingAllowDividendForInactiveClientValue = true;

        String chargeValue = this.wicket.getJdbcTemplate().queryForObject("select id from m_charge where currency_code = ? and charge_applies_to_enum = ? and is_penalty = ? and is_active = 1 limit 1", String.class, currencyCodeValue, ChargeType.Share.getLiteral(), 0);

        int accountingValue = 1;

        String cashShareReferenceValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Asset.getLiteral());
        String cashShareSuspenseControlValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Liability.getLiteral());
        String cashEquityValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Equity.getLiteral());
        String cashIncomeFromFeesValue = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where account_usage = ? and classification_enum = ? limit 1", String.class, AccountUsage.Detail.getLiteral(), AccountType.Income.getLiteral());

        ShareCreatePage page = this.wicket.startPage(ShareCreatePage.class);

        JUnitFormTester form = null;

        {
            Select2SingleChoice<?> currencyCodeField = this.wicket.getComponentFromLastRenderedPage("form:currencyCodeBlock:currencyCodeContainer:currencyCodeField", Select2SingleChoice.class);
            form = this.wicket.newFormTester("form");
            form.setValue("currencyCodeBlock:currencyCodeContainer:currencyCodeField", currencyCodeValue);
            this.wicket.executeBehavior(currencyCodeField);
        }
        {
            form = this.wicket.newFormTester("form");
            form.select("accountingField", accountingValue);
            RadioGroup<?> accountingField = this.wicket.getComponentFromLastRenderedPage("form:accountingField", RadioGroup.class);
            this.wicket.executeBehavior(accountingField);
        }
        {
            AjaxLink<?> chargeAddLink = this.wicket.getComponentFromLastRenderedPage("form:chargeAddLink", AjaxLink.class);
            this.wicket.executeAjaxLink(chargeAddLink);

            JUnitFormTester popupForm = this.wicket.newFormTester("chargePopup:content:form");
            popupForm.setValue("chargeField", chargeValue);

            AjaxButton okayButton = this.wicket.getComponentFromLastRenderedPage("chargePopup:content:form:okayButton", AjaxButton.class);
            this.wicket.executeAjaxButton(okayButton);

            ModalWindow chargePopup = this.wicket.getComponentFromLastRenderedPage("chargePopup", ModalWindow.class);
            this.wicket.executeModalWindowOnClose(chargePopup);
        }
        {
            Date fromDateValue = DateTime.now().toDate();
            double unitPriceValue = 10.99;

            AjaxLink<?> marketPriceAddLink = this.wicket.getComponentFromLastRenderedPage("form:marketPriceAddLink", AjaxLink.class);
            this.wicket.executeAjaxLink(marketPriceAddLink);

            JUnitFormTester popupForm = this.wicket.newFormTester("marketPricePopup:content:form");
            popupForm.setValue("fromDateField", DateFormatUtils.format(fromDateValue, "dd/MM/yyyy"));
            popupForm.setValue("unitPriceField", unitPriceValue);

            AjaxButton okayButton = this.wicket.getComponentFromLastRenderedPage("marketPricePopup:content:form:okayButton", AjaxButton.class);
            this.wicket.executeAjaxButton(okayButton);

            ModalWindow marketPricePopup = this.wicket.getComponentFromLastRenderedPage("marketPricePopup", ModalWindow.class);
            this.wicket.executeModalWindowOnClose(marketPricePopup);
            this.wicket.startPage(page);
        }

        form = this.wicket.newFormTester("form");

        form.setValue("detailProductNameBlock:detailProductNameContainer:detailProductNameField", detailProductNameValue);
        form.setValue("detailShortNameBlock:detailShortNameContainer:detailShortNameField", detailShortNameValue);
        form.setValue("detailDescriptionBlock:detailDescriptionContainer:detailDescriptionField", detailDescriptionValue);

        form.setValue("currencyCodeBlock:currencyCodeContainer:currencyCodeField", currencyCodeValue);
        form.setValue("currencyDecimalPlaceBlock:currencyDecimalPlaceContainer:currencyDecimalPlaceField", currencyDecimalPlaceValue);
        form.setValue("currencyMultipleOfBlock:currencyMultipleOfContainer:currencyMultipleOfField", currencyMultipleOfValue);

        form.setValue("termTotalNumberOfShareBlock:termTotalNumberOfShareContainer:termTotalNumberOfShareField", termTotalNumberOfShareValue);
        form.setValue("termShareToBeIssuedBlock:termShareToBeIssuedContainer:termShareToBeIssuedField", termShareToBeIssuedValue);
        form.setValue("termNominalPriceBlock:termNominalPriceContainer:termNominalPriceField", termNominalPriceValue);
        form.setValue("termCapitalBlock:termCapitalContainer:termCapitalField", termCapitalValue);

        form.setValue("settingSharePerClientDefaultBlock:settingSharePerClientDefaultContainer:settingSharePerClientDefaultField", settingSharePerClientDefaultValue);
        form.setValue("settingSharePerClientMinimumBlock:settingSharePerClientMinimumContainer:settingSharePerClientMinimumField", settingSharePerClientMinimumValue);
        form.setValue("settingSharePerClientMaximumBlock:settingSharePerClientMaximumContainer:settingSharePerClientMaximumField", settingSharePerClientMaximumValue);
        form.setValue("settingMinimumActivePeriodBlock:settingMinimumActivePeriodContainer:settingMinimumActivePeriodField", settingMinimumActivePeriodValue);
        form.setValue("settingMinimumActiveTypeBlock:settingMinimumActiveTypeContainer:settingMinimumActiveTypeField", settingMinimumActiveTypeValue);
        form.setValue("settingLockInPeriodBlock:settingLockInPeriodContainer:settingLockInPeriodField", settingLockInPeriodValue);
        form.setValue("settingLockInTypeBlock:settingLockInTypeContainer:settingLockInTypeField", settingLockInTypeValue);
        form.setValue("settingAllowDividendForInactiveClientBlock:settingAllowDividendForInactiveClientContainer:settingAllowDividendForInactiveClientField", settingAllowDividendForInactiveClientValue);

        // Accounting

        form.select("accountingField", accountingValue);

        form.setValue("cashBlock:cashContainer:cashShareReferenceField", cashShareReferenceValue);
        form.setValue("cashBlock:cashContainer:cashShareSuspenseControlField", cashShareSuspenseControlValue);
        form.setValue("cashBlock:cashContainer:cashEquityField", cashEquityValue);
        form.setValue("cashBlock:cashContainer:cashIncomeFromFeesField", cashIncomeFromFeesValue);

        form.submit("saveButton");

        this.wicket.assertNoErrorMessage();

        JdbcTemplate jdbcTemplate = this.wicket.getJdbcTemplate();
        Map<String, Object> actual = jdbcTemplate.queryForMap("select * from m_share_product where name = ?", detailProductNameValue);
        Assert.assertNotNull("expected to have m_share_product name = '" + detailProductNameValue + "'", actual);
    }

    @Test
    public void chargeAddLinkClickDuplicatedTest() {
        this.wicket.login();

        String currencyCodeValue = "USD";

        String chargeValue = this.wicket.getJdbcTemplate().queryForObject("select id from m_charge where currency_code = ? and charge_applies_to_enum = ? and is_penalty = ? and is_active = 1 limit 1", String.class, currencyCodeValue, ChargeType.SavingDeposit.getLiteral(), 0);

        ShareCreatePage page = this.wicket.startPage(ShareCreatePage.class);

        Map<String, Object> item = Maps.newHashMap();
        item.put("uuid", chargeValue);
        page.chargeValue.add(item);

        JUnitFormTester form = this.wicket.newFormTester("form");

        form.setValue("currencyCodeBlock:currencyCodeContainer:currencyCodeField", currencyCodeValue);
        Select2SingleChoice<?> currencyCodeField = this.wicket.getComponentFromLastRenderedPage("form:currencyCodeBlock:currencyCodeContainer:currencyCodeField", Select2SingleChoice.class);
        this.wicket.executeBehavior(currencyCodeField);

        AjaxLink<?> chargeAddLink = this.wicket.getComponentFromLastRenderedPage("form:chargeAddLink", AjaxLink.class);
        this.wicket.executeAjaxLink(chargeAddLink);

        JUnitFormTester popupForm = this.wicket.newFormTester("chargePopup:content:form");
        popupForm.setValue("chargeField", chargeValue);

        AjaxButton okayButton = this.wicket.getComponentFromLastRenderedPage("chargePopup:content:form:okayButton", AjaxButton.class);
        this.wicket.executeAjaxButton(okayButton);

        ModalWindow chargePopup = this.wicket.getComponentFromLastRenderedPage("chargePopup", ModalWindow.class);
        this.wicket.executeModalWindowOnClose(chargePopup);

        this.wicket.startPage(page);

        Assert.assertEquals("expected to have chargeValue one item inside", page.chargeValue.size(), 1);

    }

    @Test
    public void chargeActionClickTest() {
        this.wicket.login();

        ShareCreatePage page = this.wicket.startPage(ShareCreatePage.class);

        JUnitFormTester form = null;

        form = this.wicket.newFormTester("form");

        this.wicket.startPage(page);

        Map<String, Object> item = Maps.newHashMap();
        String uuid = this.wicket.getStringGenerator().externalId();
        item.put("uuid", uuid);
        page.chargeValue.add(item);

        this.wicket.startPage(page);

        this.wicket.executeAjaxEvent("form:chargeTable:body:rows:1:cells:6:cell:1:link", "click");

        this.wicket.startPage(page);

        Assert.assertEquals("expected to have chargeValue one item inside", page.chargeValue.size(), 0);
    }

    @Test
    public void marketPriceActionClickTest() {
        this.wicket.login();

        ShareCreatePage page = this.wicket.startPage(ShareCreatePage.class);

        JUnitFormTester form = null;

        form = this.wicket.newFormTester("form");

        this.wicket.startPage(page);

        Map<String, Object> item = Maps.newHashMap();
        String uuid = this.wicket.getStringGenerator().externalId();
        item.put("uuid", uuid);
        page.marketPriceValue.add(item);

        this.wicket.startPage(page);

        this.wicket.executeAjaxEvent("form:marketPriceTable:body:rows:1:cells:3:cell:1:link", "click");

        this.wicket.startPage(page);

        Assert.assertEquals("expected to have marketPriceValue one item inside", page.marketPriceValue.size(), 0);
    }

    @Test
    public void chargeAddLinkClickNoCurrencyTest() {
        this.wicket.login();

        ShareCreatePage page = this.wicket.startPage(ShareCreatePage.class);

        AjaxLink<?> chargeAddLink = this.wicket.getComponentFromLastRenderedPage("form:chargeAddLink", AjaxLink.class);
        this.wicket.executeAjaxLink(chargeAddLink);

        // Assert.assertEquals("exected currencyPopup to be shown",
        // page.currencyPopup.isShown(), true);
    }

}
