package com.angkorteam.fintech.pages.charge;

import java.util.Date;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.ChargeCalculation;
import com.angkorteam.fintech.dto.ChargeTime;
import com.angkorteam.fintech.dto.ChargeType;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.request.ChargeBuilder;
import com.angkorteam.fintech.helper.ChargeHelper;
import com.angkorteam.fintech.pages.ProductDashboardPage;
import com.angkorteam.fintech.provider.ChargeCalculationProvider;
import com.angkorteam.fintech.provider.ChargeTimeProvider;
import com.angkorteam.fintech.provider.CurrencyProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.models.PageBreadcrumb;
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
public class SavingDepositChargeCreatePage extends Page {

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeLink;

    private String nameValue;
    private TextField<String> nameField;
    private TextFeedbackPanel nameFeedback;

    private CurrencyProvider currencyProvider;
    private Option currencyValue;
    private Select2SingleChoice<Option> currencyField;
    private TextFeedbackPanel currencyFeedback;

    private ChargeTimeProvider chargeTimeProvider;
    private Option chargeTimeValue;
    private Select2SingleChoice<Option> chargeTimeField;
    private TextFeedbackPanel chargeTimeFeedback;

    private ChargeCalculationProvider chargeCalculationProvider;
    private Option chargeCalculationValue;
    private Select2SingleChoice<Option> chargeCalculationField;
    private TextFeedbackPanel chargeCalculationFeedback;

    private WebMarkupContainer dueDateBlock;
    private WebMarkupContainer dueDateContainer;
    private Date dueDateValue;
    private DayMonthTextField dueDateField;
    private TextFeedbackPanel dueDateFeedback;

    private WebMarkupContainer repeatEveryBlock;
    private WebMarkupContainer repeatEveryContainer;
    private Integer repeatEveryValue;
    private TextField<Integer> repeatEveryField;
    private TextFeedbackPanel repeatEveryFeedback;

    private Double amountValue;
    private TextField<Double> amountField;
    private TextFeedbackPanel amountFeedback;

    private boolean activeValue;
    private CheckBox activeField;
    private TextFeedbackPanel activeFeedback;

    private boolean penaltyValue;
    private CheckBox penaltyField;
    private TextFeedbackPanel penaltyFeedback;

    private SingleChoiceProvider taxGroupProvider;
    private Option taxGroupValue;
    private Select2SingleChoice<Option> taxGroupField;
    private TextFeedbackPanel taxGroupFeedback;

    private static final List<PageBreadcrumb> BREADCRUMB;

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
            breadcrumb.setLabel("Saving & Deposit Charge Create");
            BREADCRUMB.add(breadcrumb);
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

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

    protected boolean chargeTimeFieldUpdate(AjaxRequestTarget target) {
        ChargeTime chargeTime = null;
        if (this.chargeTimeValue != null) {
            chargeTime = ChargeTime.valueOf(this.chargeTimeValue.getId());
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
        builder.withChargeAppliesTo(ChargeType.SavingDeposit);
        builder.withName(this.nameValue);

        if (this.currencyValue != null) {
            builder.withCurrencyCode(this.currencyValue.getId());
        }

        if (this.chargeTimeValue != null) {
            builder.withChargeTimeType(chargeTime);
        }

        if (this.chargeCalculationValue != null) {
            builder.withChargeCalculationType(ChargeCalculation.valueOf(this.chargeCalculationValue.getId()));
        }
        if (chargeTime == ChargeTime.AnnualFee || chargeTime == ChargeTime.MonthlyFee) {
            builder.withFeeOnMonthDay(this.dueDateValue);
        }
        if (chargeTime == ChargeTime.WeeklyFee || chargeTime == ChargeTime.MonthlyFee) {
            builder.withFeeOnMonthDay(this.dueDateValue);
            builder.withFeeInterval(this.repeatEveryValue);
        }
        builder.withAmount(this.amountValue);
        builder.withActive(this.activeValue);
        builder.withPenalty(this.penaltyValue);
        if (this.taxGroupValue != null) {
            builder.withTaxGroupId(this.taxGroupValue.getId());
        }

        JsonNode node = null;
        try {
            node = ChargeHelper.create((Session) getSession(), builder.build());
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
