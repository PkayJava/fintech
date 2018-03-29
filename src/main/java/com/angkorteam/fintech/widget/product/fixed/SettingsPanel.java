package com.angkorteam.fintech.widget.product.fixed;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.ddl.MTaxGroup;
import com.angkorteam.fintech.pages.product.fixed.FixedBrowsePage;
import com.angkorteam.fintech.pages.product.fixed.FixedCreatePage;
import com.angkorteam.fintech.provider.ApplyPenalOnProvider;
import com.angkorteam.fintech.provider.LockInTypeProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.Panel;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
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

    protected WebMarkupBlock settingLockInPeriodBlock;
    protected WebMarkupContainer settingLockInPeriodIContainer;
    protected TextField<Long> settingLockInPeriodField;
    protected TextFeedbackPanel settingLockInPeriodFeedback;

    protected WebMarkupBlock settingLockInTypeBlock;
    protected WebMarkupContainer settingLockInTypeIContainer;
    protected LockInTypeProvider settingLockInTypeProvider;
    protected Select2SingleChoice<Option> settingLockInTypeField;
    protected TextFeedbackPanel settingLockInTypeFeedback;

    protected WebMarkupBlock settingMinimumDepositTermBlock;
    protected WebMarkupContainer settingMinimumDepositTermIContainer;
    protected TextField<Long> settingMinimumDepositTermField;
    protected TextFeedbackPanel settingMinimumDepositTermFeedback;

    protected WebMarkupBlock settingMinimumDepositTypeBlock;
    protected WebMarkupContainer settingMinimumDepositTypeIContainer;
    protected LockInTypeProvider settingMinimumDepositTypeProvider;
    protected Select2SingleChoice<Option> settingMinimumDepositTypeField;
    protected TextFeedbackPanel settingMinimumDepositTypeFeedback;

    protected WebMarkupBlock settingInMultiplesOfBlock;
    protected WebMarkupContainer settingInMultiplesOfIContainer;
    protected TextField<Long> settingInMultiplesOfField;
    protected TextFeedbackPanel settingInMultiplesOfFeedback;

    protected WebMarkupBlock settingInMultiplesTypeBlock;
    protected WebMarkupContainer settingInMultiplesTypeIContainer;
    protected LockInTypeProvider settingInMultiplesTypeProvider;
    protected Select2SingleChoice<Option> settingInMultiplesTypeField;
    protected TextFeedbackPanel settingInMultiplesTypeFeedback;

    protected WebMarkupBlock settingMaximumDepositTermBlock;
    protected WebMarkupContainer settingMaximumDepositTermIContainer;
    protected TextField<Long> settingMaximumDepositTermField;
    protected TextFeedbackPanel settingMaximumDepositTermFeedback;

    protected WebMarkupBlock settingMaximumDepositTypeBlock;
    protected WebMarkupContainer settingMaximumDepositTypeIContainer;
    protected LockInTypeProvider settingMaximumDepositTypeProvider;
    protected Select2SingleChoice<Option> settingMaximumDepositTypeField;
    protected TextFeedbackPanel settingMaximumDepositTypeFeedback;

    protected WebMarkupBlock settingForPreMatureClosureBlock;
    protected WebMarkupContainer settingForPreMatureClosureIContainer;
    protected CheckBox settingForPreMatureClosureField;
    protected TextFeedbackPanel settingForPreMatureClosureFeedback;

    protected WebMarkupBlock settingApplyPenalInterestBlock;
    protected WebMarkupContainer settingApplyPenalInterestIContainer;
    protected TextField<Double> settingApplyPenalInterestField;
    protected TextFeedbackPanel settingApplyPenalInterestFeedback;

    protected WebMarkupBlock settingApplyPenalOnBlock;
    protected WebMarkupContainer settingApplyPenalOnIContainer;
    protected ApplyPenalOnProvider settingApplyPenalOnProvider;
    protected Select2SingleChoice<Option> settingApplyPenalOnField;
    protected TextFeedbackPanel settingApplyPenalOnFeedback;

    protected WebMarkupBlock settingWithholdTaxApplicableBlock;
    protected WebMarkupContainer settingWithholdTaxApplicableIContainer;
    protected CheckBox settingWithholdTaxApplicableField;
    protected TextFeedbackPanel settingWithholdTaxApplicableFeedback;

    protected WebMarkupBlock settingTaxGroupBlock;
    protected WebMarkupContainer settingTaxGroupIContainer;
    protected SingleChoiceProvider settingTaxGroupProvider;
    protected Select2SingleChoice<Option> settingTaxGroupField;
    protected TextFeedbackPanel settingTaxGroupFeedback;

    public SettingsPanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
    }

    @Override
    protected void initData() {
        this.errorSetting = new PropertyModel<>(this.itemPage, "errorSetting");
        this.tab = new PropertyModel<>(this.itemPage, "tab");
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

        this.closeLink = new BookmarkablePageLink<>("closeLink", FixedBrowsePage.class);
        this.form.add(this.closeLink);

        this.settingLockInPeriodBlock = new WebMarkupBlock("settingLockInPeriodBlock", Size.Three_3);
        this.form.add(this.settingLockInPeriodBlock);
        this.settingLockInPeriodIContainer = new WebMarkupContainer("settingLockInPeriodIContainer");
        this.settingLockInPeriodBlock.add(this.settingLockInPeriodIContainer);
        this.settingLockInPeriodField = new TextField<>("settingLockInPeriodField", new PropertyModel<>(this.itemPage, "settingLockInPeriodValue"));
        this.settingLockInPeriodField.add(new OnChangeAjaxBehavior());
        this.settingLockInPeriodField.setLabel(Model.of("Lock-in period"));
        this.settingLockInPeriodIContainer.add(this.settingLockInPeriodField);
        this.settingLockInPeriodFeedback = new TextFeedbackPanel("settingLockInPeriodFeedback", this.settingLockInPeriodField);
        this.settingLockInPeriodIContainer.add(this.settingLockInPeriodFeedback);

        this.settingLockInTypeBlock = new WebMarkupBlock("settingLockInTypeBlock", Size.Three_3);
        this.form.add(this.settingLockInTypeBlock);
        this.settingLockInTypeIContainer = new WebMarkupContainer("settingLockInTypeIContainer");
        this.settingLockInTypeBlock.add(this.settingLockInTypeIContainer);
        this.settingLockInTypeProvider = new LockInTypeProvider();
        this.settingLockInTypeField = new Select2SingleChoice<>("settingLockInTypeField", new PropertyModel<>(this.itemPage, "settingLockInTypeValue"), this.settingLockInTypeProvider);
        this.settingLockInTypeField.setLabel(Model.of("Type"));
        this.settingLockInTypeField.add(new OnChangeAjaxBehavior());
        this.settingLockInTypeIContainer.add(this.settingLockInTypeField);
        this.settingLockInTypeFeedback = new TextFeedbackPanel("settingLockInTypeFeedback", this.settingLockInTypeField);
        this.settingLockInTypeIContainer.add(this.settingLockInTypeFeedback);

        this.settingMinimumDepositTermBlock = new WebMarkupBlock("settingMinimumDepositTermBlock", Size.Three_3);
        this.form.add(this.settingMinimumDepositTermBlock);
        this.settingMinimumDepositTermIContainer = new WebMarkupContainer("settingMinimumDepositTermIContainer");
        this.settingMinimumDepositTermBlock.add(this.settingMinimumDepositTermIContainer);
        this.settingMinimumDepositTermField = new TextField<>("settingMinimumDepositTermField", new PropertyModel<>(this.itemPage, "settingMinimumDepositTermValue"));
        this.settingMinimumDepositTermField.setLabel(Model.of("Minimum Deposit Term"));
        this.settingMinimumDepositTermField.add(new OnChangeAjaxBehavior());
        this.settingMinimumDepositTermIContainer.add(this.settingMinimumDepositTermField);
        this.settingMinimumDepositTermFeedback = new TextFeedbackPanel("settingMinimumDepositTermFeedback", this.settingMinimumDepositTermField);
        this.settingMinimumDepositTermIContainer.add(this.settingMinimumDepositTermFeedback);

        this.settingMinimumDepositTypeBlock = new WebMarkupBlock("settingMinimumDepositTypeBlock", Size.Three_3);
        this.form.add(this.settingMinimumDepositTypeBlock);
        this.settingMinimumDepositTypeIContainer = new WebMarkupContainer("settingMinimumDepositTypeIContainer");
        this.settingMinimumDepositTypeBlock.add(this.settingMinimumDepositTypeIContainer);
        this.settingMinimumDepositTypeProvider = new LockInTypeProvider();
        this.settingMinimumDepositTypeField = new Select2SingleChoice<>("settingMinimumDepositTypeField", new PropertyModel<>(this.itemPage, "settingMinimumDepositTypeValue"), this.settingMinimumDepositTypeProvider);
        this.settingMinimumDepositTypeField.setLabel(Model.of("Type"));
        this.settingMinimumDepositTypeField.add(new OnChangeAjaxBehavior());
        this.settingMinimumDepositTypeIContainer.add(this.settingMinimumDepositTypeField);
        this.settingMinimumDepositTypeFeedback = new TextFeedbackPanel("settingMinimumDepositTypeFeedback", this.settingMinimumDepositTypeField);
        this.settingMinimumDepositTypeIContainer.add(this.settingMinimumDepositTypeFeedback);

        this.settingInMultiplesOfBlock = new WebMarkupBlock("settingInMultiplesOfBlock", Size.Three_3);
        this.form.add(this.settingInMultiplesOfBlock);
        this.settingInMultiplesOfIContainer = new WebMarkupContainer("settingInMultiplesOfIContainer");
        this.settingInMultiplesOfBlock.add(this.settingInMultiplesOfIContainer);
        this.settingInMultiplesOfField = new TextField<>("settingInMultiplesOfField", new PropertyModel<>(this.itemPage, "settingInMultiplesOfValue"));
        this.settingInMultiplesOfField.setLabel(Model.of("And thereafter, In Multiples of"));
        this.settingInMultiplesOfField.add(new OnChangeAjaxBehavior());
        this.settingInMultiplesOfIContainer.add(this.settingInMultiplesOfField);
        this.settingInMultiplesOfFeedback = new TextFeedbackPanel("settingInMultiplesOfFeedback", this.settingInMultiplesOfField);
        this.settingInMultiplesOfIContainer.add(this.settingInMultiplesOfFeedback);

        this.settingInMultiplesTypeBlock = new WebMarkupBlock("settingInMultiplesTypeBlock", Size.Three_3);
        this.form.add(this.settingInMultiplesTypeBlock);
        this.settingInMultiplesTypeIContainer = new WebMarkupContainer("settingInMultiplesTypeIContainer");
        this.settingInMultiplesTypeBlock.add(this.settingInMultiplesTypeIContainer);
        this.settingInMultiplesTypeProvider = new LockInTypeProvider();
        this.settingInMultiplesTypeField = new Select2SingleChoice<>("settingInMultiplesTypeField", new PropertyModel<>(this.itemPage, "settingInMultiplesTypeValue"), this.settingInMultiplesTypeProvider);
        this.settingInMultiplesTypeField.setLabel(Model.of("Type"));
        this.settingInMultiplesTypeField.add(new OnChangeAjaxBehavior());
        this.settingInMultiplesTypeIContainer.add(this.settingInMultiplesTypeField);
        this.settingInMultiplesTypeFeedback = new TextFeedbackPanel("settingInMultiplesTypeFeedback", this.settingInMultiplesTypeField);
        this.settingInMultiplesTypeIContainer.add(this.settingInMultiplesTypeFeedback);

        this.settingMaximumDepositTermBlock = new WebMarkupBlock("settingMaximumDepositTermBlock", Size.Three_3);
        this.form.add(this.settingMaximumDepositTermBlock);
        this.settingMaximumDepositTermIContainer = new WebMarkupContainer("settingMaximumDepositTermIContainer");
        this.settingMaximumDepositTermBlock.add(this.settingMaximumDepositTermIContainer);
        this.settingMaximumDepositTermField = new TextField<>("settingMaximumDepositTermField", new PropertyModel<>(this.itemPage, "settingMaximumDepositTermValue"));
        this.settingMaximumDepositTermField.setLabel(Model.of("Maximum Deposit Term"));
        this.settingMaximumDepositTermField.add(new OnChangeAjaxBehavior());
        this.settingMaximumDepositTermIContainer.add(this.settingMaximumDepositTermField);
        this.settingMaximumDepositTermFeedback = new TextFeedbackPanel("settingMaximumDepositTermFeedback", this.settingMaximumDepositTermField);
        this.settingMaximumDepositTermIContainer.add(this.settingMaximumDepositTermFeedback);

        this.settingMaximumDepositTypeBlock = new WebMarkupBlock("settingMaximumDepositTypeBlock", Size.Three_3);
        this.form.add(this.settingMaximumDepositTypeBlock);
        this.settingMaximumDepositTypeIContainer = new WebMarkupContainer("settingMaximumDepositTypeIContainer");
        this.settingMaximumDepositTypeBlock.add(this.settingMaximumDepositTypeIContainer);
        this.settingMaximumDepositTypeProvider = new LockInTypeProvider();
        this.settingMaximumDepositTypeField = new Select2SingleChoice<>("settingMaximumDepositTypeField", new PropertyModel<>(this.itemPage, "settingMaximumDepositTypeValue"), this.settingMaximumDepositTypeProvider);
        this.settingMaximumDepositTypeField.setLabel(Model.of("Type"));
        this.settingMaximumDepositTypeField.add(new OnChangeAjaxBehavior());
        this.settingMaximumDepositTypeIContainer.add(this.settingMaximumDepositTypeField);
        this.settingMaximumDepositTypeFeedback = new TextFeedbackPanel("settingMaximumDepositTypeFeedback", this.settingMaximumDepositTypeField);
        this.settingMaximumDepositTypeIContainer.add(this.settingMaximumDepositTypeFeedback);

        this.settingForPreMatureClosureBlock = new WebMarkupBlock("settingForPreMatureClosureBlock", Size.Three_3);
        this.form.add(this.settingForPreMatureClosureBlock);
        this.settingForPreMatureClosureIContainer = new WebMarkupContainer("settingForPreMatureClosureIContainer");
        this.settingForPreMatureClosureBlock.add(this.settingForPreMatureClosureIContainer);
        this.settingForPreMatureClosureField = new CheckBox("settingForPreMatureClosureField", new PropertyModel<>(this.itemPage, "settingForPreMatureClosureValue"));
        this.settingForPreMatureClosureField.add(new OnChangeAjaxBehavior());
        this.settingForPreMatureClosureIContainer.add(this.settingForPreMatureClosureField);
        this.settingForPreMatureClosureFeedback = new TextFeedbackPanel("settingForPreMatureClosureFeedback", this.settingForPreMatureClosureField);
        this.settingForPreMatureClosureIContainer.add(this.settingForPreMatureClosureFeedback);

        this.settingApplyPenalInterestBlock = new WebMarkupBlock("settingApplyPenalInterestBlock", Size.Three_3);
        this.form.add(this.settingApplyPenalInterestBlock);
        this.settingApplyPenalInterestIContainer = new WebMarkupContainer("settingApplyPenalInterestIContainer");
        this.settingApplyPenalInterestBlock.add(this.settingApplyPenalInterestIContainer);
        this.settingApplyPenalInterestField = new TextField<>("settingApplyPenalInterestField", new PropertyModel<>(this.itemPage, "settingApplyPenalInterestValue"));
        this.settingApplyPenalInterestField.setLabel(Model.of("Apply penal interest"));
        this.settingApplyPenalInterestField.add(new OnChangeAjaxBehavior());
        this.settingApplyPenalInterestIContainer.add(this.settingApplyPenalInterestField);
        this.settingApplyPenalInterestFeedback = new TextFeedbackPanel("settingApplyPenalInterestFeedback", this.settingApplyPenalInterestField);
        this.settingApplyPenalInterestIContainer.add(this.settingApplyPenalInterestFeedback);

        this.settingApplyPenalOnBlock = new WebMarkupBlock("settingApplyPenalOnBlock", Size.Three_3);
        this.form.add(this.settingApplyPenalOnBlock);
        this.settingApplyPenalOnIContainer = new WebMarkupContainer("settingApplyPenalOnIContainer");
        this.settingApplyPenalOnBlock.add(this.settingApplyPenalOnIContainer);
        this.settingApplyPenalOnProvider = new ApplyPenalOnProvider();
        this.settingApplyPenalOnField = new Select2SingleChoice<>("settingApplyPenalOnField", new PropertyModel<>(this.itemPage, "settingApplyPenalOnValue"), this.settingApplyPenalOnProvider);
        this.settingApplyPenalOnField.setLabel(Model.of("On"));
        this.settingApplyPenalOnField.add(new OnChangeAjaxBehavior());
        this.settingApplyPenalOnIContainer.add(this.settingApplyPenalOnField);
        this.settingApplyPenalOnFeedback = new TextFeedbackPanel("settingApplyPenalOnFeedback", this.settingApplyPenalOnField);
        this.settingApplyPenalOnIContainer.add(this.settingApplyPenalOnFeedback);

        this.settingWithholdTaxApplicableBlock = new WebMarkupBlock("settingWithholdTaxApplicableBlock", Size.Six_6);
        this.form.add(this.settingWithholdTaxApplicableBlock);
        this.settingWithholdTaxApplicableIContainer = new WebMarkupContainer("settingWithholdTaxApplicableIContainer");
        this.settingWithholdTaxApplicableBlock.add(this.settingWithholdTaxApplicableIContainer);
        this.settingWithholdTaxApplicableField = new CheckBox("settingWithholdTaxApplicableField", new PropertyModel<>(this.itemPage, "settingWithholdTaxApplicableValue"));
        this.settingWithholdTaxApplicableField.add(new OnChangeAjaxBehavior(this::settingWithholdTaxApplicableFieldUpdate));
        this.settingWithholdTaxApplicableIContainer.add(this.settingWithholdTaxApplicableField);
        this.settingWithholdTaxApplicableFeedback = new TextFeedbackPanel("settingWithholdTaxApplicableFeedback", this.settingWithholdTaxApplicableField);
        this.settingWithholdTaxApplicableIContainer.add(this.settingWithholdTaxApplicableFeedback);

        this.settingTaxGroupBlock = new WebMarkupBlock("settingTaxGroupBlock", Size.Six_6);
        this.form.add(this.settingTaxGroupBlock);
        this.settingTaxGroupIContainer = new WebMarkupContainer("settingTaxGroupIContainer");
        this.settingTaxGroupBlock.add(this.settingTaxGroupIContainer);
        this.settingTaxGroupProvider = new SingleChoiceProvider(MTaxGroup.NAME, MTaxGroup.Field.ID, MTaxGroup.Field.NAME);
        this.settingTaxGroupField = new Select2SingleChoice<>("settingTaxGroupField", new PropertyModel<>(this.itemPage, "settingTaxGroupValue"), this.settingTaxGroupProvider);
        this.settingTaxGroupField.setLabel(Model.of("Tax Group"));
        this.settingTaxGroupField.add(new OnChangeAjaxBehavior());
        this.settingTaxGroupIContainer.add(this.settingTaxGroupField);
        this.settingTaxGroupFeedback = new TextFeedbackPanel("settingTaxGroupFeedback", this.settingTaxGroupField);
        this.settingTaxGroupIContainer.add(this.settingTaxGroupFeedback);
    }

    @Override
    protected void configureMetaData() {
        this.settingMinimumDepositTermField.setRequired(true);
        this.settingMinimumDepositTypeField.setRequired(true);

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
        this.tab.getObject().setSelectedTab(FixedCreatePage.TAB_TERM);
        if (target != null) {
            target.add(this.tab.getObject());
        }
        return false;
    }

    protected void nextButtonSubmit(Button button) {
        this.tab.getObject().setSelectedTab(FixedCreatePage.TAB_INTEREST_RATE_CHART);
        this.errorSetting.setObject(false);
    }

    protected void nextButtonError(Button button) {
        this.errorSetting.setObject(true);
    }

}
