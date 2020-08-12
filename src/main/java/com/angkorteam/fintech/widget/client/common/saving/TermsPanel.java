//package com.angkorteam.fintech.widget.client.common.saving;
//
//import org.apache.wicket.Page;
//import org.apache.wicket.ajax.AjaxRequestTarget;
//import org.apache.wicket.markup.html.form.CheckBox;
//import org.apache.wicket.markup.html.form.TextField;
//import org.apache.wicket.markup.html.link.BookmarkablePageLink;
//import org.apache.wicket.model.PropertyModel;
//import org.apache.wicket.request.mapper.parameter.PageParameters;
//import org.apache.wicket.validation.validator.RangeValidator;
//
//import com.angkorteam.fintech.dto.ClientEnum;
//import com.angkorteam.fintech.dto.enums.DayInYear;
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.layout.UIBlock;
//import com.angkorteam.fintech.layout.UIContainer;
//import com.angkorteam.fintech.layout.UIRow;
//import com.angkorteam.fintech.pages.client.center.CenterPreviewPage;
//import com.angkorteam.fintech.pages.client.client.ClientPreviewPage;
//import com.angkorteam.fintech.pages.client.common.saving.AccountCreatePage;
//import com.angkorteam.fintech.pages.client.group.GroupPreviewPage;
//import com.angkorteam.fintech.provider.DayInYearProvider;
//import com.angkorteam.fintech.provider.InterestCalculatedUsingProvider;
//import com.angkorteam.fintech.provider.InterestCompoundingPeriodProvider;
//import com.angkorteam.fintech.provider.InterestPostingPeriodProvider;
//import com.angkorteam.fintech.provider.LockInTypeProvider;
//import com.angkorteam.fintech.widget.Panel;
//import com.angkorteam.fintech.widget.ReadOnlyView;
//import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
//import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
//import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;
//import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;
//import com.angkorteam.framework.wicket.markup.html.form.Button;
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
//    protected PropertyModel<String> officeId;
//
//    protected Form<Void> form;
//    protected Button nextButton;
//    protected AjaxLink<Void> backLink;
//    protected BookmarkablePageLink<Void> closeLink;
//
//    protected ClientEnum client;
//    protected String clientId;
//
//    protected UIRow row1;
//
//    protected UIBlock termCurrencyBlock;
//    protected UIContainer termCurrencyVContainer;
//    protected ReadOnlyView termCurrencyView;
//    protected PropertyModel<String> termCurrencyValue;
//
//    protected UIBlock termDecimalPlacesBlock;
//    protected UIContainer termDecimalPlacesVContainer;
//    protected ReadOnlyView termDecimalPlacesView;
//    protected PropertyModel<Long> termDecimalPlacesValue;
//
//    protected UIRow row2;
//
//    protected UIBlock termNominalAnnualInterestBlock;
//    protected UIContainer termNominalAnnualInterestIContainer;
//    protected TextField<Double> termNominalAnnualInterestField;
//    protected PropertyModel<Double> termNominalAnnualInterestValue;
//
//    protected InterestCompoundingPeriodProvider termInterestCompoundingPeriodProvider;
//    protected UIBlock termInterestCompoundingPeriodBlock;
//    protected UIContainer termInterestCompoundingPeriodIContainer;
//    protected Select2SingleChoice<Option> termInterestCompoundingPeriodField;
//    protected PropertyModel<Option> termInterestCompoundingPeriodValue;
//
//    protected UIRow row3;
//
//    protected UIBlock termCurrencyInMultiplesOfBlock;
//    protected UIContainer termCurrencyInMultiplesOfVContainer;
//    protected ReadOnlyView termCurrencyInMultiplesOfView;
//    protected PropertyModel<Long> termCurrencyInMultiplesOfValue;
//
//    protected InterestPostingPeriodProvider termInterestPostingPeriodProvider;
//    protected UIBlock termInterestPostingPeriodBlock;
//    protected UIContainer termInterestPostingPeriodIContainer;
//    protected Select2SingleChoice<Option> termInterestPostingPeriodField;
//    protected PropertyModel<Option> termInterestPostingPeriodValue;
//
//    protected UIRow row4;
//
//    protected InterestCalculatedUsingProvider termInterestCalculatedUsingProvider;
//    protected UIBlock termInterestCalculatedUsingBlock;
//    protected UIContainer termInterestCalculatedUsingIContainer;
//    protected Select2SingleChoice<Option> termInterestCalculatedUsingField;
//    protected PropertyModel<Option> termInterestCalculatedUsingValue;
//
//    protected DayInYearProvider termDayInYearProvider;
//    protected UIBlock termDayInYearBlock;
//    protected UIContainer termDayInYearIContainer;
//    protected Select2SingleChoice<Option> termDayInYearField;
//    protected PropertyModel<Option> termDayInYearValue;
//
//    protected UIRow row5;
//
//    protected UIBlock termMinimumOpeningBalanceBlock;
//    protected UIContainer termMinimumOpeningBalanceIContainer;
//    protected TextField<Double> termMinimumOpeningBalanceField;
//    protected PropertyModel<Double> termMinimumOpeningBalanceValue;
//
//    protected UIBlock termLockInPeriodBlock;
//    protected UIContainer termLockInPeriodIContainer;
//    protected TextField<Long> termLockInPeriodField;
//    protected PropertyModel<Long> termLockInPeriodValue;
//
//    protected LockInTypeProvider termLockInTypeProvider;
//    protected UIBlock termLockInTypeBlock;
//    protected UIContainer termLockInTypeIContainer;
//    protected Select2SingleChoice<Option> termLockInTypeField;
//    protected PropertyModel<Option> termLockInTypeValue;
//
//    protected UIRow row6;
//
//    protected UIBlock termApplyWithdrawalFeeForTransferBlock;
//    protected UIContainer termApplyWithdrawalFeeForTransferIContainer;
//    protected CheckBox termApplyWithdrawalFeeForTransferField;
//    protected PropertyModel<Boolean> termApplyWithdrawalFeeForTransferValue;
//
//    protected UIBlock termOverdraftAllowedBlock;
//    protected UIContainer termOverdraftAllowedIContainer;
//    protected CheckBox termOverdraftAllowedField;
//    protected PropertyModel<Boolean> termOverdraftAllowedValue;
//
//    protected UIRow row7;
//
//    protected UIBlock termMaximumOverdraftAmountLimitBlock;
//    protected UIContainer termMaximumOverdraftAmountLimitIContainer;
//    protected TextField<Double> termMaximumOverdraftAmountLimitField;
//    protected PropertyModel<Double> termMaximumOverdraftAmountLimitValue;
//
//    protected UIBlock termNominalAnnualInterestForOverdraftBlock;
//    protected UIContainer termNominalAnnualInterestForOverdraftIContainer;
//    protected TextField<Double> termNominalAnnualInterestForOverdraftField;
//    protected PropertyModel<Double> termNominalAnnualInterestForOverdraftValue;
//
//    protected UIBlock termMinOverdraftRequiredForInterestCalculationBlock;
//    protected UIContainer termMinOverdraftRequiredForInterestCalculationIContainer;
//    protected TextField<Double> termMinOverdraftRequiredForInterestCalculationField;
//    protected PropertyModel<Double> termMinOverdraftRequiredForInterestCalculationValue;
//
//    protected UIRow row8;
//
//    protected UIBlock termEnforceMinimumBalanceBlock;
//    protected UIContainer termEnforceMinimumBalanceIContainer;
//    protected CheckBox termEnforceMinimumBalanceField;
//    protected PropertyModel<Boolean> termEnforceMinimumBalanceValue;
//
//    protected UIBlock termMinimumBalanceBlock;
//    protected UIContainer termMinimumBalanceIContainer;
//    protected TextField<Double> termMinimumBalanceField;
//    protected PropertyModel<Double> termMinimumBalanceValue;
//
//    protected UIRow row9;
//
//    protected UIBlock termBalanceRequiredForInterestCalculationBlock;
//    protected UIContainer termBalanceRequiredForInterestCalculationVContainer;
//    protected ReadOnlyView termBalanceRequiredForInterestCalculationView;
//    protected PropertyModel<Double> termBalanceRequiredForInterestCalculationValue;
//
//    protected UIBlock row9Block1;
//
//    protected UIRow row10;
//
//    protected UIBlock termWithHoldBlock;
//    protected UIContainer termWithHoldIContainer;
//    protected CheckBox termWithHoldField;
//    protected PropertyModel<Boolean> termWithHoldValue;
//
//    protected UIBlock termTaxGroupBlock;
//    protected UIContainer termTaxGroupVContainer;
//    protected ReadOnlyView termTaxGroupView;
//    protected PropertyModel<String> termTaxGroupValue;
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
//        this.termLockInTypeProvider = new LockInTypeProvider();
//        this.termInterestPostingPeriodProvider = new InterestPostingPeriodProvider();
//        this.termInterestCalculatedUsingProvider = new InterestCalculatedUsingProvider();
//        this.termDayInYearProvider = new DayInYearProvider(DayInYear.D365, DayInYear.D360);
//        this.termLockInTypeProvider = new LockInTypeProvider();
//        this.termInterestCompoundingPeriodProvider = new InterestCompoundingPeriodProvider();
//
//        this.termCurrencyValue = new PropertyModel<>(this.itemPage, "termCurrencyValue");
//        this.termDecimalPlacesValue = new PropertyModel<>(this.itemPage, "termDecimalPlacesValue");
//        this.termNominalAnnualInterestValue = new PropertyModel<>(this.itemPage, "termNominalAnnualInterestValue");
//        this.termInterestCompoundingPeriodValue = new PropertyModel<>(this.itemPage, "termInterestCompoundingPeriodValue");
//        this.termCurrencyInMultiplesOfValue = new PropertyModel<>(this.itemPage, "termCurrencyInMultiplesOfValue");
//        this.termInterestPostingPeriodValue = new PropertyModel<>(this.itemPage, "termInterestPostingPeriodValue");
//        this.termInterestCalculatedUsingValue = new PropertyModel<>(this.itemPage, "termInterestCalculatedUsingValue");
//        this.termDayInYearValue = new PropertyModel<>(this.itemPage, "termDayInYearValue");
//        this.termMinimumOpeningBalanceValue = new PropertyModel<>(this.itemPage, "termMinimumOpeningBalanceValue");
//        this.termLockInPeriodValue = new PropertyModel<>(this.itemPage, "termLockInPeriodValue");
//        this.termLockInTypeValue = new PropertyModel<>(this.itemPage, "termLockInTypeValue");
//        this.termApplyWithdrawalFeeForTransferValue = new PropertyModel<>(this.itemPage, "termApplyWithdrawalFeeForTransferValue");
//        this.termOverdraftAllowedValue = new PropertyModel<>(this.itemPage, "termOverdraftAllowedValue");
//        this.termMaximumOverdraftAmountLimitValue = new PropertyModel<>(this.itemPage, "termMaximumOverdraftAmountLimitValue");
//        this.termNominalAnnualInterestForOverdraftValue = new PropertyModel<>(this.itemPage, "termNominalAnnualInterestForOverdraftValue");
//        this.termMinOverdraftRequiredForInterestCalculationValue = new PropertyModel<>(this.itemPage, "termMinOverdraftRequiredForInterestCalculationValue");
//        this.termEnforceMinimumBalanceValue = new PropertyModel<>(this.itemPage, "termEnforceMinimumBalanceValue");
//        this.termMinimumBalanceValue = new PropertyModel<>(this.itemPage, "termMinimumBalanceValue");
//        this.termBalanceRequiredForInterestCalculationValue = new PropertyModel<>(this.itemPage, "termBalanceRequiredForInterestCalculationValue");
//        this.termWithHoldValue = new PropertyModel<>(this.itemPage, "termWithHoldValue");
//        this.termTaxGroupValue = new PropertyModel<>(this.itemPage, "termTaxGroupValue");
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
//        this.termCurrencyBlock = this.row1.newUIBlock("termCurrencyBlock", Size.Six_6);
//        this.termCurrencyVContainer = this.termCurrencyBlock.newUIContainer("termCurrencyVContainer");
//        this.termCurrencyView = new ReadOnlyView("termCurrencyView", this.termCurrencyValue);
//        this.termCurrencyVContainer.add(this.termCurrencyView);
//
//        this.termDecimalPlacesBlock = this.row1.newUIBlock("termDecimalPlacesBlock", Size.Six_6);
//        this.termDecimalPlacesVContainer = this.termDecimalPlacesBlock.newUIContainer("termDecimalPlacesVContainer");
//        this.termDecimalPlacesView = new ReadOnlyView("termDecimalPlacesView", this.termDecimalPlacesValue);
//        this.termDecimalPlacesVContainer.add(this.termDecimalPlacesView);
//
//        this.row2 = UIRow.newUIRow("row2", this.form);
//
//        this.termNominalAnnualInterestBlock = this.row2.newUIBlock("termNominalAnnualInterestBlock", Size.Six_6);
//        this.termNominalAnnualInterestIContainer = this.termNominalAnnualInterestBlock.newUIContainer("termNominalAnnualInterestIContainer");
//        this.termNominalAnnualInterestField = new TextField<>("termNominalAnnualInterestField", this.termNominalAnnualInterestValue);
//        this.termNominalAnnualInterestIContainer.add(this.termNominalAnnualInterestField);
//        this.termNominalAnnualInterestIContainer.newFeedback("termNominalAnnualInterestFeedback", this.termNominalAnnualInterestField);
//
//        this.termInterestCompoundingPeriodBlock = this.row2.newUIBlock("termInterestCompoundingPeriodBlock", Size.Six_6);
//        this.termInterestCompoundingPeriodIContainer = this.termInterestCompoundingPeriodBlock.newUIContainer("termInterestCompoundingPeriodIContainer");
//        this.termInterestCompoundingPeriodField = new Select2SingleChoice<>("termInterestCompoundingPeriodField", this.termInterestCompoundingPeriodValue, this.termInterestCompoundingPeriodProvider);
//        this.termInterestCompoundingPeriodIContainer.add(this.termInterestCompoundingPeriodField);
//        this.termInterestCompoundingPeriodIContainer.newFeedback("termInterestCompoundingPeriodFeedback", this.termInterestCompoundingPeriodField);
//
//        this.row3 = UIRow.newUIRow("row3", this.form);
//
//        this.termCurrencyInMultiplesOfBlock = this.row3.newUIBlock("termCurrencyInMultiplesOfBlock", Size.Six_6);
//        this.termCurrencyInMultiplesOfVContainer = this.termCurrencyInMultiplesOfBlock.newUIContainer("termCurrencyInMultiplesOfVContainer");
//        this.termCurrencyInMultiplesOfView = new ReadOnlyView("termCurrencyInMultiplesOfView", this.termCurrencyInMultiplesOfValue);
//        this.termCurrencyInMultiplesOfVContainer.add(this.termCurrencyInMultiplesOfView);
//
//        this.termInterestPostingPeriodBlock = this.row3.newUIBlock("termInterestPostingPeriodBlock", Size.Six_6);
//        this.termInterestPostingPeriodIContainer = this.termInterestPostingPeriodBlock.newUIContainer("termInterestPostingPeriodIContainer");
//        this.termInterestPostingPeriodField = new Select2SingleChoice<>("termInterestPostingPeriodField", this.termInterestPostingPeriodValue, this.termInterestPostingPeriodProvider);
//        this.termInterestPostingPeriodIContainer.add(this.termInterestPostingPeriodField);
//        this.termInterestPostingPeriodIContainer.newFeedback("termInterestPostingPeriodFeedback", this.termInterestPostingPeriodField);
//
//        this.row4 = UIRow.newUIRow("row4", this.form);
//
//        this.termInterestCalculatedUsingBlock = this.row4.newUIBlock("termInterestCalculatedUsingBlock", Size.Six_6);
//        this.termInterestCalculatedUsingIContainer = this.termInterestCalculatedUsingBlock.newUIContainer("termInterestCalculatedUsingIContainer");
//        this.termInterestCalculatedUsingField = new Select2SingleChoice<>("termInterestCalculatedUsingField", this.termInterestCalculatedUsingValue, this.termInterestCalculatedUsingProvider);
//        this.termInterestCalculatedUsingIContainer.add(this.termInterestCalculatedUsingField);
//        this.termInterestCalculatedUsingIContainer.newFeedback("termInterestCalculatedUsingFeedback", this.termInterestCalculatedUsingField);
//
//        this.termDayInYearBlock = this.row4.newUIBlock("termDayInYearBlock", Size.Six_6);
//        this.termDayInYearIContainer = this.termDayInYearBlock.newUIContainer("termDayInYearIContainer");
//        this.termDayInYearField = new Select2SingleChoice<>("termDayInYearField", this.termDayInYearValue, this.termDayInYearProvider);
//        this.termDayInYearIContainer.add(this.termDayInYearField);
//        this.termDayInYearIContainer.newFeedback("termDayInYearFeedback", this.termDayInYearField);
//
//        this.row5 = UIRow.newUIRow("row5", this.form);
//
//        this.termMinimumOpeningBalanceBlock = this.row5.newUIBlock("termMinimumOpeningBalanceBlock", Size.Four_4);
//        this.termMinimumOpeningBalanceIContainer = this.termMinimumOpeningBalanceBlock.newUIContainer("termMinimumOpeningBalanceIContainer");
//        this.termMinimumOpeningBalanceField = new TextField<>("termMinimumOpeningBalanceField", this.termMinimumOpeningBalanceValue);
//        this.termMinimumOpeningBalanceIContainer.add(this.termMinimumOpeningBalanceField);
//        this.termMinimumOpeningBalanceIContainer.newFeedback("termMinimumOpeningBalanceFeedback", this.termMinimumOpeningBalanceField);
//
//        this.termLockInPeriodBlock = this.row5.newUIBlock("termLockInPeriodBlock", Size.Four_4);
//        this.termLockInPeriodIContainer = this.termLockInPeriodBlock.newUIContainer("termLockInPeriodIContainer");
//        this.termLockInPeriodField = new TextField<>("termLockInPeriodField", this.termLockInPeriodValue);
//        this.termLockInPeriodIContainer.add(this.termLockInPeriodField);
//        this.termLockInPeriodIContainer.newFeedback("termLockInPeriodFeedback", this.termLockInPeriodField);
//
//        this.termLockInTypeBlock = this.row5.newUIBlock("termLockInTypeBlock", Size.Four_4);
//        this.termLockInTypeIContainer = this.termLockInTypeBlock.newUIContainer("termLockInTypeIContainer");
//        this.termLockInTypeField = new Select2SingleChoice<>("termLockInTypeField", this.termLockInTypeValue, this.termLockInTypeProvider);
//        this.termLockInTypeIContainer.add(this.termLockInTypeField);
//        this.termLockInTypeIContainer.newFeedback("termLockInTypeFeedback", this.termLockInTypeField);
//
//        this.row6 = UIRow.newUIRow("row6", this.form);
//
//        this.termApplyWithdrawalFeeForTransferBlock = this.row6.newUIBlock("termApplyWithdrawalFeeForTransferBlock", Size.Six_6);
//        this.termApplyWithdrawalFeeForTransferIContainer = this.termApplyWithdrawalFeeForTransferBlock.newUIContainer("termApplyWithdrawalFeeForTransferIContainer");
//        this.termApplyWithdrawalFeeForTransferField = new CheckBox("termApplyWithdrawalFeeForTransferField", this.termApplyWithdrawalFeeForTransferValue);
//        this.termApplyWithdrawalFeeForTransferIContainer.add(this.termApplyWithdrawalFeeForTransferField);
//        this.termApplyWithdrawalFeeForTransferIContainer.newFeedback("termApplyWithdrawalFeeForTransferFeedback", this.termApplyWithdrawalFeeForTransferField);
//
//        this.termOverdraftAllowedBlock = this.row6.newUIBlock("termOverdraftAllowedBlock", Size.Six_6);
//        this.termOverdraftAllowedIContainer = this.termOverdraftAllowedBlock.newUIContainer("termOverdraftAllowedIContainer");
//        this.termOverdraftAllowedField = new CheckBox("termOverdraftAllowedField", this.termOverdraftAllowedValue);
//        this.termOverdraftAllowedIContainer.add(this.termOverdraftAllowedField);
//        this.termOverdraftAllowedIContainer.newFeedback("termOverdraftAllowedFeedback", this.termOverdraftAllowedField);
//
//        this.row7 = UIRow.newUIRow("row7", this.form);
//
//        this.termMaximumOverdraftAmountLimitBlock = this.row7.newUIBlock("termMaximumOverdraftAmountLimitBlock", Size.Four_4);
//        this.termMaximumOverdraftAmountLimitIContainer = this.termMaximumOverdraftAmountLimitBlock.newUIContainer("termMaximumOverdraftAmountLimitIContainer");
//        this.termMaximumOverdraftAmountLimitField = new TextField<>("termMaximumOverdraftAmountLimitField", this.termMaximumOverdraftAmountLimitValue);
//        this.termMaximumOverdraftAmountLimitIContainer.add(this.termMaximumOverdraftAmountLimitField);
//        this.termMaximumOverdraftAmountLimitIContainer.newFeedback("termMaximumOverdraftAmountLimitFeedback", this.termMaximumOverdraftAmountLimitField);
//
//        this.termNominalAnnualInterestForOverdraftBlock = this.row7.newUIBlock("termNominalAnnualInterestForOverdraftBlock", Size.Four_4);
//        this.termNominalAnnualInterestForOverdraftIContainer = this.termNominalAnnualInterestForOverdraftBlock.newUIContainer("termNominalAnnualInterestForOverdraftIContainer");
//        this.termNominalAnnualInterestForOverdraftField = new TextField<>("termNominalAnnualInterestForOverdraftField", this.termNominalAnnualInterestForOverdraftValue);
//        this.termNominalAnnualInterestForOverdraftIContainer.add(this.termNominalAnnualInterestForOverdraftField);
//        this.termNominalAnnualInterestForOverdraftIContainer.newFeedback("termNominalAnnualInterestForOverdraftFeedback", this.termNominalAnnualInterestForOverdraftField);
//
//        this.termMinOverdraftRequiredForInterestCalculationBlock = this.row7.newUIBlock("termMinOverdraftRequiredForInterestCalculationBlock", Size.Four_4);
//        this.termMinOverdraftRequiredForInterestCalculationIContainer = this.termMinOverdraftRequiredForInterestCalculationBlock.newUIContainer("termMinOverdraftRequiredForInterestCalculationIContainer");
//        this.termMinOverdraftRequiredForInterestCalculationField = new TextField<>("termMinOverdraftRequiredForInterestCalculationField", this.termMinOverdraftRequiredForInterestCalculationValue);
//        this.termMinOverdraftRequiredForInterestCalculationIContainer.add(this.termMinOverdraftRequiredForInterestCalculationField);
//        this.termMinOverdraftRequiredForInterestCalculationIContainer.newFeedback("termMinOverdraftRequiredForInterestCalculationFeedback", this.termMinOverdraftRequiredForInterestCalculationField);
//
//        this.row8 = UIRow.newUIRow("row8", this.form);
//
//        this.termEnforceMinimumBalanceBlock = this.row8.newUIBlock("termEnforceMinimumBalanceBlock", Size.Six_6);
//        this.termEnforceMinimumBalanceIContainer = this.termEnforceMinimumBalanceBlock.newUIContainer("termEnforceMinimumBalanceIContainer");
//        this.termEnforceMinimumBalanceField = new CheckBox("termEnforceMinimumBalanceField", this.termEnforceMinimumBalanceValue);
//        this.termEnforceMinimumBalanceIContainer.add(this.termEnforceMinimumBalanceField);
//        this.termEnforceMinimumBalanceIContainer.newFeedback("termEnforceMinimumBalanceFeedback", this.termEnforceMinimumBalanceField);
//
//        this.termMinimumBalanceBlock = this.row8.newUIBlock("termMinimumBalanceBlock", Size.Six_6);
//        this.termMinimumBalanceIContainer = this.termMinimumBalanceBlock.newUIContainer("termMinimumBalanceIContainer");
//        this.termMinimumBalanceField = new TextField<>("termMinimumBalanceField", this.termMinimumBalanceValue);
//        this.termMinimumBalanceIContainer.add(this.termMinimumBalanceField);
//        this.termMinimumBalanceIContainer.newFeedback("termMinimumBalanceFeedback", this.termMinimumBalanceField);
//
//        this.row9 = UIRow.newUIRow("row9", this.form);
//
//        this.termBalanceRequiredForInterestCalculationBlock = this.row9.newUIBlock("termBalanceRequiredForInterestCalculationBlock", Size.Six_6);
//        this.termBalanceRequiredForInterestCalculationVContainer = this.termBalanceRequiredForInterestCalculationBlock.newUIContainer("termBalanceRequiredForInterestCalculationVContainer");
//        this.termBalanceRequiredForInterestCalculationView = new ReadOnlyView("termBalanceRequiredForInterestCalculationView", this.termBalanceRequiredForInterestCalculationValue);
//        this.termBalanceRequiredForInterestCalculationVContainer.add(this.termBalanceRequiredForInterestCalculationView);
//
//        this.row9Block1 = this.row9.newUIBlock("row9Block1", Size.Six_6);
//
//        this.row10 = UIRow.newUIRow("row10", this.form);
//
//        this.termWithHoldBlock = this.row10.newUIBlock("termWithHoldBlock", Size.Six_6);
//        this.termWithHoldIContainer = this.termWithHoldBlock.newUIContainer("termWithHoldIContainer");
//        this.termWithHoldField = new CheckBox("termWithHoldField", this.termWithHoldValue);
//        this.termWithHoldIContainer.add(this.termWithHoldField);
//        this.termWithHoldIContainer.newFeedback("termWithHoldFeedback", this.termWithHoldField);
//
//        this.termTaxGroupBlock = this.row10.newUIBlock("termTaxGroupBlock", Size.Six_6);
//        this.termTaxGroupVContainer = this.termTaxGroupBlock.newUIContainer("termTaxGroupVContainer");
//        this.termTaxGroupView = new ReadOnlyView("termTaxGroupView", this.termTaxGroupValue);
//        this.termTaxGroupVContainer.add(this.termTaxGroupView);
//
//    }
//
//    @Override
//    protected void configureMetaData() {
//        this.termNominalAnnualInterestField.setRequired(true);
//        this.termNominalAnnualInterestField.add(new OnChangeAjaxBehavior());
//
//        this.termInterestCompoundingPeriodField.setRequired(true);
//        this.termInterestCompoundingPeriodField.add(new OnChangeAjaxBehavior());
//
//        this.termInterestPostingPeriodField.setRequired(true);
//        this.termInterestPostingPeriodField.add(new OnChangeAjaxBehavior());
//
//        this.termInterestCalculatedUsingField.setRequired(true);
//        this.termInterestCalculatedUsingField.add(new OnChangeAjaxBehavior());
//
//        this.termDayInYearField.setRequired(true);
//        this.termDayInYearField.add(new OnChangeAjaxBehavior());
//
//        this.termMinimumOpeningBalanceField.setRequired(true);
//        this.termMinimumOpeningBalanceField.add(new OnChangeAjaxBehavior());
//
//        this.termLockInPeriodField.setRequired(true);
//        this.termLockInPeriodField.add(new OnChangeAjaxBehavior());
//
//        this.termLockInTypeField.setRequired(true);
//        this.termLockInTypeField.add(new OnChangeAjaxBehavior());
//
//        this.termMaximumOverdraftAmountLimitField.setRequired(true);
//        this.termMaximumOverdraftAmountLimitField.add(RangeValidator.minimum(0d));
//        this.termMaximumOverdraftAmountLimitField.add(new OnChangeAjaxBehavior());
//
//        this.termMinOverdraftRequiredForInterestCalculationField.setRequired(true);
//        this.termMinOverdraftRequiredForInterestCalculationField.add(RangeValidator.minimum(0d));
//        this.termMinOverdraftRequiredForInterestCalculationField.add(new OnChangeAjaxBehavior());
//
//        this.termNominalAnnualInterestForOverdraftField.setRequired(true);
//        this.termNominalAnnualInterestForOverdraftField.add(RangeValidator.minimum(0d));
//        this.termNominalAnnualInterestForOverdraftField.add(new OnChangeAjaxBehavior());
//
//        this.termMinimumBalanceField.setRequired(true);
//        this.termMinimumBalanceField.add(RangeValidator.minimum(0d));
//        this.termMinimumBalanceField.add(new OnChangeAjaxBehavior());
//
//        this.termWithHoldField.add(new OnChangeAjaxBehavior(this::termWithHoldFieldUpdate));
//
//        this.termOverdraftAllowedField.add(new OnChangeAjaxBehavior(this::termOverdraftAllowedFieldUpdate));
//
//        termOverdraftAllowedFieldUpdate(null);
//    }
//
//    protected boolean termOverdraftAllowedFieldUpdate(AjaxRequestTarget target) {
//        boolean visible = this.termOverdraftAllowedValue.getObject() != null && this.termOverdraftAllowedValue.getObject();
//        this.termMinOverdraftRequiredForInterestCalculationBlock.setVisible(visible);
//        this.termNominalAnnualInterestForOverdraftBlock.setVisible(visible);
//        this.termMaximumOverdraftAmountLimitBlock.setVisible(visible);
//
//        this.termEnforceMinimumBalanceBlock.setVisible(!visible);
//        this.termMinimumBalanceBlock.setVisible(!visible);
//
//        if (target != null) {
//            target.add(this.row7);
//            target.add(this.row8);
//        }
//        return false;
//    }
//
//    protected boolean termWithHoldFieldUpdate(AjaxRequestTarget target) {
//        boolean visible = this.termWithHoldValue.getObject() != null && this.termWithHoldValue.getObject();
//        this.termTaxGroupVContainer.setVisible(visible);
//        if (target != null) {
//            target.add(this.termTaxGroupBlock);
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
