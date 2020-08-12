//package com.angkorteam.fintech.pages.client.common.saving;
//
//import java.util.Date;
//
//import org.apache.wicket.WicketRuntimeException;
//import org.apache.wicket.ajax.AjaxRequestTarget;
//import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
//import org.apache.wicket.markup.html.form.CheckBox;
//import org.apache.wicket.markup.html.form.TextArea;
//import org.apache.wicket.markup.html.form.TextField;
//import org.apache.wicket.markup.html.link.BookmarkablePageLink;
//import org.apache.wicket.model.Model;
//import org.apache.wicket.model.PropertyModel;
//import org.apache.wicket.request.mapper.parameter.PageParameters;
//import org.joda.time.DateTime;
//
//import com.angkorteam.fintech.Page;
//import com.angkorteam.fintech.Session;
//import com.angkorteam.fintech.ddl.MPaymentType;
//import com.angkorteam.fintech.dto.ClientEnum;
//import com.angkorteam.fintech.dto.Function;
//import com.angkorteam.fintech.dto.builder.saving.CloseBuilder;
//import com.angkorteam.fintech.helper.ClientHelper;
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.layout.UIBlock;
//import com.angkorteam.fintech.layout.UIContainer;
//import com.angkorteam.fintech.layout.UIRow;
//import com.angkorteam.fintech.pages.client.center.CenterPreviewPage;
//import com.angkorteam.fintech.pages.client.client.ClientPreviewPage;
//import com.angkorteam.fintech.pages.client.group.GroupPreviewPage;
//import com.angkorteam.fintech.provider.SingleChoiceProvider;
//import com.angkorteam.fintech.widget.ReadOnlyView;
//import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
//import com.angkorteam.framework.wicket.markup.html.form.Button;
//import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
//import com.angkorteam.framework.wicket.markup.html.form.Form;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
//
//import io.github.openunirest.http.JsonNode;
//
//@AuthorizeInstantiation(Function.ALL_FUNCTION)
//public class AccountClosePage extends Page {
//
//    protected ClientEnum client;
//
//    protected String clientId;
//    protected String groupId;
//    protected String centerId;
//
//    protected String savingId;
//
//    protected Form<Void> form;
//    protected Button saveButton;
//    protected BookmarkablePageLink<Void> closeLink;
//
//    protected UIRow row1;
//
//    protected UIBlock closedOnBlock;
//    protected UIContainer closedOnIContainer;
//    protected DateTextField closedOnField;
//    protected Date closedOnValue;
//
//    protected UIBlock row1Block1;
//
//    protected UIRow row2;
//
//    protected UIBlock withdrawBalanceBlock;
//    protected UIContainer withdrawBalanceIContainer;
//    protected CheckBox withdrawBalanceField;
//    protected Boolean withdrawBalanceValue;
//
//    protected UIBlock row2Block1;
//
//    protected UIRow row3;
//
//    protected UIBlock postInterestBlock;
//    protected UIContainer postInterestIContainer;
//    protected CheckBox postInterestField;
//    protected Boolean postInterestValue;
//
//    protected UIBlock row3Block1;
//
//    protected UIRow row4;
//
//    protected UIBlock transactionAmountBlock;
//    protected UIContainer transactionAmountVContainer;
//    protected ReadOnlyView transactionAmountView;
//    protected String transactionAmountValue;
//
//    protected UIBlock row4Block1;
//
//    protected UIRow row5;
//
//    protected UIBlock paymentTypeBlock;
//    protected UIContainer paymentTypeIContainer;
//    protected SingleChoiceProvider paymentTypeProvider;
//    protected Select2SingleChoice<Option> paymentTypeField;
//    protected Option paymentTypeValue;
//
//    protected UIBlock row5Block1;
//
//    protected UIRow row6;
//
//    protected UIBlock paymentDetailBlock;
//    protected UIContainer paymentDetailIContainer;
//    protected CheckBox paymentDetailField;
//    protected Boolean paymentDetailValue;
//
//    protected UIBlock row6Block1;
//
//    protected UIRow row7;
//
//    protected UIBlock accountBlock;
//    protected UIContainer accountIContainer;
//    protected TextField<String> accountField;
//    protected String accountValue;
//
//    protected UIBlock chequeBlock;
//    protected UIContainer chequeIContainer;
//    protected TextField<String> chequeField;
//    protected String chequeValue;
//
//    protected UIRow row8;
//
//    protected UIBlock routingBlock;
//    protected UIContainer routingIContainer;
//    protected TextField<String> routingField;
//    protected String routingValue;
//
//    protected UIBlock receiptBlock;
//    protected UIContainer receiptIContainer;
//    protected TextField<String> receiptField;
//    protected String receiptValue;
//
//    protected UIRow row9;
//
//    protected UIBlock bankBlock;
//    protected UIContainer bankIContainer;
//    protected TextField<String> bankField;
//    protected String bankValue;
//
//    protected UIBlock row9Block1;
//
//    protected UIRow row10;
//
//    protected UIBlock noteBlock;
//    protected UIContainer noteIContainer;
//    protected TextArea<String> noteField;
//    protected String noteValue;
//
//    protected UIBlock row10Block1;
//
//    @Override
//    protected void initComponent() {
//
//        this.form = new Form<>("form");
//        add(this.form);
//
//        this.saveButton = new Button("saveButton");
//        this.saveButton.setOnSubmit(this::saveButtonSubmit);
//        this.form.add(this.saveButton);
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
//        this.closedOnBlock = this.row1.newUIBlock("closedOnBlock", Size.Six_6);
//        this.closedOnIContainer = this.closedOnBlock.newUIContainer("closedOnIContainer");
//        this.closedOnField = new DateTextField("closedOnField", new PropertyModel<>(this, "closedOnValue"));
//        this.closedOnIContainer.add(this.closedOnField);
//        this.closedOnIContainer.newFeedback("closedOnFeedback", this.closedOnField);
//
//        this.row1Block1 = this.row1.newUIBlock("row1Block1", Size.Six_6);
//
//        this.row2 = UIRow.newUIRow("row2", this.form);
//
//        this.withdrawBalanceBlock = this.row2.newUIBlock("withdrawBalanceBlock", Size.Six_6);
//        this.withdrawBalanceIContainer = this.withdrawBalanceBlock.newUIContainer("withdrawBalanceIContainer");
//        this.withdrawBalanceField = new CheckBox("withdrawBalanceField", new PropertyModel<>(this, "withdrawBalanceValue"));
//        this.withdrawBalanceIContainer.add(this.withdrawBalanceField);
//        this.withdrawBalanceIContainer.newFeedback("withdrawBalanceFeedback", this.withdrawBalanceField);
//
//        this.row2Block1 = this.row2.newUIBlock("row2Block1", Size.Six_6);
//
//        this.row3 = UIRow.newUIRow("row3", this.form);
//
//        this.postInterestBlock = this.row3.newUIBlock("postInterestBlock", Size.Six_6);
//        this.postInterestIContainer = this.postInterestBlock.newUIContainer("postInterestIContainer");
//        this.postInterestField = new CheckBox("postInterestField", new PropertyModel<>(this, "postInterestValue"));
//        this.postInterestIContainer.add(this.postInterestField);
//        this.postInterestIContainer.newFeedback("postInterestFeedback", this.postInterestField);
//
//        this.row3Block1 = this.row3.newUIBlock("row3Block1", Size.Six_6);
//
//        this.row4 = UIRow.newUIRow("row4", this.form);
//
//        this.transactionAmountBlock = this.row4.newUIBlock("transactionAmountBlock", Size.Six_6);
//        this.transactionAmountVContainer = this.transactionAmountBlock.newUIContainer("transactionAmountVContainer");
//        this.transactionAmountView = new ReadOnlyView("transactionAmountView", new PropertyModel<>(this, "transactionAmountValue"));
//        this.transactionAmountVContainer.add(this.transactionAmountView);
//
//        this.row4Block1 = this.row4.newUIBlock("row4Block1", Size.Six_6);
//
//        this.row5 = UIRow.newUIRow("row5", this.form);
//
//        this.paymentTypeBlock = this.row5.newUIBlock("paymentTypeBlock", Size.Six_6);
//        this.paymentTypeIContainer = this.paymentTypeBlock.newUIContainer("paymentTypeIContainer");
//        this.paymentTypeField = new Select2SingleChoice<>("paymentTypeField", new PropertyModel<>(this, "paymentTypeValue"), this.paymentTypeProvider);
//        this.paymentTypeIContainer.add(this.paymentTypeField);
//        this.paymentTypeIContainer.newFeedback("paymentTypeFeedback", this.paymentTypeField);
//
//        this.row5Block1 = this.row5.newUIBlock("row5Block1", Size.Six_6);
//
//        this.row6 = UIRow.newUIRow("row6", this.form);
//
//        this.paymentDetailBlock = this.row6.newUIBlock("paymentDetailBlock", Size.Six_6);
//        this.paymentDetailIContainer = this.paymentDetailBlock.newUIContainer("paymentDetailIContainer");
//        this.paymentDetailField = new CheckBox("paymentDetailField", new PropertyModel<>(this, "paymentDetailValue"));
//        this.paymentDetailIContainer.add(this.paymentDetailField);
//        this.paymentDetailIContainer.newFeedback("paymentDetailFeedback", this.paymentDetailField);
//
//        this.row6Block1 = this.row6.newUIBlock("row6Block1", Size.Six_6);
//
//        this.row7 = UIRow.newUIRow("row7", this.form);
//
//        this.accountBlock = this.row7.newUIBlock("accountBlock", Size.Six_6);
//        this.accountIContainer = this.accountBlock.newUIContainer("accountIContainer");
//        this.accountField = new TextField<>("accountField", new PropertyModel<>(this, "accountValue"));
//        this.accountIContainer.add(this.accountField);
//        this.accountIContainer.newFeedback("accountFeedback", this.accountField);
//
//        this.chequeBlock = this.row7.newUIBlock("chequeBlock", Size.Six_6);
//        this.chequeIContainer = this.chequeBlock.newUIContainer("chequeIContainer");
//        this.chequeField = new TextField<>("chequeField", new PropertyModel<>(this, "chequeValue"));
//        this.chequeIContainer.add(this.chequeField);
//        this.chequeIContainer.newFeedback("chequeFeedback", this.chequeField);
//
//        this.row8 = UIRow.newUIRow("row8", this.form);
//
//        this.routingBlock = this.row8.newUIBlock("routingBlock", Size.Six_6);
//        this.routingIContainer = this.routingBlock.newUIContainer("routingIContainer");
//        this.routingField = new TextField<>("routingField", new PropertyModel<>(this, "routingValue"));
//        this.routingIContainer.add(this.routingField);
//        this.routingIContainer.newFeedback("routingFeedback", this.routingField);
//
//        this.receiptBlock = this.row8.newUIBlock("receiptBlock", Size.Six_6);
//        this.receiptIContainer = this.receiptBlock.newUIContainer("receiptIContainer");
//        this.receiptField = new TextField<>("receiptField", new PropertyModel<>(this, "receiptValue"));
//        this.receiptIContainer.add(this.receiptField);
//        this.receiptIContainer.newFeedback("receiptFeedback", this.receiptField);
//
//        this.row9 = UIRow.newUIRow("row9", this.form);
//
//        this.bankBlock = this.row9.newUIBlock("bankBlock", Size.Six_6);
//        this.bankIContainer = this.bankBlock.newUIContainer("bankIContainer");
//        this.bankField = new TextField<>("bankField", new PropertyModel<>(this, "bankValue"));
//        this.bankIContainer.add(this.bankField);
//        this.bankIContainer.newFeedback("bankFeedback", this.bankField);
//
//        this.row9Block1 = this.row9.newUIBlock("row9Block1", Size.Six_6);
//
//        this.row10 = UIRow.newUIRow("row10", this.form);
//
//        this.noteBlock = this.row10.newUIBlock("noteBlock", Size.Six_6);
//        this.noteIContainer = this.noteBlock.newUIContainer("noteIContainer");
//        this.noteField = new TextArea<>("noteField", new PropertyModel<>(this, "noteValue"));
//        this.noteIContainer.add(this.noteField);
//        this.noteIContainer.newFeedback("noteFeedback", this.noteField);
//
//        this.row10Block1 = this.row10.newUIBlock("row10Block1", Size.Six_6);
//    }
//
//    @Override
//    protected void configureMetaData() {
//        this.closedOnField.setLabel(Model.of("Closed On"));
//
//        this.withdrawBalanceField.add(new OnChangeAjaxBehavior(this::withdrawBalanceFieldUpdate));
//
//        this.paymentDetailField.add(new OnChangeAjaxBehavior(this::paymentDetailFieldUpdate));
//
//        this.accountField.setLabel(Model.of("Account"));
//
//        this.chequeField.setLabel(Model.of("Cheque"));
//
//        this.routingField.setLabel(Model.of("Routing"));
//
//        this.bankField.setLabel(Model.of("bank"));
//
//        this.noteField.setLabel(Model.of("Note"));
//
//        this.receiptField.setLabel(Model.of("Receipt"));
//
//        withdrawBalanceFieldUpdate(null);
//    }
//
//    @Override
//    protected void initData() {
//        this.client = ClientEnum.valueOf(getPageParameters().get("client").toString());
//        this.clientId = getPageParameters().get("clientId").toString();
//        this.savingId = getPageParameters().get("savingId").toString();
//        this.closedOnValue = DateTime.now().toDate();
//        this.postInterestValue = true;
//
//        this.paymentTypeProvider = new SingleChoiceProvider(MPaymentType.NAME, MPaymentType.Field.ID, MPaymentType.Field.VALUE);
//    }
//
//    protected boolean withdrawBalanceFieldUpdate(AjaxRequestTarget target) {
//        boolean visible = this.withdrawBalanceValue == null ? false : this.withdrawBalanceValue;
//        this.transactionAmountVContainer.setVisible(visible);
//        this.paymentTypeIContainer.setVisible(visible);
//        if (target != null) {
//            target.add(this.transactionAmountBlock);
//            target.add(this.paymentTypeBlock);
//        }
//        paymentDetailFieldUpdate(target);
//        return false;
//    }
//
//    protected boolean paymentDetailFieldUpdate(AjaxRequestTarget target) {
//        boolean visible = this.paymentDetailValue == null ? false : this.paymentDetailValue;
//        this.accountIContainer.setVisible(visible);
//        this.chequeIContainer.setVisible(visible);
//        this.routingIContainer.setVisible(visible);
//        this.receiptIContainer.setVisible(visible);
//        this.bankIContainer.setVisible(visible);
//        if (target != null) {
//            target.add(this.accountBlock);
//            target.add(this.chequeBlock);
//            target.add(this.routingBlock);
//            target.add(this.receiptBlock);
//            target.add(this.bankBlock);
//        }
//        return false;
//    }
//
//    protected void saveButtonSubmit(Button button) {
//        CloseBuilder builder = new CloseBuilder();
//
//        builder.withId(this.savingId);
//
//        builder.withPostInterestValidationOnClosure(this.postInterestValue == null ? false : this.postInterestValue);
//        builder.withClosedOnDate(this.closedOnValue);
//        builder.withWithdrawBalance(this.withdrawBalanceValue == null ? false : this.withdrawBalanceValue);
//        if (this.paymentTypeValue != null) {
//            builder.withPaymentTypeId(this.paymentTypeValue.getId());
//        }
//
//        if (this.paymentDetailValue != null && this.paymentDetailValue) {
//            builder.withAccountNumber(this.accountValue);
//            builder.withCheckNumber(this.chequeValue);
//            builder.withRoutingCode(this.routingValue);
//            builder.withReceiptNumber(this.receiptValue);
//            builder.withBankNumber(this.bankValue);
//        }
//
//        builder.withNote(this.noteValue);
//
//        JsonNode request = builder.build();
//        JsonNode node = ClientHelper.closeSavingAccount((Session) getSession(), request);
//
//        if (reportError(node)) {
//            return;
//        }
//
//        if (this.client == ClientEnum.Client) {
//            PageParameters parameters = new PageParameters();
//            parameters.add("clientId", this.clientId);
//            setResponsePage(ClientPreviewPage.class, parameters);
//        } else if (this.client == ClientEnum.Center) {
//            PageParameters parameters = new PageParameters();
//            parameters.add("centerId", this.centerId);
//            setResponsePage(CenterPreviewPage.class, parameters);
//        } else if (this.client == ClientEnum.Group) {
//            PageParameters parameters = new PageParameters();
//            parameters.add("groupId", this.groupId);
//            setResponsePage(GroupPreviewPage.class, parameters);
//        } else {
//            throw new WicketRuntimeException("Unknown " + this.client);
//        }
//    }
//
//}
