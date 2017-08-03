package com.angkorteam.fintech.pages.product;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.popup.LoanCyclePopup;
import com.angkorteam.fintech.provider.NominalInterestRateTypeProvider;
import com.angkorteam.fintech.provider.RepaidTypeProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.framework.share.provider.ListDataProvider;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.TextColumn;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionFilterColumn;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionItem;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemCss;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

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

    // Row 1 : Terms vary based on loan cycle
    private Boolean termVaryBasedOnLoanCycleValue;
    private CheckBox termVaryBasedOnLoanCycleField;
    private TextFeedbackPanel termVaryBasedOnLoanCycleFeedback;

    // Row 2 : Principal
    private Double principalMinimumValue;
    private TextField<Double> principalMinimumField;
    private TextFeedbackPanel principalMinimumFeedback;

    private Double principalDefaultValue;
    private TextField<Double> principalDefaultField;
    private TextFeedbackPanel principalDefaultFeedback;

    private Double principalMaximumValue;
    private TextField<Double> principalMaximumField;
    private TextFeedbackPanel principalMaximumFeedback;

    // Row 2 (Optional) : Principal by loan cycle
    private WebMarkupContainer principalByLoanCycleContainer;

    private List<Map<String, Object>> principalByLoanCycleValue;
    private DataTable<Map<String, Object>, String> principalByLoanCycleTable;
    private ListDataProvider principalByLoanCycleProvider;

    // Row 3 : Number of repayments
    private Double numberOfRepaymentMinimumValue;
    private TextField<Double> numberOfRepaymentMinimumField;
    private TextFeedbackPanel numberOfRepaymentMinimumFeedback;

    private Double numberOfRepaymentDefaultValue;
    private TextField<Double> numberOfRepaymentDefaultField;
    private TextFeedbackPanel numberOfRepaymentDefaultFeedback;

    private Double numberOfRepaymentMaximumValue;
    private TextField<Double> numberOfRepaymentMaximumField;
    private TextFeedbackPanel numberOfRepaymentMaximumFeedback;

    // Row 3 (Optional) : Number of Repayments by loan cycle
    private WebMarkupContainer numberOfRepaymentByLoanCycleContainer;

    private List<Map<String, Object>> numberOfRepaymentByLoanCycleValue;
    private DataTable<Map<String, Object>, String> numberOfRepaymentByLoanCycleTable;
    private ListDataProvider numberOfRepaymentByLoanCycleProvider;

    // Row 4 : Is Linked to Floating Interest Rates?
    private Boolean linkedToFloatingInterestRatesValue;
    private CheckBox linkedToFloatingInterestRatesField;
    private TextFeedbackPanel linkedToFloatingInterestRatesFeedback;

    private WebMarkupContainer nominalInterestRateContainer;

    // Row 5 : Nominal interest rate
    private Double nominalInterestRateMinimumValue;
    private TextField<Double> nominalInterestRateMinimumField;
    private TextFeedbackPanel nominalInterestRateMinimumFeedback;

    private Double nominalInterestRateDefaultValue;
    private TextField<Double> nominalInterestRateDefaultField;
    private TextFeedbackPanel nominalInterestRateDefaultFeedback;

    private Double nominalInterestRateMaximumValue;
    private TextField<Double> nominalInterestRateMaximumField;
    private TextFeedbackPanel nominalInterestRateMaximumFeedback;

    private NominalInterestRateTypeProvider nominalInterestRateTypeProvider;
    private Option nominalInterestRateTypeValue;
    private Select2SingleChoice<Option> nominalInterestRateTypeField;
    private TextFeedbackPanel nominalInterestRateTypeFeedback;

    private WebMarkupContainer floatInterestRateContainer;

    // Row 3 (Optional) : Nominal interest rate by loan cycle
    private WebMarkupContainer nominalInterestRateByLoanCycleContainer;

    // Row 6
    private List<Map<String, Object>> nominalInterestRateByLoanCycleValue;
    private DataTable<Map<String, Object>, String> nominalInterestRateByLoanCycleTable;
    private ListDataProvider nominalInterestRateByLoanCycleProvider;

    private Integer repaidEveryValue;
    private TextField<Integer> repaidEveryField;
    private TextFeedbackPanel repaidEveryFeedback;

    private RepaidTypeProvider repaidTypeProvider;
    private Option repaidTypeValue;
    private Select2SingleChoice<Option> repaidTypeField;
    private TextFeedbackPanel repaidTypeFeedback;

    private Double minimumDayBetweenDisbursalAndFirstRepaymentDateValue;
    private TextField<Double> minimumDayBetweenDisbursalAndFirstRepaymentDateField;
    private TextFeedbackPanel minimumDayBetweenDisbursalAndFirstRepaymentDateFeedback;

    private SingleChoiceProvider floatingInterestRateProvider;
    private Option floatingInterestRateValue;
    private Select2SingleChoice<Option> floatingInterestRateField;
    private TextFeedbackPanel floatingInterestRateFeedback;

    private Double floatingInterestMinimumValue;
    private TextField<Double> floatingInterestMinimumField;
    private TextFeedbackPanel floatingInterestMinimumFeedback;

    private Double floatingInterestDefaultValue;
    private TextField<Double> floatingInterestDefaultField;
    private TextFeedbackPanel floatingInterestDefaultFeedback;

    private Double floatingInterestMaximumValue;
    private TextField<Double> floatingInterestMaximumField;
    private TextFeedbackPanel floatingInterestMaximumFeedback;

    private Double floatingInterestDifferentialValue;
    private TextField<Double> floatingInterestDifferentialField;
    private TextFeedbackPanel floatingInterestDifferentialFeedback;

    private Boolean floatingInterestAllowedValue;
    private CheckBox floatingInterestAllowedField;
    private TextFeedbackPanel floatingInterestAllowedFeedback;

    private Option itemWhenValue;
    private Integer itemLoanCycleValue;
    private Double itemMinimumValue;
    private Double itemDefaultValue;
    private Double itemMaximumValue;
    private List<Map<String, Object>> loanCycleValue;
    private ModalWindow loanCyclePopup;

    @Override
    protected void onInitialize() {
        super.onInitialize();
        this.loanCyclePopup = new ModalWindow("loanCyclePopup");
        add(this.loanCyclePopup);

        this.loanCyclePopup
                .setContent(new LoanCyclePopup(this.loanCyclePopup.getContentId(), this.loanCyclePopup, this));
        this.loanCyclePopup.setOnClose(this::loanCyclePopupOnClose);

        this.form = new Form<>("form");
        add(this.form);

        initDetail();

        initCurrency();

        initTerms();

        initDefault();
    }

    protected void initDefault() {
        this.principalByLoanCycleContainer
                .setVisible(this.termVaryBasedOnLoanCycleValue == null ? false : this.termVaryBasedOnLoanCycleValue);
        this.numberOfRepaymentByLoanCycleContainer
                .setVisible(this.termVaryBasedOnLoanCycleValue == null ? false : this.termVaryBasedOnLoanCycleValue);
        this.nominalInterestRateByLoanCycleContainer
                .setVisible(this.termVaryBasedOnLoanCycleValue == null ? false : this.termVaryBasedOnLoanCycleValue);
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
        // loanCycleValue
    }

    protected void initTerms() {

        this.termVaryBasedOnLoanCycleField = new CheckBox("termVaryBasedOnLoanCycleField",
                new PropertyModel<>(this, "termVaryBasedOnLoanCycleValue"));
        this.termVaryBasedOnLoanCycleField.setRequired(true);
        this.termVaryBasedOnLoanCycleField.add(new OnChangeAjaxBehavior(this::termVaryBasedOnLoanCycleFieldUpdate));
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

        {
            this.principalByLoanCycleContainer = new WebMarkupContainer("principalByLoanCycleContainer");
            this.form.add(this.principalByLoanCycleContainer);

            List<IColumn<Map<String, Object>, String>> principalByLoanCycleColumn = Lists.newArrayList();
            principalByLoanCycleColumn
                    .add(new TextColumn(Model.of("When"), "when", "when", this::principalByLoanCycleWhenColumn));
            principalByLoanCycleColumn.add(
                    new TextColumn(Model.of("Loan Cycle"), "cycle", "cycle", this::principalByLoanCycleCycleColumn));
            principalByLoanCycleColumn.add(
                    new TextColumn(Model.of("Min"), "minimum", "minimum", this::principalByLoanCycleMinimumColumn));
            principalByLoanCycleColumn.add(
                    new TextColumn(Model.of("Default"), "default", "default", this::principalByLoanCycleDefaultColumn));
            principalByLoanCycleColumn.add(
                    new TextColumn(Model.of("Max"), "maximum", "maximum", this::principalByLoanCycleMaximumColumn));
            principalByLoanCycleColumn.add(new ActionFilterColumn<>(Model.of("Action"),
                    this::principalByLoanCycleActionItem, this::principalByLoanCycleActionClick));
            this.principalByLoanCycleValue = Lists.newArrayList();
            this.principalByLoanCycleProvider = new ListDataProvider(this.principalByLoanCycleValue);
            this.principalByLoanCycleTable = new DataTable<>("principalByLoanCycleTable", principalByLoanCycleColumn,
                    this.principalByLoanCycleProvider, 20);
            this.principalByLoanCycleContainer.add(this.principalByLoanCycleTable);
            this.principalByLoanCycleTable.addTopToolbar(
                    new HeadersToolbar<>(this.principalByLoanCycleTable, this.principalByLoanCycleProvider));
            this.principalByLoanCycleTable.addBottomToolbar(new NoRecordsToolbar(this.principalByLoanCycleTable));

            AjaxLink<Void> addLink = new AjaxLink<>("addLink");
            addLink.setOnClick(this::principalByLoanCycleAddLinkClick);
            this.principalByLoanCycleContainer.add(addLink);
        }

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

        {
            this.numberOfRepaymentByLoanCycleContainer = new WebMarkupContainer(
                    "numberOfRepaymentByLoanCycleContainer");
            this.form.add(this.numberOfRepaymentByLoanCycleContainer);

            List<IColumn<Map<String, Object>, String>> numberOfRepaymentByLoanCycleColumn = Lists.newArrayList();
            numberOfRepaymentByLoanCycleColumn.add(
                    new TextColumn(Model.of("When"), "when", "when", this::numberOfRepaymentByLoanCycleWhenColumn));
            numberOfRepaymentByLoanCycleColumn.add(new TextColumn(Model.of("Loan Cycle"), "cycle", "cycle",
                    this::numberOfRepaymentByLoanCycleCycleColumn));
            numberOfRepaymentByLoanCycleColumn.add(new TextColumn(Model.of("Min"), "minimum", "minimum",
                    this::numberOfRepaymentByLoanCycleMinimumColumn));
            numberOfRepaymentByLoanCycleColumn.add(new TextColumn(Model.of("Default"), "default", "default",
                    this::numberOfRepaymentByLoanCycleDefaultColumn));
            numberOfRepaymentByLoanCycleColumn.add(new TextColumn(Model.of("Max"), "maximum", "maximum",
                    this::numberOfRepaymentByLoanCycleMaximumColumn));
            numberOfRepaymentByLoanCycleColumn.add(new ActionFilterColumn<>(Model.of("Action"),
                    this::numberOfRepaymentByLoanCycleActionItem, this::numberOfRepaymentByLoanCycleActionClick));
            this.numberOfRepaymentByLoanCycleValue = Lists.newArrayList();
            this.numberOfRepaymentByLoanCycleProvider = new ListDataProvider(this.numberOfRepaymentByLoanCycleValue);
            this.numberOfRepaymentByLoanCycleTable = new DataTable<>("numberOfRepaymentByLoanCycleTable",
                    numberOfRepaymentByLoanCycleColumn, this.numberOfRepaymentByLoanCycleProvider, 20);
            this.numberOfRepaymentByLoanCycleContainer.add(this.numberOfRepaymentByLoanCycleTable);
            this.numberOfRepaymentByLoanCycleTable.addTopToolbar(new HeadersToolbar<>(
                    this.numberOfRepaymentByLoanCycleTable, this.numberOfRepaymentByLoanCycleProvider));
            this.numberOfRepaymentByLoanCycleTable
                    .addBottomToolbar(new NoRecordsToolbar(this.numberOfRepaymentByLoanCycleTable));

            AjaxLink<Void> addLink = new AjaxLink<>("addLink");
            addLink.setOnClick(this::numberOfRepaymentByLoanCycleAddLinkClick);
            this.numberOfRepaymentByLoanCycleContainer.add(addLink);
        }

        // Linked to Floating Interest Rates
        this.linkedToFloatingInterestRatesField = new CheckBox("linkedToFloatingInterestRatesField",
                new PropertyModel<>(this, "linkedToFloatingInterestRatesValue"));
        this.linkedToFloatingInterestRatesField.setRequired(true);
        this.linkedToFloatingInterestRatesField
                .add(new OnChangeAjaxBehavior(this::linkedToFloatingInterestRatesFieldUpdate));
        this.form.add(this.linkedToFloatingInterestRatesField);
        this.linkedToFloatingInterestRatesFeedback = new TextFeedbackPanel("linkedToFloatingInterestRatesFeedback",
                this.linkedToFloatingInterestRatesField);
        this.form.add(this.linkedToFloatingInterestRatesFeedback);

        this.nominalInterestRateContainer = new WebMarkupContainer("nominalInterestRateContainer");
        this.form.add(this.nominalInterestRateContainer);
        {
            this.nominalInterestRateMinimumField = new TextField<>("nominalInterestRateMinimumField",
                    new PropertyModel<>(this, "nominalInterestRateMinimumValue"));
            this.nominalInterestRateMinimumField.setRequired(true);
            this.nominalInterestRateContainer.add(this.nominalInterestRateMinimumField);
            this.nominalInterestRateMinimumFeedback = new TextFeedbackPanel("nominalInterestRateMinimumFeedback",
                    this.nominalInterestRateMinimumField);
            this.nominalInterestRateContainer.add(this.nominalInterestRateMinimumFeedback);

            this.nominalInterestRateDefaultField = new TextField<>("nominalInterestRateDefaultField",
                    new PropertyModel<>(this, "nominalInterestRateDefaultValue"));
            this.nominalInterestRateDefaultField.setRequired(true);
            this.nominalInterestRateContainer.add(this.nominalInterestRateDefaultField);
            this.nominalInterestRateDefaultFeedback = new TextFeedbackPanel("nominalInterestRateDefaultFeedback",
                    this.nominalInterestRateDefaultField);
            this.nominalInterestRateContainer.add(this.nominalInterestRateDefaultFeedback);

            this.nominalInterestRateMaximumField = new TextField<>("nominalInterestRateMaximumField",
                    new PropertyModel<>(this, "nominalInterestRateMaximumValue"));
            this.nominalInterestRateMaximumField.setRequired(true);
            this.nominalInterestRateContainer.add(this.nominalInterestRateMaximumField);
            this.nominalInterestRateMaximumFeedback = new TextFeedbackPanel("nominalInterestRateMaximumFeedback",
                    this.nominalInterestRateMaximumField);
            this.nominalInterestRateContainer.add(this.nominalInterestRateMaximumFeedback);

            this.nominalInterestRateTypeProvider = new NominalInterestRateTypeProvider();
            this.nominalInterestRateTypeField = new Select2SingleChoice<>("nominalInterestRateTypeField", 0,
                    new PropertyModel<>(this, "nominalInterestRateTypeValue"), this.nominalInterestRateTypeProvider);
            this.nominalInterestRateTypeField.setRequired(true);
            this.nominalInterestRateContainer.add(this.nominalInterestRateTypeField);
            this.nominalInterestRateTypeFeedback = new TextFeedbackPanel("nominalInterestRateTypeFeedback",
                    this.nominalInterestRateTypeField);
            this.nominalInterestRateContainer.add(this.nominalInterestRateTypeFeedback);
        }

        this.floatInterestRateContainer = new WebMarkupContainer("floatInterestRateContainer");
        this.form.add(this.floatInterestRateContainer);
        {
            this.floatingInterestRateProvider = new SingleChoiceProvider("m_office", "id", "name");
            this.floatingInterestRateField = new Select2SingleChoice<>("floatingInterestRateField", 0,
                    new PropertyModel<>(this, "floatingInterestRateValue"), this.floatingInterestRateProvider);
            this.floatingInterestRateField.setRequired(true);
            this.floatInterestRateContainer.add(this.floatingInterestRateField);
            this.floatingInterestRateFeedback = new TextFeedbackPanel("floatingInterestRateFeedback",
                    this.floatingInterestRateField);
            this.floatInterestRateContainer.add(this.floatingInterestRateFeedback);

            this.floatingInterestMinimumField = new TextField<>("floatingInterestMinimumField",
                    new PropertyModel<>(this, "floatingInterestMinimumValue"));
            this.floatingInterestMinimumField.setRequired(true);
            this.floatInterestRateContainer.add(this.floatingInterestMinimumField);
            this.floatingInterestMinimumFeedback = new TextFeedbackPanel("floatingInterestMinimumFeedback",
                    this.floatingInterestMinimumField);
            this.floatInterestRateContainer.add(this.floatingInterestMinimumFeedback);

            this.floatingInterestDefaultField = new TextField<>("floatingInterestDefaultField",
                    new PropertyModel<>(this, "floatingInterestDefaultValue"));
            this.floatingInterestDefaultField.setRequired(true);
            this.floatInterestRateContainer.add(this.floatingInterestDefaultField);
            this.floatingInterestDefaultFeedback = new TextFeedbackPanel("floatingInterestDefaultFeedback",
                    this.floatingInterestDefaultField);
            this.floatInterestRateContainer.add(this.floatingInterestDefaultFeedback);

            this.floatingInterestMaximumField = new TextField<>("floatingInterestMaximumField",
                    new PropertyModel<>(this, "floatingInterestMaximumValue"));
            this.floatingInterestMaximumField.setRequired(true);
            this.floatInterestRateContainer.add(this.floatingInterestMaximumField);
            this.floatingInterestMaximumFeedback = new TextFeedbackPanel("floatingInterestMaximumFeedback",
                    this.floatingInterestMaximumField);
            this.floatInterestRateContainer.add(this.floatingInterestMaximumFeedback);

            this.floatingInterestDifferentialField = new TextField<>("floatingInterestDifferentialField",
                    new PropertyModel<>(this, "floatingInterestDifferentialValue"));
            this.floatingInterestDifferentialField.setRequired(true);
            this.floatInterestRateContainer.add(this.floatingInterestDifferentialField);
            this.floatingInterestDifferentialFeedback = new TextFeedbackPanel("floatingInterestDifferentialFeedback",
                    this.floatingInterestDifferentialField);
            this.floatInterestRateContainer.add(this.floatingInterestDifferentialFeedback);

            this.floatingInterestAllowedField = new CheckBox("floatingInterestAllowedField",
                    new PropertyModel<>(this, "floatingInterestAllowedValue"));
            this.floatingInterestAllowedField.setRequired(true);
            this.floatInterestRateContainer.add(this.floatingInterestAllowedField);
            this.floatingInterestAllowedFeedback = new TextFeedbackPanel("floatingInterestAllowedFeedback",
                    this.floatingInterestAllowedField);
            this.floatInterestRateContainer.add(this.floatingInterestAllowedFeedback);
        }

        {
            this.nominalInterestRateByLoanCycleContainer = new WebMarkupContainer(
                    "nominalInterestRateByLoanCycleContainer");
            this.form.add(this.nominalInterestRateByLoanCycleContainer);

            List<IColumn<Map<String, Object>, String>> nominalInterestRateByLoanCycleColumn = Lists.newArrayList();
            nominalInterestRateByLoanCycleColumn.add(
                    new TextColumn(Model.of("When"), "when", "when", this::nominalInterestRateByLoanCycleWhenColumn));
            nominalInterestRateByLoanCycleColumn.add(new TextColumn(Model.of("Loan Cycle"), "cycle", "cycle",
                    this::nominalInterestRateByLoanCycleCycleColumn));
            nominalInterestRateByLoanCycleColumn.add(new TextColumn(Model.of("Min"), "minimum", "minimum",
                    this::nominalInterestRateByLoanCycleMinimumColumn));
            nominalInterestRateByLoanCycleColumn.add(new TextColumn(Model.of("Default"), "default", "default",
                    this::nominalInterestRateByLoanCycleDefaultColumn));
            nominalInterestRateByLoanCycleColumn.add(new TextColumn(Model.of("Max"), "maximum", "maximum",
                    this::nominalInterestRateByLoanCycleMaximumColumn));
            nominalInterestRateByLoanCycleColumn.add(new ActionFilterColumn<>(Model.of("Action"),
                    this::nominalInterestRateByLoanCycleActionItem, this::nominalInterestRateByLoanCycleActionClick));
            this.nominalInterestRateByLoanCycleValue = Lists.newArrayList();
            this.nominalInterestRateByLoanCycleProvider = new ListDataProvider(
                    this.nominalInterestRateByLoanCycleValue);
            this.nominalInterestRateByLoanCycleTable = new DataTable<>("nominalInterestRateByLoanCycleTable",
                    nominalInterestRateByLoanCycleColumn, this.nominalInterestRateByLoanCycleProvider, 20);
            this.nominalInterestRateByLoanCycleContainer.add(this.nominalInterestRateByLoanCycleTable);
            this.nominalInterestRateByLoanCycleTable.addTopToolbar(new HeadersToolbar<>(
                    this.nominalInterestRateByLoanCycleTable, this.nominalInterestRateByLoanCycleProvider));
            this.nominalInterestRateByLoanCycleTable
                    .addBottomToolbar(new NoRecordsToolbar(this.nominalInterestRateByLoanCycleTable));

            AjaxLink<Void> addLink = new AjaxLink<>("addLink");
            addLink.setOnClick(this::nominalInterestRateByLoanCycleAddLinkClick);
            this.nominalInterestRateByLoanCycleContainer.add(addLink);
        }

        this.repaidEveryField = new TextField<>("repaidEveryField", new PropertyModel<>(this, "repaidEveryValue"));
        this.repaidEveryField.setRequired(true);
        this.form.add(this.repaidEveryField);
        this.repaidEveryFeedback = new TextFeedbackPanel("repaidEveryFeedback", this.repaidEveryField);
        this.form.add(this.repaidEveryFeedback);

        this.repaidTypeProvider = new RepaidTypeProvider();
        this.repaidTypeField = new Select2SingleChoice<>("repaidTypeField", 0,
                new PropertyModel<>(this, "repaidTypeValue"), this.repaidTypeProvider);
        this.repaidTypeField.setRequired(true);
        this.form.add(this.repaidTypeField);
        this.repaidTypeFeedback = new TextFeedbackPanel("repaidTypeFeedback", this.repaidTypeField);
        this.form.add(this.repaidTypeFeedback);

        this.minimumDayBetweenDisbursalAndFirstRepaymentDateField = new TextField<>(
                "minimumDayBetweenDisbursalAndFirstRepaymentDateField",
                new PropertyModel<>(this, "minimumDayBetweenDisbursalAndFirstRepaymentDateValue"));
        this.minimumDayBetweenDisbursalAndFirstRepaymentDateField.setRequired(true);
        this.form.add(this.minimumDayBetweenDisbursalAndFirstRepaymentDateField);
        this.minimumDayBetweenDisbursalAndFirstRepaymentDateFeedback = new TextFeedbackPanel(
                "minimumDayBetweenDisbursalAndFirstRepaymentDateFeedback",
                this.minimumDayBetweenDisbursalAndFirstRepaymentDateField);
        this.form.add(this.minimumDayBetweenDisbursalAndFirstRepaymentDateFeedback);

        // floatingInterestRateDifferential
        // floatingCalculationAllowed
    }

    private void nominalInterestRateByLoanCycleAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.loanCycleValue = this.nominalInterestRateByLoanCycleValue;
        this.loanCyclePopup.show(target);
    }

    private ItemPanel nominalInterestRateByLoanCycleWhenColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(value));
    }

    private ItemPanel nominalInterestRateByLoanCycleMinimumColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    private ItemPanel nominalInterestRateByLoanCycleDefaultColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    private ItemPanel nominalInterestRateByLoanCycleMaximumColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    private ItemPanel nominalInterestRateByLoanCycleCycleColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        Integer value = (Integer) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    private void nominalInterestRateByLoanCycleActionClick(String s, Map<String, Object> stringObjectMap,
            AjaxRequestTarget ajaxRequestTarget) {
        int index = -1;
        for (int i = 0; i < this.nominalInterestRateByLoanCycleValue.size(); i++) {
            Map<String, Object> column = this.nominalInterestRateByLoanCycleValue.get(i);
            if (stringObjectMap.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.nominalInterestRateByLoanCycleValue.remove(index);
        }
        ajaxRequestTarget.add(this.nominalInterestRateByLoanCycleTable);
    }

    private List<ActionItem> nominalInterestRateByLoanCycleActionItem(String s, Map<String, Object> stringObjectMap) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    private void numberOfRepaymentByLoanCycleAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.loanCycleValue = this.numberOfRepaymentByLoanCycleValue;
        this.loanCyclePopup.show(target);
    }

    private ItemPanel numberOfRepaymentByLoanCycleWhenColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(value));
    }

    private ItemPanel numberOfRepaymentByLoanCycleMinimumColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    private ItemPanel numberOfRepaymentByLoanCycleDefaultColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    private ItemPanel numberOfRepaymentByLoanCycleMaximumColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    private ItemPanel numberOfRepaymentByLoanCycleCycleColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        Integer value = (Integer) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    private void numberOfRepaymentByLoanCycleActionClick(String s, Map<String, Object> stringObjectMap,
            AjaxRequestTarget ajaxRequestTarget) {
        int index = -1;
        for (int i = 0; i < this.numberOfRepaymentByLoanCycleValue.size(); i++) {
            Map<String, Object> column = this.numberOfRepaymentByLoanCycleValue.get(i);
            if (stringObjectMap.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.numberOfRepaymentByLoanCycleValue.remove(index);
        }
        ajaxRequestTarget.add(this.numberOfRepaymentByLoanCycleTable);
    }

    private List<ActionItem> numberOfRepaymentByLoanCycleActionItem(String s, Map<String, Object> stringObjectMap) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    private void principalByLoanCycleAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.loanCycleValue = this.principalByLoanCycleValue;
        this.loanCyclePopup.show(target);
    }

    private ItemPanel principalByLoanCycleWhenColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(String.valueOf(value)));
    }

    private ItemPanel principalByLoanCycleCycleColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        Integer value = (Integer) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    private ItemPanel principalByLoanCycleMinimumColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    private ItemPanel principalByLoanCycleDefaultColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    private ItemPanel principalByLoanCycleMaximumColumn(String jdbcColumn, IModel<String> display,
            Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        if (value == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(String.valueOf(value)));
        }
    }

    private void principalByLoanCycleActionClick(String s, Map<String, Object> stringObjectMap,
            AjaxRequestTarget ajaxRequestTarget) {
        int index = -1;
        for (int i = 0; i < this.principalByLoanCycleValue.size(); i++) {
            Map<String, Object> column = this.principalByLoanCycleValue.get(i);
            if (stringObjectMap.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.principalByLoanCycleValue.remove(index);
        }
        ajaxRequestTarget.add(this.principalByLoanCycleTable);
    }

    private List<ActionItem> principalByLoanCycleActionItem(String s, Map<String, Object> stringObjectMap) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected void linkedToFloatingInterestRatesFieldUpdate(AjaxRequestTarget target) {
        this.nominalInterestRateContainer.setVisible(
                this.linkedToFloatingInterestRatesValue == null ? true : !this.linkedToFloatingInterestRatesValue);
        this.floatInterestRateContainer.setVisible(!this.nominalInterestRateContainer.isVisible());
        target.add(this.form);
    }

    protected void termVaryBasedOnLoanCycleFieldUpdate(AjaxRequestTarget target) {
        this.principalByLoanCycleContainer
                .setVisible(this.termVaryBasedOnLoanCycleValue == null ? false : this.termVaryBasedOnLoanCycleValue);
        this.numberOfRepaymentByLoanCycleContainer
                .setVisible(this.termVaryBasedOnLoanCycleValue == null ? false : this.termVaryBasedOnLoanCycleValue);
        this.nominalInterestRateByLoanCycleContainer
                .setVisible(this.termVaryBasedOnLoanCycleValue == null ? false : this.termVaryBasedOnLoanCycleValue);
        target.add(this.form);
    }

    private void loanCyclePopupOnClose(String elementId, AjaxRequestTarget target) {
        Map<String, Object> item = Maps.newHashMap();
        item.put("uuid", UUID.randomUUID().toString());
        item.put("when", this.itemWhenValue.getText());
        item.put("cycle", this.itemLoanCycleValue);
        item.put("minimum", this.itemMinimumValue);
        item.put("default", this.itemDefaultValue);
        item.put("maximum", this.itemMaximumValue);
        this.loanCycleValue.add(item);
        this.loanCycleValue = null;
        target.add(this.form);
    }

}
