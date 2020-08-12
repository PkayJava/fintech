//package com.angkorteam.fintech.widget.client.common.loan;
//
//import java.util.Date;
//
//import org.apache.wicket.Page;
//import org.apache.wicket.ajax.AjaxRequestTarget;
//import org.apache.wicket.markup.html.form.CheckBox;
//import org.apache.wicket.markup.html.form.TextField;
//import org.apache.wicket.markup.html.link.BookmarkablePageLink;
//import org.apache.wicket.model.PropertyModel;
//import org.apache.wicket.request.mapper.parameter.PageParameters;
//
//import com.angkorteam.fintech.dto.ClientEnum;
//import com.angkorteam.fintech.dto.enums.ChargeFrequency;
//import com.angkorteam.fintech.dto.enums.loan.InterestCalculationPeriod;
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.layout.UIBlock;
//import com.angkorteam.fintech.layout.UIContainer;
//import com.angkorteam.fintech.layout.UIRow;
//import com.angkorteam.fintech.pages.client.center.CenterPreviewPage;
//import com.angkorteam.fintech.pages.client.client.ClientPreviewPage;
//import com.angkorteam.fintech.pages.client.common.saving.AccountCreatePage;
//import com.angkorteam.fintech.pages.client.group.GroupPreviewPage;
//import com.angkorteam.fintech.provider.ChargeFrequencyProvider;
//import com.angkorteam.fintech.provider.SingleChoiceProvider;
//import com.angkorteam.fintech.provider.loan.AmortizationProvider;
//import com.angkorteam.fintech.provider.loan.FrequencyDayProvider;
//import com.angkorteam.fintech.provider.loan.InterestCalculationPeriodProvider;
//import com.angkorteam.fintech.provider.loan.InterestMethodProvider;
//import com.angkorteam.fintech.provider.loan.RepaidOnProvider;
//import com.angkorteam.fintech.provider.loan.RepaymentStrategyProvider;
//import com.angkorteam.fintech.widget.Panel;
//import com.angkorteam.fintech.widget.ReadOnlyView;
//import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
//import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
//import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;
//import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;
//import com.angkorteam.framework.wicket.markup.html.form.Button;
//import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
//import com.angkorteam.framework.wicket.markup.html.form.Form;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
//
//public class TermsPanel extends Panel {
//
//    protected Page itemPage;
//    protected PropertyModel<AjaxTabbedPanel<ITab>> tab;
//    protected PropertyModel<Boolean> errorTerm;
//
//    protected ClientEnum client;
//    protected String clientId;
//
//    protected Form<Void> form;
//    protected Button nextButton;
//    protected AjaxLink<Void> backLink;
//    protected BookmarkablePageLink<Void> closeLink;
//
//    protected UIRow row1;
//
//    protected UIBlock termPrincipalBlock;
//    protected UIContainer termPrincipalIContainer;
//    protected TextField<Double> termPrincipalField;
//    protected PropertyModel<Double> termPrincipalValue;
//
//    protected UIBlock termLoanTermBlock;
//    protected UIContainer termLoanTermIContainer;
//    protected TextField<Long> termLoanTermField;
//    protected PropertyModel<Long> termLoanTermValue;
//
//    protected ChargeFrequencyProvider termLoanTermTypeProvider;
//    protected UIBlock termLoanTermTypeBlock;
//    protected UIContainer termLoanTermTypeIContainer;
//    protected Select2SingleChoice<Option> termLoanTermTypeField;
//    protected PropertyModel<Option> termLoanTermTypeValue;
//
//    protected UIRow row2;
//
//    protected UIBlock termNumberOfRepaymentBlock;
//    protected UIContainer termNumberOfRepaymentIContainer;
//    protected TextField<Long> termNumberOfRepaymentField;
//    protected PropertyModel<Long> termNumberOfRepaymentValue;
//
//    protected UIBlock termRepaidEveryBlock;
//    protected UIContainer termRepaidEveryIContainer;
//    protected TextField<Long> termRepaidEveryField;
//    protected PropertyModel<Long> termRepaidEveryValue;
//
//    protected ChargeFrequencyProvider termRepaidTypeProvider;
//    protected UIBlock termRepaidTypeBlock;
//    protected UIContainer termRepaidTypeIContainer;
//    protected Select2SingleChoice<Option> termRepaidTypeField;
//    protected PropertyModel<Option> termRepaidTypeValue;
//
//    protected RepaidOnProvider termRepaidOnProvider;
//    protected UIBlock termRepaidOnBlock;
//    protected UIContainer termRepaidOnIContainer;
//    protected Select2SingleChoice<Option> termRepaidOnField;
//    protected PropertyModel<Option> termRepaidOnValue;
//
//    protected FrequencyDayProvider termRepaidDayProvider;
//    protected UIBlock termRepaidDayBlock;
//    protected UIContainer termRepaidDayIContainer;
//    protected Select2SingleChoice<Option> termRepaidDayField;
//    protected PropertyModel<Option> termRepaidDayValue;
//
//    protected UIRow row3;
//
//    protected UIBlock termFirstRepaymentOnBlock;
//    protected UIContainer termFirstRepaymentOnIContainer;
//    protected DateTextField termFirstRepaymentOnField;
//    protected PropertyModel<Date> termFirstRepaymentOnValue;
//
//    protected UIBlock termInterestChargedFromBlock;
//    protected UIContainer termInterestChargedFromIContainer;
//    protected DateTextField termInterestChargedFromField;
//    protected PropertyModel<Date> termInterestChargedFromValue;
//
//    protected UIRow row4;
//
//    protected UIBlock termNominalInterestRateBlock;
//    protected UIContainer termNominalInterestRateIContainer;
//    protected TextField<Double> termNominalInterestRateField;
//    protected PropertyModel<Double> termNominalInterestRateValue;
//
//    protected UIBlock termNominalInterestTypeBlock;
//    protected UIContainer termNominalInterestTypeVContainer;
//    protected ReadOnlyView termNominalInterestTypeView;
//    protected PropertyModel<Option> termNominalInterestTypeValue;
//
//    protected InterestMethodProvider termInterestMethodProvider;
//    protected UIBlock termInterestMethodBlock;
//    protected UIContainer termInterestMethodIContainer;
//    protected Select2SingleChoice<Option> termInterestMethodField;
//    protected PropertyModel<Option> termInterestMethodValue;
//
//    protected UIBlock termEqualAmortizationBlock;
//    protected UIContainer termEqualAmortizationIContainer;
//    protected CheckBox termEqualAmortizationField;
//    protected PropertyModel<Boolean> termEqualAmortizationValue;
//
//    protected UIRow row5;
//
//    protected AmortizationProvider termAmortizationProvider;
//    protected UIBlock termAmortizationBlock;
//    protected UIContainer termAmortizationIContainer;
//    protected Select2SingleChoice<Option> termAmortizationField;
//    protected PropertyModel<Option> termAmortizationValue;
//
//    protected UIBlock row5Block1;
//
//    protected UIRow row6;
//
//    protected InterestCalculationPeriodProvider termInterestCalculationPeriodProvider;
//    protected UIBlock termInterestCalculationPeriodBlock;
//    protected UIContainer termInterestCalculationPeriodIContainer;
//    protected Select2SingleChoice<Option> termInterestCalculationPeriodField;
//    protected PropertyModel<Option> termInterestCalculationPeriodValue;
//
//    protected UIBlock termCalculateInterestForExactDayInPartialPeriodBlock;
//    protected UIContainer termCalculateInterestForExactDayInPartialPeriodIContainer;
//    protected CheckBox termCalculateInterestForExactDayInPartialPeriodField;
//    protected PropertyModel<Boolean> termCalculateInterestForExactDayInPartialPeriodValue;
//
//    protected UIRow row7;
//
//    protected UIBlock termArrearsToleranceBlock;
//    protected UIContainer termArrearsToleranceIContainer;
//    protected TextField<Double> termArrearsToleranceField;
//    protected PropertyModel<Double> termArrearsToleranceValue;
//
//    protected UIBlock termInterestFreePeriodBlock;
//    protected UIContainer termInterestFreePeriodIContainer;
//    protected TextField<Long> termInterestFreePeriodField;
//    protected PropertyModel<Long> termInterestFreePeriodValue;
//
//    protected UIRow row8;
//
//    protected RepaymentStrategyProvider termRepaymentStrategyProvider;
//    protected UIBlock termRepaymentStrategyBlock;
//    protected UIContainer termRepaymentStrategyIContainer;
//    protected Select2SingleChoice<Option> termRepaymentStrategyField;
//    protected PropertyModel<Option> termRepaymentStrategyValue;
//
//    protected UIBlock termPrincipalPaymentBlock;
//    protected UIContainer termPrincipalPaymentIContainer;
//    protected TextField<Long> termPrincipalPaymentField;
//    protected PropertyModel<Long> termPrincipalPaymentValue;
//
//    protected UIBlock termInterestPaymentBlock;
//    protected UIContainer termInterestPaymentIContainer;
//    protected TextField<Long> termInterestPaymentField;
//    protected PropertyModel<Long> termInterestPaymentValue;
//
//    protected UIBlock termArrearsAgingBlock;
//    protected UIContainer termArrearsAgingIContainer;
//    protected TextField<Long> termArrearsAgingField;
//    protected PropertyModel<Long> termArrearsAgingValue;
//
//    protected UIRow row9;
//
//    protected UIBlock termInstallmentAmountBlock;
//    protected UIContainer termInstallmentAmountIContainer;
//    protected TextField<Double> termInstallmentAmountField;
//    protected PropertyModel<Double> termInstallmentAmountValue;
//
//    protected UIBlock row9Block1;
//
//    protected UIRow row10;
//
//    protected UIBlock termTopupLoanBlock;
//    protected UIContainer termTopupLoanIContainer;
//    protected CheckBox termTopupLoanField;
//    protected PropertyModel<Boolean> termTopupLoanValue;
//
//    protected ChargeFrequencyProvider termLoanToCloseProvider;
//    protected UIBlock termLoanToCloseBlock;
//    protected UIContainer termLoanToCloseIContainer;
//    protected Select2SingleChoice<Option> termLoanToCloseField;
//    protected PropertyModel<Option> termLoanToCloseValue;
//
//    public TermsPanel(String id, Page itemPage) {
//        super(id);
//        this.itemPage = itemPage;
//    }
//
//    @Override
//    protected void initData() {
//        this.tab = new PropertyModel<>(this.itemPage, "tab");
//        this.errorTerm = new PropertyModel<>(this.itemPage, "errorTerm");
//
//        this.client = new PropertyModel<ClientEnum>(this.itemPage, "client").getObject();
//        this.clientId = new PropertyModel<String>(this.itemPage, "clientId").getObject();
//
//        // TODO : TO BE CHECK
//        this.termLoanToCloseProvider = new ChargeFrequencyProvider();
//
//        this.termLoanTermTypeProvider = new ChargeFrequencyProvider();
//        this.termRepaidTypeProvider = new ChargeFrequencyProvider();
//        this.termRepaidOnProvider = new RepaidOnProvider();
//        this.termRepaidDayProvider = new FrequencyDayProvider();
//        this.termInterestMethodProvider = new InterestMethodProvider();
//        this.termAmortizationProvider = new AmortizationProvider();
//        this.termInterestCalculationPeriodProvider = new InterestCalculationPeriodProvider();
//        this.termRepaymentStrategyProvider = new RepaymentStrategyProvider();
//
//        this.termPrincipalValue = new PropertyModel<>(this.itemPage, "termPrincipalValue");
//        this.termLoanTermValue = new PropertyModel<>(this.itemPage, "termLoanTermValue");
//        this.termLoanTermTypeValue = new PropertyModel<>(this.itemPage, "termLoanTermTypeValue");
//        this.termNumberOfRepaymentValue = new PropertyModel<>(this.itemPage, "termNumberOfRepaymentValue");
//        this.termRepaidEveryValue = new PropertyModel<>(this.itemPage, "termRepaidEveryValue");
//        this.termRepaidTypeValue = new PropertyModel<>(this.itemPage, "termRepaidTypeValue");
//        this.termRepaidOnValue = new PropertyModel<>(this.itemPage, "termRepaidOnValue");
//        this.termRepaidDayValue = new PropertyModel<>(this.itemPage, "termRepaidDayValue");
//        this.termFirstRepaymentOnValue = new PropertyModel<>(this.itemPage, "termFirstRepaymentOnValue");
//        this.termInterestChargedFromValue = new PropertyModel<>(this.itemPage, "termInterestChargedFromValue");
//        this.termNominalInterestRateValue = new PropertyModel<>(this.itemPage, "termNominalInterestRateValue");
//        this.termNominalInterestTypeValue = new PropertyModel<>(this.itemPage, "termNominalInterestTypeValue");
//        this.termInterestMethodValue = new PropertyModel<>(this.itemPage, "termInterestMethodValue");
//        this.termEqualAmortizationValue = new PropertyModel<>(this.itemPage, "termEqualAmortizationValue");
//        this.termAmortizationValue = new PropertyModel<>(this.itemPage, "termAmortizationValue");
//        this.termInterestCalculationPeriodValue = new PropertyModel<>(this.itemPage, "termInterestCalculationPeriodValue");
//        this.termCalculateInterestForExactDayInPartialPeriodValue = new PropertyModel<>(this.itemPage, "termCalculateInterestForExactDayInPartialPeriodValue");
//        this.termArrearsToleranceValue = new PropertyModel<>(this.itemPage, "termArrearsToleranceValue");
//        this.termInterestFreePeriodValue = new PropertyModel<>(this.itemPage, "termInterestFreePeriodValue");
//        this.termRepaymentStrategyValue = new PropertyModel<>(this.itemPage, "termRepaymentStrategyValue");
//        this.termPrincipalPaymentValue = new PropertyModel<>(this.itemPage, "termPrincipalPaymentValue");
//        this.termInterestPaymentValue = new PropertyModel<>(this.itemPage, "termInterestPaymentValue");
//        this.termArrearsAgingValue = new PropertyModel<>(this.itemPage, "termArrearsAgingValue");
//        this.termInstallmentAmountValue = new PropertyModel<>(this.itemPage, "termInstallmentAmountValue");
//        this.termTopupLoanValue = new PropertyModel<>(this.itemPage, "termTopupLoanValue");
//        this.termLoanToCloseValue = new PropertyModel<>(this.itemPage, "termLoanToCloseValue");
//    }
//
//    @Override
//    protected void initComponent() {
//        this.form = new Form<>("form");
//        add(this.form);
//
//        this.nextButton = new Button("nextButton");
//        this.nextButton.setOnSubmit(this::nextButtonSubmit);
//        this.nextButton.setOnError(this::nextButtonError);
//        this.form.add(this.nextButton);
//
//        this.backLink = new AjaxLink<>("backLink");
//        this.backLink.setOnClick(this::backLinkClick);
//        this.form.add(this.backLink);
//
//        PageParameters parameters = new PageParameters();
//        parameters.add("clientId", this.clientId);
//        if (this.client == ClientEnum.Client) {
//            this.closeLink = new BookmarkablePageLink<>("closeLink", ClientPreviewPage.class, parameters);
//        } else if (this.client == ClientEnum.Group) {
//            this.closeLink = new BookmarkablePageLink<>("closeLink", GroupPreviewPage.class, parameters);
//        } else if (this.client == ClientEnum.Center) {
//            this.closeLink = new BookmarkablePageLink<>("closeLink", CenterPreviewPage.class, parameters);
//        }
//        this.form.add(this.closeLink);
//
//        this.row1 = UIRow.newUIRow("row1", this.form);
//
//        this.termPrincipalBlock = this.row1.newUIBlock("termPrincipalBlock", Size.Four_4);
//        this.termPrincipalIContainer = this.termPrincipalBlock.newUIContainer("termPrincipalIContainer");
//        this.termPrincipalField = new TextField<>("termPrincipalField", this.termPrincipalValue);
//        this.termPrincipalIContainer.add(this.termPrincipalField);
//        this.termPrincipalIContainer.newFeedback("termPrincipalFeedback", this.termPrincipalField);
//
//        this.termLoanTermBlock = this.row1.newUIBlock("termLoanTermBlock", Size.Four_4);
//        this.termLoanTermIContainer = this.termLoanTermBlock.newUIContainer("termLoanTermIContainer");
//        this.termLoanTermField = new TextField<>("termLoanTermField", this.termLoanTermValue);
//        this.termLoanTermIContainer.add(this.termLoanTermField);
//        this.termLoanTermIContainer.newFeedback("termLoanTermFeedback", this.termLoanTermField);
//
//        this.termLoanTermTypeBlock = this.row1.newUIBlock("termLoanTermTypeBlock", Size.Four_4);
//        this.termLoanTermTypeIContainer = this.termLoanTermTypeBlock.newUIContainer("termLoanTermTypeIContainer");
//        this.termLoanTermTypeField = new Select2SingleChoice<>("termLoanTermTypeField", this.termLoanTermTypeValue, this.termLoanTermTypeProvider);
//        this.termLoanTermTypeIContainer.add(this.termLoanTermTypeField);
//        this.termLoanTermTypeIContainer.newFeedback("termLoanTermTypeFeedback", this.termLoanTermTypeField);
//
//        this.row2 = UIRow.newUIRow("row2", this.form);
//
//        this.termNumberOfRepaymentBlock = this.row2.newUIBlock("termNumberOfRepaymentBlock", Size.Four_4);
//        this.termNumberOfRepaymentIContainer = this.termNumberOfRepaymentBlock.newUIContainer("termNumberOfRepaymentIContainer");
//        this.termNumberOfRepaymentField = new TextField<>("termNumberOfRepaymentField", this.termNumberOfRepaymentValue);
//        this.termNumberOfRepaymentIContainer.add(this.termNumberOfRepaymentField);
//        this.termNumberOfRepaymentIContainer.newFeedback("termNumberOfRepaymentFeedback", this.termNumberOfRepaymentField);
//
//        this.termRepaidEveryBlock = this.row2.newUIBlock("termRepaidEveryBlock", Size.Two_2);
//        this.termRepaidEveryIContainer = this.termRepaidEveryBlock.newUIContainer("termRepaidEveryIContainer");
//        this.termRepaidEveryField = new TextField<>("termRepaidEveryField", this.termRepaidEveryValue);
//        this.termRepaidEveryIContainer.add(this.termRepaidEveryField);
//        this.termRepaidEveryIContainer.newFeedback("termRepaidEveryFeedback", this.termRepaidEveryField);
//
//        this.termRepaidTypeBlock = this.row2.newUIBlock("termRepaidTypeBlock", Size.Two_2);
//        this.termRepaidTypeIContainer = this.termRepaidTypeBlock.newUIContainer("termRepaidTypeIContainer");
//        this.termRepaidTypeField = new Select2SingleChoice<>("termRepaidTypeField", this.termRepaidTypeValue, this.termRepaidTypeProvider);
//        this.termRepaidTypeIContainer.add(this.termRepaidTypeField);
//        this.termRepaidTypeIContainer.newFeedback("termRepaidTypeFeedback", this.termRepaidTypeField);
//
//        this.termRepaidOnBlock = this.row2.newUIBlock("termRepaidOnBlock", Size.Two_2);
//        this.termRepaidOnIContainer = this.termRepaidOnBlock.newUIContainer("termRepaidOnIContainer");
//        this.termRepaidOnField = new Select2SingleChoice<>("termRepaidOnField", this.termRepaidOnValue, this.termRepaidOnProvider);
//        this.termRepaidOnIContainer.add(this.termRepaidOnField);
//        this.termRepaidOnIContainer.newFeedback("termRepaidOnFeedback", this.termRepaidOnField);
//
//        this.termRepaidDayBlock = this.row2.newUIBlock("termRepaidDayBlock", Size.Two_2);
//        this.termRepaidDayIContainer = this.termRepaidDayBlock.newUIContainer("termRepaidDayIContainer");
//        this.termRepaidDayField = new Select2SingleChoice<>("termRepaidDayField", this.termRepaidDayValue, this.termRepaidDayProvider);
//        this.termRepaidDayIContainer.add(this.termRepaidDayField);
//        this.termRepaidDayIContainer.newFeedback("termRepaidDayFeedback", this.termRepaidDayField);
//
//        this.row3 = UIRow.newUIRow("row3", this.form);
//
//        this.termFirstRepaymentOnBlock = this.row3.newUIBlock("termFirstRepaymentOnBlock", Size.Six_6);
//        this.termFirstRepaymentOnIContainer = this.termFirstRepaymentOnBlock.newUIContainer("termFirstRepaymentOnIContainer");
//        this.termFirstRepaymentOnField = new DateTextField("termFirstRepaymentOnField", this.termFirstRepaymentOnValue);
//        this.termFirstRepaymentOnIContainer.add(this.termFirstRepaymentOnField);
//        this.termFirstRepaymentOnIContainer.newFeedback("termFirstRepaymentOnFeedback", this.termFirstRepaymentOnField);
//
//        this.termInterestChargedFromBlock = this.row3.newUIBlock("termInterestChargedFromBlock", Size.Six_6);
//        this.termInterestChargedFromIContainer = this.termInterestChargedFromBlock.newUIContainer("termInterestChargedFromIContainer");
//        this.termInterestChargedFromField = new DateTextField("termInterestChargedFromField", this.termInterestChargedFromValue);
//        this.termInterestChargedFromIContainer.add(this.termInterestChargedFromField);
//        this.termInterestChargedFromIContainer.newFeedback("termInterestChargedFromFeedback", this.termInterestChargedFromField);
//
//        this.row4 = UIRow.newUIRow("row4", this.form);
//
//        this.termNominalInterestRateBlock = this.row4.newUIBlock("termNominalInterestRateBlock", Size.Three_3);
//        this.termNominalInterestRateIContainer = this.termNominalInterestRateBlock.newUIContainer("termNominalInterestRateIContainer");
//        this.termNominalInterestRateField = new TextField<>("termNominalInterestRateField", this.termNominalInterestRateValue);
//        this.termNominalInterestRateIContainer.add(this.termNominalInterestRateField);
//        this.termNominalInterestRateIContainer.newFeedback("termNominalInterestRateFeedback", this.termNominalInterestRateField);
//
//        this.termNominalInterestTypeBlock = this.row4.newUIBlock("termNominalInterestTypeBlock", Size.Three_3);
//        this.termNominalInterestTypeVContainer = this.termNominalInterestTypeBlock.newUIContainer("termNominalInterestTypeVContainer");
//        this.termNominalInterestTypeView = new ReadOnlyView("termNominalInterestTypeView", this.termNominalInterestTypeValue);
//        this.termNominalInterestTypeVContainer.add(this.termNominalInterestTypeView);
//
//        this.termInterestMethodBlock = this.row4.newUIBlock("termInterestMethodBlock", Size.Three_3);
//        this.termInterestMethodIContainer = this.termInterestMethodBlock.newUIContainer("termInterestMethodIContainer");
//        this.termInterestMethodField = new Select2SingleChoice<>("termInterestMethodField", this.termInterestMethodValue, this.termInterestMethodProvider);
//        this.termInterestMethodIContainer.add(this.termInterestMethodField);
//        this.termInterestMethodIContainer.newFeedback("termInterestMethodFeedback", this.termInterestMethodField);
//
//        this.termEqualAmortizationBlock = this.row4.newUIBlock("termEqualAmortizationBlock", Size.Three_3);
//        this.termEqualAmortizationIContainer = this.termEqualAmortizationBlock.newUIContainer("termEqualAmortizationIContainer");
//        this.termEqualAmortizationField = new CheckBox("termEqualAmortizationField", this.termEqualAmortizationValue);
//        this.termEqualAmortizationIContainer.add(this.termEqualAmortizationField);
//        this.termEqualAmortizationIContainer.newFeedback("termEqualAmortizationFeedback", this.termEqualAmortizationField);
//
//        this.row5 = UIRow.newUIRow("row5", this.form);
//
//        this.termAmortizationBlock = this.row5.newUIBlock("termAmortizationBlock", Size.Six_6);
//        this.termAmortizationIContainer = this.termAmortizationBlock.newUIContainer("termAmortizationIContainer");
//        this.termAmortizationField = new Select2SingleChoice<>("termAmortizationField", this.termAmortizationValue, this.termAmortizationProvider);
//        this.termAmortizationIContainer.add(this.termAmortizationField);
//        this.termAmortizationIContainer.newFeedback("termAmortizationFeedback", this.termAmortizationField);
//
//        this.row5Block1 = this.row5.newUIBlock("row5Block1", Size.Six_6);
//
//        this.row6 = UIRow.newUIRow("row6", this.form);
//
//        this.termInterestCalculationPeriodBlock = this.row6.newUIBlock("termInterestCalculationPeriodBlock", Size.Six_6);
//        this.termInterestCalculationPeriodIContainer = this.termInterestCalculationPeriodBlock.newUIContainer("termInterestCalculationPeriodIContainer");
//        this.termInterestCalculationPeriodField = new Select2SingleChoice<>("termInterestCalculationPeriodField", this.termInterestCalculationPeriodValue, this.termInterestCalculationPeriodProvider);
//        this.termInterestCalculationPeriodIContainer.add(this.termInterestCalculationPeriodField);
//        this.termInterestCalculationPeriodIContainer.newFeedback("termInterestCalculationPeriodFeedback", this.termInterestCalculationPeriodField);
//
//        this.termCalculateInterestForExactDayInPartialPeriodBlock = this.row6.newUIBlock("termCalculateInterestForExactDayInPartialPeriodBlock", Size.Six_6);
//        this.termCalculateInterestForExactDayInPartialPeriodIContainer = this.termCalculateInterestForExactDayInPartialPeriodBlock.newUIContainer("termCalculateInterestForExactDayInPartialPeriodIContainer");
//        this.termCalculateInterestForExactDayInPartialPeriodField = new CheckBox("termCalculateInterestForExactDayInPartialPeriodField", this.termCalculateInterestForExactDayInPartialPeriodValue);
//        this.termCalculateInterestForExactDayInPartialPeriodIContainer.add(this.termCalculateInterestForExactDayInPartialPeriodField);
//        this.termCalculateInterestForExactDayInPartialPeriodIContainer.newFeedback("termCalculateInterestForExactDayInPartialPeriodFeedback", this.termCalculateInterestForExactDayInPartialPeriodField);
//
//        this.row7 = UIRow.newUIRow("row7", this.form);
//
//        this.termArrearsToleranceBlock = this.row7.newUIBlock("termArrearsToleranceBlock", Size.Six_6);
//        this.termArrearsToleranceIContainer = this.termArrearsToleranceBlock.newUIContainer("termArrearsToleranceIContainer");
//        this.termArrearsToleranceField = new TextField<>("termArrearsToleranceField", this.termArrearsToleranceValue);
//        this.termArrearsToleranceIContainer.add(this.termArrearsToleranceField);
//        this.termArrearsToleranceIContainer.newFeedback("termArrearsToleranceFeedback", this.termArrearsToleranceField);
//
//        this.termInterestFreePeriodBlock = this.row7.newUIBlock("termInterestFreePeriodBlock", Size.Six_6);
//        this.termInterestFreePeriodIContainer = this.termInterestFreePeriodBlock.newUIContainer("termInterestFreePeriodIContainer");
//        this.termInterestFreePeriodField = new TextField<>("termInterestFreePeriodField", this.termInterestFreePeriodValue);
//        this.termInterestFreePeriodIContainer.add(this.termInterestFreePeriodField);
//        this.termInterestFreePeriodIContainer.newFeedback("termInterestFreePeriodFeedback", this.termInterestFreePeriodField);
//
//        this.row8 = UIRow.newUIRow("row8", this.form);
//
//        this.termRepaymentStrategyBlock = this.row8.newUIBlock("termRepaymentStrategyBlock", Size.Six_6);
//        this.termRepaymentStrategyIContainer = this.termRepaymentStrategyBlock.newUIContainer("termRepaymentStrategyIContainer");
//        this.termRepaymentStrategyField = new Select2SingleChoice<>("termRepaymentStrategyField", this.termRepaymentStrategyValue, this.termRepaymentStrategyProvider);
//        this.termRepaymentStrategyIContainer.add(this.termRepaymentStrategyField);
//        this.termRepaymentStrategyIContainer.newFeedback("termRepaymentStrategyFeedback", this.termRepaymentStrategyField);
//
//        this.termPrincipalPaymentBlock = this.row8.newUIBlock("termPrincipalPaymentBlock", Size.Two_2);
//        this.termPrincipalPaymentIContainer = this.termPrincipalPaymentBlock.newUIContainer("termPrincipalPaymentIContainer");
//        this.termPrincipalPaymentField = new TextField<>("termPrincipalPaymentField", this.termPrincipalPaymentValue);
//        this.termPrincipalPaymentIContainer.add(this.termPrincipalPaymentField);
//        this.termPrincipalPaymentIContainer.newFeedback("termPrincipalPaymentFeedback", this.termPrincipalPaymentField);
//
//        this.termInterestPaymentBlock = this.row8.newUIBlock("termInterestPaymentBlock", Size.Two_2);
//        this.termInterestPaymentIContainer = this.termInterestPaymentBlock.newUIContainer("termInterestPaymentIContainer");
//        this.termInterestPaymentField = new TextField<>("termInterestPaymentField", this.termInterestPaymentValue);
//        this.termInterestPaymentIContainer.add(this.termInterestPaymentField);
//        this.termInterestPaymentIContainer.newFeedback("termInterestPaymentFeedback", this.termInterestPaymentField);
//
//        this.termArrearsAgingBlock = this.row8.newUIBlock("termArrearsAgingBlock", Size.Two_2);
//        this.termArrearsAgingIContainer = this.termArrearsAgingBlock.newUIContainer("termArrearsAgingIContainer");
//        this.termArrearsAgingField = new TextField<>("termArrearsAgingField", this.termArrearsAgingValue);
//        this.termArrearsAgingIContainer.add(this.termArrearsAgingField);
//        this.termArrearsAgingIContainer.newFeedback("termArrearsAgingFeedback", this.termArrearsAgingField);
//
//        this.row9 = UIRow.newUIRow("row9", this.form);
//
//        this.termInstallmentAmountBlock = this.row9.newUIBlock("termInstallmentAmountBlock", Size.Six_6);
//        this.termInstallmentAmountIContainer = this.termInstallmentAmountBlock.newUIContainer("termInstallmentAmountIContainer");
//        this.termInstallmentAmountField = new TextField<>("termInstallmentAmountField", this.termInstallmentAmountValue);
//        this.termInstallmentAmountIContainer.add(this.termInstallmentAmountField);
//        this.termInstallmentAmountIContainer.newFeedback("termInstallmentAmountFeedback", this.termInstallmentAmountField);
//
//        this.row9Block1 = this.row9.newUIBlock("row9Block1", Size.Six_6);
//
//        this.row10 = UIRow.newUIRow("row10", this.form);
//
//        this.termTopupLoanBlock = this.row10.newUIBlock("termTopupLoanBlock", Size.Six_6);
//        this.termTopupLoanIContainer = this.termTopupLoanBlock.newUIContainer("termTopupLoanIContainer");
//        this.termTopupLoanField = new CheckBox("termTopupLoanField", this.termTopupLoanValue);
//        this.termTopupLoanIContainer.add(this.termTopupLoanField);
//        this.termTopupLoanIContainer.newFeedback("termTopupLoanFeedback", this.termTopupLoanField);
//
//        this.termLoanToCloseBlock = this.row10.newUIBlock("termLoanToCloseBlock", Size.Six_6);
//        this.termLoanToCloseIContainer = this.termLoanToCloseBlock.newUIContainer("termLoanToCloseIContainer");
//        this.termLoanToCloseField = new Select2SingleChoice<>("termLoanToCloseField", this.termLoanToCloseValue, this.termLoanToCloseProvider);
//        this.termLoanToCloseIContainer.add(this.termLoanToCloseField);
//        this.termLoanToCloseIContainer.newFeedback("termLoanToCloseFeedback", this.termLoanToCloseField);
//
//    }
//
//    @Override
//    protected void configureMetaData() {
//        this.termPrincipalField.add(new OnChangeAjaxBehavior());
//
//        this.termLoanTermField.add(new OnChangeAjaxBehavior());
//
//        this.termLoanTermTypeField.add(new OnChangeAjaxBehavior());
//
//        this.termNumberOfRepaymentField.add(new OnChangeAjaxBehavior());
//
//        this.termRepaidEveryField.add(new OnChangeAjaxBehavior());
//
//        this.termRepaidTypeField.setRequired(true);
//        this.termRepaidTypeField.add(new OnChangeAjaxBehavior(this::termRepaidTypeFieldUpdate));
//
//        this.termRepaidDayField.setRequired(true);
//        this.termRepaidDayField.add(new OnChangeAjaxBehavior());
//
//        this.termRepaidTypeField.setRequired(true);
//        this.termRepaidTypeField.add(new OnChangeAjaxBehavior());
//
//        this.termFirstRepaymentOnField.add(new OnChangeAjaxBehavior());
//
//        this.termInterestChargedFromField.add(new OnChangeAjaxBehavior());
//
//        this.termNominalInterestRateField.add(new OnChangeAjaxBehavior());
//
//        this.termInterestMethodField.add(new OnChangeAjaxBehavior());
//
//        this.termEqualAmortizationField.add(new OnChangeAjaxBehavior());
//
//        this.termAmortizationField.add(new OnChangeAjaxBehavior());
//
//        this.termInterestCalculationPeriodField.add(new OnChangeAjaxBehavior(this::termInterestCalculationPeriodFieldUpdate));
//
//        this.termCalculateInterestForExactDayInPartialPeriodField.add(new OnChangeAjaxBehavior());
//
//        this.termTopupLoanField.add(new OnChangeAjaxBehavior(this::termTopupLoanFieldUpdate));
//
//        termRepaidTypeFieldUpdate(null);
//
//        termInterestCalculationPeriodFieldUpdate(null);
//
//        termTopupLoanFieldUpdate(null);
//    }
//
//    protected boolean termTopupLoanFieldUpdate(AjaxRequestTarget target) {
//        boolean visible = this.termTopupLoanValue.getObject() != null && this.termTopupLoanValue.getObject();
//        this.termLoanToCloseIContainer.setVisible(visible);
//        if (target != null) {
//            target.add(this.termLoanToCloseBlock);
//        }
//        return false;
//    }
//
//    protected boolean termInterestCalculationPeriodFieldUpdate(AjaxRequestTarget target) {
//        InterestCalculationPeriod period = null;
//        if (this.termInterestCalculationPeriodValue.getObject() != null) {
//            period = InterestCalculationPeriod.parseLiteral(this.termInterestCalculationPeriodValue.getObject().getId());
//        }
//
//        boolean visible = period == InterestCalculationPeriod.SameAsPayment;
//
//        this.termCalculateInterestForExactDayInPartialPeriodIContainer.setVisible(visible);
//
//        if (target != null) {
//            target.add(this.termCalculateInterestForExactDayInPartialPeriodBlock);
//        }
//
//        return false;
//    }
//
//    protected boolean termRepaidTypeFieldUpdate(AjaxRequestTarget target) {
//        ChargeFrequency chargeFrequency = null;
//        if (this.termRepaidTypeValue.getObject() != null) {
//            chargeFrequency = ChargeFrequency.parseLiteral(this.termRepaidTypeValue.getObject().getId());
//        }
//        boolean visible = chargeFrequency == ChargeFrequency.Month;
//        this.termRepaidDayIContainer.setVisible(visible);
//        this.termRepaidTypeIContainer.setVisible(visible);
//        if (target != null) {
//            target.add(this.termRepaidDayBlock);
//            target.add(this.termRepaidTypeBlock);
//        }
//        return false;
//    }
//
//    protected boolean backLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
//        this.tab.getObject().setSelectedTab(AccountCreatePage.TAB_DETAIL);
//        if (target != null) {
//            target.add(this.tab.getObject());
//        }
//        return false;
//    }
//
//    protected void nextButtonSubmit(Button button) {
//        this.errorTerm.setObject(false);
//        this.tab.getObject().setSelectedTab(AccountCreatePage.TAB_CHARGE);
//    }
//
//    protected void nextButtonError(Button button) {
//        this.errorTerm.setObject(true);
//    }
//
//}
