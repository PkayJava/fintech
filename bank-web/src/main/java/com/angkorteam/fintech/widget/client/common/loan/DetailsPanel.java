//package com.angkorteam.fintech.widget.client.common.loan;
//
//import java.util.Date;
//
//import org.apache.wicket.Page;
//import org.apache.wicket.markup.html.form.CheckBox;
//import org.apache.wicket.markup.html.form.TextField;
//import org.apache.wicket.markup.html.link.BookmarkablePageLink;
//import org.apache.wicket.model.Model;
//import org.apache.wicket.model.PropertyModel;
//import org.apache.wicket.request.mapper.parameter.PageParameters;
//
//import com.angkorteam.fintech.ddl.MSavingsAccount;
//import com.angkorteam.fintech.ddl.MSavingsProduct;
//import com.angkorteam.fintech.ddl.MStaff;
//import com.angkorteam.fintech.dto.ClientEnum;
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.layout.UIBlock;
//import com.angkorteam.fintech.layout.UIContainer;
//import com.angkorteam.fintech.layout.UIRow;
//import com.angkorteam.fintech.pages.client.center.CenterPreviewPage;
//import com.angkorteam.fintech.pages.client.client.ClientPreviewPage;
//import com.angkorteam.fintech.pages.client.common.saving.AccountCreatePage;
//import com.angkorteam.fintech.pages.client.group.GroupPreviewPage;
//import com.angkorteam.fintech.provider.FundProvider;
//import com.angkorteam.fintech.provider.LoanPurposeProvider;
//import com.angkorteam.fintech.provider.SingleChoiceProvider;
//import com.angkorteam.fintech.widget.Panel;
//import com.angkorteam.fintech.widget.ReadOnlyView;
//import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
//import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;
//import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;
//import com.angkorteam.framework.wicket.markup.html.form.Button;
//import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
//import com.angkorteam.framework.wicket.markup.html.form.Form;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
//
//public class DetailsPanel extends Panel {
//
//    protected Page itemPage;
//    protected PropertyModel<AjaxTabbedPanel<ITab>> tab;
//    protected PropertyModel<Boolean> errorDetail;
//
//    protected PropertyModel<String> officeId;
//
//    protected Form<Void> form;
//    protected Button nextButton;
//    protected BookmarkablePageLink<Void> closeLink;
//
//    protected ClientEnum client;
//    protected String clientId;
//
//    // Detail
//
//    protected UIRow row1;
//
//    protected UIBlock detailProductBlock;
//    protected UIContainer detailProductVContainer;
//    protected ReadOnlyView detailProductView;
//    protected PropertyModel<String> detailProductValue;
//
//    protected SingleChoiceProvider detailLoanOfficerProvider;
//    protected UIBlock detailLoanOfficerBlock;
//    protected UIContainer detailLoanOfficerIContainer;
//    protected Select2SingleChoice<Option> detailLoanOfficerField;
//    protected PropertyModel<Option> detailLoanOfficerValue;
//
//    protected UIRow row2;
//
//    protected SingleChoiceProvider detailLoanPurposeProvider;
//    protected UIBlock detailLoanPurposeBlock;
//    protected UIContainer detailLoanPurposeIContainer;
//    protected Select2SingleChoice<Option> detailLoanPurposeField;
//    protected PropertyModel<Option> detailLoanPurposeValue;
//
//    protected SingleChoiceProvider detailFundProvider;
//    protected UIBlock detailFundBlock;
//    protected UIContainer detailFundIContainer;
//    protected Select2SingleChoice<Option> detailFundField;
//    protected PropertyModel<Option> detailFundValue;
//
//    protected UIRow row3;
//
//    protected UIBlock detailSubmittedOnBlock;
//    protected UIContainer detailSubmittedOnIContainer;
//    protected DateTextField detailSubmittedOnField;
//    protected PropertyModel<Date> detailSubmittedOnValue;
//
//    protected UIBlock detailDisbursementOnBlock;
//    protected UIContainer detailDisbursementOnIContainer;
//    protected DateTextField detailDisbursementOnField;
//    protected PropertyModel<Date> detailDisbursementOnValue;
//
//    protected UIRow row4;
//
//    protected UIBlock detailExternalIdBlock;
//    protected UIContainer detailExternalIdIContainer;
//    protected TextField<String> detailExternalIdField;
//    protected PropertyModel<String> detailExternalIdValue;
//
//    protected UIBlock row4Block1;
//
//    protected UIRow row5;
//
//    protected SingleChoiceProvider detailLinkSavingProvider;
//    protected UIBlock detailLinkSavingBlock;
//    protected UIContainer detailLinkSavingIContainer;
//    protected Select2SingleChoice<Option> detailLinkSavingField;
//    protected PropertyModel<Option> detailLinkSavingValue;
//
//    protected UIBlock detailCreateStandingInstructionAtDisbursementBlock;
//    protected UIContainer detailCreateStandingInstructionAtDisbursementIContainer;
//    protected CheckBox detailCreateStandingInstructionAtDisbursementField;
//    protected PropertyModel<Boolean> detailCreateStandingInstructionAtDisbursementValue;
//
//    public DetailsPanel(String id, Page itemPage) {
//        super(id);
//        this.itemPage = itemPage;
//    }
//
//    @Override
//    protected void initData() {
//        this.tab = new PropertyModel<>(this.itemPage, "tab");
//        this.errorDetail = new PropertyModel<>(this.itemPage, "errorDetail");
//
//        this.client = new PropertyModel<ClientEnum>(this.itemPage, "client").getObject();
//        this.clientId = new PropertyModel<String>(this.itemPage, "clientId").getObject();
//        this.officeId = new PropertyModel<>(this.itemPage, "officeId");
//
//        this.detailProductValue = new PropertyModel<>(this.itemPage, "detailProductValue");
//        this.detailLoanOfficerValue = new PropertyModel<>(this.itemPage, "detailLoanOfficerValue");
//        this.detailLoanPurposeValue = new PropertyModel<>(this.itemPage, "detailLoanPurposeValue");
//        this.detailFundValue = new PropertyModel<>(this.itemPage, "detailFundValue");
//        this.detailSubmittedOnValue = new PropertyModel<>(this.itemPage, "detailSubmittedOnValue");
//        this.detailDisbursementOnValue = new PropertyModel<>(this.itemPage, "detailDisbursementOnValue");
//        this.detailExternalIdValue = new PropertyModel<>(this.itemPage, "detailExternalIdValue");
//        this.detailLinkSavingValue = new PropertyModel<>(this.itemPage, "detailLinkSavingValue");
//        this.detailCreateStandingInstructionAtDisbursementValue = new PropertyModel<>(this.itemPage, "detailCreateStandingInstructionAtDisbursementValue");
//
//        this.detailLoanOfficerProvider = new SingleChoiceProvider(MStaff.NAME, MStaff.Field.ID, MStaff.Field.DISPLAY_NAME);
//        this.detailLoanOfficerProvider.applyWhere("is_active", MStaff.Field.IS_ACTIVE + " = 1");
//        this.detailLoanOfficerProvider.applyWhere("is_loan_officer", MStaff.Field.IS_LOAN_OFFICER + " = 1");
//        this.detailLoanOfficerProvider.applyWhere("office_id", MStaff.Field.OFFICE_ID + " = " + this.officeId.getObject());
//
//        this.detailFundProvider = new FundProvider();
//
//        this.detailLoanPurposeProvider = new LoanPurposeProvider();
//
//        this.detailLinkSavingProvider = new SingleChoiceProvider(MSavingsAccount.NAME, MSavingsAccount.NAME + "." + MSavingsAccount.Field.ID, MSavingsAccount.NAME + "." + MSavingsAccount.Field.ACCOUNT_NO, "CONCAT(" + MSavingsAccount.NAME + "." + MSavingsAccount.Field.ACCOUNT_NO + ", ' => ', " + MSavingsProduct.NAME + "." + MSavingsProduct.Field.NAME + ")");
//        this.detailLinkSavingProvider.applyJoin("m_savings_product", "INNER JOIN " + MSavingsProduct.NAME + " ON " + MSavingsAccount.NAME + "." + MSavingsAccount.Field.PRODUCT_ID + " = " + MSavingsProduct.NAME + "." + MSavingsProduct.Field.ID);
//        this.detailLinkSavingProvider.applyWhere("status_enum", MSavingsAccount.NAME + "." + MSavingsAccount.Field.STATUS_ENUM + " = 300");
//        this.detailLinkSavingProvider.applyWhere("client_id", MSavingsAccount.NAME + "." + MSavingsAccount.Field.CLIENT_ID + " = " + this.clientId);
//
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
//        this.detailProductBlock = this.row1.newUIBlock("detailProductBlock", Size.Six_6);
//        this.detailProductVContainer = this.detailProductBlock.newUIContainer("detailProductVContainer");
//        this.detailProductView = new ReadOnlyView("detailProductView", this.detailProductValue);
//        this.detailProductVContainer.add(this.detailProductView);
//
//        this.detailLoanOfficerBlock = this.row1.newUIBlock("detailLoanOfficerBlock", Size.Six_6);
//        this.detailLoanOfficerIContainer = this.detailLoanOfficerBlock.newUIContainer("detailLoanOfficerIContainer");
//        this.detailLoanOfficerField = new Select2SingleChoice<>("detailLoanOfficerField", this.detailLoanOfficerValue, this.detailLoanOfficerProvider);
//        this.detailLoanOfficerIContainer.add(this.detailLoanOfficerField);
//        this.detailLoanOfficerIContainer.newFeedback("detailLoanOfficerFeedback", this.detailLoanOfficerField);
//
//        this.row2 = UIRow.newUIRow("row2", this.form);
//
//        this.detailLoanPurposeBlock = this.row2.newUIBlock("detailLoanPurposeBlock", Size.Six_6);
//        this.detailLoanPurposeIContainer = this.detailLoanPurposeBlock.newUIContainer("detailLoanPurposeIContainer");
//        this.detailLoanPurposeField = new Select2SingleChoice<>("detailLoanPurposeField", this.detailLoanPurposeValue, this.detailLoanPurposeProvider);
//        this.detailLoanPurposeIContainer.add(this.detailLoanPurposeField);
//        this.detailLoanPurposeIContainer.newFeedback("detailLoanPurposeFeedback", this.detailLoanPurposeField);
//
//        this.detailFundBlock = this.row2.newUIBlock("detailFundBlock", Size.Six_6);
//        this.detailFundIContainer = this.detailFundBlock.newUIContainer("detailFundIContainer");
//        this.detailFundField = new Select2SingleChoice<>("detailFundField", this.detailFundValue, this.detailFundProvider);
//        this.detailFundIContainer.add(this.detailFundField);
//        this.detailFundIContainer.newFeedback("detailFundFeedback", this.detailFundField);
//
//        this.row3 = UIRow.newUIRow("row3", this.form);
//
//        this.detailSubmittedOnBlock = this.row3.newUIBlock("detailSubmittedOnBlock", Size.Six_6);
//        this.detailSubmittedOnIContainer = this.detailSubmittedOnBlock.newUIContainer("detailSubmittedOnIContainer");
//        this.detailSubmittedOnField = new DateTextField("detailSubmittedOnField", this.detailSubmittedOnValue);
//        this.detailSubmittedOnIContainer.add(this.detailSubmittedOnField);
//        this.detailSubmittedOnIContainer.newFeedback("detailSubmittedOnFeedback", this.detailSubmittedOnField);
//
//        this.detailDisbursementOnBlock = this.row3.newUIBlock("detailDisbursementOnBlock", Size.Six_6);
//        this.detailDisbursementOnIContainer = this.detailDisbursementOnBlock.newUIContainer("detailDisbursementOnIContainer");
//        this.detailDisbursementOnField = new DateTextField("detailDisbursementOnField", this.detailDisbursementOnValue);
//        this.detailDisbursementOnIContainer.add(this.detailDisbursementOnField);
//        this.detailDisbursementOnIContainer.newFeedback("detailDisbursementOnFeedback", this.detailDisbursementOnField);
//
//        this.row4 = UIRow.newUIRow("row4", this.form);
//
//        this.detailExternalIdBlock = this.row4.newUIBlock("detailExternalIdBlock", Size.Six_6);
//        this.detailExternalIdIContainer = this.detailExternalIdBlock.newUIContainer("detailExternalIdIContainer");
//        this.detailExternalIdField = new TextField<>("detailExternalIdField", this.detailExternalIdValue);
//        this.detailExternalIdIContainer.add(this.detailExternalIdField);
//        this.detailExternalIdIContainer.newFeedback("detailExternalIdFeedback", this.detailExternalIdField);
//
//        this.row4Block1 = this.row4.newUIBlock("row4Block1", Size.Six_6);
//
//        this.row5 = UIRow.newUIRow("row5", this.form);
//
//        this.detailLinkSavingBlock = this.row5.newUIBlock("detailLinkSavingBlock", Size.Six_6);
//        this.detailLinkSavingIContainer = this.detailLinkSavingBlock.newUIContainer("detailLinkSavingIContainer");
//        this.detailLinkSavingField = new Select2SingleChoice<>("detailLinkSavingField", this.detailLinkSavingValue, this.detailLinkSavingProvider);
//        this.detailLinkSavingIContainer.add(this.detailLinkSavingField);
//        this.detailLinkSavingIContainer.newFeedback("detailLinkSavingFeedback", this.detailLinkSavingField);
//
//        this.detailCreateStandingInstructionAtDisbursementBlock = this.row5.newUIBlock("detailCreateStandingInstructionAtDisbursementBlock", Size.Six_6);
//        this.detailCreateStandingInstructionAtDisbursementIContainer = this.detailCreateStandingInstructionAtDisbursementBlock.newUIContainer("detailCreateStandingInstructionAtDisbursementIContainer");
//        this.detailCreateStandingInstructionAtDisbursementField = new CheckBox("detailCreateStandingInstructionAtDisbursementField", this.detailCreateStandingInstructionAtDisbursementValue);
//        this.detailCreateStandingInstructionAtDisbursementIContainer.add(this.detailCreateStandingInstructionAtDisbursementField);
//        this.detailCreateStandingInstructionAtDisbursementIContainer.newFeedback("detailCreateStandingInstructionAtDisbursementFeedback", this.detailCreateStandingInstructionAtDisbursementField);
//    }
//
//    @Override
//    protected void configureMetaData() {
//        this.detailFundField.add(new OnChangeAjaxBehavior());
//
//        this.detailLoanOfficerField.add(new OnChangeAjaxBehavior());
//
//        this.detailLoanPurposeField.add(new OnChangeAjaxBehavior());
//
//        this.detailSubmittedOnField.add(new OnChangeAjaxBehavior());
//        this.detailSubmittedOnField.setLabel(Model.of("Submitted On"));
//        this.detailSubmittedOnField.setRequired(true);
//
//        this.detailExternalIdField.add(new OnChangeAjaxBehavior());
//        this.detailExternalIdField.setLabel(Model.of("External ID"));
//
//        this.detailExternalIdField.add(new OnChangeAjaxBehavior());
//
//        this.detailLinkSavingField.add(new OnChangeAjaxBehavior());
//
//        this.detailCreateStandingInstructionAtDisbursementField.add(new OnChangeAjaxBehavior());
//    }
//
//    protected void nextButtonSubmit(Button button) {
//        this.errorDetail.setObject(false);
//        this.tab.getObject().setSelectedTab(AccountCreatePage.TAB_TERM);
//    }
//
//    protected void nextButtonError(Button button) {
//        this.errorDetail.setObject(true);
//    }
//
//}
