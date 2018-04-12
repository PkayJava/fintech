package com.angkorteam.fintech.pages.client.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import com.angkorteam.fintech.widget.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.ddl.MCharge;
import com.angkorteam.fintech.ddl.MClient;
import com.angkorteam.fintech.ddl.MGroup;
import com.angkorteam.fintech.ddl.MLoan;
import com.angkorteam.fintech.dto.ClientEnum;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.loan.LoanChargeBuilder;
import com.angkorteam.fintech.dto.enums.ChargeCalculation;
import com.angkorteam.fintech.dto.enums.ChargeTime;
import com.angkorteam.fintech.dto.enums.ChargeType;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.pages.client.center.CenterBrowsePage;
import com.angkorteam.fintech.pages.client.center.CenterPreviewPage;
import com.angkorteam.fintech.pages.client.client.ClientBrowsePage;
import com.angkorteam.fintech.pages.client.client.ClientPreviewPage;
import com.angkorteam.fintech.pages.client.group.GroupBrowsePage;
import com.angkorteam.fintech.pages.client.group.GroupPreviewPage;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.ReadOnlyView;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
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
public class LoanChargeCreatePage extends Page {

    protected ClientEnum client;

    protected String clientId;
    protected String groupId;
    protected String centerId;

    protected String clientDisplayName;
    protected String groupDisplayName;
    protected String centerDisplayName;
    protected String loanAccountNo;

    protected String loanId;
    protected String currencyCode;

    protected SingleChoiceProvider chargeProvider;
    protected WebMarkupContainer chargeBlock;
    protected WebMarkupContainer chargeIContainer;
    protected Option chargeValue;
    protected Select2SingleChoice<Option> chargeField;
    protected TextFeedbackPanel chargeFeedback;

    protected WebMarkupContainer amountBlock;
    protected WebMarkupContainer amountIContainer;
    protected Double amountValue;
    protected TextField<Double> amountField;
    protected TextFeedbackPanel amountFeedback;

    protected WebMarkupBlock chargeCalculationBlock;
    protected WebMarkupContainer chargeCalculationVContainer;
    protected String chargeCalculationValue;
    protected ReadOnlyView chargeCalculationView;

    protected WebMarkupBlock chargeTimeBlock;
    protected WebMarkupContainer chargeTimeVContainer;
    protected String chargeTimeValue;
    protected ReadOnlyView chargeTimeView;

    protected WebMarkupContainer dueDateBlock;
    protected WebMarkupContainer dueDateIContainer;
    protected Date dueDateValue;
    protected DateTextField dueDateField;
    protected TextFeedbackPanel dueDateFeedback;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    @Override
    protected void initData() {
        this.client = ClientEnum.valueOf(getPageParameters().get("client").toString());

        this.clientId = getPageParameters().get("clientId").toString();
        this.groupId = getPageParameters().get("groupId").toString();
        this.centerId = getPageParameters().get("centerId").toString();

        this.loanId = getPageParameters().get("loanId").toString();

        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);

        SelectQuery selectQuery = null;

        selectQuery = new SelectQuery(MLoan.NAME);
        selectQuery.addWhere(MLoan.Field.ID + " = '" + this.loanId + "'");
        selectQuery.addField(MLoan.Field.CURRENCY_CODE);
        selectQuery.addField(MLoan.Field.ACCOUNT_NO);
        Map<String, Object> loanObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());

        this.currencyCode = (String) loanObject.get("currency_code");

        if (this.client == ClientEnum.Client) {
            selectQuery = new SelectQuery(MClient.NAME);
            selectQuery.addField(MClient.Field.OFFICE_ID);
            selectQuery.addField(MClient.Field.DISPLAY_NAME);
            selectQuery.addWhere(MClient.Field.ID + " = :" + MClient.Field.ID, this.clientId);
            Map<String, Object> clientObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
            this.clientDisplayName = (String) clientObject.get("display_name");
        } else if (this.client == ClientEnum.Group) {
            selectQuery = new SelectQuery(MGroup.NAME);
            selectQuery.addField(MGroup.Field.OFFICE_ID);
            selectQuery.addField(MGroup.Field.DISPLAY_NAME);
            selectQuery.addWhere(MGroup.Field.ID + " = :" + MGroup.Field.ID, this.groupId);
            Map<String, Object> groupObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
            this.groupDisplayName = (String) groupObject.get("display_name");
        } else if (this.client == ClientEnum.Center) {
            selectQuery = new SelectQuery(MGroup.NAME);
            selectQuery.addField(MGroup.Field.OFFICE_ID);
            selectQuery.addField(MGroup.Field.DISPLAY_NAME);
            selectQuery.addWhere(MGroup.Field.ID + " = :" + MGroup.Field.ID, this.centerId);
            Map<String, Object> centerObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
            this.centerDisplayName = (String) centerObject.get("display_name");
        }

        this.loanAccountNo = (String) loanObject.get("account_no");
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
            breadcrumb.setLabel("Add Charge");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

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

        List<String> charge_time_enum = new ArrayList<>();
        charge_time_enum.add("'" + ChargeTime.Disbursement.getLiteral() + "'");
        charge_time_enum.add("'" + ChargeTime.SpecifiedDueDate.getLiteral() + "'");
        charge_time_enum.add("'" + ChargeTime.InstallmentFee.getLiteral() + "'");
        charge_time_enum.add("'" + ChargeTime.TrancheDisbursement.getLiteral() + "'");

        this.chargeProvider = new SingleChoiceProvider(MCharge.NAME, MCharge.Field.ID, MCharge.Field.NAME);
        this.chargeProvider.applyWhere("currency_code", MCharge.Field.CURRENCY_CODE + " = '" + this.currencyCode + "'");
        this.chargeProvider.applyWhere("is_active", MCharge.Field.IS_ACTIVE + " = 1");
        this.chargeProvider.applyWhere("charge_applies_to_enum", MCharge.Field.CHARGE_APPLIES_TO_ENUM + " = '" + ChargeType.Loan.getLiteral() + "'");
        this.chargeProvider.applyWhere("charge_time_enum", MCharge.Field.CHARGE_TIME_ENUM + " IN (" + StringUtils.join(charge_time_enum, ", ") + ")");
        this.chargeBlock = new WebMarkupBlock("chargeBlock", Size.Six_6);
        this.form.add(this.chargeBlock);
        this.chargeIContainer = new WebMarkupContainer("chargeIContainer");
        this.chargeBlock.add(this.chargeIContainer);
        this.chargeField = new Select2SingleChoice<>("chargeField", new PropertyModel<>(this, "chargeValue"), this.chargeProvider);
        this.chargeField.setLabel(Model.of("Charge"));
        this.chargeField.setRequired(true);
        this.chargeField.add(new OnChangeAjaxBehavior(this::chargeFieldUpdate));
        this.chargeIContainer.add(this.chargeField);
        this.chargeFeedback = new TextFeedbackPanel("chargeFeedback", this.chargeField);
        this.chargeIContainer.add(this.chargeFeedback);

        this.amountBlock = new WebMarkupBlock("amountBlock", Size.Six_6);
        this.form.add(this.amountBlock);
        this.amountIContainer = new WebMarkupContainer("amountIContainer");
        this.amountBlock.add(this.amountIContainer);
        this.amountField = new TextField<>("amountField", new PropertyModel<>(this, "amountValue"));
        this.amountField.setLabel(Model.of("Amount"));
        this.amountField.add(new OnChangeAjaxBehavior());
        this.amountField.setRequired(false);
        this.amountIContainer.add(this.amountField);
        this.amountFeedback = new TextFeedbackPanel("amountFeedback", this.amountField);
        this.amountIContainer.add(this.amountFeedback);

        this.chargeCalculationBlock = new WebMarkupBlock("chargeCalculationBlock", Size.Six_6);
        this.form.add(this.chargeCalculationBlock);
        this.chargeCalculationVContainer = new WebMarkupContainer("chargeCalculationVContainer");
        this.chargeCalculationBlock.add(this.chargeCalculationVContainer);
        this.chargeCalculationView = new ReadOnlyView("chargeCalculationView", new PropertyModel<>(this, "chargeCalculationValue"));
        this.chargeCalculationVContainer.add(this.chargeCalculationView);

        this.chargeTimeBlock = new WebMarkupBlock("chargeTimeBlock", Size.Six_6);
        this.form.add(this.chargeTimeBlock);
        this.chargeTimeVContainer = new WebMarkupContainer("chargeTimeVContainer");
        this.chargeTimeBlock.add(this.chargeTimeVContainer);
        this.chargeTimeView = new ReadOnlyView("chargeTimeView", new PropertyModel<>(this, "chargeTimeValue"));
        this.chargeTimeVContainer.add(this.chargeTimeView);

        this.dueDateBlock = new WebMarkupBlock("dueDateBlock", Size.Six_6);
        this.form.add(this.dueDateBlock);
        this.dueDateIContainer = new WebMarkupContainer("dueDateIContainer");
        this.dueDateBlock.add(this.dueDateIContainer);
        this.dueDateField = new DateTextField("dueDateField", new PropertyModel<>(this, "dueDateValue"));
        this.dueDateField.add(new OnChangeAjaxBehavior());
        this.dueDateField.setLabel(Model.of("Due Date"));
        this.dueDateField.setRequired(false);
        this.dueDateIContainer.add(this.dueDateField);
        this.dueDateFeedback = new TextFeedbackPanel("dueDateFeedback", this.dueDateField);
        this.dueDateIContainer.add(this.dueDateFeedback);

    }

    @Override
    protected void configureMetaData() {
        chargeFieldUpdate(null);
    }

    protected boolean chargeFieldUpdate(AjaxRequestTarget target) {
        this.amountIContainer.setVisible(false);
        this.chargeCalculationVContainer.setVisible(false);
        this.chargeTimeVContainer.setVisible(false);
        this.dueDateIContainer.setVisible(false);

        if (this.chargeValue != null) {
            JdbcNamed named = SpringBean.getBean(JdbcNamed.class);

            SelectQuery selectQuery = null;

            selectQuery = new SelectQuery(MCharge.NAME);
            selectQuery.addField(MCharge.Field.CHARGE_TIME_ENUM);
            selectQuery.addField(MCharge.Field.CHARGE_CALCULATION_ENUM);
            selectQuery.addWhere(MCharge.Field.ID + " = :" + MCharge.Field.ID, this.chargeValue.getId());
            Map<String, Object> chargeObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
            ChargeTime chargeTime = ChargeTime.parseLiteral(String.valueOf(chargeObject.get("charge_time_enum")));
            ChargeCalculation chargeCalculation = ChargeCalculation.parseLiteral(String.valueOf(chargeObject.get("charge_calculation_enum")));
            this.chargeCalculationValue = chargeCalculation.getDescription();
            this.chargeTimeValue = chargeTime.getDescription();
            if (chargeTime == ChargeTime.Disbursement || chargeTime == ChargeTime.InstallmentFee || chargeTime == ChargeTime.TrancheDisbursement) {
                this.amountIContainer.setVisible(true);
                this.chargeCalculationVContainer.setVisible(true);
                this.chargeTimeVContainer.setVisible(true);
                this.dueDateIContainer.setVisible(false);
            } else if (chargeTime == ChargeTime.SpecifiedDueDate) {
                this.amountIContainer.setVisible(true);
                this.chargeCalculationVContainer.setVisible(true);
                this.chargeTimeVContainer.setVisible(true);
                this.dueDateIContainer.setVisible(true);
            }
        }

        if (target != null) {
            target.add(this.amountBlock);
            target.add(this.chargeCalculationBlock);
            target.add(this.chargeTimeBlock);
            target.add(this.dueDateBlock);
        }
        return false;
    }

    protected void saveButtonSubmit(Button button) {
        LoanChargeBuilder builder = new LoanChargeBuilder();
        builder.withLoanId(this.loanId);
        builder.withAmount(this.amountValue);
        if (this.chargeValue != null) {
            builder.withChargeId(this.chargeValue.getId());
            ChargeTime chargeTime = ChargeTime.parseLiteral(this.chargeValue.getId());
            if (chargeTime == ChargeTime.SpecifiedDueDate) {
                builder.withDueDate(this.dueDateValue);
            }
        }

        JsonNode node = ClientHelper.addChargeLoanAccount((Session) getSession(), builder.build());

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