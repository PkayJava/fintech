package com.angkorteam.fintech.pages.client.client;

import java.util.Date;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
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
import com.angkorteam.fintech.helper.acount.WithdrawBuilder;
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
public class SavingAccountWithdrawPage extends DeprecatedPage {

    protected String clientId;
    protected String accountId;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupContainer transactionDateBlock;
    protected WebMarkupContainer transactionDateContainer;
    protected Date transactionDateValue;
    protected DateTextField transactionDateField;
    protected TextFeedbackPanel transactionDateFeedback;

    protected WebMarkupContainer transactionAmountBlock;
    protected WebMarkupContainer transactionAmountContainer;
    protected Double transactionAmountValue;
    protected TextField<Double> transactionAmountField;
    protected TextFeedbackPanel transactionAmountFeedback;

    protected SingleChoiceProvider paymentTypeProvider;
    protected WebMarkupContainer paymentTypeBlock;
    protected WebMarkupContainer paymentTypeContainer;
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

        this.transactionDateBlock = new WebMarkupContainer("transactionDateBlock");
        this.transactionDateBlock.setOutputMarkupId(true);
        this.form.add(this.transactionDateBlock);
        this.transactionDateContainer = new WebMarkupContainer("transactionDateContainer");
        this.transactionDateBlock.add(this.transactionDateContainer);
        this.transactionDateField = new DateTextField("transactionDateField", new PropertyModel<>(this, "transactionDateValue"));
        this.transactionDateField.setLabel(Model.of("Transaction Date"));
        this.transactionDateField.setRequired(false);
        this.transactionDateContainer.add(this.transactionDateField);
        this.transactionDateFeedback = new TextFeedbackPanel("transactionDateFeedback", this.transactionDateField);
        this.transactionDateContainer.add(this.transactionDateFeedback);

        this.transactionAmountBlock = new WebMarkupContainer("transactionAmountBlock");
        this.transactionAmountBlock.setOutputMarkupId(true);
        this.form.add(this.transactionAmountBlock);
        this.transactionAmountContainer = new WebMarkupContainer("transactionAmountContainer");
        this.transactionAmountBlock.add(this.transactionAmountContainer);
        this.transactionAmountField = new TextField<>("transactionAmountField", new PropertyModel<>(this, "transactionAmountValue"));
        this.transactionAmountField.setLabel(Model.of("Transaction Amount"));
        this.transactionAmountField.setRequired(false);
        this.transactionAmountContainer.add(this.transactionAmountField);
        this.transactionAmountFeedback = new TextFeedbackPanel("transactionAmountFeedback", this.transactionAmountField);
        this.transactionAmountContainer.add(this.transactionAmountFeedback);

        this.paymentTypeProvider = new SingleChoiceProvider("m_payment_type", "id", "value");
        this.paymentTypeBlock = new WebMarkupContainer("paymentTypeBlock");
        this.paymentTypeBlock.setOutputMarkupId(true);
        this.form.add(this.paymentTypeBlock);
        this.paymentTypeContainer = new WebMarkupContainer("paymentTypeContainer");
        this.paymentTypeBlock.add(this.paymentTypeContainer);
        this.paymentTypeField = new Select2SingleChoice<>("paymentTypeField", new PropertyModel<>(this, "paymentTypeValue"), this.paymentTypeProvider);
        this.paymentTypeField.setLabel(Model.of("Payment Type"));
        this.paymentTypeField.setRequired(false);
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

        paymentDetailFieldUpdate(null);

    }

    protected void initData() {
        this.clientId = getPageParameters().get("clientId").toString();
        this.accountId = getPageParameters().get("accountId").toString();
        this.transactionDateValue = DateTime.now().toDate();
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
        WithdrawBuilder builder = new WithdrawBuilder();
        builder.withId(this.accountId);
        builder.withTransactionDate(this.transactionDateValue);
        builder.withTransactionAmount(this.transactionAmountValue);
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

        JsonNode node = null;
        try {
            node = ClientHelper.withdrawSavingAccount((Session) getSession(), builder.build());
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
