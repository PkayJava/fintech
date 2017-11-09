package com.angkorteam.fintech.pages.client.client;

import java.util.Date;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.joda.time.DateTime;

import com.angkorteam.fintech.DeprecatedPage;
import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.helper.acount.CloseBuilder;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class SavingAccountClosePage extends DeprecatedPage {

    protected String clientId;
    protected String accountId;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupContainer closedOnBlock;
    protected WebMarkupContainer closedOnContainer;
    protected Date closedOnValue;
    protected DateTextField closedOnField;
    protected TextFeedbackPanel closedOnFeedback;

    protected WebMarkupContainer withdrawBalanceBlock;
    protected WebMarkupContainer withdrawBalanceContainer;
    protected Boolean withdrawBalanceValue;
    protected CheckBox withdrawBalanceField;
    protected TextFeedbackPanel withdrawBalanceFeedback;

    protected WebMarkupContainer postInterestBlock;
    protected WebMarkupContainer postInterestContainer;
    protected Boolean postInterestValue;
    protected CheckBox postInterestField;
    protected TextFeedbackPanel postInterestFeedback;

    protected WebMarkupContainer transactionAmountBlock;
    protected WebMarkupContainer transactionAmountContainer;
    protected String transactionAmountValue;
    protected Label transactionAmountView;

    protected WebMarkupContainer paymentTypeBlock;
    protected WebMarkupContainer paymentTypeContainer;
    protected SingleChoiceProvider paymentTypeProvider;
    protected Option paymentTypeValue;
    protected Select2SingleChoice<Option> paymentTypeField;
    protected TextFeedbackPanel paymentTypeFeedback;

    protected WebMarkupContainer paymentDetailBlock;
    protected WebMarkupContainer paymentDetailContainer;
    protected Boolean paymentDetailValue;
    protected CheckBox paymentDetailField;
    protected TextFeedbackPanel paymentDetailFeedback;

    protected WebMarkupContainer accountBlock;
    protected WebMarkupContainer accountContainer;
    protected String accountValue;
    protected TextField<String> accountField;
    protected TextFeedbackPanel accountFeedback;

    protected WebMarkupContainer chequeBlock;
    protected WebMarkupContainer chequeContainer;
    protected String chequeValue;
    protected TextField<String> chequeField;
    protected TextFeedbackPanel chequeFeedback;

    protected WebMarkupContainer routingBlock;
    protected WebMarkupContainer routingContainer;
    protected String routingValue;
    protected TextField<String> routingField;
    protected TextFeedbackPanel routingFeedback;

    protected WebMarkupContainer receiptBlock;
    protected WebMarkupContainer receiptContainer;
    protected String receiptValue;
    protected TextField<String> receiptField;
    protected TextFeedbackPanel receiptFeedback;

    protected WebMarkupContainer bankBlock;
    protected WebMarkupContainer bankContainer;
    protected String bankValue;
    protected TextField<String> bankField;
    protected TextFeedbackPanel bankFeedback;

    protected WebMarkupContainer noteBlock;
    protected WebMarkupContainer noteContainer;
    protected String noteValue;
    protected TextArea<String> noteField;
    protected TextFeedbackPanel noteFeedback;

    @Override
    protected void onInitialize() {
        super.onInitialize();
        initData();

        PageParameters parameters = new PageParameters();
        parameters.add("clientId", this.clientId);

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", ClientPreviewPage.class, parameters);
        this.form.add(this.closeLink);

        this.closedOnBlock = new WebMarkupContainer("closedOnBlock");
        this.closedOnBlock.setOutputMarkupId(true);
        this.form.add(this.closedOnBlock);
        this.closedOnContainer = new WebMarkupContainer("closedOnContainer");
        this.closedOnBlock.add(this.closedOnContainer);
        this.closedOnField = new DateTextField("closedOnField", new PropertyModel<>(this, "closedOnValue"));
        this.closedOnField.setLabel(Model.of("Closed On"));
        this.closedOnContainer.add(this.closedOnField);
        this.closedOnFeedback = new TextFeedbackPanel("closedOnFeedback", this.closedOnField);
        this.closedOnContainer.add(this.closedOnFeedback);

        this.withdrawBalanceBlock = new WebMarkupContainer("withdrawBalanceBlock");
        this.withdrawBalanceBlock.setOutputMarkupId(true);
        this.form.add(this.withdrawBalanceBlock);
        this.withdrawBalanceContainer = new WebMarkupContainer("withdrawBalanceContainer");
        this.withdrawBalanceBlock.add(this.withdrawBalanceContainer);
        this.withdrawBalanceField = new CheckBox("withdrawBalanceField", new PropertyModel<>(this, "withdrawBalanceValue"));
        this.withdrawBalanceField.add(new OnChangeAjaxBehavior(this::withdrawBalanceFieldUpdate));
        this.withdrawBalanceContainer.add(this.withdrawBalanceField);
        this.withdrawBalanceFeedback = new TextFeedbackPanel("withdrawBalanceFeedback", this.withdrawBalanceField);
        this.withdrawBalanceContainer.add(this.withdrawBalanceFeedback);

        this.postInterestBlock = new WebMarkupContainer("postInterestBlock");
        this.postInterestBlock.setOutputMarkupId(true);
        this.form.add(this.postInterestBlock);
        this.postInterestContainer = new WebMarkupContainer("postInterestContainer");
        this.postInterestBlock.add(this.postInterestContainer);
        this.postInterestField = new CheckBox("postInterestField", new PropertyModel<>(this, "postInterestValue"));
        this.postInterestContainer.add(this.postInterestField);
        this.postInterestFeedback = new TextFeedbackPanel("postInterestFeedback", this.postInterestField);
        this.postInterestContainer.add(this.postInterestFeedback);

        this.transactionAmountBlock = new WebMarkupContainer("transactionAmountBlock");
        this.transactionAmountBlock.setOutputMarkupId(true);
        this.form.add(this.transactionAmountBlock);
        this.transactionAmountContainer = new WebMarkupContainer("transactionAmountContainer");
        this.transactionAmountBlock.add(this.transactionAmountContainer);
        this.transactionAmountView = new Label("transactionAmountView", new PropertyModel<>(this, "transactionAmountValue"));
        this.transactionAmountContainer.add(this.transactionAmountView);

        this.paymentTypeProvider = new SingleChoiceProvider("m_payment_type", "id", "value");
        this.paymentTypeBlock = new WebMarkupContainer("paymentTypeBlock");
        this.paymentTypeBlock.setOutputMarkupId(true);
        this.form.add(this.paymentTypeBlock);
        this.paymentTypeContainer = new WebMarkupContainer("paymentTypeContainer");
        this.paymentTypeBlock.add(this.paymentTypeContainer);
        this.paymentTypeField = new Select2SingleChoice<>("paymentTypeField", new PropertyModel<>(this, "paymentTypeValue"), this.paymentTypeProvider);
        this.paymentTypeContainer.add(this.paymentTypeField);
        this.paymentTypeFeedback = new TextFeedbackPanel("paymentTypeFeedback", this.paymentTypeField);
        this.paymentTypeContainer.add(this.paymentTypeFeedback);

        this.paymentDetailBlock = new WebMarkupContainer("paymentDetailBlock");
        this.paymentDetailBlock.setOutputMarkupId(true);
        this.form.add(this.paymentDetailBlock);
        this.paymentDetailContainer = new WebMarkupContainer("paymentDetailContainer");
        this.paymentDetailBlock.add(this.paymentDetailContainer);
        this.paymentDetailField = new CheckBox("paymentDetailField", new PropertyModel<>(this, "paymentDetailValue"));
        this.paymentDetailField.add(new OnChangeAjaxBehavior(this::paymentDetailFieldUpdate));
        this.paymentDetailContainer.add(this.paymentDetailField);
        this.paymentDetailFeedback = new TextFeedbackPanel("paymentDetailFeedback", this.paymentDetailField);
        this.paymentDetailContainer.add(this.paymentDetailFeedback);

        this.accountBlock = new WebMarkupContainer("accountBlock");
        this.accountBlock.setOutputMarkupId(true);
        this.form.add(this.accountBlock);
        this.accountContainer = new WebMarkupContainer("accountContainer");
        this.accountBlock.add(this.accountContainer);
        this.accountField = new TextField<>("accountField", new PropertyModel<>(this, "accountValue"));
        this.accountField.setLabel(Model.of("Account"));
        this.accountContainer.add(this.accountField);
        this.accountFeedback = new TextFeedbackPanel("accountFeedback", this.accountField);
        this.accountContainer.add(this.accountFeedback);

        this.chequeBlock = new WebMarkupContainer("chequeBlock");
        this.chequeBlock.setOutputMarkupId(true);
        this.form.add(this.chequeBlock);
        this.chequeContainer = new WebMarkupContainer("chequeContainer");
        this.chequeBlock.add(this.chequeContainer);
        this.chequeField = new TextField<>("chequeField", new PropertyModel<>(this, "chequeValue"));
        this.chequeField.setLabel(Model.of("Cheque"));
        this.chequeContainer.add(this.chequeField);
        this.chequeFeedback = new TextFeedbackPanel("chequeFeedback", this.chequeField);
        this.chequeContainer.add(this.chequeFeedback);

        this.routingBlock = new WebMarkupContainer("routingBlock");
        this.routingBlock.setOutputMarkupId(true);
        this.form.add(this.routingBlock);
        this.routingContainer = new WebMarkupContainer("routingContainer");
        this.routingBlock.add(this.routingContainer);
        this.routingField = new TextField<>("routingField", new PropertyModel<>(this, "routingValue"));
        this.routingField.setLabel(Model.of("Routing"));
        this.routingContainer.add(this.routingField);
        this.routingFeedback = new TextFeedbackPanel("routingFeedback", this.routingField);
        this.routingContainer.add(this.routingFeedback);

        this.receiptBlock = new WebMarkupContainer("receiptBlock");
        this.receiptBlock.setOutputMarkupId(true);
        this.form.add(this.receiptBlock);
        this.receiptContainer = new WebMarkupContainer("receiptContainer");
        this.receiptBlock.add(this.receiptContainer);
        this.receiptField = new TextField<>("receiptField", new PropertyModel<>(this, "receiptValue"));
        this.receiptField.setLabel(Model.of("Receipt"));
        this.receiptContainer.add(this.receiptField);
        this.receiptFeedback = new TextFeedbackPanel("receiptFeedback", this.receiptField);
        this.receiptContainer.add(this.receiptFeedback);

        this.bankBlock = new WebMarkupContainer("bankBlock");
        this.bankBlock.setOutputMarkupId(true);
        this.form.add(this.bankBlock);
        this.bankContainer = new WebMarkupContainer("bankContainer");
        this.bankBlock.add(this.bankContainer);
        this.bankField = new TextField<>("bankField", new PropertyModel<>(this, "bankValue"));
        this.bankField.setLabel(Model.of("bank"));
        this.bankContainer.add(this.bankField);
        this.bankFeedback = new TextFeedbackPanel("bankFeedback", this.bankField);
        this.bankContainer.add(this.bankFeedback);

        this.noteBlock = new WebMarkupContainer("noteBlock");
        this.noteBlock.setOutputMarkupId(true);
        this.form.add(this.noteBlock);
        this.noteContainer = new WebMarkupContainer("noteContainer");
        this.noteBlock.add(this.noteContainer);
        this.noteField = new TextArea<>("noteField", new PropertyModel<>(this, "noteValue"));
        this.noteField.setLabel(Model.of("Note"));
        this.noteContainer.add(this.noteField);
        this.noteFeedback = new TextFeedbackPanel("noteFeedback", this.noteField);
        this.noteContainer.add(this.noteFeedback);

        withdrawBalanceFieldUpdate(null);
    }

    protected void initData() {
        this.clientId = getPageParameters().get("clientId").toString();
        this.accountId = getPageParameters().get("accountId").toString();
        this.closedOnValue = DateTime.now().toDate();
        this.postInterestValue = true;
    }

    protected boolean withdrawBalanceFieldUpdate(AjaxRequestTarget target) {
        boolean visible = this.withdrawBalanceValue == null ? false : this.withdrawBalanceValue;
        this.transactionAmountContainer.setVisible(visible);
        this.paymentTypeContainer.setVisible(visible);
        if (target != null) {
            target.add(this.transactionAmountBlock);
            target.add(this.paymentTypeBlock);
        }
        paymentDetailFieldUpdate(target);
        return false;
    }

    protected boolean paymentDetailFieldUpdate(AjaxRequestTarget target) {
        boolean visible = this.paymentDetailValue == null ? false : this.paymentDetailValue;
        this.accountContainer.setVisible(visible);
        this.chequeContainer.setVisible(visible);
        this.routingContainer.setVisible(visible);
        this.receiptContainer.setVisible(visible);
        this.bankContainer.setVisible(visible);
        if (target != null) {
            target.add(this.accountBlock);
            target.add(this.chequeBlock);
            target.add(this.routingBlock);
            target.add(this.receiptBlock);
            target.add(this.bankBlock);
        }
        return false;
    }

    protected void saveButtonSubmit(Button button) {
        CloseBuilder builder = new CloseBuilder();

        builder.withId(this.accountId);

        builder.withPostInterestValidationOnClosure(this.postInterestValue == null ? false : this.postInterestValue);
        builder.withClosedOnDate(this.closedOnValue);
        builder.withWithdrawBalance(this.withdrawBalanceValue == null ? false : this.withdrawBalanceValue);
        if (this.paymentTypeValue != null) {
            builder.withPaymentTypeId(this.paymentTypeValue.getId());
        }

        if (this.paymentDetailValue != null && this.paymentDetailValue) {
            builder.withAccountNumber(this.accountValue);
            builder.withCheckNumber(this.chequeValue);
            builder.withRoutingCode(this.routingValue);
            builder.withReceiptNumber(this.receiptValue);
            builder.withBankNumber(this.bankValue);
        }

        builder.withNote(this.noteValue);

        JsonNode node = null;
        JsonNode request = builder.build();
        try {
            node = ClientHelper.closeSavingAccount((Session) getSession(), request);
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }

        PageParameters parameters = new PageParameters();
        parameters.add("clientId", this.clientId);
        setResponsePage(ClientPreviewPage.class, parameters);
    }

}
