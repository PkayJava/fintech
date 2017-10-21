package com.angkorteam.fintech.pages.charge;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.ChargeBuilder;
import com.angkorteam.fintech.dto.enums.ChargeCalculation;
import com.angkorteam.fintech.dto.enums.ChargeTime;
import com.angkorteam.fintech.helper.ChargeHelper;
import com.angkorteam.fintech.pages.ProductDashboardPage;
import com.angkorteam.fintech.provider.ChargeCalculationProvider;
import com.angkorteam.fintech.provider.ChargeTimeProvider;
import com.angkorteam.fintech.provider.CurrencyProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DayMonthTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class SavingDepositChargeModifyPage extends Page {

    protected static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/M");

    protected String chargeId;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected String nameValue;
    protected TextField<String> nameField;
    protected TextFeedbackPanel nameFeedback;

    protected CurrencyProvider currencyProvider;
    protected Option currencyValue;
    protected Select2SingleChoice<Option> currencyField;
    protected TextFeedbackPanel currencyFeedback;

    protected ChargeTimeProvider chargeTimeProvider;
    protected Option chargeTimeValue;
    protected Select2SingleChoice<Option> chargeTimeField;
    protected TextFeedbackPanel chargeTimeFeedback;

    protected ChargeCalculationProvider chargeCalculationProvider;
    protected Option chargeCalculationValue;
    protected Select2SingleChoice<Option> chargeCalculationField;
    protected TextFeedbackPanel chargeCalculationFeedback;

    protected WebMarkupContainer dueDateBlock;
    protected WebMarkupContainer dueDateContainer;
    protected Date dueDateValue;
    protected DayMonthTextField dueDateField;
    protected TextFeedbackPanel dueDateFeedback;

    protected WebMarkupContainer repeatEveryBlock;
    protected WebMarkupContainer repeatEveryContainer;
    protected Integer repeatEveryValue;
    protected TextField<Integer> repeatEveryField;
    protected TextFeedbackPanel repeatEveryFeedback;

    protected Double amountValue;
    protected TextField<Double> amountField;
    protected TextFeedbackPanel amountFeedback;

    protected boolean activeValue;
    protected CheckBox activeField;
    protected TextFeedbackPanel activeFeedback;

    protected boolean penaltyValue;
    protected CheckBox penaltyField;
    protected TextFeedbackPanel penaltyFeedback;

    protected SingleChoiceProvider taxGroupProvider;
    protected Option taxGroupValue;
    protected Select2SingleChoice<Option> taxGroupField;
    protected TextFeedbackPanel taxGroupFeedback;

    protected static final List<PageBreadcrumb> BREADCRUMB;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        return Model.ofList(BREADCRUMB);
    }

    static {
        BREADCRUMB = Lists.newArrayList();
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Admin");
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Product");
            breadcrumb.setPage(ProductDashboardPage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Charge");
            breadcrumb.setPage(ChargeBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }

        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Saving & Deposit Charge Modify");
            BREADCRUMB.add(breadcrumb);
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        initData();

        this.form = new Form<>("form");
        this.add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", ChargeBrowsePage.class);
        this.form.add(this.closeLink);

        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameField.setLabel(Model.of("Name"));
        this.nameField.setRequired(true);
        this.nameField.add(new OnChangeAjaxBehavior());
        this.form.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.form.add(this.nameFeedback);

        this.currencyProvider = new CurrencyProvider();
        this.currencyField = new Select2SingleChoice<>("currencyField", 0, new PropertyModel<>(this, "currencyValue"), this.currencyProvider);
        this.currencyField.setLabel(Model.of("Currency"));
        this.currencyField.setRequired(true);
        this.currencyField.add(new OnChangeAjaxBehavior());
        this.form.add(this.currencyField);
        this.currencyFeedback = new TextFeedbackPanel("currencyFeedback", this.currencyField);
        this.form.add(this.currencyFeedback);

        this.chargeTimeProvider = new ChargeTimeProvider();
        this.chargeTimeProvider.setValues(ChargeTime.SpecifiedDueDate, ChargeTime.SavingsActivation, ChargeTime.WithdrawalFee, ChargeTime.AnnualFee, ChargeTime.MonthlyFee, ChargeTime.WeeklyFee, ChargeTime.OverdraftFee, ChargeTime.SavingNoActivityFee);
        this.chargeTimeField = new Select2SingleChoice<>("chargeTimeField", 0, new PropertyModel<>(this, "chargeTimeValue"), this.chargeTimeProvider);
        this.chargeTimeField.setLabel(Model.of("Charge time type"));
        this.chargeTimeField.setRequired(true);
        this.chargeTimeField.add(new OnChangeAjaxBehavior(this::chargeTimeFieldUpdate));
        this.form.add(this.chargeTimeField);
        this.chargeTimeFeedback = new TextFeedbackPanel("chargeTimeFeedback", this.chargeTimeField);
        this.form.add(this.chargeTimeFeedback);

        this.chargeCalculationProvider = new ChargeCalculationProvider();
        this.chargeCalculationProvider.setValues(ChargeCalculation.Flat, ChargeCalculation.ApprovedAmount);
        this.chargeCalculationField = new Select2SingleChoice<>("chargeCalculationField", 0, new PropertyModel<>(this, "chargeCalculationValue"), this.chargeCalculationProvider);
        this.chargeCalculationField.setLabel(Model.of("Charge calculation"));
        this.chargeCalculationField.setRequired(true);
        this.chargeCalculationField.add(new OnChangeAjaxBehavior());
        this.form.add(this.chargeCalculationField);
        this.chargeCalculationFeedback = new TextFeedbackPanel("chargeCalculationFeedback", this.chargeCalculationField);
        this.form.add(this.chargeCalculationFeedback);

        this.dueDateBlock = new WebMarkupContainer("dueDateBlock");
        this.dueDateBlock.setOutputMarkupId(true);
        this.form.add(this.dueDateBlock);
        this.dueDateContainer = new WebMarkupContainer("dueDateContainer");
        this.dueDateBlock.add(this.dueDateContainer);
        this.dueDateField = new DayMonthTextField("dueDateField", new PropertyModel<>(this, "dueDateValue"));
        this.dueDateField.setRequired(true);
        this.dueDateField.setLabel(Model.of("Due date"));
        this.dueDateField.add(new OnChangeAjaxBehavior());
        this.dueDateContainer.add(this.dueDateField);
        this.dueDateFeedback = new TextFeedbackPanel("dueDateFeedback", this.dueDateField);
        this.dueDateContainer.add(this.dueDateFeedback);

        this.repeatEveryBlock = new WebMarkupContainer("repeatEveryBlock");
        this.repeatEveryBlock.setOutputMarkupId(true);
        this.form.add(this.repeatEveryBlock);
        this.repeatEveryContainer = new WebMarkupContainer("repeatEveryContainer");
        this.repeatEveryBlock.add(this.repeatEveryContainer);
        this.repeatEveryField = new TextField<>("repeatEveryField", new PropertyModel<>(this, "repeatEveryValue"));
        this.repeatEveryField.setLabel(Model.of("Repeats every"));
        this.repeatEveryField.setRequired(true);
        this.repeatEveryField.add(new OnChangeAjaxBehavior());
        this.repeatEveryContainer.add(this.repeatEveryField);
        this.repeatEveryFeedback = new TextFeedbackPanel("repeatEveryFeedback", this.repeatEveryField);
        this.repeatEveryContainer.add(this.repeatEveryFeedback);

        this.amountField = new TextField<>("amountField", new PropertyModel<>(this, "amountValue"));
        this.amountField.setRequired(true);
        this.amountField.setLabel(Model.of("Amount"));
        this.amountField.add(new OnChangeAjaxBehavior());
        this.form.add(this.amountField);
        this.amountFeedback = new TextFeedbackPanel("amountFeedback", this.amountField);
        this.form.add(this.amountFeedback);

        this.activeField = new CheckBox("activeField", new PropertyModel<>(this, "activeValue"));
        this.activeField.setRequired(true);
        this.activeField.add(new OnChangeAjaxBehavior());
        this.form.add(this.activeField);
        this.activeFeedback = new TextFeedbackPanel("activeFeedback", this.activeField);
        this.form.add(this.activeFeedback);

        this.penaltyField = new CheckBox("penaltyField", new PropertyModel<>(this, "penaltyValue"));
        this.penaltyField.setRequired(true);
        this.penaltyField.add(new OnChangeAjaxBehavior());
        this.form.add(this.penaltyField);
        this.penaltyFeedback = new TextFeedbackPanel("penaltyFeedback", this.penaltyField);
        this.form.add(this.penaltyFeedback);

        this.taxGroupProvider = new SingleChoiceProvider("m_tax_group", "id", "name");
        this.taxGroupField = new Select2SingleChoice<>("taxGroupField", 0, new PropertyModel<>(this, "taxGroupValue"), this.taxGroupProvider);
        this.taxGroupField.setLabel(Model.of("Tax Group"));
        this.taxGroupField.add(new OnChangeAjaxBehavior());
        this.form.add(this.taxGroupField);
        this.taxGroupFeedback = new TextFeedbackPanel("taxGroupFeedback", this.taxGroupField);
        this.form.add(this.taxGroupFeedback);

        chargeTimeFieldUpdate(null);
    }

    protected void initData() {
        PageParameters parameters = getPageParameters();
        this.chargeId = parameters.get("chargeId").toString("");

        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
        Map<String, Object> chargeObject = jdbcTemplate.queryForMap("select * from m_charge where id = ?", this.chargeId);

        this.nameValue = (String) chargeObject.get("name");

        this.currencyValue = jdbcTemplate.queryForObject("select code id, concat(name,' [', code,']') text from m_organisation_currency where code = ?", Option.MAPPER, chargeObject.get("currency_code"));

        String charge_time_enum = String.valueOf(chargeObject.get("charge_time_enum"));
        this.chargeTimeValue = ChargeTime.optionLiteral(charge_time_enum);

        String charge_calculation_enum = String.valueOf(chargeObject.get("charge_calculation_enum"));
        this.chargeCalculationValue = ChargeCalculation.optionLiteral(charge_calculation_enum);

        try {
            this.dueDateValue = DATE_FORMAT.parse(String.valueOf(chargeObject.get("fee_on_day")) + "/" + String.valueOf(chargeObject.get("fee_on_month")));
        } catch (ParseException e) {
        }

        this.repeatEveryValue = (Integer) chargeObject.get("fee_interval");

        if (chargeObject.get("amount") instanceof BigDecimal) {
            this.amountValue = ((BigDecimal) chargeObject.get("amount")).doubleValue();
        } else if (chargeObject.get("amount") instanceof Double) {
            this.amountValue = (Double) chargeObject.get("amount");
        }

        this.activeValue = (Boolean) chargeObject.get("is_active");

        this.penaltyValue = (Boolean) chargeObject.get("is_penalty");

        this.taxGroupValue = jdbcTemplate.queryForObject("select id, name text from m_tax_group where id = ?", Option.MAPPER, chargeObject.get("tax_group_id"));
    }

    protected boolean chargeTimeFieldUpdate(AjaxRequestTarget target) {
        ChargeTime chargeTime = null;
        if (this.chargeTimeValue != null) {
            chargeTime = ChargeTime.valueOf(this.chargeTimeValue.getId());
        }

        if (target != null) {
            this.dueDateValue = null;
            this.repeatEveryValue = null;
        }

        boolean dueDateVisible = chargeTime == ChargeTime.AnnualFee || chargeTime == ChargeTime.MonthlyFee;
        boolean repeatEveryVisible = chargeTime == ChargeTime.MonthlyFee || chargeTime == ChargeTime.WeeklyFee;
        this.dueDateContainer.setVisible(dueDateVisible);
        this.repeatEveryContainer.setVisible(repeatEveryVisible);

        this.dueDateField.setRequired(dueDateVisible);
        this.repeatEveryField.setRequired(repeatEveryVisible);

        if (target != null) {
            target.add(this.dueDateBlock);
            target.add(this.repeatEveryBlock);
        }
        return false;
    }

    protected void saveButtonSubmit(Button button) {
        ChargeTime chargeTime = null;

        if (this.chargeTimeValue != null) {
            chargeTime = ChargeTime.valueOf(this.chargeTimeValue.getId());
        }

        ChargeBuilder builder = new ChargeBuilder();
        builder.withId(this.chargeId);
        builder.withName(this.nameValue);
        builder.withCurrencyCode(this.currencyValue == null ? null : this.currencyValue.getId());
        builder.withChargeTimeType(chargeTime);
        builder.withChargeCalculationType(this.chargeCalculationValue == null ? null : ChargeCalculation.valueOf(this.chargeCalculationValue.getId()));
        if (chargeTime == ChargeTime.AnnualFee || chargeTime == ChargeTime.MonthlyFee) {
            builder.withFeeOnMonthDay(this.dueDateValue);
        } else {
            builder.withFeeOnMonthDay(null);
        }
        if (chargeTime == ChargeTime.WeeklyFee || chargeTime == ChargeTime.MonthlyFee) {
            builder.withFeeInterval(this.repeatEveryValue);
        } else {
            builder.withFeeInterval(null);
        }
        builder.withAmount(this.amountValue);
        builder.withActive(this.activeValue);
        builder.withPenalty(this.penaltyValue);
        if (this.taxGroupValue != null) {
            builder.withTaxGroupId(this.taxGroupValue.getId());
        } else {
            builder.withTaxGroupId(null);
        }

        JsonNode node = null;
        try {
            node = ChargeHelper.update((Session) getSession(), builder.build());
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }
        setResponsePage(ChargeBrowsePage.class);
    }

}
