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
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.pages.product.share.ShareBrowsePage;
import com.angkorteam.fintech.pages.product.share.ShareCreatePage;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.Panel;
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

    protected UIRow row1;

    protected UIBlock cashShareReferenceBlock;
    protected UIContainer cashShareReferenceIContainer;
    protected SingleChoiceProvider cashShareReferenceProvider;
    protected Select2SingleChoice<Option> cashShareReferenceField;

    protected UIBlock row1Block1;

    protected UIRow row2;

    protected UIBlock cashShareSuspenseControlBlock;
    protected UIContainer cashShareSuspenseControlIContainer;
    protected SingleChoiceProvider cashShareSuspenseControlProvider;
    protected Select2SingleChoice<Option> cashShareSuspenseControlField;

    protected UIBlock row2Block1;

    protected UIRow row3;

    protected UIBlock cashEquityBlock;
    protected UIContainer cashEquityIContainer;
    protected SingleChoiceProvider cashEquityProvider;
    protected Select2SingleChoice<Option> cashEquityField;

    protected UIBlock row3Block1;

    protected UIRow row4;

    protected UIBlock cashIncomeFromFeeBlock;
    protected UIContainer cashIncomeFromFeeIContainer;
    protected SingleChoiceProvider cashIncomeFromFeeProvider;
    protected Select2SingleChoice<Option> cashIncomeFromFeeField;

    protected UIBlock row4Block1;

    public AccountingPanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
    }

    @Override
    protected void initData() {
        this.errorAccounting = new PropertyModel<>(this.itemPage, "errorAccounting");
        this.tab = new PropertyModel<>(this.itemPage, "tab");

        this.cashShareReferenceProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.cashShareReferenceProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.cashShareReferenceProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Asset.getLiteral());

        this.cashShareSuspenseControlProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.cashShareSuspenseControlProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.cashShareSuspenseControlProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Liability.getLiteral());

        this.cashEquityProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.cashEquityProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.cashEquityProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Equity.getLiteral());

        this.cashIncomeFromFeeProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.cashIncomeFromFeeProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.cashIncomeFromFeeProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Income.getLiteral());
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
        this.cashIContainer.setOutputMarkupId(true);
        this.cashBlock.add(this.cashIContainer);

        this.row1 = UIRow.newUIRow("row1", this.cashIContainer);

        this.cashShareReferenceBlock = this.row1.newUIBlock("cashShareReferenceBlock", Size.Six_6);
        this.cashShareReferenceIContainer = this.cashShareReferenceBlock.newUIContainer("cashShareReferenceIContainer");
        this.cashShareReferenceField = new Select2SingleChoice<>("cashShareReferenceField", new PropertyModel<>(this.itemPage, "cashShareReferenceValue"), this.cashShareReferenceProvider);
        this.cashShareReferenceIContainer.add(this.cashShareReferenceField);
        this.cashShareReferenceIContainer.newFeedback("cashShareReferenceFeedback", this.cashShareReferenceField);

        this.row1Block1 = this.row1.newUIBlock("row1Block1", Size.Six_6);

        this.row2 = UIRow.newUIRow("row2", this.cashIContainer);

        this.cashShareSuspenseControlBlock = this.row2.newUIBlock("cashShareSuspenseControlBlock", Size.Six_6);
        this.cashShareSuspenseControlIContainer = this.cashShareSuspenseControlBlock.newUIContainer("cashShareSuspenseControlIContainer");
        this.cashShareSuspenseControlField = new Select2SingleChoice<>("cashShareSuspenseControlField", new PropertyModel<>(this.itemPage, "cashShareSuspenseControlValue"), this.cashShareSuspenseControlProvider);
        this.cashShareSuspenseControlIContainer.add(this.cashShareSuspenseControlField);
        this.cashShareSuspenseControlIContainer.newFeedback("cashShareSuspenseControlFeedback", this.cashShareSuspenseControlField);

        this.row2Block1 = this.row2.newUIBlock("row2Block1", Size.Six_6);

        this.row3 = UIRow.newUIRow("row3", this.cashIContainer);

        this.cashEquityBlock = this.row3.newUIBlock("cashEquityBlock", Size.Six_6);
        this.cashEquityIContainer = this.cashEquityBlock.newUIContainer("cashEquityIContainer");
        this.cashEquityField = new Select2SingleChoice<>("cashEquityField", new PropertyModel<>(this.itemPage, "cashEquityValue"), this.cashEquityProvider);
        this.cashEquityIContainer.add(this.cashEquityField);
        this.cashEquityIContainer.newFeedback("cashEquityFeedback", this.cashEquityField);

        this.row3Block1 = this.row3.newUIBlock("row3Block1", Size.Six_6);

        this.row4 = UIRow.newUIRow("row4", this.cashIContainer);

        this.cashIncomeFromFeeBlock = this.row4.newUIBlock("cashIncomeFromFeeBlock", Size.Six_6);
        this.cashIncomeFromFeeIContainer = this.cashIncomeFromFeeBlock.newUIContainer("cashIncomeFromFeeIContainer");
        this.cashIncomeFromFeeField = new Select2SingleChoice<>("cashIncomeFromFeeField", new PropertyModel<>(this.itemPage, "cashIncomeFromFeeValue"), this.cashIncomeFromFeeProvider);
        this.cashIncomeFromFeeIContainer.add(this.cashIncomeFromFeeField);
        this.cashIncomeFromFeeIContainer.newFeedback("cashIncomeFromFeeFeedback", this.cashIncomeFromFeeField);

        this.row4Block1 = this.row4.newUIBlock("row4Block1", Size.Six_6);

    }

    @Override
    protected void configureMetaData() {
        this.cashIncomeFromFeeField.setLabel(Model.of("Income From Fees"));
        this.cashIncomeFromFeeField.add(new OnChangeAjaxBehavior());
        this.cashIncomeFromFeeField.setRequired(true);

        this.cashEquityField.setLabel(Model.of("Equity"));
        this.cashEquityField.add(new OnChangeAjaxBehavior());
        this.cashEquityField.setRequired(true);

        this.cashShareSuspenseControlField.setLabel(Model.of("Share Suspense Control"));
        this.cashShareSuspenseControlField.add(new OnChangeAjaxBehavior());
        this.cashShareSuspenseControlField.setRequired(true);

        this.cashShareReferenceField.setLabel(Model.of("Share Reference"));
        this.cashShareReferenceField.add(new OnChangeAjaxBehavior());
        this.cashShareReferenceField.setRequired(true);

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
