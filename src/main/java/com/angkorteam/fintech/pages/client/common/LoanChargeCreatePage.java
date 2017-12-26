package com.angkorteam.fintech.pages.client.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.ClientEnum;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.enums.ChargeCalculation;
import com.angkorteam.fintech.dto.enums.ChargeTime;
import com.angkorteam.fintech.dto.enums.ChargeType;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.helper.loan.LoanChargeBuilder;
import com.angkorteam.fintech.pages.client.center.CenterPreviewPage;
import com.angkorteam.fintech.pages.client.client.ClientPreviewPage;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.ReadOnlyView;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
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
public class LoanChargeCreatePage extends Page {

    protected ClientEnum client;

    protected String clientId;
    protected String groupId;
    protected String centerId;

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

        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
        SelectQuery loanQuery = new SelectQuery("m_loan");
        loanQuery.addWhere("id = '" + this.loanId + "'");
        loanQuery.addField("currency_code");
        Map<String, Object> loanObject = jdbcTemplate.queryForMap(loanQuery.toSQL());

        this.currencyCode = (String) loanObject.get("currency_code");
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

        this.chargeProvider = new SingleChoiceProvider("m_charge", "id", "name");
        this.chargeProvider.applyWhere("currency_code", "currency_code = '" + this.currencyCode + "'");
        this.chargeProvider.applyWhere("is_active", "is_active = 1");
        this.chargeProvider.applyWhere("charge_applies_to_enum", "charge_applies_to_enum = '" + ChargeType.Loan.getLiteral() + "'");
        this.chargeProvider.applyWhere("charge_time_enum", "charge_time_enum in (" + StringUtils.join(charge_time_enum, ", ") + ")");
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
    protected void configureRequiredValidation() {
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
            JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
            Map<String, Object> chargeObject = jdbcTemplate.queryForMap("select charge_time_enum, charge_calculation_enum from m_charge where id = ?", this.chargeValue.getId());
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

        JsonNode node = null;
        try {
            node = ClientHelper.addChargeLoanAccount((Session) getSession(), builder.build());
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