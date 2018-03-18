package com.angkorteam.fintech.pages.client.common;

import java.util.Date;

import org.apache.wicket.WicketRuntimeException;
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

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.ddl.MPaymentType;
import com.angkorteam.fintech.dto.ClientEnum;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.saving.DepositBuilder;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.pages.client.center.CenterPreviewPage;
import com.angkorteam.fintech.pages.client.client.ClientPreviewPage;
import com.angkorteam.fintech.pages.client.group.GroupPreviewPage;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.mashape.unirest.http.JsonNode;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class SavingAccountDepositPage extends Page {

    protected ClientEnum client;

    protected String clientId;
    protected String groupId;
    protected String centerId;

    protected String savingId;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock transactionDateBlock;
    protected WebMarkupContainer transactionDateIContainer;
    protected Date transactionDateValue;
    protected DateTextField transactionDateField;
    protected TextFeedbackPanel transactionDateFeedback;

    protected WebMarkupBlock transactionAmountBlock;
    protected WebMarkupContainer transactionAmountIContainer;
    protected Double transactionAmountValue;
    protected TextField<Double> transactionAmountField;
    protected TextFeedbackPanel transactionAmountFeedback;

    protected SingleChoiceProvider paymentTypeProvider;
    protected WebMarkupBlock paymentTypeBlock;
    protected WebMarkupContainer paymentTypeIContainer;
    protected Option paymentTypeValue;
    protected Select2SingleChoice<Option> paymentTypeField;
    protected TextFeedbackPanel paymentTypeFeedback;

    protected WebMarkupBlock paymentDetailBlock;
    protected WebMarkupContainer paymentDetailIContainer;
    protected Boolean paymentDetailValue;
    protected CheckBox paymentDetailField;
    protected TextFeedbackPanel paymentDetailFeedback;

    protected WebMarkupBlock accountBlock;
    protected WebMarkupContainer accountIContainer;
    protected String accountValue;
    protected TextField<String> accountField;
    protected TextFeedbackPanel accountFeedback;

    protected WebMarkupBlock chequeBlock;
    protected WebMarkupContainer chequeIContainer;
    protected String chequeValue;
    protected TextField<String> chequeField;
    protected TextFeedbackPanel chequeFeedback;

    protected WebMarkupBlock routingBlock;
    protected WebMarkupContainer routingIContainer;
    protected String routingValue;
    protected TextField<String> routingField;
    protected TextFeedbackPanel routingFeedback;

    protected WebMarkupBlock receiptBlock;
    protected WebMarkupContainer receiptIContainer;
    protected String receiptValue;
    protected TextField<String> receiptField;
    protected TextFeedbackPanel receiptFeedback;

    protected WebMarkupBlock bankBlock;
    protected WebMarkupContainer bankIContainer;
    protected String bankValue;
    protected TextField<String> bankField;
    protected TextFeedbackPanel bankFeedback;

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        PageParameters parameters = new PageParameters();
        if (this.client == ClientEnum.Client) {
            parameters.add("clientId", this.clientId);
            this.closeLink = new BookmarkablePageLink<>("closeLink", ClientPreviewPage.class, parameters);
        } else if (this.client == ClientEnum.Group) {
            parameters.add("groupId", this.groupId);
            this.closeLink = new BookmarkablePageLink<>("closeLink", GroupPreviewPage.class, parameters);
        } else if (this.client == ClientEnum.Center) {
            parameters.add("centerId", this.centerId);
            this.closeLink = new BookmarkablePageLink<>("closeLink", CenterPreviewPage.class, parameters);
        } else {
            throw new WicketRuntimeException("Unknown " + this.client);
        }
        this.form.add(this.closeLink);

        initTransactionDateBlock();

        initTransactionAmountBlock();

        initPaymentTypeBlock();

        initPaymentDetailBlock();

        initAccountBlock();

        initChequeBlock();

        initRoutingBlock();

        initReceiptBlock();

        initBankBlock();
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
        paymentDetailFieldUpdate(null);
    }

    protected void initBankBlock() {
        this.bankBlock = new WebMarkupBlock("bankBlock", Size.Six_6);
        this.form.add(this.bankBlock);
        this.bankIContainer = new WebMarkupContainer("bankIContainer");
        this.bankBlock.add(this.bankIContainer);
        this.bankField = new TextField<>("bankField", new PropertyModel<>(this, "bankValue"));
        this.bankField.setLabel(Model.of("bank"));
        this.bankIContainer.add(this.bankField);
        this.bankFeedback = new TextFeedbackPanel("bankFeedback", this.bankField);
        this.bankIContainer.add(this.bankFeedback);
    }

    protected void initReceiptBlock() {
        this.receiptBlock = new WebMarkupBlock("receiptBlock", Size.Six_6);
        this.form.add(this.receiptBlock);
        this.receiptIContainer = new WebMarkupContainer("receiptIContainer");
        this.receiptBlock.add(this.receiptIContainer);
        this.receiptField = new TextField<>("receiptField", new PropertyModel<>(this, "receiptValue"));
        this.receiptField.setLabel(Model.of("Receipt"));
        this.receiptIContainer.add(this.receiptField);
        this.receiptFeedback = new TextFeedbackPanel("receiptFeedback", this.receiptField);
        this.receiptIContainer.add(this.receiptFeedback);
    }

    protected void initRoutingBlock() {
        this.routingBlock = new WebMarkupBlock("routingBlock", Size.Six_6);
        this.form.add(this.routingBlock);
        this.routingIContainer = new WebMarkupContainer("routingIContainer");
        this.routingBlock.add(this.routingIContainer);
        this.routingField = new TextField<>("routingField", new PropertyModel<>(this, "routingValue"));
        this.routingField.setLabel(Model.of("Routing"));
        this.routingIContainer.add(this.routingField);
        this.routingFeedback = new TextFeedbackPanel("routingFeedback", this.routingField);
        this.routingIContainer.add(this.routingFeedback);
    }

    protected void initChequeBlock() {
        this.chequeBlock = new WebMarkupBlock("chequeBlock", Size.Six_6);
        this.form.add(this.chequeBlock);
        this.chequeIContainer = new WebMarkupContainer("chequeIContainer");
        this.chequeBlock.add(this.chequeIContainer);
        this.chequeField = new TextField<>("chequeField", new PropertyModel<>(this, "chequeValue"));
        this.chequeField.setLabel(Model.of("Cheque"));
        this.chequeIContainer.add(this.chequeField);
        this.chequeFeedback = new TextFeedbackPanel("chequeFeedback", this.chequeField);
        this.chequeIContainer.add(this.chequeFeedback);
    }

    protected void initAccountBlock() {
        this.accountBlock = new WebMarkupBlock("accountBlock", Size.Six_6);
        this.form.add(this.accountBlock);
        this.accountIContainer = new WebMarkupContainer("accountIContainer");
        this.accountBlock.add(this.accountIContainer);
        this.accountField = new TextField<>("accountField", new PropertyModel<>(this, "accountValue"));
        this.accountField.setLabel(Model.of("Account"));
        this.accountIContainer.add(this.accountField);
        this.accountFeedback = new TextFeedbackPanel("accountFeedback", this.accountField);
        this.accountIContainer.add(this.accountFeedback);
    }

    protected void initPaymentDetailBlock() {
        this.paymentDetailBlock = new WebMarkupBlock("paymentDetailBlock", Size.Six_6);
        this.form.add(this.paymentDetailBlock);
        this.paymentDetailIContainer = new WebMarkupContainer("paymentDetailIContainer");
        this.paymentDetailBlock.add(this.paymentDetailIContainer);
        this.paymentDetailField = new CheckBox("paymentDetailField", new PropertyModel<>(this, "paymentDetailValue"));
        this.paymentDetailField.add(new OnChangeAjaxBehavior(this::paymentDetailFieldUpdate));
        this.paymentDetailIContainer.add(this.paymentDetailField);
        this.paymentDetailFeedback = new TextFeedbackPanel("paymentDetailFeedback", this.paymentDetailField);
        this.paymentDetailIContainer.add(this.paymentDetailFeedback);
    }

    protected void initPaymentTypeBlock() {
        this.paymentTypeProvider = new SingleChoiceProvider(MPaymentType.NAME, MPaymentType.Field.ID, MPaymentType.Field.VALUE);
        this.paymentTypeBlock = new WebMarkupBlock("paymentTypeBlock", Size.Six_6);
        this.form.add(this.paymentTypeBlock);
        this.paymentTypeIContainer = new WebMarkupContainer("paymentTypeIContainer");
        this.paymentTypeBlock.add(this.paymentTypeIContainer);
        this.paymentTypeField = new Select2SingleChoice<>("paymentTypeField", new PropertyModel<>(this, "paymentTypeValue"), this.paymentTypeProvider);
        this.paymentTypeField.setLabel(Model.of("Payment Type"));
        this.paymentTypeField.setRequired(false);
        this.paymentTypeIContainer.add(this.paymentTypeField);
        this.paymentTypeFeedback = new TextFeedbackPanel("paymentTypeFeedback", this.paymentTypeField);
        this.paymentTypeIContainer.add(this.paymentTypeFeedback);
    }

    protected void initTransactionAmountBlock() {
        this.transactionAmountBlock = new WebMarkupBlock("transactionAmountBlock", Size.Six_6);
        this.form.add(this.transactionAmountBlock);
        this.transactionAmountIContainer = new WebMarkupContainer("transactionAmountIContainer");
        this.transactionAmountBlock.add(this.transactionAmountIContainer);
        this.transactionAmountField = new TextField<>("transactionAmountField", new PropertyModel<>(this, "transactionAmountValue"));
        this.transactionAmountField.setLabel(Model.of("Transaction Amount"));
        this.transactionAmountField.setRequired(false);
        this.transactionAmountIContainer.add(this.transactionAmountField);
        this.transactionAmountFeedback = new TextFeedbackPanel("transactionAmountFeedback", this.transactionAmountField);
        this.transactionAmountIContainer.add(this.transactionAmountFeedback);
    }

    protected void initTransactionDateBlock() {
        this.transactionDateBlock = new WebMarkupBlock("transactionDateBlock", Size.Six_6);
        this.form.add(this.transactionDateBlock);
        this.transactionDateIContainer = new WebMarkupContainer("transactionDateIContainer");
        this.transactionDateBlock.add(this.transactionDateIContainer);
        this.transactionDateField = new DateTextField("transactionDateField", new PropertyModel<>(this, "transactionDateValue"));
        this.transactionDateField.setLabel(Model.of("Transaction Date"));
        this.transactionDateField.setRequired(false);
        this.transactionDateIContainer.add(this.transactionDateField);
        this.transactionDateFeedback = new TextFeedbackPanel("transactionDateFeedback", this.transactionDateField);
        this.transactionDateIContainer.add(this.transactionDateFeedback);
    }

    @Override
    protected void initData() {
        this.client = ClientEnum.valueOf(getPageParameters().get("client").toString());

        this.clientId = getPageParameters().get("clientId").toString();
        this.groupId = getPageParameters().get("groupId").toString();
        this.centerId = getPageParameters().get("centerId").toString();

        this.savingId = getPageParameters().get("savingId").toString();
        this.transactionDateValue = DateTime.now().toDate();
    }

    protected boolean paymentDetailFieldUpdate(AjaxRequestTarget target) {
        boolean visible = this.paymentDetailValue == null ? false : this.paymentDetailValue;
        this.accountIContainer.setVisible(visible);
        this.chequeIContainer.setVisible(visible);
        this.routingIContainer.setVisible(visible);
        this.receiptIContainer.setVisible(visible);
        this.bankIContainer.setVisible(visible);
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
        DepositBuilder builder = new DepositBuilder();
        builder.withId(this.savingId);
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

        JsonNode node = ClientHelper.depositSavingAccount((Session) getSession(), builder.build());

        if (reportError(node)) {
            return;
        }

        if (this.client == ClientEnum.Client) {
            PageParameters parameters = new PageParameters();
            parameters.add("clientId", this.clientId);
            setResponsePage(ClientPreviewPage.class, parameters);
        } else if (this.client == ClientEnum.Center) {
            PageParameters parameters = new PageParameters();
            parameters.add("centerId", this.centerId);
            setResponsePage(CenterPreviewPage.class, parameters);
        } else if (this.client == ClientEnum.Group) {
            PageParameters parameters = new PageParameters();
            parameters.add("groupId", this.groupId);
            setResponsePage(GroupPreviewPage.class, parameters);
        } else {
            throw new WicketRuntimeException("Unknown " + this.client);
        }
    }

}
