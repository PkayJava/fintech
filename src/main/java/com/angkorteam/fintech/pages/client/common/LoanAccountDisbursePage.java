package com.angkorteam.fintech.pages.client.common;

import java.util.Date;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.joda.time.DateTime;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.ClientEnum;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.helper.loan.DisburseBuilder;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class LoanAccountDisbursePage extends Page {

    protected ClientEnum client;

    protected String clientId;
    protected String groupId;
    protected String centerId;

    protected String loanId;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock disbursedOnBlock;
    protected WebMarkupContainer disbursedOnIContainer;
    protected Date disbursedOnValue;
    protected DateTextField disbursedOnField;
    protected TextFeedbackPanel disbursedOnFeedback;

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

    protected WebMarkupBlock noteBlock;
    protected WebMarkupContainer noteIContainer;
    protected String noteValue;
    protected TextArea<String> noteField;
    protected TextFeedbackPanel noteFeedback;

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        PageParameters parameters = new PageParameters();
        parameters.add("client", this.client.name());
        parameters.add("loanId", this.loanId);
        if (this.client == ClientEnum.Client) {
            parameters.add("clientId", this.clientId);
        } else if (this.client == ClientEnum.Center) {
            parameters.add("centerId", this.centerId);
        } else if (this.client == ClientEnum.Group) {
            parameters.add("groupId", this.groupId);
        } else {
            throw new WicketRuntimeException("Unknown " + this.client);
        }

        this.closeLink = new BookmarkablePageLink<>("closeLink", LoanAccountPreviewPage.class, parameters);
        this.form.add(this.closeLink);

        initDisbursedOnBlock();

        initTransactionAmountBlock();

        initPaymentTypeBlock();

        initPaymentDetailBlock();

        initAccountBlock();

        initChequeBlock();

        initRoutingBlock();

        initReceiptBlock();

        initBankBlock();

        initNoteBlock();
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
        paymentDetailFieldUpdate(null);
    }

    protected void initNoteBlock() {
        this.noteBlock = new WebMarkupBlock("noteBlock", Size.Six_6);
        this.form.add(this.noteBlock);
        this.noteIContainer = new WebMarkupContainer("noteIContainer");
        this.noteBlock.add(this.noteIContainer);
        this.noteField = new TextArea<>("noteField", new PropertyModel<>(this, "noteValue"));
        this.noteField.setLabel(Model.of("Note"));
        this.noteIContainer.add(this.noteField);
        this.noteFeedback = new TextFeedbackPanel("noteFeedback", this.noteField);
        this.noteIContainer.add(this.noteFeedback);
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
        this.paymentTypeProvider = new SingleChoiceProvider("m_payment_type", "id", "value");
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

    protected void initDisbursedOnBlock() {
        this.disbursedOnBlock = new WebMarkupBlock("disbursedOnBlock", Size.Six_6);
        this.form.add(this.disbursedOnBlock);
        this.disbursedOnIContainer = new WebMarkupContainer("disbursedOnIContainer");
        this.disbursedOnBlock.add(this.disbursedOnIContainer);
        this.disbursedOnField = new DateTextField("disbursedOnField", new PropertyModel<>(this, "disbursedOnValue"));
        this.disbursedOnField.setLabel(Model.of("Disbursed On"));
        this.disbursedOnField.setRequired(false);
        this.disbursedOnIContainer.add(this.disbursedOnField);
        this.disbursedOnFeedback = new TextFeedbackPanel("disbursedOnFeedback", this.disbursedOnField);
        this.disbursedOnIContainer.add(this.disbursedOnFeedback);
    }

    @Override
    protected void initData() {
        this.client = ClientEnum.valueOf(getPageParameters().get("client").toString());

        this.clientId = getPageParameters().get("clientId").toString();
        this.groupId = getPageParameters().get("groupId").toString();
        this.centerId = getPageParameters().get("centerId").toString();

        this.loanId = getPageParameters().get("loanId").toString();
        this.disbursedOnValue = DateTime.now().toDate();

        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
        this.transactionAmountValue = jdbcTemplate.queryForObject("select principle_amount from m_loan where id = ?", Double.class, this.loanId);
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
        DisburseBuilder builder = new DisburseBuilder();
        builder.withId(this.loanId);
        builder.withActualDisbursementDate(this.disbursedOnValue);
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
        builder.withNote(this.noteValue);

        JsonNode node = null;
        try {
            node = ClientHelper.disburseLoanAccount((Session) getSession(), builder.build());
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }

        PageParameters parameters = new PageParameters();
        parameters.add("client", this.client.name());
        parameters.add("loanId", this.loanId);
        if (this.client == ClientEnum.Client) {
            parameters.add("clientId", this.clientId);
        } else if (this.client == ClientEnum.Center) {
            parameters.add("centerId", this.centerId);
        } else if (this.client == ClientEnum.Group) {
            parameters.add("groupId", this.groupId);
        } else {
            throw new WicketRuntimeException("Unknown " + this.client);
        }
        setResponsePage(LoanAccountPreviewPage.class, parameters);
    }

}
