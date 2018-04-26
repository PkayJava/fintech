package com.angkorteam.fintech.pages.client.common;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.joda.time.DateTime;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.ddl.MClient;
import com.angkorteam.fintech.ddl.MGroup;
import com.angkorteam.fintech.ddl.MLoan;
import com.angkorteam.fintech.ddl.MPaymentType;
import com.angkorteam.fintech.dto.ClientEnum;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.loan.DisburseBuilder;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.pages.client.center.CenterBrowsePage;
import com.angkorteam.fintech.pages.client.center.CenterPreviewPage;
import com.angkorteam.fintech.pages.client.client.ClientBrowsePage;
import com.angkorteam.fintech.pages.client.client.ClientPreviewPage;
import com.angkorteam.fintech.pages.client.group.GroupBrowsePage;
import com.angkorteam.fintech.pages.client.group.GroupPreviewPage;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;

import io.github.openunirest.http.JsonNode;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class LoanAccountDisbursePage extends Page {

    protected ClientEnum client;

    protected String clientId;
    protected String groupId;
    protected String centerId;

    protected String clientDisplayName;
    protected String groupDisplayName;
    protected String centerDisplayName;
    protected String loanAccountNo;

    protected String loanId;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected UIRow row1;

    protected UIBlock disbursedOnBlock;
    protected UIContainer disbursedOnIContainer;
    protected Date disbursedOnValue;
    protected DateTextField disbursedOnField;

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
    protected Option paymentTypeValue;
    protected Select2SingleChoice<Option> paymentTypeField;

    protected UIBlock row3Block1;

    protected UIRow row4;

    protected UIBlock paymentDetailBlock;
    protected UIContainer paymentDetailIContainer;
    protected Boolean paymentDetailValue;
    protected CheckBox paymentDetailField;

    protected UIBlock row4Block1;

    protected UIRow row5;

    protected UIBlock accountBlock;
    protected UIContainer accountIContainer;
    protected String accountValue;
    protected TextField<String> accountField;

    protected UIBlock chequeBlock;
    protected UIContainer chequeIContainer;
    protected String chequeValue;
    protected TextField<String> chequeField;

    protected UIRow row6;

    protected UIBlock routingBlock;
    protected UIContainer routingIContainer;
    protected String routingValue;
    protected TextField<String> routingField;

    protected UIBlock receiptBlock;
    protected UIContainer receiptIContainer;
    protected String receiptValue;
    protected TextField<String> receiptField;

    protected UIRow row7;

    protected UIBlock bankBlock;
    protected UIContainer bankIContainer;
    protected String bankValue;
    protected TextField<String> bankField;

    protected UIBlock row7Block1;

    protected UIRow row8;

    protected UIBlock noteBlock;
    protected UIContainer noteIContainer;
    protected String noteValue;
    protected TextArea<String> noteField;

    protected UIBlock row8Block1;

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

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.disbursedOnBlock = this.row1.newUIBlock("disbursedOnBlock", Size.Six_6);
        this.disbursedOnIContainer = this.disbursedOnBlock.newUIContainer("disbursedOnIContainer");
        this.disbursedOnField = new DateTextField("disbursedOnField", new PropertyModel<>(this, "disbursedOnValue"));
        this.disbursedOnIContainer.add(this.disbursedOnField);
        this.disbursedOnIContainer.newFeedback("disbursedOnFeedback", this.disbursedOnField);

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

        this.row8 = UIRow.newUIRow("row8", this.form);

        this.noteBlock = this.row8.newUIBlock("noteBlock", Size.Six_6);
        this.noteIContainer = this.noteBlock.newUIContainer("noteIContainer");
        this.noteField = new TextArea<>("noteField", new PropertyModel<>(this, "noteValue"));
        this.noteIContainer.add(this.noteField);
        this.noteIContainer.newFeedback("noteFeedback", this.noteField);

        this.row8Block1 = this.row8.newUIBlock("row8Block1", Size.Six_6);

    }

    @Override
    protected void configureMetaData() {
        this.noteField.setLabel(Model.of("Note"));

        this.bankField.setLabel(Model.of("Bank"));

        this.receiptField.setLabel(Model.of("Receipt"));

        this.routingField.setLabel(Model.of("Routing"));

        this.chequeField.setLabel(Model.of("Cheque"));

        this.accountField.setLabel(Model.of("Account"));

        this.paymentDetailField.add(new OnChangeAjaxBehavior(this::paymentDetailFieldUpdate));

        this.paymentTypeField.setLabel(Model.of("Payment Type"));
        this.paymentTypeField.setRequired(false);

        this.transactionAmountField.setLabel(Model.of("Transaction Amount"));
        this.transactionAmountField.setRequired(false);

        this.disbursedOnField.setLabel(Model.of("Disbursed On"));
        this.disbursedOnField.setRequired(false);

        paymentDetailFieldUpdate(null);
    }

    @Override
    protected void initData() {
        this.client = ClientEnum.valueOf(getPageParameters().get("client").toString());

        this.clientId = getPageParameters().get("clientId").toString();
        this.groupId = getPageParameters().get("groupId").toString();
        this.centerId = getPageParameters().get("centerId").toString();

        this.loanId = getPageParameters().get("loanId").toString();
        this.disbursedOnValue = DateTime.now().toDate();

        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);

        SelectQuery selectQuery = null;
        selectQuery = new SelectQuery(MLoan.NAME);
        selectQuery.addWhere(MLoan.Field.ID + " = '" + this.loanId + "'");
        selectQuery.addField(MLoan.Field.ACCOUNT_NO);
        selectQuery.addField(MLoan.Field.PRINCIPAL_AMOUNT);
        selectQuery.addField(MLoan.Field.LOAN_OFFICER_ID);
        Map<String, Object> loanObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
        this.loanAccountNo = (String) loanObject.get("account_no");

        if (this.client == ClientEnum.Client) {
            selectQuery = new SelectQuery(MClient.NAME);
            selectQuery.addWhere(MClient.Field.ID + " = :" + MClient.Field.ID, this.clientId);
            selectQuery.addField(MClient.Field.OFFICE_ID);
            selectQuery.addField(MClient.Field.DISPLAY_NAME);
            Map<String, Object> clientObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
            this.clientDisplayName = (String) clientObject.get("display_name");
        } else if (this.client == ClientEnum.Group) {
            selectQuery = new SelectQuery(MGroup.NAME);
            selectQuery.addWhere(MGroup.Field.ID + " = :" + MGroup.Field.ID, this.groupId);
            selectQuery.addField(MGroup.Field.OFFICE_ID);
            selectQuery.addField(MGroup.Field.DISPLAY_NAME);
            Map<String, Object> groupObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
            this.groupDisplayName = (String) groupObject.get("display_name");
        } else if (this.client == ClientEnum.Center) {
            selectQuery = new SelectQuery(MGroup.NAME);
            selectQuery.addWhere(MGroup.Field.ID + " = :" + MGroup.Field.ID, this.centerId);
            selectQuery.addField(MGroup.Field.OFFICE_ID);
            selectQuery.addField(MGroup.Field.DISPLAY_NAME);
            Map<String, Object> centerObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
            this.centerDisplayName = (String) centerObject.get("display_name");
        }

        this.transactionAmountValue = (Double) loanObject.get("principal_amount");

        this.paymentTypeProvider = new SingleChoiceProvider(MPaymentType.NAME, MPaymentType.Field.ID, MPaymentType.Field.VALUE);
    }

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            if (this.client == ClientEnum.Client) {
                breadcrumb.setLabel("Clients");
            } else if (this.client == ClientEnum.Group) {
                breadcrumb.setLabel("Groups");
            } else if (this.client == ClientEnum.Center) {
                breadcrumb.setLabel("Centers");
            }
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            if (this.client == ClientEnum.Client) {
                breadcrumb.setLabel("Clients");
                breadcrumb.setPage(ClientBrowsePage.class);
            } else if (this.client == ClientEnum.Group) {
                breadcrumb.setLabel("Groups");
                breadcrumb.setPage(GroupBrowsePage.class);
            } else if (this.client == ClientEnum.Center) {
                breadcrumb.setLabel("Centers");
                breadcrumb.setPage(CenterBrowsePage.class);
            }
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            PageParameters parameters = new PageParameters();
            if (this.client == ClientEnum.Client) {
                parameters.add("clientId", this.clientId);
                breadcrumb.setLabel(this.clientDisplayName);
                breadcrumb.setPage(ClientPreviewPage.class);
            } else if (this.client == ClientEnum.Group) {
                parameters.add("groupId", this.groupId);
                breadcrumb.setLabel(this.groupDisplayName);
                breadcrumb.setPage(GroupPreviewPage.class);
            } else if (this.client == ClientEnum.Center) {
                parameters.add("centerId", this.centerId);
                breadcrumb.setLabel(this.centerDisplayName);
                breadcrumb.setPage(CenterPreviewPage.class);
            }
            breadcrumb.setParameters(parameters);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            PageParameters parameters = new PageParameters();
            parameters.add("client", this.client.name());
            parameters.add("loanId", this.loanId);
            if (this.client == ClientEnum.Client) {
                parameters.add("clientId", this.clientId);
            } else if (this.client == ClientEnum.Group) {
                parameters.add("groupId", this.groupId);
            } else if (this.client == ClientEnum.Center) {
                parameters.add("centerId", this.centerId);
            }
            breadcrumb.setParameters(parameters);
            breadcrumb.setLabel(this.loanAccountNo);
            breadcrumb.setPage(LoanAccountPreviewPage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Disburse");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
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

        JsonNode node = ClientHelper.disburseLoanAccount((Session) getSession(), builder.build());

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
