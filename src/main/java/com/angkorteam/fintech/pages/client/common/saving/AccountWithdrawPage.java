package com.angkorteam.fintech.pages.client.common.saving;

import java.util.Date;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
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
import com.angkorteam.fintech.dto.builder.saving.WithdrawBuilder;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.pages.client.center.CenterPreviewPage;
import com.angkorteam.fintech.pages.client.client.ClientPreviewPage;
import com.angkorteam.fintech.pages.client.group.GroupPreviewPage;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

import io.github.openunirest.http.JsonNode;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class AccountWithdrawPage extends Page {

    protected ClientEnum client;

    protected String clientId;

    protected String savingId;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected UIRow row1;

    protected UIBlock transactionDateBlock;
    protected UIContainer transactionDateIContainer;
    protected Date transactionDateValue;
    protected DateTextField transactionDateField;

    protected UIBlock row1Block1;

    protected UIRow row2;

    protected UIBlock transactionAmountBlock;
    protected UIContainer transactionAmountIContainer;
    protected Double transactionAmountValue;
    protected TextField<Double> transactionAmountField;

    protected UIBlock row2Block1;

    protected UIRow row3;

    protected SingleChoiceProvider paymentTypeProvider;
    protected UIBlock paymentTypeBlock;
    protected UIContainer paymentTypeIContainer;
    protected Select2SingleChoice<Option> paymentTypeField;
    protected Option paymentTypeValue;

    protected UIBlock row3Block1;

    protected UIRow row4;

    protected UIBlock paymentDetailBlock;
    protected UIContainer paymentDetailIContainer;
    protected CheckBox paymentDetailField;
    protected Boolean paymentDetailValue;

    protected UIBlock row4Block1;

    protected UIRow row5;

    protected UIBlock accountBlock;
    protected UIContainer accountIContainer;
    protected TextField<String> accountField;
    protected String accountValue;

    protected UIBlock chequeBlock;
    protected UIContainer chequeIContainer;
    protected TextField<String> chequeField;
    protected String chequeValue;

    protected UIRow row6;

    protected UIBlock routingBlock;
    protected UIContainer routingIContainer;
    protected TextField<String> routingField;
    protected String routingValue;

    protected UIBlock receiptBlock;
    protected UIContainer receiptIContainer;
    protected TextField<String> receiptField;
    protected String receiptValue;

    protected UIRow row7;

    protected UIBlock bankBlock;
    protected UIContainer bankIContainer;
    protected TextField<String> bankField;
    protected String bankValue;

    protected UIBlock row7Block1;

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        PageParameters parameters = new PageParameters();
        parameters.add("clientId", this.clientId);
        if (this.client == ClientEnum.Client) {
            this.closeLink = new BookmarkablePageLink<>("closeLink", ClientPreviewPage.class, parameters);
        } else if (this.client == ClientEnum.Group) {
            this.closeLink = new BookmarkablePageLink<>("closeLink", GroupPreviewPage.class, parameters);
        } else if (this.client == ClientEnum.Center) {
            this.closeLink = new BookmarkablePageLink<>("closeLink", CenterPreviewPage.class, parameters);
        }

        this.form.add(this.closeLink);

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.transactionDateBlock = this.row1.newUIBlock("transactionDateBlock", Size.Six_6);
        this.transactionDateIContainer = this.transactionDateBlock.newUIContainer("transactionDateIContainer");
        this.transactionDateField = new DateTextField("transactionDateField", new PropertyModel<>(this, "transactionDateValue"));
        this.transactionDateIContainer.add(this.transactionDateField);
        this.transactionDateIContainer.newFeedback("transactionDateFeedback", this.transactionDateField);

        this.row1Block1 = this.row1.newUIBlock("row1Block1", Size.Six_6);

        this.row2 = UIRow.newUIRow("row2", this.form);

        this.transactionAmountBlock = this.row2.newUIBlock("transactionAmountBlock", Size.Six_6);
        this.transactionAmountIContainer = this.transactionAmountBlock.newUIContainer("transactionAmountIContainer");
        this.transactionAmountField = new TextField<>("transactionAmountField", new PropertyModel<>(this, "transactionAmountValue"));
        this.transactionAmountIContainer.add(this.transactionAmountField);
        this.transactionAmountIContainer.newFeedback("transactionAmountFeedback", this.transactionAmountField);

        this.row2Block1 = this.row2.newUIBlock("row2Block1", Size.Six_6);

        this.row3 = UIRow.newUIRow("row3", this.form);

        this.paymentTypeBlock = this.row3.newUIBlock("paymentTypeBlock", Size.Six_6);
        this.paymentTypeIContainer = this.paymentTypeBlock.newUIContainer("paymentTypeIContainer");
        this.paymentTypeField = new Select2SingleChoice<>("paymentTypeField", new PropertyModel<>(this, "paymentTypeValue"), this.paymentTypeProvider);
        this.paymentTypeIContainer.add(this.paymentTypeField);
        this.paymentTypeIContainer.newFeedback("paymentTypeFeedback", this.paymentTypeField);

        this.row3Block1 = this.row3.newUIBlock("row3Block1", Size.Six_6);

        this.row4 = UIRow.newUIRow("row4", this.form);

        this.paymentDetailBlock = this.row4.newUIBlock("paymentDetailBlock", Size.Six_6);
        this.paymentDetailIContainer = this.paymentDetailBlock.newUIContainer("paymentDetailIContainer");
        this.paymentDetailField = new CheckBox("paymentDetailField", new PropertyModel<>(this, "paymentDetailValue"));
        this.paymentDetailIContainer.add(this.paymentDetailField);
        this.paymentDetailIContainer.newFeedback("paymentDetailFeedback", this.paymentDetailField);

        this.row4Block1 = this.row4.newUIBlock("row4Block1", Size.Six_6);

        this.row5 = UIRow.newUIRow("row5", this.form);

        this.accountBlock = this.row5.newUIBlock("accountBlock", Size.Six_6);
        this.accountIContainer = this.accountBlock.newUIContainer("accountIContainer");
        this.accountField = new TextField<>("accountField", new PropertyModel<>(this, "accountValue"));
        this.accountIContainer.add(this.accountField);
        this.accountIContainer.newFeedback("accountFeedback", this.accountField);

        this.chequeBlock = this.row5.newUIBlock("chequeBlock", Size.Six_6);
        this.chequeIContainer = this.chequeBlock.newUIContainer("chequeIContainer");
        this.chequeField = new TextField<>("chequeField", new PropertyModel<>(this, "chequeValue"));
        this.chequeIContainer.add(this.chequeField);
        this.chequeIContainer.newFeedback("chequeFeedback", this.chequeField);

        this.row6 = UIRow.newUIRow("row6", this.form);

        this.routingBlock = this.row6.newUIBlock("routingBlock", Size.Six_6);
        this.routingIContainer = this.routingBlock.newUIContainer("routingIContainer");
        this.routingField = new TextField<>("routingField", new PropertyModel<>(this, "routingValue"));
        this.routingIContainer.add(this.routingField);
        this.routingIContainer.newFeedback("routingFeedback", this.routingField);

        this.receiptBlock = this.row6.newUIBlock("receiptBlock", Size.Six_6);
        this.receiptIContainer = this.receiptBlock.newUIContainer("receiptIContainer");
        this.receiptField = new TextField<>("receiptField", new PropertyModel<>(this, "receiptValue"));
        this.receiptIContainer.add(this.receiptField);
        this.receiptIContainer.newFeedback("receiptFeedback", this.receiptField);

        this.row7 = UIRow.newUIRow("row7", this.form);

        this.bankBlock = this.row7.newUIBlock("bankBlock", Size.Six_6);
        this.bankIContainer = this.bankBlock.newUIContainer("bankIContainer");
        this.bankField = new TextField<>("bankField", new PropertyModel<>(this, "bankValue"));
        this.bankIContainer.add(this.bankField);
        this.bankIContainer.newFeedback("bankFeedback", this.bankField);

        this.row7Block1 = this.row7.newUIBlock("row7Block1", Size.Six_6);
    }

    @Override
    protected void configureMetaData() {
        this.transactionDateField.setLabel(Model.of("Transaction Date"));

        this.transactionAmountField.setLabel(Model.of("Transaction Amount"));

        this.paymentTypeField.setLabel(Model.of("Payment Type"));

        this.paymentDetailField.add(new OnChangeAjaxBehavior(this::paymentDetailFieldUpdate));

        this.accountField.setLabel(Model.of("Account"));

        this.chequeField.setLabel(Model.of("Cheque"));

        this.routingField.setLabel(Model.of("Routing"));

        this.receiptField.setLabel(Model.of("Receipt"));

        this.bankField.setLabel(Model.of("Bank"));

        paymentDetailFieldUpdate(null);
    }

    @Override
    protected void initData() {
        this.client = ClientEnum.valueOf(getPageParameters().get("client").toString());

        this.clientId = getPageParameters().get("clientId").toString();

        this.paymentTypeProvider = new SingleChoiceProvider(MPaymentType.NAME, MPaymentType.Field.ID, MPaymentType.Field.VALUE);

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
        WithdrawBuilder builder = new WithdrawBuilder();
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

        JsonNode node = ClientHelper.withdrawSavingAccount((Session) getSession(), builder.build());

        if (reportError(node)) {
            return;
        }

        PageParameters parameters = new PageParameters();
        parameters.add("clientId", this.clientId);
        if (this.client == ClientEnum.Client) {
            setResponsePage(ClientPreviewPage.class, parameters);
        } else if (this.client == ClientEnum.Center) {
            setResponsePage(CenterPreviewPage.class, parameters);
        } else if (this.client == ClientEnum.Group) {
            setResponsePage(GroupPreviewPage.class, parameters);
        }
    }

}
