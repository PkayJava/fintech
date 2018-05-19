package com.angkorteam.fintech.widget.product.recurring;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.RangeValidator;

import com.angkorteam.fintech.ddl.MTaxGroup;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.pages.product.recurring.RecurringBrowsePage;
import com.angkorteam.fintech.pages.product.recurring.RecurringCreatePage;
import com.angkorteam.fintech.provider.ApplyPenalOnProvider;
import com.angkorteam.fintech.provider.LockInTypeProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.Panel;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

public class SettingsPanel extends Panel {

    protected Page itemPage;
    protected PropertyModel<AjaxTabbedPanel<ITab>> tab;
    protected PropertyModel<Boolean> errorSetting;

    protected Form<Void> form;
    protected Button nextButton;
    protected AjaxLink<Void> backLink;
    protected BookmarkablePageLink<Void> closeLink;

    protected UIRow row1;

    protected UIBlock settingMandatoryDepositBlock;
    protected UIContainer settingMandatoryDepositIContainer;
    protected CheckBox settingMandatoryDepositField;

    protected UIBlock settingAdjustAdvancePaymentBlock;
    protected UIContainer settingAdjustAdvancePaymentIContainer;
    protected CheckBox settingAdjustAdvancePaymentField;

    protected UIBlock settingAllowWithdrawalBlock;
    protected UIContainer settingAllowWithdrawalIContainer;
    protected CheckBox settingAllowWithdrawalField;

    protected UIRow row2;

    protected UIBlock settingLockInPeriodBlock;
    protected UIContainer settingLockInPeriodIContainer;
    protected TextField<Long> settingLockInPeriodField;

    protected UIBlock settingLockInTypeBlock;
    protected UIContainer settingLockInTypeIContainer;
    protected LockInTypeProvider settingLockInTypeProvider;
    protected Select2SingleChoice<Option> settingLockInTypeField;

    protected UIBlock settingMinimumDepositTermBlock;
    protected UIContainer settingMinimumDepositTermIContainer;
    protected TextField<Long> settingMinimumDepositTermField;

    protected UIBlock settingMinimumDepositTypeBlock;
    protected UIContainer settingMinimumDepositTypeIContainer;
    protected LockInTypeProvider settingMinimumDepositTypeProvider;
    protected Select2SingleChoice<Option> settingMinimumDepositTypeField;

    protected UIRow row3;

    protected UIBlock settingInMultiplesOfBlock;
    protected UIContainer settingInMultiplesOfIContainer;
    protected TextField<Long> settingInMultiplesOfField;

    protected UIBlock settingInMultiplesTypeBlock;
    protected UIContainer settingInMultiplesTypeIContainer;
    protected LockInTypeProvider settingInMultiplesTypeProvider;
    protected Select2SingleChoice<Option> settingInMultiplesTypeField;

    protected UIBlock settingMaximumDepositTermBlock;
    protected UIContainer settingMaximumDepositTermIContainer;
    protected TextField<Long> settingMaximumDepositTermField;

    protected UIBlock settingMaximumDepositTypeBlock;
    protected UIContainer settingMaximumDepositTypeIContainer;
    protected LockInTypeProvider settingMaximumDepositTypeProvider;
    protected Select2SingleChoice<Option> settingMaximumDepositTypeField;

    protected UIRow row4;

    protected UIBlock settingForPreMatureClosureBlock;
    protected UIContainer settingForPreMatureClosureIContainer;
    protected CheckBox settingForPreMatureClosureField;

    protected UIBlock settingApplyPenalInterestBlock;
    protected UIContainer settingApplyPenalInterestIContainer;
    protected TextField<Double> settingApplyPenalInterestField;

    protected UIBlock settingApplyPenalOnBlock;
    protected UIContainer settingApplyPenalOnIContainer;
    protected ApplyPenalOnProvider settingApplyPenalOnProvider;
    protected Select2SingleChoice<Option> settingApplyPenalOnField;

    protected UIBlock row4Block1;

    protected UIRow row5;

    protected UIBlock settingWithholdTaxApplicableBlock;
    protected UIContainer settingWithholdTaxApplicableIContainer;
    protected CheckBox settingWithholdTaxApplicableField;

    protected UIBlock settingTaxGroupBlock;
    protected UIContainer settingTaxGroupIContainer;
    protected SingleChoiceProvider settingTaxGroupProvider;
    protected Select2SingleChoice<Option> settingTaxGroupField;

    protected UIBlock settingBalanceRequiredForInterestCalculationBlock;
    protected UIContainer settingBalanceRequiredForInterestCalculationIContainer;
    protected TextField<Double> settingBalanceRequiredForInterestCalculationField;

    public SettingsPanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
    }

    @Override
    protected void initData() {
        this.errorSetting = new PropertyModel<>(this.itemPage, "errorSetting");
        this.tab = new PropertyModel<>(this.itemPage, "tab");
        this.settingLockInTypeProvider = new LockInTypeProvider();
        this.settingMinimumDepositTypeProvider = new LockInTypeProvider();
        this.settingInMultiplesTypeProvider = new LockInTypeProvider();
        this.settingMaximumDepositTypeProvider = new LockInTypeProvider();
        this.settingApplyPenalOnProvider = new ApplyPenalOnProvider();
        this.settingTaxGroupProvider = new SingleChoiceProvider(MTaxGroup.NAME, MTaxGroup.Field.ID, MTaxGroup.Field.NAME);
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.nextButton = new Button("nextButton");
        this.nextButton.setOnSubmit(this::nextButtonSubmit);
        this.nextButton.setOnError(this::nextButtonError);
        this.form.add(this.nextButton);

        this.backLink = new AjaxLink<>("backLink");
        this.backLink.setOnClick(this::backLinkClick);
        this.form.add(this.backLink);

        this.closeLink = new BookmarkablePageLink<>("closeLink", RecurringBrowsePage.class);
        this.form.add(this.closeLink);

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.settingMandatoryDepositBlock = this.row1.newUIBlock("settingMandatoryDepositBlock", Size.Four_4);
        this.settingMandatoryDepositIContainer = this.settingMandatoryDepositBlock.newUIContainer("settingMandatoryDepositIContainer");
        this.settingMandatoryDepositField = new CheckBox("settingMandatoryDepositField", new PropertyModel<>(this.itemPage, "settingMandatoryDepositValue"));
        this.settingMandatoryDepositIContainer.add(this.settingMandatoryDepositField);
        this.settingMandatoryDepositIContainer.newFeedback("settingMandatoryDepositFeedback", this.settingMandatoryDepositField);

        this.settingAdjustAdvancePaymentBlock = this.row1.newUIBlock("settingAdjustAdvancePaymentBlock", Size.Four_4);
        this.settingAdjustAdvancePaymentIContainer = this.settingAdjustAdvancePaymentBlock.newUIContainer("settingAdjustAdvancePaymentIContainer");
        this.settingAdjustAdvancePaymentField = new CheckBox("settingAdjustAdvancePaymentField", new PropertyModel<>(this.itemPage, "settingAdjustAdvancePaymentValue"));
        this.settingAdjustAdvancePaymentIContainer.add(this.settingAdjustAdvancePaymentField);
        this.settingAdjustAdvancePaymentIContainer.newFeedback("settingAdjustAdvancePaymentFeedback", this.settingAdjustAdvancePaymentField);

        this.settingAllowWithdrawalBlock = this.row1.newUIBlock("settingAllowWithdrawalBlock", Size.Four_4);
        this.settingAllowWithdrawalIContainer = this.settingAllowWithdrawalBlock.newUIContainer("settingAllowWithdrawalIContainer");
        this.settingAllowWithdrawalField = new CheckBox("settingAllowWithdrawalField", new PropertyModel<>(this.itemPage, "settingAllowWithdrawalValue"));
        this.settingAllowWithdrawalIContainer.add(this.settingAllowWithdrawalField);
        this.settingAllowWithdrawalIContainer.newFeedback("settingAllowWithdrawalFeedback", this.settingAllowWithdrawalField);

        this.row2 = UIRow.newUIRow("row2", this.form);

        this.settingLockInPeriodBlock = this.row2.newUIBlock("settingLockInPeriodBlock", Size.Three_3);
        this.settingLockInPeriodIContainer = this.settingLockInPeriodBlock.newUIContainer("settingLockInPeriodIContainer");
        this.settingLockInPeriodField = new TextField<>("settingLockInPeriodField", new PropertyModel<>(this.itemPage, "settingLockInPeriodValue"));
        this.settingLockInPeriodIContainer.add(this.settingLockInPeriodField);
        this.settingLockInPeriodIContainer.newFeedback("settingLockInPeriodFeedback", this.settingLockInPeriodField);

        this.settingLockInTypeBlock = this.row2.newUIBlock("settingLockInTypeBlock", Size.Three_3);
        this.settingLockInTypeIContainer = this.settingLockInTypeBlock.newUIContainer("settingLockInTypeIContainer");
        this.settingLockInTypeField = new Select2SingleChoice<>("settingLockInTypeField", new PropertyModel<>(this.itemPage, "settingLockInTypeValue"), this.settingLockInTypeProvider);
        this.settingLockInTypeIContainer.add(this.settingLockInTypeField);
        this.settingLockInTypeIContainer.newFeedback("settingLockInTypeFeedback", this.settingLockInTypeField);

        this.settingMinimumDepositTermBlock = this.row2.newUIBlock("settingMinimumDepositTermBlock", Size.Three_3);
        this.settingMinimumDepositTermIContainer = this.settingMinimumDepositTermBlock.newUIContainer("settingMinimumDepositTermIContainer");
        this.settingMinimumDepositTermField = new TextField<>("settingMinimumDepositTermField", new PropertyModel<>(this.itemPage, "settingMinimumDepositTermValue"));
        this.settingMinimumDepositTermIContainer.add(this.settingMinimumDepositTermField);
        this.settingMinimumDepositTermIContainer.newFeedback("settingMinimumDepositTermFeedback", this.settingMinimumDepositTermField);

        this.settingMinimumDepositTypeBlock = this.row2.newUIBlock("settingMinimumDepositTypeBlock", Size.Three_3);
        this.settingMinimumDepositTypeIContainer = this.settingMinimumDepositTypeBlock.newUIContainer("settingMinimumDepositTypeIContainer");
        this.settingMinimumDepositTypeField = new Select2SingleChoice<>("settingMinimumDepositTypeField", new PropertyModel<>(this.itemPage, "settingMinimumDepositTypeValue"), this.settingMinimumDepositTypeProvider);
        this.settingMinimumDepositTypeIContainer.add(this.settingMinimumDepositTypeField);
        this.settingMinimumDepositTypeIContainer.newFeedback("settingMinimumDepositTypeFeedback", this.settingMinimumDepositTypeField);

        this.row3 = UIRow.newUIRow("row3", this.form);

        this.settingInMultiplesOfBlock = this.row3.newUIBlock("settingInMultiplesOfBlock", Size.Three_3);
        this.settingInMultiplesOfIContainer = this.settingInMultiplesOfBlock.newUIContainer("settingInMultiplesOfIContainer");
        this.settingInMultiplesOfField = new TextField<>("settingInMultiplesOfField", new PropertyModel<>(this.itemPage, "settingInMultiplesOfValue"));
        this.settingInMultiplesOfIContainer.add(this.settingInMultiplesOfField);
        this.settingInMultiplesOfIContainer.newFeedback("settingInMultiplesOfFeedback", this.settingInMultiplesOfField);

        this.settingInMultiplesTypeBlock = this.row3.newUIBlock("settingInMultiplesTypeBlock", Size.Three_3);
        this.settingInMultiplesTypeIContainer = this.settingInMultiplesTypeBlock.newUIContainer("settingInMultiplesTypeIContainer");
        this.settingInMultiplesTypeField = new Select2SingleChoice<>("settingInMultiplesTypeField", new PropertyModel<>(this.itemPage, "settingInMultiplesTypeValue"), this.settingInMultiplesTypeProvider);
        this.settingInMultiplesTypeIContainer.add(this.settingInMultiplesTypeField);
        this.settingInMultiplesTypeIContainer.newFeedback("settingInMultiplesTypeFeedback", this.settingInMultiplesTypeField);

        this.settingMaximumDepositTermBlock = this.row3.newUIBlock("settingMaximumDepositTermBlock", Size.Three_3);
        this.settingMaximumDepositTermIContainer = this.settingMaximumDepositTermBlock.newUIContainer("settingMaximumDepositTermIContainer");
        this.settingMaximumDepositTermField = new TextField<>("settingMaximumDepositTermField", new PropertyModel<>(this.itemPage, "settingMaximumDepositTermValue"));
        this.settingMaximumDepositTermIContainer.add(this.settingMaximumDepositTermField);
        this.settingMaximumDepositTermIContainer.newFeedback("settingMaximumDepositTermFeedback", this.settingMaximumDepositTermField);

        this.settingMaximumDepositTypeBlock = this.row3.newUIBlock("settingMaximumDepositTypeBlock", Size.Three_3);
        this.settingMaximumDepositTypeIContainer = this.settingMaximumDepositTypeBlock.newUIContainer("settingMaximumDepositTypeIContainer");
        this.settingMaximumDepositTypeField = new Select2SingleChoice<>("settingMaximumDepositTypeField", new PropertyModel<>(this.itemPage, "settingMaximumDepositTypeValue"), this.settingMaximumDepositTypeProvider);
        this.settingMaximumDepositTypeIContainer.add(this.settingMaximumDepositTypeField);
        this.settingMaximumDepositTypeIContainer.newFeedback("settingMaximumDepositTypeFeedback", this.settingMaximumDepositTypeField);

        this.row4 = UIRow.newUIRow("row4", this.form);

        this.settingForPreMatureClosureBlock = this.row4.newUIBlock("settingForPreMatureClosureBlock", Size.Three_3);
        this.settingForPreMatureClosureIContainer = this.settingForPreMatureClosureBlock.newUIContainer("settingForPreMatureClosureIContainer");
        this.settingForPreMatureClosureField = new CheckBox("settingForPreMatureClosureField", new PropertyModel<>(this.itemPage, "settingForPreMatureClosureValue"));
        this.settingForPreMatureClosureIContainer.add(this.settingForPreMatureClosureField);
        this.settingForPreMatureClosureIContainer.newFeedback("settingForPreMatureClosureFeedback", this.settingForPreMatureClosureField);

        this.settingApplyPenalInterestBlock = this.row4.newUIBlock("settingApplyPenalInterestBlock", Size.Three_3);
        this.settingApplyPenalInterestIContainer = this.settingApplyPenalInterestBlock.newUIContainer("settingApplyPenalInterestIContainer");
        this.settingApplyPenalInterestField = new TextField<>("settingApplyPenalInterestField", new PropertyModel<>(this.itemPage, "settingApplyPenalInterestValue"));
        this.settingApplyPenalInterestIContainer.add(this.settingApplyPenalInterestField);
        this.settingApplyPenalInterestIContainer.newFeedback("settingApplyPenalInterestFeedback", this.settingApplyPenalInterestField);

        this.settingApplyPenalOnBlock = this.row4.newUIBlock("settingApplyPenalOnBlock", Size.Three_3);
        this.settingApplyPenalOnIContainer = this.settingApplyPenalOnBlock.newUIContainer("settingApplyPenalOnIContainer");
        this.settingApplyPenalOnField = new Select2SingleChoice<>("settingApplyPenalOnField", new PropertyModel<>(this.itemPage, "settingApplyPenalOnValue"), this.settingApplyPenalOnProvider);
        this.settingApplyPenalOnIContainer.add(this.settingApplyPenalOnField);
        this.settingApplyPenalOnIContainer.newFeedback("settingApplyPenalOnFeedback", this.settingApplyPenalOnField);

        this.row4Block1 = this.row4.newUIBlock("row4Block1", Size.Three_3);

        this.row5 = UIRow.newUIRow("row5", this.form);

        this.settingWithholdTaxApplicableBlock = this.row5.newUIBlock("settingWithholdTaxApplicableBlock", Size.Four_4);
        this.settingWithholdTaxApplicableIContainer = this.settingWithholdTaxApplicableBlock.newUIContainer("settingWithholdTaxApplicableIContainer");
        this.settingWithholdTaxApplicableField = new CheckBox("settingWithholdTaxApplicableField", new PropertyModel<>(this.itemPage, "settingWithholdTaxApplicableValue"));
        this.settingWithholdTaxApplicableIContainer.add(this.settingWithholdTaxApplicableField);
        this.settingWithholdTaxApplicableIContainer.newFeedback("settingWithholdTaxApplicableFeedback", this.settingWithholdTaxApplicableField);

        this.settingTaxGroupBlock = this.row5.newUIBlock("settingTaxGroupBlock", Size.Four_4);
        this.settingTaxGroupIContainer = this.settingTaxGroupBlock.newUIContainer("settingTaxGroupIContainer");
        this.settingTaxGroupField = new Select2SingleChoice<>("settingTaxGroupField", new PropertyModel<>(this.itemPage, "settingTaxGroupValue"), this.settingTaxGroupProvider);
        this.settingTaxGroupIContainer.add(this.settingTaxGroupField);
        this.settingTaxGroupIContainer.newFeedback("settingTaxGroupFeedback", this.settingTaxGroupField);

        this.settingBalanceRequiredForInterestCalculationBlock = this.row5.newUIBlock("settingBalanceRequiredForInterestCalculationBlock", Size.Four_4);
        this.settingBalanceRequiredForInterestCalculationIContainer = this.settingBalanceRequiredForInterestCalculationBlock.newUIContainer("settingBalanceRequiredForInterestCalculationIContainer");
        this.settingBalanceRequiredForInterestCalculationField = new TextField<>("settingBalanceRequiredForInterestCalculationField", new PropertyModel<>(this.itemPage, "settingBalanceRequiredForInterestCalculationValue"));
        this.settingBalanceRequiredForInterestCalculationIContainer.add(this.settingBalanceRequiredForInterestCalculationField);
        this.settingBalanceRequiredForInterestCalculationIContainer.newFeedback("settingBalanceRequiredForInterestCalculationFeedback", this.settingBalanceRequiredForInterestCalculationField);

    }

    @Override
    protected void configureMetaData() {
        this.settingBalanceRequiredForInterestCalculationField.setLabel(Model.of("Balance Required For Interest Calculation"));
        this.settingBalanceRequiredForInterestCalculationField.add(new OnChangeAjaxBehavior());
        this.settingBalanceRequiredForInterestCalculationField.setRequired(true);
        this.settingBalanceRequiredForInterestCalculationField.add(RangeValidator.minimum(0d));

        this.settingTaxGroupField.setLabel(Model.of("Tax Group"));
        this.settingTaxGroupField.add(new OnChangeAjaxBehavior());
        this.settingTaxGroupField.setRequired(true);

        this.settingWithholdTaxApplicableField.add(new OnChangeAjaxBehavior(this::settingWithholdTaxApplicableFieldUpdate));

        this.settingApplyPenalOnField.setLabel(Model.of("On"));
        this.settingApplyPenalOnField.add(new OnChangeAjaxBehavior());
        this.settingApplyPenalOnField.setRequired(true);

        this.settingApplyPenalInterestField.setLabel(Model.of("Apply penal interest"));
        this.settingApplyPenalInterestField.add(new OnChangeAjaxBehavior());
        this.settingApplyPenalInterestField.setRequired(true);
        this.settingApplyPenalInterestField.add(RangeValidator.minimum(0d));

        this.settingForPreMatureClosureField.add(new OnChangeAjaxBehavior());

        this.settingMaximumDepositTypeField.setLabel(Model.of("Type"));
        this.settingMaximumDepositTypeField.add(new OnChangeAjaxBehavior());
        this.settingMaximumDepositTypeField.setRequired(true);

        this.settingMaximumDepositTermField.setLabel(Model.of("Maximum Deposit Term"));
        this.settingMaximumDepositTermField.add(new OnChangeAjaxBehavior());
        this.settingMaximumDepositTermField.setRequired(true);
        this.settingMaximumDepositTermField.add(RangeValidator.minimum(0l));

        this.settingInMultiplesTypeField.setLabel(Model.of("Type"));
        this.settingInMultiplesTypeField.add(new OnChangeAjaxBehavior());
        this.settingInMultiplesTypeField.setRequired(true);

        this.settingInMultiplesOfField.setLabel(Model.of("And thereafter, In Multiples of"));
        this.settingInMultiplesOfField.add(new OnChangeAjaxBehavior());
        this.settingInMultiplesOfField.setRequired(true);
        this.settingInMultiplesOfField.add(RangeValidator.minimum(0l));

        this.settingMinimumDepositTypeField.setLabel(Model.of("Type"));
        this.settingMinimumDepositTypeField.add(new OnChangeAjaxBehavior());
        this.settingMinimumDepositTypeField.setRequired(true);

        this.settingMinimumDepositTermField.setLabel(Model.of("Minimum Deposit Term"));
        this.settingMinimumDepositTermField.add(new OnChangeAjaxBehavior());
        this.settingMinimumDepositTermField.setRequired(true);
        this.settingMinimumDepositTermField.add(RangeValidator.minimum(0l));

        this.settingLockInTypeField.setLabel(Model.of("Type"));
        this.settingLockInTypeField.add(new OnChangeAjaxBehavior());
        this.settingLockInTypeField.setRequired(true);

        this.settingLockInPeriodField.add(new OnChangeAjaxBehavior());
        this.settingLockInPeriodField.setLabel(Model.of("Lock-in period"));
        this.settingLockInPeriodField.add(RangeValidator.minimum(0l));
        this.settingLockInPeriodField.setRequired(true);

        this.settingAllowWithdrawalField.add(new OnChangeAjaxBehavior());

        this.settingAdjustAdvancePaymentField.add(new OnChangeAjaxBehavior());

        this.settingMandatoryDepositField.add(new OnChangeAjaxBehavior());

        settingWithholdTaxApplicableFieldUpdate(null);
    }

    protected boolean settingWithholdTaxApplicableFieldUpdate(AjaxRequestTarget target) {
        PropertyModel<Boolean> settingWithholdTaxApplicableValue = new PropertyModel<>(this.itemPage, "settingWithholdTaxApplicableValue");
        boolean visible = settingWithholdTaxApplicableValue.getObject() != null && settingWithholdTaxApplicableValue.getObject();
        this.settingTaxGroupIContainer.setVisible(visible);
        if (target != null) {
            target.add(this.settingTaxGroupBlock);
        }
        return false;
    }

    protected boolean backLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.tab.getObject().setSelectedTab(RecurringCreatePage.TAB_TERM);
        if (target != null) {
            target.add(this.tab.getObject());
        }
        return false;
    }

    protected void nextButtonSubmit(Button button) {
        this.tab.getObject().setSelectedTab(RecurringCreatePage.TAB_INTEREST_RATE_CHART);
        this.errorSetting.setObject(false);
    }

    protected void nextButtonError(Button button) {
        this.errorSetting.setObject(true);
    }

}
