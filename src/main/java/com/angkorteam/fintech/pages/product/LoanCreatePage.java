package com.angkorteam.fintech.pages.product;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.framework.share.provider.ListDataProvider;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;

import java.util.Date;
import java.util.List;
import java.util.Map;

// @AuthorizeInstantiation(Function.ALL_FUNCTION)
public class LoanCreatePage extends Page {

    private Form<Void> form;

    // Loan Product Detail
    private String productNameValue;
    private TextField<String> productNameField;
    private TextFeedbackPanel productNameFeedback;

    private String shortNameValue;
    private TextField<String> shortNameField;
    private TextFeedbackPanel shortNameFeedback;

    private String descriptionValue;
    private TextField<String> descriptionField;
    private TextFeedbackPanel descriptionFeedback;

    private SingleChoiceProvider fundProvider;
    private Option fundValue;
    private Select2SingleChoice<Option> fundField;
    private TextFeedbackPanel fundFeedback;

    private Date startDateValue;
    private DateTextField startDateField;
    private TextFeedbackPanel startDateFeedback;

    private Date closeDateValue;
    private DateTextField closeDateField;
    private TextFeedbackPanel closeDateFeedback;

    private Boolean includeInCustomerLoanCounterValue;
    private CheckBox includeInCustomerLoanCounterField;
    private TextFeedbackPanel includeInCustomerLoanCounterFeedback;

    // Currency
    private SingleChoiceProvider currencyProvider;
    private Option currencyValue;
    private Select2SingleChoice<Option> currencyField;
    private TextFeedbackPanel currencyFeedback;

    private Integer decimalPlaceValue;
    private TextField<Integer> decimalPlaceField;
    private TextFeedbackPanel decimalPlaceFeedback;

    private Integer currencyInMultipleOfValue;
    private TextField<Integer> currencyInMultipleOfField;
    private TextFeedbackPanel currencyInMultipleOfFeedback;

    private Integer installmentInMultipleOfValue;
    private TextField<Integer> installmentInMultipleOfField;
    private TextFeedbackPanel installmentInMultipleOfFeedback;

    // Terms
    private Boolean termVaryBasedOnLoanCycleValue;
    private CheckBox termVaryBasedOnLoanCycleField;
    private TextFeedbackPanel termVaryBasedOnLoanCycleFeedback;

    private Double principalMinimumValue;
    private TextField<Double> principalMinimumField;
    private TextFeedbackPanel principalMinimumFeedback;

    private Double principalDefaultValue;
    private TextField<Double> principalDefaultField;
    private TextFeedbackPanel principalDefaultFeedback;

    private Double principalMaximumValue;
    private TextField<Double> principalMaximumField;
    private TextFeedbackPanel principalMaximumFeedback;

    private Double numberOfRepaymentMinimumValue;
    private TextField<Double> numberOfRepaymentMinimumField;
    private TextFeedbackPanel numberOfRepaymentMinimumFeedback;

    private Double numberOfRepaymentDefaultValue;
    private TextField<Double> numberOfRepaymentDefaultField;
    private TextFeedbackPanel numberOfRepaymentDefaultFeedback;

    private Double numberOfRepaymentMaximumValue;
    private TextField<Double> numberOfRepaymentMaximumField;
    private TextFeedbackPanel numberOfRepaymentMaximumFeedback;

    private List<Map<String, Object>> principalByLoanCycleValue;
    private DataTable<Map<String, Object>, String> principalByLoanCycleTable;
    private ListDataProvider principalByLoanCycleProvider;

    private List<Map<String, Object>> numberOfRepaymentsByLoanCycleValue;
    private DataTable<Map<String, Object>, String> numberOfRepaymentsByLoanCycleTable;
    private ListDataProvider numberOfRepaymentsByLoanCycleProvider;

    private List<Map<String, Object>> nominalInterestRateByLoanCycleValue;
    private DataTable<Map<String, Object>, String> nominalInterestRateByLoanCycleTable;
    private ListDataProvider nominalInterestRateByLoanCycleProvider;

    private Boolean linkedToFloatingInterestRatesValue;
    private CheckBox linkedToFloatingInterestRatesField;
    private TextFeedbackPanel linkedToFloatingInterestRatesFeedback;

    private Double nominalInterestRateMinimumValue;
    private TextField<Double> nominalInterestRateMinimumField;
    private TextFeedbackPanel nominalInterestRateMinimumFeedback;

    private Double nominalInterestRateDefaultValue;
    private TextField<Double> nominalInterestRateDefaultField;
    private TextFeedbackPanel nominalInterestRateDefaultFeedback;

    private Double nominalInterestRateMaximumValue;
    private TextField<Double> nominalInterestRateMaximumField;
    private TextFeedbackPanel nominalInterestRateMaximumFeedback;

    private String nominalInterestRatePerValue;
    private DropDownChoice<String> nominalInterestRatePerField;
    private TextFeedbackPanel nominalInterestRatePerFeedback;

    private Double floatingInterestRateMinimumValue;
    private TextField<Double> floatingInterestRateMinimumField;
    private TextFeedbackPanel floatingInterestRateMinimumFeedback;

    private Double floatingInterestRateDefaultValue;
    private TextField<Double> floatingInterestRateDefaultField;
    private TextFeedbackPanel floatingInterestRateDefaultFeedback;

    private Double floatingInterestRateMaximumValue;
    private TextField<Double> floatingInterestRateMaximumField;
    private TextFeedbackPanel floatingInterestRateMaximumFeedback;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.form = new Form<>("form");
        add(this.form);

        initDetail();

        initCurrency();

        initTerms();
    }

    protected void initDetail() {
        this.productNameField = new TextField<>("productNameField", new PropertyModel<>(this, "productNameValue"));
        this.productNameField.setRequired(true);
        this.form.add(this.productNameField);
        this.productNameFeedback = new TextFeedbackPanel("productNameFeedback", this.productNameField);
        this.form.add(this.productNameFeedback);

        this.shortNameField = new TextField<>("shortNameField", new PropertyModel<>(this, "shortNameValue"));
        this.shortNameField.setRequired(true);
        this.form.add(this.shortNameField);
        this.shortNameFeedback = new TextFeedbackPanel("shortNameFeedback", this.shortNameField);
        this.form.add(this.shortNameFeedback);

        this.descriptionField = new TextField<>("descriptionField", new PropertyModel<>(this, "descriptionValue"));
        this.descriptionField.setRequired(true);
        this.form.add(this.descriptionField);
        this.descriptionFeedback = new TextFeedbackPanel("descriptionFeedback", this.descriptionField);
        this.form.add(this.descriptionFeedback);

        this.fundProvider = new SingleChoiceProvider("m_fund", "id", "name");
        this.fundField = new Select2SingleChoice<>("fundField", 0, new PropertyModel<>(this, "fundValue"),
                this.fundProvider);
        this.fundField.setRequired(true);
        this.form.add(this.fundField);
        this.fundFeedback = new TextFeedbackPanel("fundFeedback", this.fundField);
        this.form.add(this.fundFeedback);

        this.startDateField = new DateTextField("startDateField", new PropertyModel<>(this, "startDateValue"));
        this.startDateField.setRequired(true);
        this.form.add(this.startDateField);
        this.startDateFeedback = new TextFeedbackPanel("startDateFeedback", this.startDateField);
        this.form.add(this.startDateFeedback);

        this.closeDateField = new DateTextField("closeDateField", new PropertyModel<>(this, "closeDateValue"));
        this.closeDateField.setRequired(true);
        this.form.add(this.closeDateField);
        this.closeDateFeedback = new TextFeedbackPanel("closeDateFeedback", this.closeDateField);
        this.form.add(this.closeDateFeedback);

        this.includeInCustomerLoanCounterField = new CheckBox("includeInCustomerLoanCounterField",
                new PropertyModel<>(this, "includeInCustomerLoanCounterValue"));
        this.includeInCustomerLoanCounterField.setRequired(true);
        this.form.add(this.includeInCustomerLoanCounterField);
        this.includeInCustomerLoanCounterFeedback = new TextFeedbackPanel("includeInCustomerLoanCounterFeedback",
                this.includeInCustomerLoanCounterField);
        this.form.add(this.includeInCustomerLoanCounterFeedback);
    }

    protected void initCurrency() {
        this.currencyProvider = new SingleChoiceProvider("m_organisation_currency", "code", "name",
                "concat(name,' [', code,']')");
        this.currencyField = new Select2SingleChoice<>("currencyField", 0, new PropertyModel<>(this, "currencyValue"),
                this.currencyProvider);
        this.currencyField.add(new OnChangeAjaxBehavior());
        this.currencyField.setRequired(true);
        this.form.add(this.currencyField);
        this.currencyFeedback = new TextFeedbackPanel("currencyFeedback", this.currencyField);
        this.form.add(this.currencyFeedback);

        this.decimalPlaceField = new TextField<>("decimalPlaceField", new PropertyModel<>(this, "decimalPlaceValue"));
        this.decimalPlaceField.setRequired(true);
        this.form.add(this.decimalPlaceField);
        this.decimalPlaceFeedback = new TextFeedbackPanel("decimalPlaceFeedback", this.decimalPlaceField);
        this.form.add(this.decimalPlaceFeedback);

        this.currencyInMultipleOfField = new TextField<>("currencyInMultipleOfField",
                new PropertyModel<>(this, "currencyInMultipleOfValue"));
        this.currencyInMultipleOfField.setRequired(true);
        this.form.add(this.currencyInMultipleOfField);
        this.currencyInMultipleOfFeedback = new TextFeedbackPanel("currencyInMultipleOfFeedback",
                this.currencyInMultipleOfField);
        this.form.add(this.currencyInMultipleOfFeedback);

        this.installmentInMultipleOfField = new TextField<>("installmentInMultipleOfField",
                new PropertyModel<>(this, "installmentInMultipleOfValue"));
        this.installmentInMultipleOfField.setRequired(true);
        this.form.add(this.installmentInMultipleOfField);
        this.installmentInMultipleOfFeedback = new TextFeedbackPanel("installmentInMultipleOfFeedback",
                this.installmentInMultipleOfField);
        this.form.add(this.installmentInMultipleOfFeedback);

    }

    protected void initTerms() {

        this.termVaryBasedOnLoanCycleField = new CheckBox("termVaryBasedOnLoanCycleField",
                new PropertyModel<>(this, "termVaryBasedOnLoanCycleValue"));
        this.termVaryBasedOnLoanCycleField.setRequired(true);
        this.form.add(this.termVaryBasedOnLoanCycleField);
        this.termVaryBasedOnLoanCycleFeedback = new TextFeedbackPanel("termVaryBasedOnLoanCycleFeedback",
                this.termVaryBasedOnLoanCycleField);
        this.form.add(this.termVaryBasedOnLoanCycleFeedback);

        this.principalMinimumField = new TextField<>("principalMinimumField",
                new PropertyModel<>(this, "principalMinimumValue"));
        this.principalMinimumField.setRequired(true);
        this.form.add(this.principalMinimumField);
        this.principalMinimumFeedback = new TextFeedbackPanel("principalMinimumFeedback", this.principalMinimumField);
        this.form.add(this.principalMinimumFeedback);

        this.principalDefaultField = new TextField<>("principalDefaultField",
                new PropertyModel<>(this, "principalDefaultValue"));
        this.principalDefaultField.setRequired(true);
        this.form.add(this.principalDefaultField);
        this.principalDefaultFeedback = new TextFeedbackPanel("principalDefaultFeedback", this.principalDefaultField);
        this.form.add(this.principalDefaultFeedback);

        this.principalMaximumField = new TextField<>("principalMaximumField",
                new PropertyModel<>(this, "principalMaximumValue"));
        this.principalMaximumField.setRequired(true);
        this.form.add(this.principalMaximumField);
        this.principalMaximumFeedback = new TextFeedbackPanel("principalMaximumFeedback", this.principalMaximumField);
        this.form.add(this.principalMaximumFeedback);

        this.numberOfRepaymentMinimumField = new TextField<>("numberOfRepaymentMinimumField",
                new PropertyModel<>(this, "numberOfRepaymentMinimumValue"));
        this.numberOfRepaymentMinimumField.setRequired(true);
        this.form.add(this.numberOfRepaymentMinimumField);
        this.numberOfRepaymentMinimumFeedback = new TextFeedbackPanel("numberOfRepaymentMinimumFeedback",
                this.numberOfRepaymentMinimumField);
        this.form.add(this.numberOfRepaymentMinimumFeedback);

        this.numberOfRepaymentDefaultField = new TextField<>("numberOfRepaymentDefaultField",
                new PropertyModel<>(this, "numberOfRepaymentDefaultValue"));
        this.numberOfRepaymentDefaultField.setRequired(true);
        this.form.add(this.numberOfRepaymentDefaultField);
        this.numberOfRepaymentDefaultFeedback = new TextFeedbackPanel("numberOfRepaymentDefaultFeedback",
                this.numberOfRepaymentDefaultField);
        this.form.add(this.numberOfRepaymentDefaultFeedback);

        this.numberOfRepaymentMaximumField = new TextField<>("numberOfRepaymentMaximumField",
                new PropertyModel<>(this, "numberOfRepaymentMaximumValue"));
        this.numberOfRepaymentMaximumField.setRequired(true);
        this.form.add(this.numberOfRepaymentMaximumField);
        this.numberOfRepaymentMaximumFeedback = new TextFeedbackPanel("numberOfRepaymentMaximumFeedback",
                this.numberOfRepaymentMaximumField);
        this.form.add(this.numberOfRepaymentMaximumFeedback);

    }

}
