package com.angkorteam.fintech.widget.product.share;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.ddl.AccGLAccount;
import com.angkorteam.fintech.dto.enums.AccountType;
import com.angkorteam.fintech.dto.enums.AccountUsage;
import com.angkorteam.fintech.dto.enums.AccountingType;
import com.angkorteam.fintech.pages.product.share.ShareBrowsePage;
import com.angkorteam.fintech.pages.product.share.ShareCreatePage;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.Panel;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.wicket.ajax.form.AjaxFormChoiceComponentUpdatingBehavior;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

public class AccountingPanel extends Panel {

    protected Page itemPage;
    protected PropertyModel<AjaxTabbedPanel<ITab>> tab;
    protected PropertyModel<Boolean> errorAccounting;

    protected Form<Void> form;
    protected Button nextButton;
    protected AjaxLink<Void> backLink;
    protected BookmarkablePageLink<Void> closeLink;

    protected RadioGroup<String> accountingField;

    protected WebMarkupContainer cashBlock;
    protected WebMarkupContainer cashIContainer;

    protected WebMarkupBlock cashShareReferenceBlock;
    protected WebMarkupContainer cashShareReferenceIContainer;
    protected SingleChoiceProvider cashShareReferenceProvider;
    protected Select2SingleChoice<Option> cashShareReferenceField;
    protected TextFeedbackPanel cashShareReferenceFeedback;

    protected WebMarkupBlock cashShareSuspenseControlBlock;
    protected WebMarkupContainer cashShareSuspenseControlIContainer;
    protected SingleChoiceProvider cashShareSuspenseControlProvider;
    protected Select2SingleChoice<Option> cashShareSuspenseControlField;
    protected TextFeedbackPanel cashShareSuspenseControlFeedback;

    protected WebMarkupBlock cashEquityBlock;
    protected WebMarkupContainer cashEquityIContainer;
    protected SingleChoiceProvider cashEquityProvider;
    protected Select2SingleChoice<Option> cashEquityField;
    protected TextFeedbackPanel cashEquityFeedback;

    protected WebMarkupBlock cashIncomeFromFeeBlock;
    protected WebMarkupContainer cashIncomeFromFeeIContainer;
    protected SingleChoiceProvider cashIncomeFromFeeProvider;
    protected Select2SingleChoice<Option> cashIncomeFromFeeField;
    protected TextFeedbackPanel cashIncomeFromFeeFeedback;

    public AccountingPanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
    }

    @Override
    protected void initData() {
        this.errorAccounting = new PropertyModel<>(this.itemPage, "errorAccounting");
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

        this.closeLink = new BookmarkablePageLink<>("closeLink", ShareBrowsePage.class);
        this.form.add(this.closeLink);

        this.accountingField = new RadioGroup<>("accountingField", new PropertyModel<>(this.itemPage, "accountingValue"));
        this.accountingField.add(new AjaxFormChoiceComponentUpdatingBehavior(this::accountingFieldUpdate));
        this.accountingField.add(new Radio<>("accountingNone", new Model<>(AccountingType.None.getDescription())));
        this.accountingField.add(new Radio<>("accountingCash", new Model<>(AccountingType.Cash.getDescription())));
        this.form.add(this.accountingField);

        this.cashBlock = new WebMarkupContainer("cashBlock");
        this.cashBlock.setOutputMarkupId(true);
        this.form.add(this.cashBlock);
        this.cashIContainer = new WebMarkupContainer("cashIContainer");
        this.cashBlock.add(this.cashIContainer);

        this.cashShareReferenceBlock = new WebMarkupBlock("cashShareReferenceBlock", Size.Six_6);
        this.cashIContainer.add(this.cashShareReferenceBlock);
        this.cashShareReferenceIContainer = new WebMarkupContainer("cashShareReferenceIContainer");
        this.cashShareReferenceBlock.add(this.cashShareReferenceIContainer);
        this.cashShareReferenceProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.cashShareReferenceProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.cashShareReferenceProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Asset.getLiteral());
        this.cashShareReferenceField = new Select2SingleChoice<>("cashShareReferenceField", new PropertyModel<>(this.itemPage, "cashShareReferenceValue"), this.cashShareReferenceProvider);
        this.cashShareReferenceField.setLabel(Model.of("Share Reference"));
        this.cashShareReferenceField.add(new OnChangeAjaxBehavior());
        this.cashShareReferenceIContainer.add(this.cashShareReferenceField);
        this.cashShareReferenceFeedback = new TextFeedbackPanel("cashShareReferenceFeedback", this.cashShareReferenceField);
        this.cashShareReferenceIContainer.add(this.cashShareReferenceFeedback);

        this.cashShareSuspenseControlBlock = new WebMarkupBlock("cashShareSuspenseControlBlock", Size.Six_6);
        this.cashIContainer.add(this.cashShareSuspenseControlBlock);
        this.cashShareSuspenseControlIContainer = new WebMarkupContainer("cashShareSuspenseControlIContainer");
        this.cashShareSuspenseControlBlock.add(this.cashShareSuspenseControlIContainer);
        this.cashShareSuspenseControlProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.cashShareSuspenseControlProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.cashShareSuspenseControlProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Liability.getLiteral());
        this.cashShareSuspenseControlField = new Select2SingleChoice<>("cashShareSuspenseControlField", new PropertyModel<>(this.itemPage, "cashShareSuspenseControlValue"), this.cashShareSuspenseControlProvider);
        this.cashShareSuspenseControlField.setLabel(Model.of("Share Suspense Control"));
        this.cashShareSuspenseControlField.add(new OnChangeAjaxBehavior());
        this.cashShareSuspenseControlIContainer.add(this.cashShareSuspenseControlField);
        this.cashShareSuspenseControlFeedback = new TextFeedbackPanel("cashShareSuspenseControlFeedback", this.cashShareSuspenseControlField);
        this.cashShareSuspenseControlIContainer.add(this.cashShareSuspenseControlFeedback);

        this.cashEquityBlock = new WebMarkupBlock("cashEquityBlock", Size.Six_6);
        this.cashIContainer.add(this.cashEquityBlock);
        this.cashEquityIContainer = new WebMarkupContainer("cashEquityIContainer");
        this.cashEquityBlock.add(this.cashEquityIContainer);
        this.cashEquityProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.cashEquityProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.cashEquityProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Equity.getLiteral());
        this.cashEquityField = new Select2SingleChoice<>("cashEquityField", new PropertyModel<>(this.itemPage, "cashEquityValue"), this.cashEquityProvider);
        this.cashEquityField.setLabel(Model.of("Equity"));
        this.cashEquityField.add(new OnChangeAjaxBehavior());
        this.cashEquityIContainer.add(this.cashEquityField);
        this.cashEquityFeedback = new TextFeedbackPanel("cashEquityFeedback", this.cashEquityField);
        this.cashEquityIContainer.add(this.cashEquityFeedback);

        this.cashIncomeFromFeeBlock = new WebMarkupBlock("cashIncomeFromFeeBlock", Size.Six_6);
        this.cashIContainer.add(this.cashIncomeFromFeeBlock);
        this.cashIncomeFromFeeIContainer = new WebMarkupContainer("cashIncomeFromFeeIContainer");
        this.cashIncomeFromFeeBlock.add(this.cashIncomeFromFeeIContainer);
        this.cashIncomeFromFeeProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.cashIncomeFromFeeProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.cashIncomeFromFeeProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Income.getLiteral());
        this.cashIncomeFromFeeField = new Select2SingleChoice<>("cashIncomeFromFeeField", new PropertyModel<>(this.itemPage, "cashIncomeFromFeeValue"), this.cashIncomeFromFeeProvider);
        this.cashIncomeFromFeeField.setLabel(Model.of("Income From Fees"));
        this.cashIncomeFromFeeField.add(new OnChangeAjaxBehavior());
        this.cashIncomeFromFeeIContainer.add(this.cashIncomeFromFeeField);
        this.cashIncomeFromFeeFeedback = new TextFeedbackPanel("cashIncomeFromFeeFeedback", this.cashIncomeFromFeeField);
        this.cashIncomeFromFeeIContainer.add(this.cashIncomeFromFeeFeedback);

    }

    @Override
    protected void configureMetaData() {
        this.accountingField.setRequired(true);
        accountingFieldUpdate(null);
    }

    protected boolean backLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.tab.getObject().setSelectedTab(ShareCreatePage.TAB_CHARGE);
        if (target != null) {
            target.add(this.tab.getObject());
        }
        return false;
    }

    protected void nextButtonSubmit(Button button) {
        this.tab.getObject().setSelectedTab(ShareCreatePage.TAB_PREVIEW);
        this.errorAccounting.setObject(false);
    }

    protected void nextButtonError(Button button) {
        this.errorAccounting.setObject(true);
    }

    protected boolean accountingFieldUpdate(AjaxRequestTarget target) {
        this.cashIContainer.setVisible(false);
        PropertyModel<String> accountingValue = new PropertyModel<>(this.itemPage, "accountingValue");
        if (AccountingType.Cash.getDescription().equals(accountingValue.getObject())) {
            this.cashIContainer.setVisible(true);
        }

        if (target != null) {
            target.add(this.cashBlock);
        }
        return false;
    }

}
