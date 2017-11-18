package com.angkorteam.fintech.pages.client.client;

import java.util.Date;
import java.util.Map;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.builder.client.client.ClientChargeBuilder;
import com.angkorteam.fintech.dto.enums.ChargeCalculation;
import com.angkorteam.fintech.dto.enums.ChargeTime;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.widget.ReadOnlyView;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ChargeCreatePage extends Page {

    private String clientId;
    private String chargeId;

    protected Form<Void> form;
    protected Button saveButton;

    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock chargeBlock;
    protected WebMarkupContainer chargeVContainer;
    protected String chargeValue;
    protected ReadOnlyView chargeView;

    protected WebMarkupBlock chargeCalculationBlock;
    protected WebMarkupContainer chargeCalculationVContainer;
    protected Option chargeCalculationValue;
    protected ReadOnlyView chargeCalculationView;

    protected WebMarkupBlock chargeTypeBlock;
    protected WebMarkupContainer chargeTypeVContainer;
    protected Option chargeTypeValue;
    protected ReadOnlyView chargeTypeView;

    protected WebMarkupBlock collectOnBlock;
    protected WebMarkupContainer collectOnIContainer;
    protected Date collectOnValue;
    protected DateTextField collectOnField;
    protected TextFeedbackPanel collectOnFeedback;

    protected WebMarkupBlock amountBlock;
    protected WebMarkupContainer amountIContainer;
    protected Double amountValue;
    protected TextField<Double> amountField;
    protected TextFeedbackPanel amountFeedback;

    @Override
    protected void initData() {
        this.clientId = getPageParameters().get("clientId").toString();
        this.chargeId = getPageParameters().get("chargeId").toString();

        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);

        Map<String, Object> chargeObject = jdbcTemplate.queryForMap("select * from m_charge where id = ?", this.chargeId);

        this.chargeTypeValue = ChargeTime.optionLiteral(String.valueOf(chargeObject.get("charge_time_enum")));
        this.chargeCalculationValue = ChargeCalculation.optionLiteral(String.valueOf(chargeObject.get("charge_calculation_enum")));
        this.chargeValue = (String) chargeObject.get("name");
        this.amountValue = (Double) chargeObject.get("amount");
    }

    @Override
    protected void initComponent() {
        PageParameters parameters = new PageParameters();
        parameters.add("clientId", this.clientId);

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", ClientPreviewPage.class, parameters);
        this.form.add(this.closeLink);

        initChargeBlock();

        initChargeCalculationBlock();

        initChargeTypeBlock();

        initAmountBlock();

        initCollectOnBlock();
    }

    protected void initCollectOnBlock() {
        this.collectOnBlock = new WebMarkupBlock("collectOnBlock", Size.Six_6);
        this.form.add(this.collectOnBlock);
        this.collectOnIContainer = new WebMarkupContainer("collectOnIContainer");
        this.collectOnBlock.add(this.collectOnIContainer);
        this.collectOnField = new DateTextField("collectOnField", new PropertyModel<>(this, "collectOnValue"));
        this.collectOnField.setLabel(Model.of("Collect On"));
        this.collectOnIContainer.add(this.collectOnField);
        this.collectOnFeedback = new TextFeedbackPanel("collectOnFeedback", this.collectOnField);
        this.collectOnIContainer.add(this.collectOnFeedback);
    }

    protected void initAmountBlock() {
        this.amountBlock = new WebMarkupBlock("amountBlock", Size.Six_6);
        this.form.add(this.amountBlock);
        this.amountIContainer = new WebMarkupContainer("amountIContainer");
        this.amountBlock.add(this.amountIContainer);
        this.amountField = new TextField<>("amountField", new PropertyModel<>(this, "amountValue"));
        this.amountField.setLabel(Model.of("Amount"));
        this.amountIContainer.add(this.amountField);
        this.amountFeedback = new TextFeedbackPanel("amountFeedback", this.amountField);
        this.amountIContainer.add(this.amountFeedback);
    }

    protected void initChargeTypeBlock() {
        this.chargeTypeBlock = new WebMarkupBlock("chargeTypeBlock", Size.Six_6);
        this.form.add(this.chargeTypeBlock);
        this.chargeTypeVContainer = new WebMarkupContainer("chargeTypeVContainer");
        this.chargeTypeBlock.add(this.chargeTypeVContainer);
        this.chargeTypeView = new ReadOnlyView("chargeTypeView", new PropertyModel<>(this, "chargeTypeValue"));
        this.chargeTypeVContainer.add(this.chargeTypeView);
    }

    protected void initChargeCalculationBlock() {
        this.chargeCalculationBlock = new WebMarkupBlock("chargeCalculationBlock", Size.Six_6);
        this.form.add(this.chargeCalculationBlock);
        this.chargeCalculationVContainer = new WebMarkupContainer("chargeCalculationVContainer");
        this.chargeCalculationBlock.add(this.chargeCalculationVContainer);
        this.chargeCalculationView = new ReadOnlyView("chargeCalculationView", new PropertyModel<>(this, "chargeCalculationValue"));
        this.chargeCalculationVContainer.add(this.chargeCalculationView);
    }

    protected void initChargeBlock() {
        this.chargeBlock = new WebMarkupBlock("chargeBlock", Size.Six_6);
        this.form.add(this.chargeBlock);
        this.chargeVContainer = new WebMarkupContainer("chargeVContainer");
        this.chargeBlock.add(this.chargeVContainer);
        this.chargeView = new ReadOnlyView("chargeView", new PropertyModel<>(this, "chargeValue"));
        this.chargeVContainer.add(this.chargeView);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    protected void saveButtonSubmit(Button button) {
        ClientChargeBuilder builder = new ClientChargeBuilder();
        builder.withClientId(this.clientId);
        builder.withAmount(this.amountValue);
        builder.withDueDate(this.collectOnValue);
        builder.withChargeId(this.chargeId);

        JsonNode node = null;
        try {
            node = ClientHelper.postClientCharge((Session) getSession(), builder.build());
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
