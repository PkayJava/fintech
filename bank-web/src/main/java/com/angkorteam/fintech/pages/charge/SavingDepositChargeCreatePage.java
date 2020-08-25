//package com.angkorteam.fintech.pages.charge;
//
//import java.util.Date;
//import java.util.List;
//
//import org.apache.wicket.ajax.AjaxRequestTarget;
//import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
//import org.apache.wicket.markup.html.form.CheckBox;
//import org.apache.wicket.markup.html.form.TextField;
//import org.apache.wicket.markup.html.link.BookmarkablePageLink;
//import org.apache.wicket.model.IModel;
//import org.apache.wicket.model.Model;
//import org.apache.wicket.model.PropertyModel;
//
//import com.angkorteam.fintech.Page;
//import com.angkorteam.fintech.Session;
//import com.angkorteam.fintech.ddl.MTaxGroup;
//import com.angkorteam.fintech.dto.Function;
//import com.angkorteam.fintech.dto.builder.ChargeBuilder;
//import com.angkorteam.fintech.dto.enums.ChargeCalculation;
//import com.angkorteam.fintech.dto.enums.ChargeTime;
//import com.angkorteam.fintech.dto.enums.ChargeType;
//import com.angkorteam.fintech.helper.ChargeHelper;
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.layout.UIBlock;
//import com.angkorteam.fintech.layout.UIContainer;
//import com.angkorteam.fintech.layout.UIRow;
//import com.angkorteam.fintech.pages.ProductDashboardPage;
//import com.angkorteam.fintech.provider.ChargeCalculationProvider;
//import com.angkorteam.fintech.provider.ChargeTimeProvider;
//import com.angkorteam.fintech.provider.CurrencyProvider;
//import com.angkorteam.fintech.provider.SingleChoiceProvider;
//import com.angkorteam.framework.models.PageBreadcrumb;
//import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
//import com.angkorteam.framework.wicket.markup.html.form.Button;
//import com.angkorteam.framework.wicket.markup.html.form.DayMonthTextField;
//import com.angkorteam.framework.wicket.markup.html.form.Form;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
//import com.google.common.collect.Lists;
//
//import io.github.openunirest.http.JsonNode;
//
//@AuthorizeInstantiation(Function.ALL_FUNCTION)
//public class SavingDepositChargeCreatePage extends Page {
//
//    protected Form<Void> form;
//    protected Button saveButton;
//    protected BookmarkablePageLink<Void> closeLink;
//
//    protected UIRow row1;
//
//    protected UIBlock nameBlock;
//    protected UIContainer nameIContainer;
//    protected String nameValue;
//    protected TextField<String> nameField;
//
//    protected UIBlock currencyBlock;
//    protected UIContainer currencyIContainer;
//    protected CurrencyProvider currencyProvider;
//    protected Option currencyValue;
//    protected Select2SingleChoice<Option> currencyField;
//
//    protected UIRow row2;
//
//    protected UIBlock chargeTimeBlock;
//    protected UIContainer chargeTimeIContainer;
//    protected ChargeTimeProvider chargeTimeProvider;
//    protected Option chargeTimeValue;
//    protected Select2SingleChoice<Option> chargeTimeField;
//
//    protected UIBlock chargeCalculationBlock;
//    protected UIContainer chargeCalculationIContainer;
//    protected ChargeCalculationProvider chargeCalculationProvider;
//    protected Option chargeCalculationValue;
//    protected Select2SingleChoice<Option> chargeCalculationField;
//
//    protected UIRow row3;
//
//    protected UIBlock dueDateBlock;
//    protected UIContainer dueDateIContainer;
//    protected Date dueDateValue;
//    protected DayMonthTextField dueDateField;
//
//    protected UIBlock repeatEveryBlock;
//    protected UIContainer repeatEveryIContainer;
//    protected Long repeatEveryValue;
//    protected TextField<Long> repeatEveryField;
//
//    protected UIRow row4;
//
//    protected UIBlock amountBlock;
//    protected UIContainer amountIContainer;
//    protected Double amountValue;
//    protected TextField<Double> amountField;
//
//    protected UIBlock row4Block1;
//
//    protected UIRow row5;
//
//    protected UIBlock activeBlock;
//    protected UIContainer activeIContainer;
//    protected Boolean activeValue;
//    protected CheckBox activeField;
//
//    protected UIBlock penaltyBlock;
//    protected UIContainer penaltyIContainer;
//    protected Boolean penaltyValue;
//    protected CheckBox penaltyField;
//
//    protected UIRow row6;
//
//    protected UIBlock taxGroupBlock;
//    protected UIContainer taxGroupIContainer;
//    protected SingleChoiceProvider taxGroupProvider;
//    protected Option taxGroupValue;
//    protected Select2SingleChoice<Option> taxGroupField;
//
//    protected UIBlock row6Block1;
//
//    @Override
//    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
//        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Admin");
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Product");
//            breadcrumb.setPage(ProductDashboardPage.class);
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Charge");
//            breadcrumb.setPage(ChargeBrowsePage.class);
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Saving & Deposit Charge Create");
//            BREADCRUMB.add(breadcrumb);
//        }
//        return Model.ofList(BREADCRUMB);
//    }
//
//    @Override
//    protected void initComponent() {
//        this.form = new Form<>("form");
//        this.add(this.form);
//
//        this.saveButton = new Button("saveButton");
//        this.saveButton.setOnSubmit(this::saveButtonSubmit);
//        this.form.add(this.saveButton);
//
//        this.closeLink = new BookmarkablePageLink<>("closeLink", ChargeBrowsePage.class);
//        this.form.add(this.closeLink);
//
//        this.row1 = UIRow.newUIRow("row1", this.form);
//
//        this.nameBlock = this.row1.newUIBlock("nameBlock", Size.Six_6);
//        this.nameIContainer = this.nameBlock.newUIContainer("nameIContainer");
//        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
//        this.nameIContainer.add(this.nameField);
//        this.nameIContainer.newFeedback("nameFeedback", this.nameField);
//
//        this.currencyBlock = this.row1.newUIBlock("currencyBlock", Size.Six_6);
//        this.currencyIContainer = this.currencyBlock.newUIContainer("currencyIContainer");
//        this.currencyField = new Select2SingleChoice<>("currencyField", new PropertyModel<>(this, "currencyValue"), this.currencyProvider);
//        this.currencyIContainer.add(this.currencyField);
//        this.currencyIContainer.newFeedback("currencyFeedback", this.currencyField);
//
//        this.row2 = UIRow.newUIRow("row2", this.form);
//
//        this.chargeTimeBlock = this.row2.newUIBlock("chargeTimeBlock", Size.Six_6);
//        this.chargeTimeIContainer = this.chargeTimeBlock.newUIContainer("chargeTimeIContainer");
//        this.chargeTimeField = new Select2SingleChoice<>("chargeTimeField", new PropertyModel<>(this, "chargeTimeValue"), this.chargeTimeProvider);
//        this.chargeTimeIContainer.add(this.chargeTimeField);
//        this.chargeTimeIContainer.newFeedback("chargeTimeFeedback", this.chargeTimeField);
//
//        this.chargeCalculationBlock = this.row2.newUIBlock("chargeCalculationBlock", Size.Six_6);
//        this.chargeCalculationIContainer = this.chargeCalculationBlock.newUIContainer("chargeCalculationIContainer");
//        this.chargeCalculationField = new Select2SingleChoice<>("chargeCalculationField", new PropertyModel<>(this, "chargeCalculationValue"), this.chargeCalculationProvider);
//        this.chargeCalculationIContainer.add(this.chargeCalculationField);
//        this.chargeCalculationIContainer.newFeedback("chargeCalculationFeedback", this.chargeCalculationField);
//
//        this.row3 = UIRow.newUIRow("row3", this.form);
//
//        this.dueDateBlock = this.row3.newUIBlock("dueDateBlock", Size.Six_6);
//        this.dueDateIContainer = this.dueDateBlock.newUIContainer("dueDateIContainer");
//        this.dueDateField = new DayMonthTextField("dueDateField", new PropertyModel<>(this, "dueDateValue"));
//        this.dueDateIContainer.add(this.dueDateField);
//        this.dueDateIContainer.newFeedback("dueDateFeedback", this.dueDateField);
//
//        this.repeatEveryBlock = this.row3.newUIBlock("repeatEveryBlock", Size.Six_6);
//        this.repeatEveryIContainer = this.repeatEveryBlock.newUIContainer("repeatEveryIContainer");
//        this.repeatEveryField = new TextField<>("repeatEveryField", new PropertyModel<>(this, "repeatEveryValue"));
//        this.repeatEveryIContainer.add(this.repeatEveryField);
//        this.repeatEveryIContainer.newFeedback("repeatEveryFeedback", this.repeatEveryField);
//
//        this.row4 = UIRow.newUIRow("row4", this.form);
//
//        this.amountBlock = this.row4.newUIBlock("amountBlock", Size.Six_6);
//        this.amountIContainer = this.amountBlock.newUIContainer("amountIContainer");
//        this.amountField = new TextField<>("amountField", new PropertyModel<>(this, "amountValue"));
//        this.amountIContainer.add(this.amountField);
//        this.amountIContainer.newFeedback("amountFeedback", this.amountField);
//
//        this.row4Block1 = this.row4.newUIBlock("row4Block1", Size.Six_6);
//
//        this.row5 = UIRow.newUIRow("row5", this.form);
//
//        this.activeBlock = this.row5.newUIBlock("activeBlock", Size.Six_6);
//        this.activeIContainer = this.activeBlock.newUIContainer("activeIContainer");
//        this.activeField = new CheckBox("activeField", new PropertyModel<>(this, "activeValue"));
//        this.activeIContainer.add(this.activeField);
//        this.activeIContainer.newFeedback("activeFeedback", this.activeField);
//
//        this.penaltyBlock = this.row5.newUIBlock("penaltyBlock", Size.Six_6);
//        this.penaltyIContainer = this.penaltyBlock.newUIContainer("penaltyIContainer");
//        this.penaltyField = new CheckBox("penaltyField", new PropertyModel<>(this, "penaltyValue"));
//        this.penaltyIContainer.add(this.penaltyField);
//        this.penaltyIContainer.newFeedback("penaltyFeedback", this.penaltyField);
//
//        this.row6 = UIRow.newUIRow("row6", this.form);
//
//        this.taxGroupBlock = this.row6.newUIBlock("taxGroupBlock", Size.Six_6);
//        this.taxGroupIContainer = this.taxGroupBlock.newUIContainer("taxGroupIContainer");
//        this.taxGroupField = new Select2SingleChoice<>("taxGroupField", new PropertyModel<>(this, "taxGroupValue"), this.taxGroupProvider);
//        this.taxGroupIContainer.add(this.taxGroupField);
//        this.taxGroupIContainer.newFeedback("taxGroupFeedback", this.taxGroupField);
//
//        this.row6Block1 = this.row6.newUIBlock("row6Block1", Size.Six_6);
//
//    }
//
//    @Override
//    protected void initData() {
//        this.taxGroupProvider = new SingleChoiceProvider(MTaxGroup.NAME, MTaxGroup.Field.ID, MTaxGroup.Field.NAME);
//
//        this.chargeCalculationProvider = new ChargeCalculationProvider();
//        this.chargeCalculationProvider.setValues(ChargeCalculation.Flat, ChargeCalculation.ApprovedAmount);
//
//        this.chargeTimeProvider = new ChargeTimeProvider();
//        this.chargeTimeProvider.setValues(ChargeTime.SpecifiedDueDate, ChargeTime.SavingsActivation, ChargeTime.WithdrawalFee, ChargeTime.AnnualFee, ChargeTime.MonthlyFee, ChargeTime.WeeklyFee, ChargeTime.OverdraftFee, ChargeTime.SavingNoActivityFee);
//
//        this.currencyProvider = new CurrencyProvider();
//    }
//
//    @Override
//    protected void configureMetaData() {
//        this.taxGroupField.setLabel(Model.of("Tax Group"));
//        this.taxGroupField.add(new OnChangeAjaxBehavior());
//
//        this.penaltyField.setRequired(true);
//        this.penaltyField.add(new OnChangeAjaxBehavior());
//
//        this.activeField.setRequired(true);
//        this.activeField.add(new OnChangeAjaxBehavior());
//
//        this.amountField.setRequired(true);
//        this.amountField.setLabel(Model.of("Amount"));
//        this.amountField.add(new OnChangeAjaxBehavior());
//
//        this.repeatEveryField.setLabel(Model.of("Repeats every"));
//        this.repeatEveryField.setRequired(true);
//        this.repeatEveryField.add(new OnChangeAjaxBehavior());
//
//        this.dueDateField.setRequired(true);
//        this.dueDateField.setLabel(Model.of("Due date"));
//        this.dueDateField.add(new OnChangeAjaxBehavior());
//
//        this.chargeCalculationField.setLabel(Model.of("Charge calculation"));
//        this.chargeCalculationField.setRequired(true);
//        this.chargeCalculationField.add(new OnChangeAjaxBehavior());
//
//        this.chargeTimeField.setLabel(Model.of("Charge time type"));
//        this.chargeTimeField.setRequired(true);
//        this.chargeTimeField.add(new OnChangeAjaxBehavior(this::chargeTimeFieldUpdate));
//
//        this.currencyField.setLabel(Model.of("Currency"));
//        this.currencyField.setRequired(true);
//        this.currencyField.add(new OnChangeAjaxBehavior());
//
//        this.nameField.setLabel(Model.of("Name"));
//        this.nameField.setRequired(true);
//        this.nameField.add(new OnChangeAjaxBehavior());
//
//        chargeTimeFieldUpdate(null);
//    }
//
//    protected boolean chargeTimeFieldUpdate(AjaxRequestTarget target) {
//        ChargeTime chargeTime = null;
//        if (this.chargeTimeValue != null) {
//            chargeTime = ChargeTime.valueOf(this.chargeTimeValue.getId());
//        }
//
//        boolean dueDateVisible = chargeTime == ChargeTime.AnnualFee || chargeTime == ChargeTime.MonthlyFee;
//        boolean repeatEveryVisible = chargeTime == ChargeTime.MonthlyFee || chargeTime == ChargeTime.WeeklyFee;
//        this.dueDateIContainer.setVisible(dueDateVisible);
//        this.repeatEveryIContainer.setVisible(repeatEveryVisible);
//
//        this.dueDateField.setRequired(dueDateVisible);
//        this.repeatEveryField.setRequired(repeatEveryVisible);
//
//        if (target != null) {
//            target.add(this.dueDateBlock);
//            target.add(this.repeatEveryBlock);
//        }
//        return false;
//    }
//
//    protected void saveButtonSubmit(Button button) {
//
//        ChargeTime chargeTime = null;
//
//        if (this.chargeTimeValue != null) {
//            chargeTime = ChargeTime.valueOf(this.chargeTimeValue.getId());
//        }
//
//        ChargeBuilder builder = new ChargeBuilder();
//        builder.withChargeAppliesTo(ChargeType.SavingDeposit);
//        builder.withName(this.nameValue);
//
//        if (this.currencyValue != null) {
//            builder.withCurrencyCode(this.currencyValue.getId());
//        }
//
//        if (this.chargeTimeValue != null) {
//            builder.withChargeTimeType(chargeTime);
//        }
//
//        if (this.chargeCalculationValue != null) {
//            builder.withChargeCalculationType(ChargeCalculation.valueOf(this.chargeCalculationValue.getId()));
//        }
//        if (chargeTime == ChargeTime.AnnualFee || chargeTime == ChargeTime.MonthlyFee) {
//            builder.withFeeOnMonthDay(this.dueDateValue);
//        }
//        if (chargeTime == ChargeTime.WeeklyFee || chargeTime == ChargeTime.MonthlyFee) {
//            builder.withFeeOnMonthDay(this.dueDateValue);
//            builder.withFeeInterval(this.repeatEveryValue);
//        }
//        builder.withAmount(this.amountValue);
//        builder.withActive(this.activeValue);
//        builder.withPenalty(this.penaltyValue);
//        if (this.taxGroupValue != null) {
//            builder.withTaxGroupId(this.taxGroupValue.getId());
//        }
//
//        JsonNode node = ChargeHelper.create((Session) getSession(), builder.build());
//
//        if (reportError(node)) {
//            return;
//        }
//        setResponsePage(ChargeBrowsePage.class);
//    }
//
//}
