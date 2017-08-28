package com.angkorteam.fintech.pages.product;

import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;

public class ShareCreatePage extends Page {

    public static final String ACC_NONE = "None";
    public static final String ACC_CASH = "Cash";

    // Detail

    private String detailProductNameValue;
    private TextField<String> detailProductNameField;
    private TextFeedbackPanel detailProductNameFeedback;

    private String detailShortNameValue;
    private TextField<String> detailShortNameField;
    private TextFeedbackPanel detailShortNameFeedback;

    private String detailDescriptionValue;
    private TextField<String> detailDescriptionField;
    private TextFeedbackPanel detailDescriptionFeedback;

    // Currency

    private SingleChoiceProvider currencyCodeProvider;
    private Option currencyCodeValue;
    private Select2SingleChoice<Option> currencyCodeField;
    private TextFeedbackPanel currencyCodeFeedback;

    private Integer decimalPlaceValue;
    private TextField<Integer> decimalPlaceField;
    private TextFeedbackPanel decimalPlaceFeedback;

    private Integer currencyMultipleOfValue;
    private TextField<Integer> currencyMultipleOfField;
    private TextFeedbackPanel currencyMultipleOfFeedback;

    // Terms

    private String termTotalNumberOfSharesValue;
    private TextField<String> termTotalNumberOfSharesField;
    private TextFeedbackPanel termTotalNumberOfSharesFeedback;

    private String termSharesToBeIssuedValue;
    private TextField<String> termSharesToBeIssuedField;
    private TextFeedbackPanel termSharesToBeIssuedFeedback;

    private String termNominalPriceValue;
    private TextField<String> termNominalPriceField;
    private TextFeedbackPanel termNominalPriceFeedback;

    private String termCapitalValue;
    private TextField<String> termCapitalField;
    private TextFeedbackPanel termCapitalFeedback;

    // Settings

    private String settingSharesPerClientMinimumValue;
    private TextField<String> settingSharesPerClientMinimumField;
    private TextFeedbackPanel settingSharesPerClientMinimumFeedback;

    private String settingSharesPerClientDefaultValue;
    private TextField<String> settingSharesPerClientDefaultField;
    private TextFeedbackPanel settingSharesPerClientDefaultFeedback;

    private String settingSharesPerClientMaximumValue;
    private TextField<String> settingSharesPerClientMaximumField;
    private TextFeedbackPanel settingSharesPerClientMaximumFeedback;

    private String settingMinimumActivePeriodValue;
    private TextField<String> settingMinimumActivePeriodField;
    private TextFeedbackPanel settingMinimumActivePeriodFeedback;

    private SingleChoiceProvider settingMinimumActiveTypeProvider;
    private Option settingMinimumActiveTypeValue;
    private Select2SingleChoice<Option> settingMinimumActiveTypeField;
    private TextFeedbackPanel settingMinimumActiveTypeFeedback;

    private String settingLockInPeriodValue;
    private TextField<String> settingLockInPeriodField;
    private TextFeedbackPanel settingLockInPeriodFeedback;

    private SingleChoiceProvider settingLockInTypeProvider;
    private Option settingLockInTypeValue;
    private Select2SingleChoice<Option> settingLockInTypeField;
    private TextFeedbackPanel settingLockInTypeFeedback;

    private Boolean settingAllowDividendsForInactiveClientsValue;
    private CheckBox settingAllowDividendsForInactiveClientsField;
    private TextFeedbackPanel settingAllowDividendsForInactiveClientsFeedback;

}
